package com.example.firebasetest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.firebasetest.Model.Student;
import com.example.firebasetest.Repository.StudentRepository;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StudentRepository repository = new StudentRepository();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_list);

        repository.getAllStudent(students -> {
            MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter((List<Student>)students,this);
            System.out.println(adapter);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        });

    }
}