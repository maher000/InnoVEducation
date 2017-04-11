package com.education.innov.innoveducation.Entities;

import java.util.ArrayList;

/**
 * Created by maher on 11/04/2017.
 */

public class User {
    protected String email;
    protected String firstName;
    protected String lastName;
    protected String password;
    protected String phone;
    protected String adresse;
    protected String urlImage;
    protected String active; // true or false to indicate if the acount is active or not
    protected String codePostal;
    protected String contry;
    // do not add to firbase
    protected String connected; // boolean to indicate if is the user online or not
    protected String lastConnection; // timeStamp
    protected String id;

    public User(String email, String firstName, String lastName, String password, String phone, String adresse, String urlImage, String active, String codePostal, String contry, String connected, String lastConnection, String id) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phone = phone;
        this.adresse = adresse;
        this.urlImage = urlImage;
        this.active = active;
        this.codePostal = codePostal;
        this.contry = contry;
        this.connected = connected;
        this.lastConnection = lastConnection;
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", adresse='" + adresse + '\'' +
                ", urlImage='" + urlImage + '\'' +
                ", active='" + active + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", contry='" + contry + '\'' +
                ", connected='" + connected + '\'' +
                ", lastConnection='" + lastConnection + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
