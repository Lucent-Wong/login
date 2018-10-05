package com.primedice.common.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public enum RoleEnum {
    ROOT("root", 1);

    private String name;
    private int index;
    private final static Map<String, RoleEnum> roleMap;

    static {
        roleMap = Collections.unmodifiableMap(
                Arrays.stream(RoleEnum.values())
                        .collect(Collectors.toMap(k -> k.getName().toLowerCase(), v -> v)));
    }

    RoleEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public static RoleEnum get(String name) {
        if (name == null) {
            return null;
        }
        return roleMap.get(name.toLowerCase());
    }
}
