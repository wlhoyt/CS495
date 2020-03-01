package com.example.candor365;




import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.Api;

import java.util.HashMap;
import java.util.List;
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

     static void readPreregisterDb(final String date, String time, final readCallBack reader){
        db.collection("classesByDate").document(date).collection(time).document("Preregister")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Map data = documentSnapshot.getData();
                        if (data != null) {
                            Log.d(TAG, "Document data => " + data.toString());
                        }
//                        else
//                        {
//                            Log.d(TAG,"No data in Map Data");
//                        }
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
//
//    static void readAttendanceDb(final String date, String time, final readCallBack reader){
//
//
//        db.collection("classesByDate").document(date).collection(time).document("Attendance")
//                .get()
//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        Map data = documentSnapshot.getData();
//                        if (data != null)
//                            Log.d(TAG, "Document data => " + data.toString());
//                        reader.onCallBack(data);
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Map emptyData=null;
//                reader.onCallBack(emptyData);
//            }
//        });
//    }
}