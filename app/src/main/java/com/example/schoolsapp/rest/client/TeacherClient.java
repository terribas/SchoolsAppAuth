package com.example.schoolsapp.rest.client;

import com.example.schoolsapp.rest.pojo.DBResponse;
import com.example.schoolsapp.rest.pojo.School;
import com.example.schoolsapp.rest.pojo.Teacher;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface TeacherClient {

    @GET("teacher")
    Call<ArrayList<Teacher>> getAllTeachers();

    @Multipart
    @POST("save_image")
    Call<DBResponse> saveImage(@Part MultipartBody.Part image);


    @GET("teacher/{id}")
    Call<Teacher> getTeacher(@Path("id") long id);


    @GET("teachers_of/{id}")
    Call<ArrayList<Teacher>> getTeachersOf(@Path("id") long id);


    @POST("teacher")
    Call<DBResponse> addTeacher(@Body Teacher teacher);

    @PUT("teacher/{id}")
    Call<DBResponse> updateTeacher(@Path("id") long id, @Body Teacher teacher);

    @DELETE("teacher/{id}")
    Call<DBResponse> deleteTeacher(@Path("id") long id);



}
