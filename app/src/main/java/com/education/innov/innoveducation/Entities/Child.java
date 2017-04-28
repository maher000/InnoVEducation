package com.education.innov.innoveducation.Entities;

import java.util.ArrayList;

/**
 * Created by maher on 11/04/2017.
 */

public class Child extends User {

    private String classRommId;
    private ClassRoom classeRoom; // only set if function equal teacher
    // do not add to firbase
    private ArrayList<Parent> parents; // only set if function equal student;
    private String parentId;

    public Child() {
        super("NONE" ,"NONE", "NONE", "NONE"," NONE", "NONE", "NONE", "NONE", "NONE", "false", "NONE","NONE","NONE");

    }
    public Child(String sex, String firstName, String lastName, String phone, String adresse, String urlImage, String active, String codePostal, String contry, String connected,  String id,String type,String classRommId,String birthday,String city) {
        super( sex ,firstName, lastName,  phone, adresse, urlImage, active, codePostal, contry, connected, id,birthday,city);
        this.classRommId = classRommId;
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

    public ArrayList<Parent> getParents() {
        return parents;
    }

    public void setParents(ArrayList<Parent> parents) {
        this.parents = parents;
    }

    public String getConnected() {
        return connected;
    }

    public void setConnected(String connected) {
        this.connected = connected;
    }


    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "Child{" +
                "classRommId='" + classRommId + '\'' +
                ", classeRoom=" + classeRoom +
                ", parents=" + parents +
                ", parentId='" + parentId + '\'' +
                super.toString()+
                '}';
    }
}
