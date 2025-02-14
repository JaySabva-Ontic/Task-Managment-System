package org.jaysabva.entity;

import java.time.LocalDateTime;
import java.util.*;

public enum UserDataType {
    TEXT(String.class),
    NUMBER(Double.class),
    DATE(LocalDateTime.class),
    LIST(ArrayList.class),
    HASH_MAP(LinkedHashMap.class);

    private final Class<?> classType;

    UserDataType(Class<?> classType) {
        this.classType = classType;
    }

    public Class<?> getClassType() {
        return classType;
    }
}
