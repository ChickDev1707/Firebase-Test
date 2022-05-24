package com.example.firebasetest.Repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.firebasetest.Model.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class StudentRepository {
    private static volatile StudentRepository instance;

    public static StudentRepository getInstance() {
        if (instance == null) {
            instance = new StudentRepository();
        }
        return instance;
    }
    FirebaseFirestore db;

    public StudentRepository(){
        db = FirebaseFirestore.getInstance();
    }
    //get all
    public void getAllStudent(){
        CollectionReference colRef =  db.collection("Student");
        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isComplete()){
                    for( DocumentSnapshot doc : task.getResult()){
                        Student student = Student.fromMap(doc.getId(), doc.getData());
                        Log.d(student.getId(), student.getName());
                    }
                }
                else {
                    Log.e("Get all", "Error getting data", task.getException());
                }
            }
        });

    }
    // add
    public void addStudent(Student student){
//        db.collection("Student").document("new-student-id").set(student.toMap());  // có id
        db.collection("Student").add(student.toMap()) // auto generate id student
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Add", "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Add", "Error adding document", e);
                    }
                });
    }
    //update
    // address: {city: "HCM", district: "Q12" }
    public void updateStudent(Student student){

//        db.collection("Student").document(student.getId())
//                .update("address.city", "Quang Nam", "age", 22);
        db.collection("Student").document(student.getId())
                .update(student.toMap())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.d("Update", "Successfully!");
                        }
                        else {
                            Log.w("Update", "Error updating document", task.getException());
                        }
                    }
                });
    }

    //delete
    public void delete(String id){
        DocumentReference docRef = db.collection("Student").document(id);
        docRef.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.d("Delete", "Successfully!");
                }
                else{
                    Log.w("Delete", "Error deleting document", task.getException());
                }
            }
        });
    }


    //Query
    public void example_1Query(String studentName){
        Query query = db.collection("Student")
                .whereEqualTo("name", studentName); // whereLessThan, whereGreaterThan, whereNotEqualTo, ...
        //whereIn, whereArrayContains

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("Query", document.getId() + " => " + document.getData());
                    }
                } else {
                    Log.d("Query", "Error getting documents: ", task.getException());
                }
            }
        });
    }
    //Paginate, order by, limit
    //orderBy + where.. must be same field
    public void example_2Query(){
        Query query = db.collection("Student")
                .orderBy("age", Query.Direction.ASCENDING)
                .limit(3);

        //get all student with age >= 20
//        Query query = db.collection("Student")
//                .orderBy("age")
//                .startAt(20);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("Query", document.getId() + " => " + document.getData());
                    }
                } else {
                    Log.d("Query", "Error getting documents: ", task.getException());
                }
            }
        });
    }


    public void example_3Query(){
        // Construct query for first 3 students, ordered by age
        Query first = db.collection("Student")
                .orderBy("age")
                .limit(3);

        first.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        // ...

                        // Get the last visible document
                        DocumentSnapshot lastVisible = documentSnapshots.getDocuments()
                                .get(documentSnapshots.size() -1);

                        // Construct a new query starting at this document,
                        // get the next 3 students.
                        Query next = db.collection("cities")
                                .orderBy("population")
                                .startAfter(lastVisible)
                                .limit(3);
                        // next.get()....
                        // Use the query for pagination
                        // ...
                    }
                });
    }

}
