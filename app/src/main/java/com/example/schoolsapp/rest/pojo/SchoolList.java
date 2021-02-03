package com.example.schoolsapp.rest.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class SchoolList {

    private ArrayList<School> schools;


    public SchoolList(){ }


    public String[] getCities(){
        Set<String> cities = new TreeSet<>();
        for (School school : schools){
            cities.add(school.getCity());
        }
        return (String[]) cities.toArray();
    }

    public ArrayList<School> getSchools(){ return schools; }

    public ArrayList<School> getSchoolsByCity(String city){
        ArrayList<School> list = new ArrayList<>();
        for (School school : schools) {
            if(school.getCity().compareTo(city) == 0){ list.add(school); }
        }
        return list;
    }

}
