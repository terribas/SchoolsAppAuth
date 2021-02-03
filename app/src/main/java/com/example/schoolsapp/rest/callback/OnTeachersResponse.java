package com.example.schoolsapp.rest.callback;

import com.example.schoolsapp.rest.pojo.Teacher;

import java.util.ArrayList;

public interface OnTeachersResponse {
    public void onResponse(ArrayList<Teacher> teachers);
    public void onFailure();
}
