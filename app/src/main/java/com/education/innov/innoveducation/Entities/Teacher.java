package com.education.innov.innoveducation.Entities;

import java.util.ArrayList;

/**
 * Created by maher on 11/04/2017.
 */

public class Teacher extends User{


    private String classRommId;
    private ClassRoom classeRoom; // only set if function equal teacher


    public Teacher(String email, String firstName, String lastName, String password, String phone, String adresse, String urlImage, String active, String codePostal, String contry, String connected, String lastConnection, String id, String classRommId, ClassRoom classeRoom) {
        super(email, firstName, lastName, password, phone, adresse, urlImage, active, codePostal, contry, connected, lastConnection, id);
        this.classRommId = classRommId;
        this.classeRoom = classeRoom;
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
}
