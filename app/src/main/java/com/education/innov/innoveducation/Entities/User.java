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
    protected String birthday;
    protected String city;
    protected String token;
    protected String topic;


    public User() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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

    public User(String sex , String firstName, String lastName, String phone, String adresse, String urlImage, String active, String codePostal, String contry, String connected, String id,String birthday,String city) {
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
        this.birthday=birthday;
        this.city=city;
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
                ", city='" + city + '\'' +
                ", birthday='" + birthday + '\'' +
                ", id='" + idUser + '\'' +
                '}';
    }
}
