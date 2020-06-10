package com.socialcodia.restapi.model;

public class LoginResponse {
    private String message;
    private Boolean error;
    private User user;

    public LoginResponse(String message, Boolean error, User user) {
        this.message = message;
        this.error = error;
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
