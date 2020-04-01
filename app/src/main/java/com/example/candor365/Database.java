package com.example.candor365;

import android.util.Log;
import android.widget.Toast;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.Api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    //This method reads a specific item from a specific category from the respective parameters
    static void readShopDb(final String category, final String item, final readCallBack reader){
        db.collection("store").document("category").collection(category).document(item)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Map Item = documentSnapshot.getData();
                        if (Item != null){
                            Log.d(TAG, "Item data => " + Item.toString());
                        }
                        reader.onCallBack(Item);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Map noItem = null;
                reader.onCallBack(noItem);
            }
        });
    }
    //this method reads all items from a specific category
    static void readShopDb(final String category, final readCallBack reader){
        db.collection("store").document("category").collection(category)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                //print everything to the text view
                                reader.onCallBack(document.getData());
                            }
                        }
                        else{
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    /*  This method takes in a HashMap of an item's name, price, and quantity
    *   This method also takes in a category
    */
    static void addNewItem(Map item, String category){
        final String item_name = (String) item.get("item_name");
        db.collection("store").document("category").collection(category).document(item_name)
                .set(item)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Item successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Item could not be added");
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
    static void readAttendanceDb(final String date, String time, final readCallBack reader){


        db.collection("classesByDate").document(date).collection(time).document("Attendance")
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

    static void writePreregisterDb(Map docData, String date){
        db.collection("classesByDate").document(date).collection("6:30").document("Preregister").set(docData);
        //need error checking
    }
    static void writeAttendanceDb(Map docData, String date){
        db.collection("classesByDate").document(date).collection("6:30").document("Attendance").set(docData);
        //need error checking
    }
}