package com.example.schoolsapp.rest.client;

import com.example.schoolsapp.rest.pojo.LoginRegister;
import com.example.schoolsapp.rest.pojo.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserClient {

    @POST("login")
    Call<User> login(@Body LoginRegister login);



    @POST("register")
    Call<User> register(@Body LoginRegister register);

    

}
