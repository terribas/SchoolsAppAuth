package com.example.schoolsapp.view.fragment;

import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.Toast;

import com.example.schoolsapp.R;
import com.example.schoolsapp.rest.callback.OnDBResponse;
import com.example.schoolsapp.rest.callback.OnUserCallback;
import com.example.schoolsapp.rest.pojo.DBResponse;
import com.example.schoolsapp.rest.pojo.LoginRegister;
import com.example.schoolsapp.rest.pojo.User;
import com.example.schoolsapp.viewmodel.ViewModelActivity;
import com.google.android.material.textfield.TextInputLayout;


public class LoginFragment extends Fragment implements View.OnClickListener {

    private ViewModelActivity viewModel;


    private ProgressDialog progressDialog;

    private TextInputLayout tiMail;
    private TextInputLayout tiPassword;


    private Button btLogin;

    public LoginFragment() { }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(ViewModelActivity.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tiMail = view.findViewById(R.id.tiMail);
        tiPassword = view.findViewById(R.id.tiPassword);

        btLogin = view.findViewById(R.id.btLogin);

        view.findViewById(R.id.fabRegister).setOnClickListener(this);
        btLogin.setOnClickListener(this);


    }





    private void attemptLogin(){

        // Con mi vena HardCoder, hubiera evitado una indentación con
        // if(!checkfields) return;

        // Pero eso no le gusta a Carmelo. Demasiadas cosas mal en una sóla línea.

        if(checkFields()) {
            btLogin.setOnClickListener(null);
            progressDialog = ProgressDialog.show(getContext(), getContext().getString(R.string.progress_loading), "", true, false);

            LoginRegister loginRegister = new LoginRegister(
                    tiMail.getEditText().getText().toString(),
                    tiPassword.getEditText().getText().toString()
            );

            viewModel.login(loginRegister, new OnUserCallback() {
                @Override
                public void OnLoginRegisterSuccess(User user) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), getContext().getString(R.string.greeting_user) + " " + user.getName(), Toast.LENGTH_SHORT).show();
                    NavHostFragment.findNavController(LoginFragment.this)
                            .navigate(R.id.action_loginFragment_to_schoolListFragment);


                }

                @Override
                public void onLoginRegisterFailure() {
                    progressDialog.dismiss();
                    btLogin.setOnClickListener(LoginFragment.this);
                    Toast.makeText(getContext(), R.string.warning_login_failed, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }



    private boolean checkFields(){
        tiMail.setErrorEnabled(false);
        tiPassword.setErrorEnabled(false);

        boolean check = true;

        if(tiMail.getEditText().getText().toString().isEmpty()){
            check = false;
            tiMail.setError(getContext().getString(R.string.error_empty_field));
        }

        if(tiPassword.getEditText().getText().toString().isEmpty()){
            check = false;
            tiPassword.setError(getContext().getString(R.string.error_empty_field));
        }

        return check;

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btLogin:
                attemptLogin();
                break;

            case R.id.fabRegister:
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.action_loginFragment_to_registerFragment);
                break;
        }
    }
}