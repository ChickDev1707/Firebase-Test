package com.example.firebasetest.Model;

import com.google.firebase.firestore.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Student {

    private String id;
    private String name;
    private Long age;


    public Student(String id, String name, Long age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Student() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("age", age);
        return result;
    }
    public static Student fromMap(String id, Map<String, Object> map){
        return new Student(
                id,
                map.get("name").toString(),
                (Long) map.get("age")
        );
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
