package com.education.innov.innoveducation.Entities;

import java.util.ArrayList;

/**
 * Created by maher on 10/04/2017.
 */

public class ClassRoom {
    private String name="gg";
    private String Country="gg";
    private String adress="gg";

    private String idAdminstrator="gg";

    private String creationDate="gg";
    private String visibility="gg";
    private String id="gg";
    private String author ="gg";
    private String urlImageAuthor="gg" ;

    public ClassRoom() {}

    public ClassRoom(String name, String country, String adress, String idAdminstrator, String creationDate, String visibility, String id, String author, String urlImageAuthor) {
        this.name = name;
        this.Country = country;
        this.adress = adress;
        this.idAdminstrator = idAdminstrator;
        this.creationDate = creationDate;
        this.visibility = visibility;
        this.id = id;
        this.author = author;
        this.urlImageAuthor = urlImageAuthor;
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


    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }


    public String getIdAdminstrator() {
        return idAdminstrator;
    }

    public void setIdAdminstrator(String idAdminstrator) {
        this.idAdminstrator = idAdminstrator;
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

                ", creationDate='" + creationDate + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
