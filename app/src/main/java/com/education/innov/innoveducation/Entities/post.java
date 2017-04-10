package com.education.innov.innoveducation.Entities;

import java.util.ArrayList;

/**
 * Created by maher on 10/04/2017.
 */

public class post {
    private String id;
    private String type; // image ,file, video,none
    private String name;
    private String description;
    private String creationDate;
    private Video video;
    private String urlImage;
    private String urlFile;
    private ArrayList<Comments> comments;
    private User owner;
    private String visibility;
    private String subject; // football, sport, education...

    public post() {}

    public post(String id, String type, String name, String description, String creationDate, Video video, String urlImage, String urlFile, ArrayList<Comments> comments, User owner, String visibility, String subject) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.video = video;
        this.urlImage = urlImage;
        this.urlFile = urlFile;
        this.comments = comments;
        this.owner = owner;
        this.visibility = visibility;
        this.subject = subject;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getUrlFile() {
        return urlFile;
    }

    public void setUrlFile(String urlFile) {
        this.urlFile = urlFile;
    }

    public ArrayList<Comments> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comments> comments) {
        this.comments = comments;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
