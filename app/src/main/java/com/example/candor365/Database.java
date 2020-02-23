package com.example.candor365;



import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

public class Database {

    private static Firestore db=null;

    public static void initializeDb() {
       db = FirestoreClient.getFirestore();
    }

    public static void writeClassDb(HashMap docData){
        db.collection("classes").document("testing").set(docData);
        //need error checking
    }


}
