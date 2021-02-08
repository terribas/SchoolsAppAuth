package com.example.schoolsapp.rest.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRegister {

    private String name;
    private String email;
    private String password;

    @SerializedName("password_confirmation")
    @Expose
    private String passwordConfirmation;


    public LoginRegister(String email, String password){
        this.email = email;
        this.password = password;
    }


    public LoginRegister(String name, String email, String password, String passwordConfirmation){
        this.name = name;
        this.email = email;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
    }

}
