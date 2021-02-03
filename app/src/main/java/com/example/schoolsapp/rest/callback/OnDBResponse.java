package com.example.schoolsapp.rest.callback;

import com.example.schoolsapp.rest.pojo.DBResponse;


public interface OnDBResponse {
    public void onResponse(DBResponse dbResponse);
    public void onFailure();
}
