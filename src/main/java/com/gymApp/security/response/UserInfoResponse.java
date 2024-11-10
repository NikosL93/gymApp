package com.gymApp.security.response;

import java.util.List;

public class UserInfoResponse {
    private Long userId;
    private String jwtToken;
    private String username;
    private List<String> roles;

    public UserInfoResponse(Long userId, String username, List<String> roles, String jwtToken) {
        this.userId = userId;
        this.username = username;
        this.roles = roles;
        this.jwtToken = jwtToken;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}


