package org.jaysabva;

public class User {
    private Long UserId;
    private String UserName;
    private String Password;
    private String Role;

    public User() {
    }

    public User(Long UserId, String UserName, String Password, String Role) {
        this.UserId = UserId;
        this.UserName = UserName;
        this.Password = Password;
        this.Role = Role;
    }

    public Long getUserId() {
        return UserId;
    }

    public void setUserId(Long UserId) {
        this.UserId = UserId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String Role) {
        this.Role = Role;
    }
}
