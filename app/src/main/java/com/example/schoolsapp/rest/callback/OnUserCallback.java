package com.example.schoolsapp.rest.callback;

import com.example.schoolsapp.rest.pojo.User;

public interface OnUserCallback {
    public void OnLoginRegisterSuccess(User user);
    public void onLoginRegisterFailure();
}
