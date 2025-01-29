package org.jaysabva.entity;

import java.util.concurrent.atomic.AtomicLong;

public class User {
    private static final AtomicLong id = new AtomicLong(1);
    private Long userId;
    private String userName;
    private String password;
    private String role;

    public User() {
    }

    public User(String userName, String password, String role) {
        this.userId = id.longValue();
        this.userName = userName;
        this.password = password;
        this.role = role;

        incrId();
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    private static void incrId() {
        id.incrementAndGet();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
