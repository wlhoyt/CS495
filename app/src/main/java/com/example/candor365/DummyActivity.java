package com.example.candor365;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class DummyActivity extends AppCompatActivity {

    private TextView shopList;
    private Button save_item_button;
    private TextView itemName;
    private TextView itemPrice;
    private TextView itemCategory;
    private TextView itemQuantity;
    private View.OnClickListener saveItemButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onSaveItemButtonClicked();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);
        save_item_button = (Button) findViewById(R.id.addItemButton);

        itemName  = (TextView) findViewById(R.id.itemName);
        itemPrice  = (TextView) findViewById(R.id.itemPrice);
        itemCategory = (TextView) findViewById(R.id.itemCategory);
        itemQuantity = (TextView) findViewById(R.id.itemQuantity);

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

        save_item_button.setOnClickListener(saveItemButtonListener);


    }

    private void onSaveItemButtonClicked(){
        String item_name = itemName.getText().toString();
        String item_price = itemPrice.getText().toString();
        String item_category = itemCategory.getText().toString();
        int item_quantity = Integer.parseInt(itemQuantity.getText().toString());

        Map<String, Object> itemData = new HashMap<>();
        itemData.put("item_name", item_name);
        itemData.put("item_price", item_price);
        itemData.put("item_quantity", item_quantity);

        Database.addNewItem(itemData, item_category);
        startActivity(new Intent(DummyActivity.this, DummyActivity.class));
        finish();



    }

}
