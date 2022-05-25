package com.example.firebasetest;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasetest.Model.Student;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<Student> students;
    private Context context;

    public MyRecyclerViewAdapter(List<Student> list, Context context) {
        this.students = list;
        this.context = context;
    }


    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.fragment_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;

    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemName.setText(students.get(position).getName());
        holder.itemAge.setText(students.get(position).getAge().toString());
    }
    @Override
    public int getItemCount() {
        return students.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
         TextView itemName;
         TextView itemAge;

        public ViewHolder(@NonNull   View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.txt_name);
            itemAge = itemView.findViewById(R.id.txt_age);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + itemName.getText() + "'";
        }
    }
}
