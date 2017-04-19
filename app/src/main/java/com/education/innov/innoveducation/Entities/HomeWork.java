package com.education.innov.innoveducation.Entities;

import java.util.ArrayList;

/**
 * Created by maher on 10/04/2017.
 */

public class HomeWork {
    private String id;
    private String title;
    private String author ;
    private String urlImageAuthor ;
    private String IdClassRom ;
    private String description;
    private String Subject ;
    private String url ;// optionel : add external url
    private Teacher teacher;
    private ArrayList<String> idComments;
    private ArrayList<Comments> comments;
    private String startDate;
    private String endDate;

    public HomeWork() {
    }

    public HomeWork(String id, String title, String description, String url, Teacher teacher, ArrayList<String> idComments, ArrayList<Comments> comments, String startDate, String endDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.url = url;
        this.teacher = teacher;
        this.idComments = idComments;
        this.comments = comments;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

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

    public String getIdClassRom() {
        return IdClassRom;
    }

    public void setIdClassRom(String idClassRom) {
        IdClassRom = idClassRom;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public ArrayList<Comments> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comments> comments) {
        this.comments = comments;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public ArrayList<String> getIdComments() {
        return idComments;
    }

    public void setIdComments(ArrayList<String> idComments) {
        this.idComments = idComments;
    }

    @Override
    public String toString() {
        return "HomeWork{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", teacher=" + teacher +
                ", idComments=" + idComments +
                ", comments=" + comments +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
