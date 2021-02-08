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
import android.widget.Toast;

import com.example.schoolsapp.R;
import com.example.schoolsapp.rest.callback.OnUserCallback;
import com.example.schoolsapp.rest.pojo.LoginRegister;
import com.example.schoolsapp.rest.pojo.User;
import com.example.schoolsapp.viewmodel.ViewModelActivity;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;


public class RegisterFragment extends Fragment implements View.OnClickListener {

    private ViewModelActivity viewModel;


    private ProgressDialog progressDialog;

    private TextInputLayout tiName;
    private TextInputLayout tiMail;
    private TextInputLayout tiPassword;
    private TextInputLayout tiConfirmPassword;

    private Button btRegister;


    public RegisterFragment() { }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(ViewModelActivity.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tiMail = view.findViewById(R.id.tiMail);
        tiPassword = view.findViewById(R.id.tiPassword);
        tiName = view.findViewById(R.id.tiName);
        tiConfirmPassword = view.findViewById(R.id.tiConfirmPassword);

        btRegister = view.findViewById(R.id.btRegister);

        btRegister.setOnClickListener(this);
    }


    private void attemptRegister(){
        if(checkFields()){
            btRegister.setOnClickListener(null);
            progressDialog = ProgressDialog.show(getContext(), getContext().getString(R.string.progress_loading), "", true, false);

            LoginRegister register = new LoginRegister(
                    tiName.getEditText().getText().toString(),
                    tiMail.getEditText().getText().toString(),
                    tiPassword.getEditText().getText().toString(),
                    tiConfirmPassword.getEditText().getText().toString()
            );

            viewModel.register(register, new OnUserCallback() {
                @Override
                public void OnLoginRegisterSuccess(User user) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), getContext().getString(R.string.message_user_registered), Toast.LENGTH_SHORT).show();
                    NavHostFragment.findNavController(RegisterFragment.this).popBackStack();
                }

                @Override
                public void onLoginRegisterFailure() {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), getContext().getString(R.string.warning_user_not_registered), Toast.LENGTH_SHORT).show();
                    btRegister.setOnClickListener(RegisterFragment.this);
                }
            });
        }
    }



    private boolean checkFields(){
        tiName.setErrorEnabled(false);
        tiMail.setErrorEnabled(false);
        tiPassword.setErrorEnabled(false);
        tiConfirmPassword.setErrorEnabled(false);

        boolean check = true;

        if(tiName.getEditText().getText().toString().isEmpty()){
            check = false;
            tiName.setError(getContext().getString(R.string.error_empty_field));
        }

        if(tiMail.getEditText().getText().toString().isEmpty()){
            check = false;
            tiMail.setError(getContext().getString(R.string.error_empty_field));
        }

        if(tiPassword.getEditText().getText().toString().isEmpty()){
            check = false;
            tiPassword.setError(getContext().getString(R.string.error_empty_field));
        }

        if(tiConfirmPassword.getEditText().getText().toString().isEmpty()){
            check = false;
            tiConfirmPassword.setError(getContext().getString(R.string.error_empty_field));
        }



        return check;
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btRegister:
                attemptRegister();
                break;
        }
    }
}