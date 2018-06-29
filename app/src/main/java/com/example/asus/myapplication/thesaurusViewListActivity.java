package com.example.asus.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by ASUS on 5/1/2018.
 */

public class thesaurusViewListActivity extends AppCompatActivity{

ListView synonymListView;
    static ArrayList<String> list = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_thesaurus);

        synonymListView = (ListView) findViewById(R.id.synonymListView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, list);
        synonymListView.setAdapter(adapter);

    }
}
