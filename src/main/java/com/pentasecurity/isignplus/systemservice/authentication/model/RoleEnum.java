package com.pentasecurity.isignplus.systemservice.authentication.model;

public enum RoleEnum {
    BASIC("BASIC"),
    ADMIN("ADMIN");

    public static class ROLES {
        public static final String BASIC = "BASIC";

        public static final String ADMIN = "ADMIN";
    }

    private final String roleName;

    RoleEnum(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString () {
        return roleName;
    }
}
