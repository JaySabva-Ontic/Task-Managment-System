//package org.jaysabva.converter;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.bson.Document;
//import org.jaysabva.entity.Task;
//import org.jaysabva.util.CustomDB;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.data.convert.WritingConverter;
//import org.springframework.data.mongodb.core.convert.MongoConverter;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Field;
//
//@WritingConverter
//@Component
//public class AksConverter implements MongoConverter {
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
////    @Override
////    public Document convert(Task source) {
////
////        for (Field field : source.getClass().getDeclaredFields()) {
////            if (field.isAnnotationPresent(CustomDB.class)) {
////                try {
////                    field.set(source, null);
////                } catch (IllegalAccessException e) {
////                    throw new RuntimeException(e);
////                }
////            }
////        }
////
////        try {
////            return Document.parse(objectMapper.writeValueAsString(source));
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////
////        return null;
////    }
//}
