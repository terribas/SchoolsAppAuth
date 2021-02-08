package com.example.schoolsapp.viewmodel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.schoolsapp.model.Repository;
import com.example.schoolsapp.rest.callback.OnDBResponse;
import com.example.schoolsapp.rest.callback.OnSchoolsResponse;
import com.example.schoolsapp.rest.callback.OnTeachersResponse;
import com.example.schoolsapp.rest.callback.OnUserCallback;
import com.example.schoolsapp.rest.pojo.LoginRegister;
import com.example.schoolsapp.rest.pojo.School;
import com.example.schoolsapp.rest.pojo.Teacher;

public class ViewModelActivity extends AndroidViewModel {
    private Repository repository;
    public ViewModelActivity(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }


    public School getCurrentSchool() {
        return repository.getCurrentSchool();
    }

    public void setCurrentSchool(School currentSchool) {
        repository.setCurrentSchool(currentSchool);
    }

    public Teacher getCurrentTeacher() {
        return repository.getCurrentTeacher();
    }

    public void setCurrentTeacher(Teacher currentTeacher) {
        repository.setCurrentTeacher(currentTeacher);
    }

    public void loadAllSchools(OnSchoolsResponse observer) {
        repository.loadAllSchools(observer);
    }

    public void loadTeachersOf(School school, OnTeachersResponse observer) {
        repository.loadTeachersOf(school, observer);
    }


    public void addSchool(School school, OnDBResponse observer) {
        repository.addSchool(school, observer);
    }

    public void updateSchool(School school, OnDBResponse observer) {
        repository.updateSchool(school, observer);
    }

    public void deleteSchool(School school, OnDBResponse observer) {
        repository.deleteSchool(school, observer);
    }

    public void loadAllTeachers(OnTeachersResponse observer) {
        repository.loadAllTeachers(observer);
    }

    public void addTeacher(Uri imageUri, Teacher teacher, OnDBResponse observer) {
        repository.addTeacher(imageUri, teacher, observer);
    }

    public void updateTeacher(Uri imageUri, Teacher teacher, OnDBResponse observer) {
        repository.updateTeacher(imageUri, teacher, observer);
    }

    public void deleteTeacher(Teacher teacher, OnDBResponse observer) {
        repository.deleteTeacher(teacher, observer);
    }

    public boolean storagePermissionIsGranted() {
        return repository.storagePermissionIsGranted();
    }


    public void login(LoginRegister loginRegister, OnUserCallback observer) {
        repository.login(loginRegister, observer);
    }

    public void register(LoginRegister register, OnUserCallback observer) {
        repository.register(register, observer);
    }
}
