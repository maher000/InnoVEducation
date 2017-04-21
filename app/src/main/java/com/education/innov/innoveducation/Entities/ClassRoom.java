package com.education.innov.innoveducation.Entities;

import java.util.ArrayList;

/**
 * Created by maher on 10/04/2017.
 */

public class ClassRoom {
    private String name;
    private String Country;
    private String adress;
    private Teacher administrator;
    private String idAdminstrator;
    private ArrayList<String> idStudents;
    private ArrayList<String>idTeachers;
    private ArrayList<Child> students;
    private ArrayList<Teacher> teachers;
    private ArrayList<HomeWork> homeWorks;
    private ArrayList<Course> courses;
    private ArrayList<String > idHomeWorks;
    private ArrayList<String > idCourses;
    private String creationDate;
    private String visibility;
    private String id;
    String author ;
    String urlImageAuthor ;

    public ClassRoom() {}

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrlImageAuthor() {
        return urlImageAuthor;
    }

    public void setUrlImageAuthor(String urlImageAuthor) {
        this.urlImageAuthor = urlImageAuthor;
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

    public Teacher getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Teacher administrator) {
        this.administrator = administrator;
    }

    public String getIdAdminstrator() {
        return idAdminstrator;
    }

    public void setIdAdminstrator(String idAdminstrator) {
        this.idAdminstrator = idAdminstrator;
    }

    public ArrayList<String> getIdStudents() {
        return idStudents;
    }

    public void setIdStudents(ArrayList<String> idStudents) {
        this.idStudents = idStudents;
    }

    public ArrayList<String> getIdTeachers() {
        return idTeachers;
    }

    public void setIdTeachers(ArrayList<String> idTeachers) {
        this.idTeachers = idTeachers;
    }

    public ArrayList<Child> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Child> students) {
        this.students = students;
    }

    public ArrayList<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(ArrayList<Teacher> teachers) {
        this.teachers = teachers;
    }

    public ArrayList<String> getIdHomeWorks() {
        return idHomeWorks;
    }

    public void setIdHomeWorks(ArrayList<String> idHomeWorks) {
        this.idHomeWorks = idHomeWorks;
    }

    public ArrayList<String> getIdCourses() {
        return idCourses;
    }

    public void setIdCourses(ArrayList<String> idCourses) {
        this.idCourses = idCourses;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }



    @Override
    public String toString() {
        return "ClassRoom{" +
                "name='" + name + '\'' +
                ", Country='" + Country + '\'' +
                ", adress='" + adress + '\'' +
                ", idAdminstrator='" + idAdminstrator + '\'' +
                ", idStudents=" + idStudents +
                ", idTeachers=" + idTeachers +
                ", idHomeWorks=" + idHomeWorks +
                ", idCourses=" + idCourses +
                ", creationDate='" + creationDate + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
