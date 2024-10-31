package org.planpal.eunm;

public enum UserType {
    USER("USER"),
    ADMIN("ADMIN");

    private final String name;

    UserType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
