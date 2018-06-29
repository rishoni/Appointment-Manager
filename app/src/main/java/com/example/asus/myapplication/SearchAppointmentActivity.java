package com.example.asus.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.myapplication.SQLiteActivity.DBHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 4/30/2018.
 */

public class SearchAppointmentActivity extends AppCompatActivity implements View.OnClickListener{

DBHelper  User_DBHelper;
EditText searchKeyWord;
    Button searchBtn;
    ListView listView;
    String searchedKeyWord;
//search
TextView titleView;
   // ListView listView;
    TextView detailView;

    /**
     * Search Activity for  searcing list
     *
     */

    List<Appointment> listArray; //store  the appointments
ArrayList<Appointment> selectedList;//store selected list
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_appointment);

        searchBtn = (Button) findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(this);
        searchKeyWord = (EditText) findViewById(R.id.searchKeyWord);

        User_DBHelper = new DBHelper(this, null, null, 1);

        listView = (ListView) findViewById(R.id.listView);


//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> val, View view, int position, long id) {
//
//
//
//            }
//        });




    }
    public void onClick(View v) {

/**
 *
 *
 * searching list
 *
 */
        selectedList = new ArrayList<>();

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(searchKeyWord.getText().toString().equals("")){
                        Toast.makeText(getBaseContext(),"Input a keyword",Toast.LENGTH_SHORT).show();
                    }else{
                        searchedKeyWord = searchKeyWord.getText().toString();
                        listArray = User_DBHelper.showAllAppointments();

                        for(int i=0;i<listArray.size();i++){
                            if(listArray.get(i).getTitle().toLowerCase().contains(searchedKeyWord.toLowerCase())){
                                selectedList.add(listArray.get(i));


                            }else if(listArray.get(i).getDetail().toLowerCase().contains(searchedKeyWord.toLowerCase())){
                                selectedList.add(listArray.get(i));
                            }
                        }


                        Toast.makeText(getBaseContext(),searchedKeyWord,Toast.LENGTH_SHORT).show();


//for(Appointment list:selectedList) {
//    selectedList.add(list.getTitle());
//ArrayAdapter<String> adpterList = new ArrayAdapter<String>(getBaseContext(),listView,selectedList);
//
//}
                    }

                }catch (Exception e){

                }
            }
        });





    }


}
