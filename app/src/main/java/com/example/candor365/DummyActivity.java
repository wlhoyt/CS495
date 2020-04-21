package com.example.candor365;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
//import android.provider.ContactsContract;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
//import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DummyActivity extends AppCompatActivity {

    AlertDialog dialog;
    AlertDialog.Builder builder;


    private TextView shopList;
    private Button save_item_button;
    private Button filterButton;
    private Button deleteButton;
    private TextView itemName;
    private TextView itemPrice;
    private TextView itemCategory;
    private TextView itemQuantity;
    private View.OnClickListener filterButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onFilterButtonClicked();
        }
    };
    private View.OnClickListener deleteButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onDeleteButtonClicked(itemName.getText().toString(), itemCategory.getText().toString());
        }
    };
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
        filterButton = (Button) findViewById(R.id.filter_button);
        deleteButton = (Button) findViewById(R.id.delete_button);
        itemName  = (TextView) findViewById(R.id.itemName);
        itemPrice  = (TextView) findViewById(R.id.itemPrice);
        itemCategory = (TextView) findViewById(R.id.itemCategory);
        itemQuantity = (TextView) findViewById(R.id.itemQuantity);

        shopList = (TextView) findViewById(R.id.shop_item);
        shopList.setMovementMethod(new ScrollingMovementMethod());
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
        deleteButton.setOnClickListener(deleteButtonListener);
        save_item_button.setOnClickListener(saveItemButtonListener);
        filterButton.setOnClickListener(filterButtonListener);

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
    //need to check if shopList = null
    private void onFilterButtonClicked(){
        String item_category = itemCategory.getText().toString();
        shopList.setText("");
        if(!item_category.equals("clothes") && !item_category.equals("consumables") && !item_category.equals("other")){
            //no filter
            Database.readShopDb("consumables", new readCallBack() {
                @Override
                public void onCallBack(Map dataMap) {
                    if(dataMap != null){
                        shopList.append(dataMap.toString());
                    }
                }
            });

            Database.readShopDb("clothes", new readCallBack() {
                @Override
                public void onCallBack(Map dataMap) {
                    if(dataMap != null){
                        shopList.append(dataMap.toString());
                    }
                }
            });

            Database.readShopDb("other", new readCallBack() {
                @Override
                public void onCallBack(Map dataMap) {
                    if(dataMap != null){
                        shopList.append(dataMap.toString());
                    }
                }
            });


        }
        else{
            Database.readShopDb(item_category, new readCallBack() {
                @Override
                public void onCallBack(Map dataMap) {
                    if(dataMap != null){
                        shopList.append(dataMap.toString());
                    }
                }
            });
        }
    }

    private void onDeleteButtonClicked(final String item, final String category){
        builder = new AlertDialog.Builder(DummyActivity.this);
        builder.setTitle("Are you sure you want to delete this item?");

        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //actually delete the item
                Database.deleteItem(item,category);
                //need to also update the textView
                startActivity(new Intent(DummyActivity.this, DummyActivity.class));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing
//                startActivity(new Intent(DummyActivity.this, DummyActivity.class));
            }
        });

        dialog = builder.create();
        dialog.show();
    }

}
