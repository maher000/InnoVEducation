package com.education.innov.innoveducation.Entities;

import java.util.ArrayList;

/**
 * Created by maher on 11/04/2017.
 */

public class Parent extends User{


    private ArrayList<User> children; // only set if function equal student;
    private String type;

    public Parent(String email, String firstName, String lastName, String password, String phone, String adresse, String urlImage, String active, String codePostal, String contry, String connected, String lastConnection, String id, ArrayList<User> children, String type) {
        super(email, firstName, lastName, password, phone, adresse, urlImage, active, codePostal, contry, connected, lastConnection, id);
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
}
