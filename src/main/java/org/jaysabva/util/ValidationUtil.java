package org.jaysabva.util;

import org.jaysabva.entity.UserDataType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ValidationUtil {
    public static boolean isNumber(Class<?> clazz, Object type) {
        return (type instanceof Number) && (clazz == Integer.class || clazz == Long.class || clazz == Float.class || clazz == Double.class);
    }

    public static boolean isList(Class<?> clazz, Object type) {
        return (type instanceof List) && (clazz == List.class || clazz == ArrayList.class);
    }

    public static boolean isDate(Class<?> clazz, String inDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            LocalDateTime.parse(inDate.trim(), formatter);
        } catch (Exception pe) {
            return false;
        }

        return clazz == UserDataType.DATE.getClassType();
    }

}
