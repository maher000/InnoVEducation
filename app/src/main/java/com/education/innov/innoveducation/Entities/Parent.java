package com.education.innov.innoveducation.Entities;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by maher on 11/04/2017.
 */

public class Parent extends User {

    private ArrayList<User> children; // only set if function equal student;
    private String type;
    private HashMap<String,String> classRooms;
    private HashMap<String,String> topics;
    private String active_class_room;

    public Parent() {
        super("NONE", "NONE", "NONE", "NONE", "NONE", "NONE", "NONE", "NONE", "NONE", "NONE", "NONE", "NONE", "NONE");

    }

    public Parent(String sex, String firstName, String lastName, String phone, String adresse, String urlImage, String active, String codePostal, String contry, String connected, String id, ArrayList<User> children, String type, String birthday, String city) {
        super(sex, firstName, lastName, phone, adresse, urlImage, active, codePostal, contry, connected, id, birthday, city);
        this.children = children;
        this.type = type;
    }

    public HashMap<String, String> getTopics() {
        return topics;
    }

    public void setTopics(HashMap<String, String> topics) {
        this.topics = topics;
    }

    public String getActive_class_room() {
        return active_class_room;
    }

    public void setActive_class_room(String active_class_room) {
        this.active_class_room = active_class_room;
    }

    public ArrayList<User> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<User> children) {
        this.children = children;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public HashMap<String, String> getClassRooms() {
        return classRooms;
    }

    public void setClassRooms(HashMap<String, String> classRooms) {
        this.classRooms = classRooms;
    }

    @Override
    public String toString() {
        return "Parent{" +
                "type='" + type + '\'' +
                super.toString() +
                '}';
    }
}
