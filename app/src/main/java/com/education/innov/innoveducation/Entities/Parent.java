package com.education.innov.innoveducation.Entities;

import java.util.ArrayList;

/**
 * Created by maher on 11/04/2017.
 */

public class Parent extends User{

    private ArrayList<User> children; // only set if function equal student;
    private String type;
    public Parent(){
        super("NONE","NONE","NONE","NONE","NONE","NONE" ,"NONE","NONE","NONE","NONE","NONE","NONE");

    }
    public Parent(String sex, String firstName, String lastName, String phone, String adresse, String urlImage, String active, String codePostal, String contry, String connected,  String id, ArrayList<User> children, String type,String birthday) {
        super( sex ,firstName, lastName,  phone, adresse, urlImage, active, codePostal, contry, connected, id,birthday);
        this.children = children;
        this.type = type;
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

    @Override
    public String toString() {
        return "Parent{" +
                "type='" + type + '\'' +
                '}';
    }
}
