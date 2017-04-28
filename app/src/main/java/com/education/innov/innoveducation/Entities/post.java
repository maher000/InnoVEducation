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
    private String classRoomId ;
    private Video video; // do not add to firbase
    private String urlVideo;
    private String urlImage;
    private String urlFile;
    private ArrayList<Comments> comments; // do not add to firebase
    private ArrayList<String> idcomments;
    private String userId;
    private User owner; // do not add to firbase
    private String visibility;
    private String subject; // football, sport, education...
    private String author ;
    private String urlImageAuthor ;
    private String urlMiniature;




    public post() {}

    public String getUrlMiniature() {
        return urlMiniature;
    }

    public void setUrlMiniature(String urlMiniature) {
        this.urlMiniature = urlMiniature;
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

    public post(String id, String type, String name, String description, String creationDate, Video video, String urlVideo, String urlImage, String urlFile, ArrayList<Comments> comments, String userId, User owner, String visibility, String subject , ArrayList<String> idcomments) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.video = video;
        this.urlVideo = urlVideo;
        this.urlImage = urlImage;
        this.urlFile = urlFile;

        this.comments = comments;
        this.userId = userId;
        this.owner = owner;
        this.visibility = visibility;
        this.subject = subject;
        this.idcomments = idcomments ;
    }

    public String getClassRoomId() {
        return classRoomId;
    }

    public void setClassRoomId(String classRoomId) {
        this.classRoomId = classRoomId;
    }

    public ArrayList<String> getIdcomments() {
        return idcomments;
    }

    public void setIdcomments(ArrayList<String> idcomments) {
        this.idcomments = idcomments;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    @Override
    public String toString() {
        return "post{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", video=" + video +
                ", urlVideo='" + urlVideo + '\'' +
                ", urlImage='" + urlImage + '\'' +
                ", urlFile='" + urlFile + '\'' +
                ", idcomments=" + idcomments +
                ", userId='" + userId + '\'' +
                ", owner=" + owner +
                ", visibility='" + visibility + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}
