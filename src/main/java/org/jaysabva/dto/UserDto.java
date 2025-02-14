package org.jaysabva.dto;

import java.util.Map;

public class UserDto {
    private String username;
    private String firstName;
    private String lastName;
    private String phoneno;
    private String password;
    private String role;
    private Map<String, Object> dynamicField;
    public UserDto() {
    }

    public UserDto(String username, String firstName, String lastName, String phoneno, String password, String role, Map<String, Object> dynamicField) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneno = phoneno;
        this.password = password;
        this.role = role;
        this.dynamicField = dynamicField;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Map<String, Object> getDynamicField() {
        return dynamicField;
    }

    public void setDynamicField(Map<String, Object> dynamicField) {
        this.dynamicField = dynamicField;
    }
}
