package com.example.candor365;




import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

class Database {
    static String TAG = "DataBase";
    private static FirebaseFirestore db=null;

    static void initializeDb() {
        db = FirebaseFirestore.getInstance();
    }

    static void writeClassDb(Map docData, String eventTitle){
        db.collection("classes").document(eventTitle).collection(eventTitle + "data").document("ClassInfo").set(docData);
        //need error checking
    }
    static Map readClassDb(String eventTitle){
        db.collection("classes").document("danielsClass").collection("danielsClassData").document("ClassInfo")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            Map data = task.getResult().getData();
                            Log.d(TAG, "Document data => " + data.toString());

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
        return null;
    }
}
