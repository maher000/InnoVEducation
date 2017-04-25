package com.education.innov.innoveducation.Entities;

/**
 * Created by Syrine on 24/04/2017.
 */

public class Presence {

    private String id ;
    private String Lastname ;
    private String Firstname ;
    private String urlImageUser ;
    private String connected ;
    private String Role ;


    public Presence() {
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }



    public Presence(String id, String lastname,String firstname, String urlImageUser, String connected,String role) {
        this.id = id;
        this.Lastname = lastname;
        this.Firstname = firstname ;
        this.urlImageUser = urlImageUser;
        this.connected = connected;
        this.Role = role ;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getUrlImageUser() {
        return urlImageUser;
    }

    public void setUrlImageUser(String urlImageUser) {
        this.urlImageUser = urlImageUser;
    }

    public String getConnected() {
        return connected;
    }

    public void setConnected(String connected) {
        this.connected = connected;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    @Override
    public String toString() {
        return "Presence{" +
                "id='" + id + '\'' +
                ", Lastname='" + Lastname + '\'' +
                ", Firstname='" + Firstname + '\'' +
                ", urlImageUser='" + urlImageUser + '\'' +
                ", connected='" + connected + '\'' +
                ", Role='" + Role + '\'' +
                '}';
    }
}
