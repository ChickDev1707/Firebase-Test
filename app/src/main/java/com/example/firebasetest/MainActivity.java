package com.example.firebasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.firebasetest.Model.Student;
import com.example.firebasetest.Repository.StudentRepository;

public class MainActivity extends AppCompatActivity {

    private StudentRepository repository = new StudentRepository();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repository.example_3Query();
        setContentView(R.layout.activity_main);
    }
}