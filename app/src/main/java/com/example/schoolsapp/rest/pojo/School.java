package com.example.schoolsapp.rest.pojo;

import java.util.ArrayList;

public class School {

    private long id;
    private String name;
    private String city;
    private String address;
    private String phone;

    private ArrayList<Teacher> teachers;

    public School(){ }


    public School(String name, String address, String city, String phone) {
        this.name = name;
        this.city = city;
        this.address = address;
        if(!phone.isEmpty()){ this.phone = phone; }
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ArrayList<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(ArrayList<Teacher> teachers) {
        this.teachers = teachers;
    }


    @Override
    public String toString() {
        return "School{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", teachers=" + teachers +
                '}';
    }
}
