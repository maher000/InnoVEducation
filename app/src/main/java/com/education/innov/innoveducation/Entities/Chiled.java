package com.education.innov.innoveducation.Entities;

import java.util.ArrayList;

/**
 * Created by maher on 11/04/2017.
 */

public class Chiled {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String phone;
    private String adresse;
    private String urlImage;
    private String active; // true or false to indicate if the acount is active or not
    private String codePostal;
    private String contry;
    private String classRommId;
    private ClassRoom classeRoom; // only set if function equal teacher
    // do not add to firbase
    private ArrayList<User> parents; // only set if function equal student;
    private String connected; // boolean to indicate if is the user online or not
    private String lastConnection; // timeStamp
    private String id;

    public Chiled() {
    }

    public Chiled(String email, String firstName, String lastName, String password, String phone, String adresse, String urlImage, String active, String codePostal, String contry, String classRommId, ClassRoom classeRoom, ArrayList<User> parents, String connected, String lastConnection, String id) {

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
        this.classRommId = classRommId;
        this.classeRoom = classeRoom;
        this.parents = parents;
        this.connected = connected;
        this.lastConnection = lastConnection;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getClassRommId() {
        return classRommId;
    }

    public void setClassRommId(String classRommId) {
        this.classRommId = classRommId;
    }

    public ClassRoom getClasseRoom() {
        return classeRoom;
    }

    public void setClasseRoom(ClassRoom classeRoom) {
        this.classeRoom = classeRoom;
    }

    public ArrayList<User> getParents() {
        return parents;
    }

    public void setParents(ArrayList<User> parents) {
        this.parents = parents;
    }

    public String getConnected() {
        return connected;
    }

    public void setConnected(String connected) {
        this.connected = connected;
    }

    public String getLastConnection() {
        return lastConnection;
    }

    public void setLastConnection(String lastConnection) {
        this.lastConnection = lastConnection;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
