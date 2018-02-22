package com.example.joshuaadeegbe.lab5;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] items = {"red", "blue","green",
                "red", "blue","green",
                "red", "blue","green",
                "red", "blue","green",
                "red", "blue","green",
                "red", "blue","green",
                "red", "blue","green",
                "red", "blue","green"};

        ListAdapter listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);     //this object converts an array to list items

        listView = (ListView) findViewById(R.id.my_listview);

        listView.setAdapter(listAdapter); //this coverts the array to display in the list

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String item = String.valueOf(parent.getItemAtPosition(position));
                Toast.makeText(getApplicationContext(),
                        "Click ListItem Number " + position +": " + item, Toast.LENGTH_LONG)
                        .show();
            }
        });

    }
}
