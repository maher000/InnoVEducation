package com.education.innov.innoveducation.Entities;

/**
 * Created by maher on 21/04/2017.
 */

public class ClassroomRequest {
    private String id;
    private String senderId;
    private String classroomId;
    private String AdminClassroomId;
    private String urlImgSender;
    private String senderName;
    private String senderType;
    private String date;
    private String classroomName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ClassroomRequest() {}

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(String classroomd) {
        this.classroomId = classroomd;
    }

    public String getAdminClassroomId() {
        return AdminClassroomId;
    }

    public void setAdminClassroomId(String adminClassroomId) {
        AdminClassroomId = adminClassroomId;
    }

    public String getUrlImgSender() {
        return urlImgSender;
    }

    public void setUrlImgSender(String urlImgSender) {
        this.urlImgSender = urlImgSender;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderType() {
        return senderType;
    }

    public void setSenderType(String senderType) {
        this.senderType = senderType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }
}
