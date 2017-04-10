package com.education.innov.innoveducation.Entities;

/**
 * Created by maher on 10/04/2017.
 */

public class Comments {

    private String body;
    private String date;
    private User User;
    private String id;

    public Comments() {
    }

    public Comments(String body, String date, com.education.innov.innoveducation.Entities.User user, String id) {
        this.body = body;
        this.date = date;
        User = user;
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public com.education.innov.innoveducation.Entities.User getUser() {
        return User;
    }

    public void setUser(com.education.innov.innoveducation.Entities.User user) {
        User = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
