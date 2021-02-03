package com.example.schoolsapp.view.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.schoolsapp.R;
import com.example.schoolsapp.rest.callback.OnSchoolsResponse;
import com.example.schoolsapp.rest.pojo.School;
import com.example.schoolsapp.view.adapter.OnSchoolClickListener;
import com.example.schoolsapp.view.adapter.SchoolAdapter;
import com.example.schoolsapp.viewmodel.ViewModelActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class SchoolListFragment extends Fragment implements View.OnClickListener {

    private ViewModelActivity viewModel;

    private SchoolAdapter adapter;
    private ArrayList<School> schoolList = new ArrayList<>();

    ProgressDialog progressDialog;
    private ConstraintLayout constraintWarning;
    private TextView tvWarning;
    private FloatingActionButton fabAddSchool;



    public SchoolListFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(ViewModelActivity.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_school_list, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        constraintWarning = view.findViewById(R.id.constraintWarning);
        tvWarning = view.findViewById(R.id.tvWarning);
        fabAddSchool = view.findViewById(R.id.fabAddSchool);
        init();
        attemptLoadSchools();
    }


    private void init() {
        RecyclerView rvSchoolList = getView().findViewById(R.id.rvSchoolList);

        adapter = new SchoolAdapter(schoolList, new OnSchoolClickListener() {
            @Override
            public void OnSchoolEdit(School school) {
                viewModel.setCurrentSchool(school);
                NavHostFragment.findNavController(SchoolListFragment.this)
                        .navigate(R.id.action_schoolListFragment_to_editSchoolFragment);
            }

            @Override
            public void OnSchoolDetails(School school) {
                viewModel.setCurrentSchool(school);
                NavHostFragment.findNavController(SchoolListFragment.this)
                        .navigate(R.id.action_schoolListFragment_to_teacherListFragment);
            }
        });

        rvSchoolList.setAdapter(adapter);
        rvSchoolList.setLayoutManager(new LinearLayoutManager(getContext()));

        fabAddSchool.setOnClickListener(this);
        constraintWarning.setOnClickListener(this);


    }



    private void attemptLoadSchools(){
        progressDialog = ProgressDialog.show(getContext(), getContext().getString(R.string.progress_loading), getContext().getString(R.string.progress_loading_schools), true, false);

        //Se declara nuevo CallBack propio, pero en nuevas llamadas ya no existe. No se acumulan (add) como en MutableLiveData.
        //Ressourceneffizienz
        viewModel.loadAllSchools(new OnSchoolsResponse() {
            @Override
            public void onResponse(ArrayList<School> schools) {
                progressDialog.dismiss();
                if(schools.size() == 0){
                    constraintWarning.setVisibility(View.VISIBLE);
                    fabAddSchool.setVisibility(View.VISIBLE);
                    tvWarning.setText(R.string.warning_no_schools_yet);
                }else{
                    constraintWarning.setVisibility(View.GONE);
                    fabAddSchool.setVisibility(View.VISIBLE);
                    schoolList.clear();
                    schoolList.addAll(schools);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure() {
                progressDialog.dismiss();
                fabAddSchool.setVisibility(View.GONE);
                constraintWarning.setVisibility(View.VISIBLE);
                tvWarning.setText(R.string.warning_no_internet);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.constraintWarning:
                attemptLoadSchools();
                break;

            case R.id.fabAddSchool:
                NavHostFragment.findNavController(SchoolListFragment.this)
                        .navigate(R.id.action_schoolListFragment_to_addSchoolFragment);
                break;
        }
    }
}