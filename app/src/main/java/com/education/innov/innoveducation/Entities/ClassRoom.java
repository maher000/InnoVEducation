package com.education.innov.innoveducation.Entities;

import java.util.ArrayList;

/**
 * Created by maher on 10/04/2017.
 */

public class ClassRoom {
    private String name;
    private String Country;
    private String adress;
    private User administrator;
    private ArrayList<User> Students;
    private ArrayList<User> teachers;
    private ArrayList<HomeWork> homeWorks;
    private ArrayList<Course> courses;
    private String creationDate;
    private String id;

    public ClassRoom() {}

    public ClassRoom(String id,String name, String country, String adress, User administrator, ArrayList<User> students, ArrayList<User> teachers, ArrayList<HomeWork> homeWorks, ArrayList<Course> courses, String creationDate) {
        this.name = name;
        Country = country;
        this.adress = adress;
        this.administrator = administrator;
        Students = students;
        this.teachers = teachers;
        this.homeWorks = homeWorks;
        this.courses = courses;
        this.creationDate = creationDate;
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public User getAdministrator() {
        return administrator;
    }

    public void setAdministrator(User administrator) {
        this.administrator = administrator;
    }

    public ArrayList<User> getStudents() {
        return Students;
    }

    public void setStudents(ArrayList<User> students) {
        Students = students;
    }

    public ArrayList<User> getTeachers() {
        return teachers;
    }

    public void setTeachers(ArrayList<User> teachers) {
        this.teachers = teachers;
    }

    public ArrayList<HomeWork> getHomeWorks() {
        return homeWorks;
    }

    public void setHomeWorks(ArrayList<HomeWork> homeWorks) {
        this.homeWorks = homeWorks;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
