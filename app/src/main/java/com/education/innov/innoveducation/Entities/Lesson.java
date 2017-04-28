package com.education.innov.innoveducation.Entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Syrine on 21/04/2017.
 */

public class Lesson  implements Parcelable {

    private String id;
    private String title;
    private String idCoursse;
    private String description;
    private Course coursse;
    private String urlVideo;
    private String urlMiniature;
    private String DateCreation;


    public Lesson() {
    }


    public Lesson(String id, String title, String idCoursse, String description, Course coursse, String urlVideo) {
        this.id = id;
        this.title = title;
        this.idCoursse = idCoursse;
        this.description = description;
        this.coursse = coursse;
        this.urlVideo = urlVideo;
    }


    public String getUrlMiniature() {
        return urlMiniature;
    }

    public void setUrlMiniature(String urlMiniature) {
        this.urlMiniature = urlMiniature;
    }

    public String getDateCreation() {
        return DateCreation;
    }

    public void setDateCreation(String dateCreation) {
        DateCreation = dateCreation;
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

    public String getIdCoursse() {
        return idCoursse;
    }

    public void setIdCoursse(String idCoursse) {
        this.idCoursse = idCoursse;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Course getCoursse() {
        return coursse;
    }

    public void setCoursse(Course coursse) {
        this.coursse = coursse;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", idCoursse='" + idCoursse + '\'' +
                ", description='" + description + '\'' +
                ", coursse=" + coursse +
                ", urlVideo=" + urlVideo +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(idCoursse);
        dest.writeString(description);
        dest.writeString(urlVideo);
        dest.writeString(urlMiniature);
        dest.writeString(DateCreation);
    }

    private Lesson(Parcel in) {
        id = in.readString();
        title = in.readString();
        idCoursse = in.readString();
        description = in.readString();
        urlVideo = in.readString();
        urlMiniature = in.readString();
        DateCreation = in.readString();
    }

    public static final Parcelable.Creator<Lesson> CREATOR = new Parcelable.Creator<Lesson>() {

        @Override
        public Lesson createFromParcel(Parcel source) {
            return new Lesson(source);
        }

        @Override
        public Lesson[] newArray(int size) {
            return new Lesson[size];
        }
    };

}