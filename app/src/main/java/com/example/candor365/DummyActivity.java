package com.example.candor365;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Map;

public class DummyActivity extends AppCompatActivity {

    private TextView shopList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);
        shopList = (TextView) findViewById(R.id.shop_item);
        shopList.setText("");
        Database.initializeDb();
        Database.readShopDb("clothes", new readCallBack() {
            @Override
            public void onCallBack(Map dataMap) {
                if(dataMap != null){
                    shopList.append(dataMap.toString());
                }
            }
        });

    }
}
