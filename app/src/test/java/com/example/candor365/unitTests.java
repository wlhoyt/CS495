package com.example.candor365;
import android.content.Context;

import com.google.android.gms.common.logging.Logger;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import junit.framework.*;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import org.mockito.runners.MockitoJUnitRunner;

public class unitTests extends TestCase {

    final FirebaseMocker firebaseMocker = new FirebaseMocker();

    public unitTests(String name){
        super(name);
    }

    protected void setUp() {

    }

//    @Override
//    protected void tearDown() throws Exception {
//        super.tearDown();
//        //can't think of anything to tear down
//    }
    public void testReadSpecificItem(){
        //setting up testItem to check if reading from our shop database works.
        final Map<String, Object> testItem = new HashMap<>();
        testItem.put("item_name","beanie");
        testItem.put("item_price", "$5.00");
        testItem.put("item_quantity", "25");

        Database.readShopDb("clothes", "beanie", new readCallBack() {
            @Override
            public void onCallBack(Map dataMap) {
                assertEquals(testItem, dataMap);
            }
        });
    }
}
