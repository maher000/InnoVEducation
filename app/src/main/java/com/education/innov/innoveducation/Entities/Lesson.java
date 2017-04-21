package com.education.innov.innoveducation.Entities;

/**
 * Created by Syrine on 21/04/2017.
 */

public class Lesson {

    private String id;
    private String title;
    private String idCoursse;
    private String description;
    private Course coursse;
    private String urlVideo;

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
}
