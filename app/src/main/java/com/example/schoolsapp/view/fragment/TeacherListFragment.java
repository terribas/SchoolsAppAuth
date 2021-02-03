package com.example.schoolsapp.view.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import com.example.schoolsapp.R;
import com.example.schoolsapp.rest.callback.OnTeachersResponse;
import com.example.schoolsapp.rest.pojo.Teacher;
import com.example.schoolsapp.view.adapter.OnTeacherClickListener;
import com.example.schoolsapp.view.adapter.TeacherAdapter;
import com.example.schoolsapp.viewmodel.ViewModelActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TeacherListFragment extends Fragment implements View.OnClickListener {


    private ViewModelActivity viewModel;

    private TeacherAdapter adapter;
    private List<Teacher> teacherList = new ArrayList<>();

    ProgressDialog progressDialog;
    private ConstraintLayout constraintWarning;
    private TextView tvWarning;
    private FloatingActionButton fabAddTeacher;

    public TeacherListFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(ViewModelActivity.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_teacher_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        constraintWarning = view.findViewById(R.id.constraintWarning);
        tvWarning = view.findViewById(R.id.tvWarning);
        fabAddTeacher = view.findViewById(R.id.fabAddTeacher);
        fabAddTeacher.setOnClickListener(this);
        constraintWarning.setOnClickListener(this);
        getView().findViewById(R.id.imgBack).setOnClickListener(this);
        ((TextView)view.findViewById(R.id.tvTitle)).setText(viewModel.getCurrentSchool().getName());
        init();
        attemptLoadTeachers();
    }

    private void init() {
        RecyclerView rvTeacherList = getView().findViewById(R.id.rvTeacherList);

        adapter = new TeacherAdapter(getActivity(), teacherList, new OnTeacherClickListener() {
            @Override
            public void onTeacherClick(Teacher teacher) {
                viewModel.setCurrentTeacher(teacher);
                NavHostFragment.findNavController(TeacherListFragment.this)
                        .navigate(R.id.action_teacherListFragment_to_editTeacherFragment);
            }
        });

        rvTeacherList.setAdapter(adapter);
        rvTeacherList.setLayoutManager(new LinearLayoutManager(getContext()));

    }



    private void attemptLoadTeachers(){
        progressDialog = ProgressDialog.show(getContext(), getContext().getString(R.string.progress_loading), getContext().getString(R.string.progress_loading_teachers), true, false);

        viewModel.loadTeachersOf(viewModel.getCurrentSchool(), new OnTeachersResponse() {
            @Override
            public void onResponse(ArrayList<Teacher> teachers) {
                progressDialog.dismiss();
                fabAddTeacher.setVisibility(View.VISIBLE);
                if(teachers.size() == 0){
                    constraintWarning.setVisibility(View.VISIBLE);
                    tvWarning.setText(R.string.warning_no_teachers_yet);
                }else{
                    constraintWarning.setVisibility(View.GONE);
                    teacherList.clear();
                    teacherList.addAll(teachers);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure() {
                progressDialog.dismiss();
                fabAddTeacher.setVisibility(View.GONE);
                constraintWarning.setVisibility(View.VISIBLE);
                tvWarning.setText(R.string.warning_no_internet);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fabAddTeacher:
                NavHostFragment.findNavController(TeacherListFragment.this)
                        .navigate(R.id.action_teacherListFragment_to_addTeacherFragment);
                break;

            case R.id.imgBack:
                NavHostFragment.findNavController(TeacherListFragment.this).popBackStack();
                break;

            case R.id.constraintWarning:
                attemptLoadTeachers();
                break;
        }
    }
}