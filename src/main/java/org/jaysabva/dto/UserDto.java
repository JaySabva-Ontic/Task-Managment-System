package org.jaysabva.dto;

public class UserDto {
    private String username;
    private String phoneno;
    private String password;
    private String role;

    public UserDto() {
    }

    public UserDto(String username, String phoneno, String password, String role) {
        this.username = username;
        this.phoneno = phoneno;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
