package com.example.candor365;

import android.util.Log;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Map;

import androidx.annotation.NonNull;

class Database {
    static String TAG = "DataBase";
    private static FirebaseFirestore db=null;
    private static Map data;



    static void initializeDb() {
        db = FirebaseFirestore.getInstance();
    }

    static void writeClassDb(Map docData, String date){
        db.collection("classesByDate").document(date).collection("6:30").document("ClassInfo").set(docData);
        //need error checking
    }

    static void readClassDb(final String date, String time, final readCallBack reader){


        db.collection("classesByDate").document(date).collection(time).document("ClassInfo")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Map data = documentSnapshot.getData();
                        if (data != null)
                            Log.d(TAG, "Document data => " + data.toString());
                        reader.onCallBack(data);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Map emptyData=null;
                reader.onCallBack(emptyData);
            }
        });
    }
}
