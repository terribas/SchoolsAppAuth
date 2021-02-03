package com.example.schoolsapp.rest.callback;

import com.example.schoolsapp.rest.pojo.School;

import java.util.ArrayList;

public interface OnSchoolsResponse {
    public void onResponse(ArrayList<School> schools);
    public void onFailure();
}
