package org.jaysabva.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.Document;
import org.jaysabva.util.IgnoreMongo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.stereotype.Component;

import javax.print.Doc;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.*;

//@Configuration
@Component
public class TMSMongoListener extends AbstractMongoEventListener<Object> {

    @Autowired
    private ObjectMapper objectMapper;

//    @Autowired
//    private AksConverter aksConverter;
//
//    @Override
//    public String getDatabaseName() {
//        return "TMS";
//    }
//
//    @Override
//    protected void configureConverters(MongoConverterConfigurationAdapter adapter) {
//        adapter.registerConverter(aksConverter);
//    }

//    @Override
//    public void onBeforeConvert(BeforeConvertEvent<Object> source) {
//        Object entity = objectMapper.convertValue(source.getSource(), source.getSource().getClass());
//
//        Class<?> entityClass = entity.getClass();
//        do {
//            for (Field field : entityClass.getDeclaredFields()) {
//                if (field.isAnnotationPresent(CustomDB.class)) {
//                    try {
//                        field.setAccessible(true);
//                        field.set(entity, null);
//                    } catch (IllegalAccessException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            }
//            entityClass = entityClass.getSuperclass();
//        } while (entityClass != null);
//
//        BeforeConvertEvent<Object> event = new BeforeConvertEvent<>(entity, Objects.requireNonNull(source.getCollectionName()));
//        super.onBeforeConvert(event);
//    }

    @Override
    public void onBeforeSave(BeforeSaveEvent<Object> event) {
        Object original = event.getSource();
        Document convertedDoc = event.getDocument();

//        Class<?> entityClass = original.getClass();
//        do {
//            for (Field field : entityClass.getDeclaredFields()) {
//
//                if (field.isAnnotationPresent(CustomDB.class)) {
//                    if (convertedDoc.containsKey(field.getName())) {
//                        convertedDoc.remove(field.getName());
//                    }
//                }
//            }
//            entityClass = entityClass.getSuperclass();
//        } while (entityClass != null);

//        processFields(original, convertedDoc);
        super.onBeforeSave(event);
    }

    private void processFields(Object original, Document convertedDoc) {

        if (original == null || !isNestedObject(original.getClass())) {
            return;
        }

        Class<?> entityClass = original.getClass();

        if (original instanceof Iterable<?>) {
            Iterable<?> list = (Iterable<?>) original;
            Iterable<?> docList = (Iterable<?>) convertedDoc;
            Iterator<?> docListIterator = docList.iterator();
            for (Object obj : list) {

                if (isNestedObject(obj.getClass())) {
                    Document nestedDoc = (Document) docListIterator.next();
                    processFields(obj, nestedDoc);
                } else {
                    processFields(obj, convertedDoc);
                    docListIterator.next();
                }

                if (!docListIterator.hasNext())
                    break;
            }
        } else if (original instanceof Map<?,?>) {
            Map<?,?> map = (Map<?,?>) original;
            Map<?,?> docMap = convertedDoc;

            for (Map.Entry<?, ?> entry : map.entrySet()) {
                if (isNestedObject(entry.getValue().getClass())) {
                    if (docMap.get(entry.getValue()).getClass().equals(ArrayList.class)) {
                        Iterable<?> docList = (Iterable<?>) docMap.get(entry.getKey());
                        Iterable<?> list = (Iterable<?>) entry.getValue();

                        Iterator<?> docListIterator = docList.iterator();
                        for (Object obj : list) {

                            if (isNestedObject(obj.getClass())) {
                                Document nestedDoc = (Document) docListIterator.next();
                                processFields(obj, nestedDoc);
                            } else {
                                processFields(obj, convertedDoc);
                                docListIterator.next();
                            }

                            if (!docListIterator.hasNext())
                                break;
                        }
                    } else {
                        Document nestedDoc = (Document) docMap.get(entry.getKey());
                        processFields(entry.getValue(), nestedDoc);
                    }
                } else {
                    processFields(entry.getValue(), convertedDoc);
                }
            }
        } else {
            do {
                for (Field field : entityClass.getDeclaredFields()) {
                    field.setAccessible(true);

                    if (field.isAnnotationPresent(IgnoreMongo.class)) {
                        if (convertedDoc.containsKey(field.getName())) {
                            convertedDoc.remove(field.getName());
                            continue;
                        }
                    }

                    try {
                        Object fieldValue = field.get(original);

                        if (fieldValue != null && isNestedField(field)) {
                            if (fieldValue instanceof Iterable<?>) {
                                Iterable<?> list = (Iterable<?>) fieldValue;
                                Iterable<?> docList = (Iterable<?>) convertedDoc.get(field.getName(), Iterable.class);

                                Iterator<?> docListIterator = docList.iterator();
                                for (Object obj : list) {

                                    if (isNestedObject(obj.getClass())) {
                                        Document nestedDoc = (Document) docListIterator.next();
                                        processFields(obj, nestedDoc);
                                    } else {
                                        processFields(obj, convertedDoc);
                                        docListIterator.next();
                                    }

                                    if (!docListIterator.hasNext())
                                        break;

                                }
                            } else if (fieldValue instanceof Map<?, ?>) {
                                Map<?, ?> map = (Map<?, ?>) fieldValue;
                                Map<?, ?> docMap = (Map<?, ?>) convertedDoc.get(field.getName(), Map.class);

                                for (Map.Entry<?, ?> entry : map.entrySet()) {
                                    if (isNestedObject(entry.getValue().getClass())) {
                                        Document nestedDoc = (Document) docMap.get(entry.getKey());
                                        processFields(entry.getValue(), nestedDoc);
                                    } else {
                                        processFields(entry.getValue(), convertedDoc);
                                    }
                                }
                            } else {
                                Document nestedDoc = convertedDoc.get(field.getName(), Document.class);
                                processFields(fieldValue, nestedDoc);
                                convertedDoc.put(field.getName(), nestedDoc);
                            }
                        }

                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

                entityClass = entityClass.getSuperclass();
            } while (entityClass != null);
        }
    }

    private boolean isNestedField(Field field) {
        return !field.getType().isPrimitive()
                && !String.class.equals(field.getType())
                && !Number.class.isAssignableFrom(field.getType())
                && !field.getType().isEnum()
                && !field.getType().equals(LocalDateTime.class)
                && !field.getType().equals(Boolean.class);
    }
    private boolean isNestedObject(Class<?> clazz) {
        return !clazz.isPrimitive()
                && !String.class.equals(clazz)
                && !Number.class.isAssignableFrom(clazz)
                && !clazz.isEnum()
                && !LocalDateTime.class.equals(clazz)
                && !Boolean.class.equals(clazz);
    }
}