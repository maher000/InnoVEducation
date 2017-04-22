package com.education.innov.innoveducation.Entities;

import java.util.ArrayList;

/**
 * Created by maher on 11/04/2017.
 */

public class User {
    protected String firstName;
    protected String lastName;
    protected String phone;
    protected String adresse;
    protected String urlImage;
    protected String active; // true or false to indicate if the acount is active or not
    protected String codePostal;
    protected String contry;
    protected String sex ;
    // do not add to firbase
    protected String connected; // boolean to indicate if is the user online or not
    protected String idUser;


    public User() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getContry() {
        return contry;
    }

    public void setContry(String contry) {
        this.contry = contry;
    }

    public String getConnected() {
        return connected;
    }

    public void setConnected(String connected) {
        this.connected = connected;
    }


    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public User(String sex , String firstName, String lastName, String phone, String adresse, String urlImage, String active, String codePostal, String contry, String connected, String id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.adresse = adresse;
        this.urlImage = urlImage;
        this.active = active;
        this.codePostal = codePostal;
        this.contry = contry;
        this.connected = connected;
        this.sex = sex;
        this.idUser = id;
    }

    @Override
    public String toString() {
        return "User{" +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", adresse='" + adresse + '\'' +
                ", urlImage='" + urlImage + '\'' +
                ", active='" + active + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", contry='" + contry + '\'' +
                ", connected='" + connected + '\'' +
                ", id='" + idUser + '\'' +
                '}';
    }
}
