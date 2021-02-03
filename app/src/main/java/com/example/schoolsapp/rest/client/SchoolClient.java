package com.example.schoolsapp.rest.client;

import com.example.schoolsapp.rest.pojo.DBResponse;
import com.example.schoolsapp.rest.pojo.School;

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

public interface SchoolClient {

    @GET("school")
    Call<ArrayList<School>> getAllSchools();

    @GET("school_and_teachers")
    Call<ArrayList<School>> getAllSchoolsWithTeachers();

    @GET("school/{id}")
    Call<School> getSchool(@Path("id") long id);

    @POST("school")
    Call<DBResponse> addSchool(@Body School school);

    @PUT("school/{id}")
    Call<DBResponse> updateSchool(@Path("id") long id, @Body School school);

    @DELETE("school/{id}")
    Call<DBResponse> deleteSchool(@Path("id") long id);

    @Multipart
    @POST("save_image")
    Call<DBResponse> saveImage(@Part MultipartBody.Part image);


}
