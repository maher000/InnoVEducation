package com.education.innov.innoveducation.Entities;

import android.os.Parcel;
import android.os.Parcelable;

import org.w3c.dom.Comment;

import java.util.ArrayList;

/**
 * Created by maher on 10/04/2017.
 */

public class Course implements Parcelable {
    private String id;
    private String name;
    private String description;
    private String Country;
    private String Subject;
    private String IdClassRoom;
    private String IdUser;
    private String langage;
    private Teacher owner;//the owner must bu a teacher in order to add a course
    private ArrayList<User> followers; //
    private ArrayList<Video> videos;
    private ArrayList<String> idFollowers; //
    private ArrayList<String> idVideos;
    private String creationDate;
    private String author;
    private String urlImageAuthor;
    private String visibility; // true or false (public to all users or only visible in the classRoom where was created
    private ClassRoom classRoom;
    private ArrayList<String> idComments;
    private ArrayList<Comments> comments;
    private String ownerId;

    public Course() {
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Course(String id, String name, String description, String country, String langage, Teacher owner, ArrayList<User> followers, ArrayList<Video> videos, ArrayList<String> idFollowers, ArrayList<String> idVideos, String creationDate, String visibility, ClassRoom classRoom, ArrayList<String> idComments, ArrayList<Comments> comments) {
        this.id = id;
        this.name = name;
        this.description = description;
        Country = country;
        this.langage = langage;
        this.owner = owner;
        this.followers = followers;
        this.videos = videos;
        this.idFollowers = idFollowers;
        this.idVideos = idVideos;
        this.creationDate = creationDate;
        this.visibility = visibility;
        this.classRoom = classRoom;
        this.idComments = idComments;
        this.comments = comments;
    }

    public String getIdUser() {
        return IdUser;
    }

    public void setIdUser(String idUser) {
        IdUser = idUser;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIdClassRoom() {
        return IdClassRoom;
    }

    public void setIdClassRoom(String idClassRoom) {
        IdClassRoom = idClassRoom;
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

    public Teacher getOwner() {
        return owner;
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

    public void setOwner(Teacher owner) {
        this.owner = owner;
    }

    public ArrayList<String> getIdFollowers() {
        return idFollowers;
    }

    public void setIdFollowers(ArrayList<String> idFollowers) {
        this.idFollowers = idFollowers;
    }

    public ArrayList<String> getIdVideos() {
        return idVideos;
    }

    public void setIdVideos(ArrayList<String> idVideos) {
        this.idVideos = idVideos;
    }

    public ArrayList<String> getIdComments() {
        return idComments;
    }

    public void setIdComments(ArrayList<String> idComments) {
        this.idComments = idComments;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", Country='" + Country + '\'' +
                ", langage='" + langage + '\'' +
                ", owner=" + owner +
                ", idFollowers=" + idFollowers +
                ", idVideos=" + idVideos +
                ", creationDate='" + creationDate + '\'' +
                ", visibility='" + visibility + '\'' +
                ", classRoom=" + classRoom +
                ", idComments=" + idComments +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(name);
        dest.writeString(urlImageAuthor);
        dest.writeString(author);
        dest.writeString(creationDate);
        dest.writeString(description);
        dest.writeString(visibility);
        dest.writeString(IdClassRoom);
        dest.writeString(id);
        dest.writeString(IdUser);
        dest.writeString(ownerId);

    }

    private Course(Parcel in) {
        name = in.readString();
        urlImageAuthor = in.readString();
        author = in.readString();
        creationDate = in.readString();
        description = in.readString();
        visibility = in.readString();
        IdClassRoom = in.readString();
        id = in.readString();
        IdUser = in.readString();
        ownerId=in.readString();
    }

    public static final Creator<Course> CREATOR = new Creator<Course>() {

        @Override
        public Course createFromParcel(Parcel source) {
            return new Course(source);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

}