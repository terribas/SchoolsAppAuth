package com.example.schoolsapp.view.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolsapp.R;
import com.example.schoolsapp.rest.callback.OnDBResponse;
import com.example.schoolsapp.rest.pojo.DBResponse;
import com.example.schoolsapp.rest.pojo.School;
import com.example.schoolsapp.viewmodel.ViewModelActivity;
import com.google.android.material.textfield.TextInputLayout;


public class EditSchoolFragment extends Fragment implements View.OnClickListener{

    private ViewModelActivity viewModel;

    private ProgressDialog progressDialog;

    private Button btEditSaveSchool;
    private ImageView imgDeleteSchool;

    private TextInputLayout tiSchoolName;
    private TextInputLayout tiSchoolAddress;
    private TextInputLayout tiSchoolCity;
    private TextInputLayout tiSchoolPhone;


    public EditSchoolFragment() { }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(ViewModelActivity.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_school, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btEditSaveSchool = view.findViewById(R.id.btEditSaveSchool);
        imgDeleteSchool = view.findViewById(R.id.imgDeleteSchool);
        tiSchoolName = view.findViewById(R.id.tiSchoolName);
        tiSchoolAddress = view.findViewById(R.id.tiSchoolAddress);
        tiSchoolCity = view.findViewById(R.id.tiSchoolCity);
        tiSchoolPhone = view.findViewById(R.id.tiSchoolPhone);

        btEditSaveSchool.setOnClickListener(this);
        imgDeleteSchool.setOnClickListener(this);
        view.findViewById(R.id.imgBack).setOnClickListener(this);

        setUI();
    }

    private void setUI() {
        tiSchoolName.getEditText().setText(viewModel.getCurrentSchool().getName());
        tiSchoolAddress.getEditText().setText(viewModel.getCurrentSchool().getAddress());
        tiSchoolCity.getEditText().setText(viewModel.getCurrentSchool().getCity());
        tiSchoolPhone.getEditText().setText(viewModel.getCurrentSchool().getPhone());
        ((TextView)getView().findViewById(R.id.tvTitle)).setText(viewModel.getCurrentSchool().getName());
    }



    private void attemptDeleteSchool(){
        btEditSaveSchool.setOnClickListener(null); //Evitamos errores y duplicados por multiples pulsaciones del boton

        progressDialog = ProgressDialog.show(getContext(), getContext().getString(R.string.progress_loading), "", true, false);

        viewModel.deleteSchool(viewModel.getCurrentSchool(), new OnDBResponse() {
            @Override
            public void onResponse(DBResponse dbResponse) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), R.string.message_school_deleted_successfully, Toast.LENGTH_SHORT).show();
                NavHostFragment.findNavController(EditSchoolFragment.this)
                        .popBackStack();
            }

            @Override
            public void onFailure() {
                progressDialog.dismiss();
                Toast.makeText(getContext(), R.string.warning_school_not_deleted, Toast.LENGTH_SHORT).show();
                btEditSaveSchool.setOnClickListener(EditSchoolFragment.this);
            }
        });
    }



    private void attemptEditSchool(){
        if(checkFields()){
            btEditSaveSchool.setOnClickListener(null); //Evitamos errores y duplicados por multiples pulsaciones del boton
            progressDialog = ProgressDialog.show(getContext(), getContext().getString(R.string.progress_loading), "", true, false);

            School school = new School(
                    tiSchoolName.getEditText().getText().toString(),        //Name
                    tiSchoolAddress.getEditText().getText().toString(),     //Address
                    tiSchoolCity.getEditText().getText().toString(),        //City
                    tiSchoolPhone.getEditText().getText().toString()        //Phone
            );

            school.setId(viewModel.getCurrentSchool().getId());

            viewModel.updateSchool(school, new OnDBResponse() {
                @Override
                public void onResponse(DBResponse dbResponse) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), R.string.message_school_updated_successfully, Toast.LENGTH_SHORT).show();
                    NavHostFragment.findNavController(EditSchoolFragment.this)
                            .popBackStack();
                }

                @Override
                public void onFailure() {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), R.string.warning_school_not_updated, Toast.LENGTH_SHORT).show();
                    btEditSaveSchool.setOnClickListener(EditSchoolFragment.this); //Si no ha podido aplicarse, puede reintentar.
                }
            });
        }
    }



    private boolean checkFields(){
        //I clear all previous error messages
        tiSchoolName.setErrorEnabled(false);
        tiSchoolAddress.setErrorEnabled(false);
        tiSchoolCity.setErrorEnabled(false);
        tiSchoolPhone.setErrorEnabled(false);

        boolean check = true;

        if(tiSchoolName.getEditText().getText().toString().isEmpty()){
            check = false;
            tiSchoolName.setError(getContext().getString(R.string.error_empty_field));
        }
        if(tiSchoolAddress.getEditText().getText().toString().isEmpty()){
            check = false;
            tiSchoolAddress.setError(getContext().getString(R.string.error_empty_field));
        }
        if(tiSchoolCity.getEditText().getText().toString().isEmpty()){
            check = false;
            tiSchoolCity.setError(getContext().getString(R.string.error_empty_field));
        }

        return check;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btEditSaveSchool:
                attemptEditSchool();
                break;

            case R.id.imgDeleteSchool:
                attemptDeleteSchool();
                break;

            case R.id.imgBack:
                NavHostFragment.findNavController(EditSchoolFragment.this).popBackStack();
                break;
        }
    }
}