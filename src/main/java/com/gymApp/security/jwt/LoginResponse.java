package com.gymApp.security.jwt;


import java.util.List;

public class LoginResponse {
    private String jwtToken;
    private Long userID;
    private String username;
    private String role;

    public LoginResponse(Long userID, String username, List<String> roles, String jwtToken) {
        this.username = username;
        this.jwtToken = jwtToken;
        this.userID = userID;
        this.role = roles.get(0);
    }

    public Long getUserID() { return userID; }

    public String getRole() { return role; }

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
}


