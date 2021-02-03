package com.example.schoolsapp.view.adapter;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolsapp.R;
import com.example.schoolsapp.rest.pojo.School;

import java.util.ArrayList;
import java.util.List;

public class SchoolAdapter extends RecyclerView.Adapter<SchoolAdapter.SchoolViewHolder> {

    private List<School> schoolList;
    private OnSchoolClickListener listener;

    public SchoolAdapter(List<School> schoolList, OnSchoolClickListener listener){
        this.schoolList = schoolList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SchoolViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SchoolViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_school, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SchoolViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return schoolList == null ? 0 : schoolList.size();
    }

    class SchoolViewHolder extends RecyclerView.ViewHolder{
        private View view;

        public SchoolViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }

        public void bind(int position){
            ((TextView)view.findViewById(R.id.tvSchoolName)).setText(schoolList.get(position).getName());
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()){
                        case R.id.constraintSchool:
                            listener.OnSchoolEdit(schoolList.get(position));
                            break;
                        case R.id.imgShowTeachers:
                            listener.OnSchoolDetails(schoolList.get(position));
                            break;
                    }
                }
            };
            view.findViewById(R.id.imgShowTeachers).setOnClickListener(onClickListener);
            view.findViewById(R.id.constraintSchool).setOnClickListener(onClickListener);
        }
    }
}
