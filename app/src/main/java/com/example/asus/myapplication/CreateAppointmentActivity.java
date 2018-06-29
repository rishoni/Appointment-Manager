package com.example.asus.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.solver.ArrayLinkedVariables;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.asus.myapplication.SQLiteActivity.DBHelper;

import java.util.ArrayList;

/**
 * Created by ASUS on 4/27/2018.
 */

public class CreateAppointmentActivity extends AppCompatActivity implements View.OnClickListener {

    private String date;
Button saveBtn;
    EditText titleTxt,timeTxt,detailTxt,editText;
    DBHelper User_DBHelper;
    Button ThesaurusBtn,findThesaurus;
    PopupWindow viewpopup;
    //EditText detailedTxt;
    String typedWord;

    public static final String THESAURUS_KEY ="0ykjP1a7cUCt2AP1wrUR";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_appointment);

        final Intent intent = getIntent();
        date = intent.getStringExtra("Date");
        Toast.makeText(getBaseContext(),date,Toast.LENGTH_SHORT).show();

        //initializing the edit text boxes,buttons
        titleTxt = (EditText) findViewById(R.id.titleTxt);
        timeTxt = (EditText) findViewById(R.id.timeTxt);
        detailTxt = (EditText) findViewById(R.id.detailTxt);
        saveBtn = (Button)findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(this);
        ThesaurusBtn = (Button) findViewById(R.id.ThesaurusBtn);
        findThesaurus =(Button) findViewById(R.id.findThesaurus);
        editText = (EditText) findViewById(R.id.editText);
      //  detailedTxt  =(EditText) findViewById(R.id.detailedTxt);
        //1 is db version other are null
        User_DBHelper = new DBHelper(this,null,null,1);
//print();
/**
 * set On click listner for thesaurus btn for task 7
 *
 *
 */

        findThesaurus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                typedWord = editText.getText().toString();
                Toast.makeText(getBaseContext(),typedWord,Toast.LENGTH_SHORT).show();
                if(typedWord == ""){

                }else {

                    Log.i("thesaurus : ", "true");
                    ThesaurusActivity thesaurusActivity = new ThesaurusActivity(typedWord);
                    Thread t = new Thread(thesaurusActivity);
                    t.start();

                    try {
                        t.join();

                        ArrayList<String> list = new ArrayList<String>();

                        ArrayList<String> array = thesaurusActivity.getSynonyms();

                        System.out.println(array);

                        for (int i = 0; i < array.size(); i++){
                            String[] tempArray1 = array.get(i).split("\\(")[0].split("\\|");
                            for (int j = 0; j < tempArray1.length; j++){
                                list.add(tempArray1[j]);
                            }
                        }

                        System.out.println(list);

                        thesaurusViewListActivity.list = list;

                        Intent intent1 = new Intent(getApplicationContext(), thesaurusViewListActivity.class);
                        startActivity(intent1);


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
            }
        });

//        int startSelection=detailsET.getSelectionStart();
//        int endSelection=detailsET.getSelectionEnd();
//
//        String selectedText = detailsET.getText().toString().substring(startSelection, endSelection);
//        Toast.makeText(getBaseContext(),"You selected the word \"" + selectedText + "\"",Toast.LENGTH_SHORT).show();
//
//        inputWord = selectedText;
//        resultPopUp(v);
//


        ThesaurusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int start=detailTxt.getSelectionStart();
                int end=detailTxt.getSelectionEnd();



            }
        });
    }

    /**
     * This method show the current database
     */





    public void onClick(View v) {


        switch (v.getId()){
            case R.id.saveBtn: {
                String title = titleTxt.getText().toString();
                String time = timeTxt.getText().toString();
                String detail = detailTxt.getText().toString();

                if (TextUtils.isEmpty(title)) {
                    titleTxt.setError("Please type a title for appointment");//displays token
                    Log.i("css0", "4654");
                    return;
                } else if (TextUtils.isEmpty(time)) {
                    timeTxt.setError("Please type a time for a appointment");
                    return;
                } else if (TextUtils.isEmpty(detail)) {
                    detailTxt.setError("Please add details of the Appointment");
                    return;
                } else {
                    Log.i("DDFDF","dsdsds");
                    Appointment appointmentUser = new Appointment(date, time, title, detail);
                    boolean val = User_DBHelper.createAppointment(appointmentUser);
                    Log.i("value ", String.valueOf(val));
                    if (val == true) {
                        alertBox(title + "  Appointment " + " on " + date + "  was created sucssesfully  ");
                        //showDatabase();
                    } else if (val == false) {
                        alertBox("Appointment " + title + "title is already exists");
                    }


                }

                break;
            }

//            case R.id.ThesaurusBtn:{
//                ThesaurusActivity thesaurusActivity = new ThesaurusActivity("dog");
//                Thread t = new Thread(thesaurusActivity);
//                Log.i("details" ,"true");
//                t.start();
//
////                typedWord =    detailTxt.getText().toString();
////
////
////
////                if(typedWord.equals(null) || typedWord.equals("")){
////                    detailTxt.setError("Please enter a word and press the button");
////                } else{
////                  Toast.makeText(getBaseContext(),"thesarus waord:"+typedWord,Toast.LENGTH_SHORT).show();
////                }
////
////
////                detailTxt.setText("");
//
//
//                break;
//            }
//
    }



    }

    /**
     *
     * thesaurus view list to display
     *
     * @param v
     */

//    public void thesaurusPopupBox(View v){
//
//
//        try{
//
//            LayoutInflater inflater = (LayoutInflater) CreateAppointmentActivity.this
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            final   View popUpLayout =  inflater.inflate(R.layout.list_thesaurus,
//                    (ViewGroup) findViewById(R.id.synonymListView));
//
//            viewpopup = new PopupWindow(popUpLayout,900,1000,true);
//            viewpopup.showAtLocation(v, Gravity.CENTER,0,0);
//
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }



    /**
     * creates a dialog box and passes string alert
     * msg as a paramter for the function
     *
     * @param alertMsg
     */
    public void alertBox(String alertMsg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(alertMsg);
        alertDialog.setCancelable(true);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            } });
        AlertDialog dialog = alertDialog.create();
        dialog.show();

    }

    public  void showDatabase(){
        String show = User_DBHelper.showDatabase();
        Toast.makeText(getBaseContext(),show,Toast.LENGTH_LONG).show();
    }
}
