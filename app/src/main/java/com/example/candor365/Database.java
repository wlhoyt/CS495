package com.example.candor365;




import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

class Database {

    private static FirebaseFirestore db=null;

    static void initializeDb() {

        db = FirebaseFirestore.getInstance();
    }

    static void writeClassDb(Map docData, String eventTitle){
        db.collection("classes").document(eventTitle).collection(eventTitle + "data").document("ClassInfo").set(docData);
        //need error checking
    }


}
