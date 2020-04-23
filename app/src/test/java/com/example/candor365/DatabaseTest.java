package com.example.candor365;

import android.provider.ContactsContract;

import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.HashMap;
import java.util.Map;
import static org.mockito.Mockito.*;


import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class DatabaseTest {

    //    @Override
//    protected void tearDown() throws Exception {
//        super.tearDown();
//        //can't think of anything to tear down
//    }


    @Test
    public void testWriteClassDb(){
        Database db = mock(Database.class);
        String date = "UnitTest";
        String time = "TestTime";
        Map<String, Object> testData = new HashMap<>();
        testData.put("Test data", "This is a test");
        Database.writeClassDb(testData,time,date);

    }
    @Test
    public void testReadClassDb(){
        String date = "UnitTest";
        String time = "TestTime";
        //Database.initializeDb();
        final Map<String, Object> testData = new HashMap<>();
        testData.put("Test data", "This is a test");
        Database.readClassDb(date, time, new readCallBack() {
            @Override
            public void onCallBack(Map dataMap) {
                assertEquals(dataMap,testData);
            }
        });
    }

    @Test
    public void testWriteSpecificItem(){
        //setting up testItem to check if reading from our shop database works.
        final Map<String, Object> testItem = new HashMap<>();
        testItem.put("item_name","beanie");
        testItem.put("item_price", "$7.99");
        testItem.put("item_quantity", "15");
        Database.addNewItem(testItem,"Test");
        assertEquals("beanie", testItem.get("item_name"));

    }

    @Test
    public void testReadSpecificCategory(){
        final Map<String, Object> testItem = new HashMap<>();
        testItem.put("item_name","beanie");
        testItem.put("item_price", "$7.99");
        testItem.put("item_quantity", "15");
        Database.readShopDb("Test", new readCallBack() {
            @Override
            public void onCallBack(Map dataMap) {
                assertEquals(testItem,dataMap);
            }
        });
    }

    @Test
    public void testReadAttendanceDb(){
        String date = "UnitTest";
        String time = "TestTime";
        //Database.initializeDb();
        final Map<String, Object> testData = new HashMap<>();
        testData.put("Test data", "This is a test");
        Database.readAttendanceDb(date, time, new readCallBack() {
            @Override
            public void onCallBack(Map dataMap) {
                assertEquals(testData,dataMap);
            }
        });
    }

}