package com.education.innov.innoveducation.Entities;

import java.util.ArrayList;

/**
 * Created by maher on 11/04/2017.
 */

public class Teacher extends User{


    private String classRommId;
    private ClassRoom classeRoom; // only set if function equal teacher

public Teacher(){
    super("hell","syrine","dridi","123456","55280536","Rue Palestine" ,"hhhh","hhhh","12345","coucou",
            "cc");

}
    public Teacher(String sex ,  String firstName, String lastName, String phone, String adresse, String urlImage, String active, String codePostal, String contry, String connected,  String id, String classRommId, ClassRoom classeRoom) {
        super(sex,firstName, lastName, phone, adresse, urlImage, active, codePostal, contry, connected, id);
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

    @Override
    public String toString() {
        return "Teacher{" +
                "classRommId='" + classRommId + '\'' +
                '}';
    }
}
