package com.example.schoolsapp.view.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.schoolsapp.R;
import com.example.schoolsapp.model.Repository;
import com.example.schoolsapp.rest.callback.OnDBResponse;
import com.example.schoolsapp.rest.pojo.DBResponse;
import com.example.schoolsapp.rest.pojo.Teacher;
import com.example.schoolsapp.viewmodel.ViewModelActivity;
import com.google.android.material.textfield.TextInputLayout;

import static android.app.Activity.RESULT_OK;

public class AddTeacherFragment extends Fragment implements View.OnClickListener {

    private ViewModelActivity viewModel;

    private static final int INTENT_IMAGE_CODE = 100;

    private ProgressDialog progressDialog;

    private Uri imageUri;

    private TextInputLayout tiTeacherDNI;
    private TextInputLayout tiTeacherName;
    private TextInputLayout tiTeacherSurname;
    private TextInputLayout tiTeacherPhone;
    private TextInputLayout tiTeacherWage;

    private Button btEditSaveTeacher;

    public AddTeacherFragment() { }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(ViewModelActivity.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_teacher, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tiTeacherDNI = view.findViewById(R.id.tiTeacherDNI);
        tiTeacherName = view.findViewById(R.id.tiTeacherName);
        tiTeacherSurname = view.findViewById(R.id.tiTeacherSurname);
        tiTeacherPhone = view.findViewById(R.id.tiTeacherPhone);
        tiTeacherWage = view.findViewById(R.id.tiTeacherWage);
        btEditSaveTeacher = view.findViewById(R.id.btEditSaveTeacher);

        getView().findViewById(R.id.imgBack).setOnClickListener(this);
        getView().findViewById(R.id.btSelectImage).setOnClickListener(this);
        btEditSaveTeacher.setOnClickListener(this);
    }



    private void attemptAddTeacher(){
        if(checkFields()){
            btEditSaveTeacher.setOnClickListener(null); //Evitamos errores y duplicados por multiples pulsaciones del boton

            progressDialog = ProgressDialog.show(getContext(), getContext().getString(R.string.progress_loading), "", true, false);


            Teacher teacher = new Teacher(
                    viewModel.getCurrentSchool().getId(),
                    tiTeacherDNI.getEditText().getText().toString(),
                    tiTeacherName.getEditText().getText().toString(),
                    tiTeacherSurname.getEditText().getText().toString(),
                    tiTeacherPhone.getEditText().getText().toString()
            );

            try { teacher.setWage(Float.parseFloat(tiTeacherWage.getEditText().getText().toString())); } catch (Exception ex) {}


            viewModel.addTeacher(imageUri, teacher, new OnDBResponse() {
                @Override
                public void onResponse(DBResponse dbResponse) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), R.string.message_teacher_added_successfully, Toast.LENGTH_LONG).show();
                    NavHostFragment.findNavController(AddTeacherFragment.this).popBackStack();
                }

                @Override
                public void onFailure() {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), R.string.warning_teacher_not_added, Toast.LENGTH_LONG).show();
                    btEditSaveTeacher.setOnClickListener(AddTeacherFragment.this); //Si no ha podido aplicarse, puede reintentar.
                }
            });
        }
    }



    private boolean checkFields(){
        //I clear all previous error messages
        tiTeacherDNI.setErrorEnabled(false);
        tiTeacherName.setErrorEnabled(false);
        tiTeacherSurname.setErrorEnabled(false);

        boolean check = true;

        if(tiTeacherDNI.getEditText().getText().toString().isEmpty()){
            check = false;
            tiTeacherDNI.setError(getContext().getString(R.string.error_empty_field));
        }
        if(tiTeacherName.getEditText().getText().toString().isEmpty()){
            check = false;
            tiTeacherName.setError(getContext().getString(R.string.error_empty_field));
        }
        if(tiTeacherSurname.getEditText().getText().toString().isEmpty()){
            check = false;
            tiTeacherSurname.setError(getContext().getString(R.string.error_empty_field));
        }

        return check;
    }





    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgBack:
                NavHostFragment.findNavController(AddTeacherFragment.this).popBackStack();
                break;

            case R.id.btEditSaveTeacher:
                attemptAddTeacher();
                break;

            case R.id.btSelectImage:
                chooseImage();
                break;
        }
    }






    private void chooseImage(){
        if(viewModel.storagePermissionIsGranted()) {
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(gallery, INTENT_IMAGE_CODE);
        }else{
            requestStoragePermission();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == INTENT_IMAGE_CODE){
            imageUri = data.getData();

            //PONER EL IMAGEVIEW DE PREVISUALIZACION CON LA URI
            ImageView imgPreview = getView().findViewById(R.id.imgPreview);
            imgPreview.setImageURI(imageUri);


        }
    }

    private void requestStoragePermission() {
        Log.v("xyzyx", "REQUEST PERMISSION");
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M) { return; }
        requestPermissions(Repository.STORAGE_PERMISSION, Repository.STORAGE_PERMISSION_CODE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int grantedCounter=0;
        switch (requestCode){
            case Repository.STORAGE_PERMISSION_CODE:
                for(int result : grantResults){
                    if(result== PackageManager.PERMISSION_GRANTED) grantedCounter++;
                }
                break;
        }
        if(grantedCounter==permissions.length){ chooseImage(); }
    }
}