package com.education.innov.innoveducation.Entities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by maher on 11/04/2017.
 */

public class Teacher extends User  implements Serializable{


    private String classRommId;
    private ClassRoom classeRoom; // only set if function equal teacher

    private static Teacher userInstance = null;
    public static User getInstance()
    {
        if (userInstance == null)
        {
            userInstance = new Teacher();
        }
        return userInstance;
    }

public Teacher(){
    super("NONE","NONE","NONE","NONE","NONE","NONE" ,"NONE","NONE","NONE","NONE",
            "NONE","NONE");

}
    public Teacher(String sex ,  String firstName, String lastName, String phone, String adresse, String urlImage, String active, String codePostal, String contry, String connected,  String id, String classRommId, ClassRoom classeRoom,String birthday) {
        super(sex,firstName, lastName, phone, adresse, urlImage, active, codePostal, contry, connected, id,birthday);
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
                super.toString()+
                '}';
    }
}
