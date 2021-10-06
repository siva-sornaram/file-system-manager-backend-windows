package com.siva;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {
    private int userId;
    private String userName;
    private String password;
    private boolean isValidated;
    private int roleId;
    private String role;
    public User() {}
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public User(String userName, boolean isValidated, int roleId) {
        this.userName = userName;
        this.isValidated = isValidated;
        this.roleId = roleId;
    }
    public int getRoleId() {
        return roleId;
    }
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
    public int getUserId() {
        return userId;
    }
    public boolean isValidated() {
        return isValidated;
    }
    public void setValidated(boolean isValidated) {
        this.isValidated = isValidated;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
}
