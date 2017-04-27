package com.education.innov.innoveducation.Entities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by maher on 11/04/2017.
 */

public class Teacher extends User  implements Serializable{


    private ArrayList<String> classRooms;
    private ClassRoom classeRoom; // only set if function equal teacher


    public Teacher(){
        super("NONE","NONE","NONE","NONE","NONE","NONE" ,"NONE","NONE","NONE","NONE",
                "NONE","NONE","NONE");

    }
    public Teacher(String sex ,  String firstName, String lastName, String phone, String adresse, String urlImage, String active, String codePostal, String contry, String connected,  String id, ArrayList<String> classRooms, ClassRoom classeRoom,String birthday,String city) {
        super(sex,firstName, lastName, phone, adresse, urlImage, active, codePostal, contry, connected, id,birthday,city);
        this.classRooms = classRooms;
        this.classeRoom = classeRoom;
    }

    public ArrayList<String> getClassRooms() {
        return classRooms;
    }

    public void setClassRooms(ArrayList<String> classRooms) {
        this.classRooms = classRooms;
    }

    public ArrayList<String> getClassRommId() {
        return classRooms;
    }

    public void setClassRommId(ArrayList<String> classRooms) {
        this.classRooms = classRooms;
    }

    public ClassRoom getClasseRoom() {
        return classeRoom;
    }

    public void setClasseRoom(ClassRoom classeRoom) {
        this.classeRoom = classeRoom;
    }

    public String getCity() {
        return city;
    }


    @Override
    public String toString() {
        return "Teacher{" +
                "classRooms='" + classRooms + '\'' +
                super.toString()+
                '}';
    }
}
