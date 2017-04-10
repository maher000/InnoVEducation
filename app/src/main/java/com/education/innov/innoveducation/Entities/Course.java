package com.education.innov.innoveducation.Entities;

import org.w3c.dom.Comment;

import java.util.ArrayList;

/**
 * Created by maher on 10/04/2017.
 */

public class Course {
    private String id;
    private String name;
    private String description;
    private String Country;
    private String langage;
    private User owner;//the owner must bu a teacher in order to add a course
    private ArrayList<User> followers; //
    private ArrayList<Video> videos;
    private String creationDate;
    private String visibility; // true or false (public to all users or only visible in the classRoom where was created
    private ClassRoom classRoom;
    private ArrayList<Comments> comments;

    public Course() {
    }

    public Course(String id,String name, String description, String country, String langage, User owner, ArrayList<User> followers, ArrayList<Video> videos, String creationDate, String visibility, ClassRoom classRoom, ArrayList<Comments> comments) {
        this.name = name;
        this.description = description;
        Country = country;
        this.langage = langage;
        this.owner = owner;
        this.followers = followers;
        this.videos = videos;
        this.creationDate = creationDate;
        this.visibility = visibility;
        this.classRoom = classRoom;
        this.comments = comments;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getLangage() {
        return langage;
    }

    public void setLangage(String langage) {
        this.langage = langage;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public ArrayList<User> getFollowers() {
        return followers;
    }

    public void setFollowers(ArrayList<User> followers) {
        this.followers = followers;
    }

    public ArrayList<Video> getVideos() {
        return videos;
    }

    public void setVideos(ArrayList<Video> videos) {
        this.videos = videos;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    public ArrayList<Comments> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comments> comments) {
        this.comments = comments;
    }
}
