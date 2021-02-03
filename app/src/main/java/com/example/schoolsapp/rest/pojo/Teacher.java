package com.example.schoolsapp.rest.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Teacher {

    private long id;

    @SerializedName("school_id")
    @Expose
    private long idSchool;
    private String DNI;
    private String name;
    private String surname;
    private String phone;
    private float wage;

    @SerializedName("picture_url")
    @Expose
    private String pictureUrl;


    public Teacher(){ }


    public Teacher(long idSchool, String DNI, String name, String surname, String phone) {
        this.idSchool = idSchool;
        this.DNI = DNI;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdSchool() {
        return idSchool;
    }

    public void setIdSchool(long idSchool) {
        this.idSchool = idSchool;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public float getWage() {
        return wage;
    }

    public void setWage(float wage) {
        this.wage = wage;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
