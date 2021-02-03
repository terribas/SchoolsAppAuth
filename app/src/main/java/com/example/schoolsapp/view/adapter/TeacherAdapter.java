package com.example.schoolsapp.view.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.schoolsapp.R;
import com.example.schoolsapp.rest.pojo.Teacher;

import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.SchoolViewHolder> {

    private Activity activity;
    private List<Teacher> teachers;
    private OnTeacherClickListener listener;

    public TeacherAdapter(Activity activity, List<Teacher> teachers, OnTeacherClickListener listener){
        this.activity = activity;
        this.teachers = teachers;
        this.listener = listener;
        // Pleasing, same length.
    }

    @NonNull
    @Override
    public SchoolViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TeacherAdapter.SchoolViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_teacher, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SchoolViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return teachers == null ? 0 : teachers.size();
    }


    class SchoolViewHolder extends RecyclerView.ViewHolder{
        private View view;
        public SchoolViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }

        public void bind(int position){
            Glide.with(activity)
                    .load(teachers.get(position).getPictureUrl())
                    .placeholder(R.drawable.iconluser)
                    .into((ImageView)view.findViewById(R.id.imgTeacher));

            ((TextView)view.findViewById(R.id.tvName)).setText(teachers.get(position).getName());
            ((TextView)view.findViewById(R.id.tvSurname)).setText(teachers.get(position).getSurname());
            ((TextView)view.findViewById(R.id.tvWage)).setText(String.format("%.02f", teachers.get(position).getWage())+" €");
            view.findViewById(R.id.constraintTeacher).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onTeacherClick(teachers.get(position));
                }
            });
        }
    }
}
