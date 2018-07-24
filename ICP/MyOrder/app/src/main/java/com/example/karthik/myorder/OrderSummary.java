package com.example.karthik.myorder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.widget.ArrayAdapter;


public class OrderSummary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
        String s = getIntent().getStringExtra("EXTRA_SESSION_ID");
        String[] splited = s.split("\\s+");
        boolean peperonni = false;
        boolean onions = false;
        boolean mushroom = false;
        boolean spinach = false;

        if(splited[3].equals("Yes"))
        {
            peperonni = true;
        }
        if(splited[6].equals("Yes"))
        {
            onions = true;
        }
        if(splited[9].equals("Yes"))
        {
            mushroom = true;
        }
        if(splited[12].equals("Yes"))
        {
            spinach = true;
        }
        final ListView orderLV = (ListView) findViewById(R.id.order_lv);
        final List<String> toppings = new ArrayList<String>();
        if(peperonni){
            toppings.add("Peperonni");
        }
        if(onions)
        {
            toppings.add("Onions");
        }
        if(spinach)
        {
            toppings.add("Spinach");
        }
        if(mushroom){
            toppings.add("Mushroom");
        }

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, R.layout.row,R.id.rowTextView,toppings);

        // DataBind ListView with items from ArrayAdapter
        orderLV.setAdapter(arrayAdapter);
    }
}
