package com.example.asus.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.myapplication.SQLiteActivity.DBHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by ASUS on 4/29/2018.
 */

public class DelEditMoveAppoinmentActivity extends AppCompatActivity {
//
    String option;
    String listNum;
    String date;
    String alertMsg;
    TextView mainHead, subHead;
 EditText searchView;
  Button okBtn;
  ListView listView;
  DBHelper User_DBHelper;

    //listing the list
    ArrayAdapter arrAdapter;
    //store the the list of appointments
    List<Appointment> appointmentList;
    ArrayList<String> arrList;

    //edit pop up box
    PopupWindow editpopup;
    EditText editTitleTxt;
    EditText editTimeTxt;
    EditText editDetailTxt;
Button editBtn;
    //move pop up
    PopupWindow movepopup;
    CalendarView calendarView;
    Button moveBtn;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_del_edit_mve_appointment);

  Intent intentUser = getIntent();
      date=  intentUser.getStringExtra("Date");
     option =   intentUser.getStringExtra("Option");


     mainHead = (TextView) findViewById(R.id.mainHead);
        subHead = (TextView) findViewById(R.id.subHead);
      okBtn=(Button)findViewById(R.id.okBtn);


  listView=(ListView) findViewById(R.id.listView);
    searchView = (EditText) findViewById(R.id.searchView);

//accdigin to users preferance changing the layout
        switch (option){
            case("delete"):
                mainHead.setText("Delete appoinement");
                subHead.setText("Select Appoinement number to delete");
                okBtn.setText("delete");
                break;
            case("edit"):
                mainHead.setText("Edit/view appoinement");
                subHead.setText("Select Appoinement number to edit");
                okBtn.setText("edit");
                break;

            case("move"):
                mainHead.setText("Move Appointment");
                subHead.setText("Select Appointmnet to move to new date");
                okBtn.setText("move");



        }


        User_DBHelper = new DBHelper(this,null,null,1);
 appointmentList =User_DBHelper.show_Appointments(date);
 arrList = new ArrayList<>();

   for(int i=0 ; i<appointmentList.size(); i++){
  arrList.add(i+1 + ". " + appointmentList.get(i).getTime() + "- " + appointmentList.get(i).getTitle());
Log.i("array list " , String.valueOf(arrList));
      }

arrAdapter = new ArrayAdapter<String>(this,R.layout.activity_appointment_array_list , arrList);
listView.setAdapter(arrAdapter);




        okBtn.setOnClickListener( new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                listNum = searchView.getText().toString();

                    try {
                        if (okBtn.getText() == "delete") {

                            //Toast.makeText(getBaseContext(), "selected num " + listNum, Toast.LENGTH_SHORT).show();
                            alertBox("Would you like to delete event :" + appointmentList.get(Integer.parseInt(listNum) - 1).getTitle() + "?");

                        } else if (okBtn.getText() == "edit") {


                            listNum = searchView.getText().toString();
                            if(listNum.equals("")){
                                Toast.makeText(getBaseContext(), "Warning - Number is unrecognized" + listNum, Toast.LENGTH_SHORT).show();
                            }else {
                                editPopUpBox(v);
                                //Toast.makeText(getBaseContext(), "selected num" + listNum, Toast.LENGTH_SHORT).show();
                            }
                        }else if(okBtn.getText()=="move") {
                            listNum = searchView.getText().toString();
                            if (listNum.equals("") || listNum.equals(null)) {
                                Toast.makeText(getBaseContext(), "Warning - Number is unrecognized" + listNum, Toast.LENGTH_SHORT).show();
                            } else {
                                movePopupBox(v);
                                Toast.makeText(getBaseContext(), "selectd number" + listNum, Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (IndexOutOfBoundsException e) {

                        Toast.makeText(getBaseContext(),listNum + "is wrong, Please select a valid number", Toast.LENGTH_SHORT).show();
                        searchView.setText("");
                    } catch (Exception e) {
                        Toast.makeText(getBaseContext(), "Invalid Output", Toast.LENGTH_SHORT).show();
                        searchView.setText("");
                    }

            }
        });






    }

    public void alertBox(String alertMsg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(alertMsg);
        alertDialog.setCancelable(true);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                User_DBHelper.deleteAppointment(date,appointmentList.get(Integer.parseInt(listNum)-1).getTitle());
                dialog.dismiss();
               finish();
                startActivity(getIntent());

         } });
alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = alertDialog.create();
        dialog.show();
 }


 public void editPopUpBox(View v){
try{

    LayoutInflater inflater = (LayoutInflater) DelEditMoveAppoinmentActivity.this
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    final   View popUpLayout =  inflater.inflate(R.layout.activity_edit_popup,
            (ViewGroup) findViewById(R.id.editpopup));

    editpopup = new PopupWindow(popUpLayout,750,900,true);
    editpopup.showAtLocation(v, Gravity.CENTER,0,0);

    editTitleTxt = (EditText) popUpLayout.findViewById(R.id.editTitleTxt);
    editTimeTxt =(EditText)popUpLayout.findViewById(R.id.editTimeTxt);
 editDetailTxt=(EditText)popUpLayout.findViewById(R.id.editDetailTxt);

    editBtn =(Button) popUpLayout.findViewById(R.id.editBtn);

    editBtn.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            try { //date,time,title,detail


                    boolean isEdited = User_DBHelper.edit(appointmentList.get(Integer.parseInt(listNum) - 1), editTimeTxt.getText().toString(),
                            editTitleTxt.getText().toString(), editDetailTxt.getText().toString()
                    );


                    if (isEdited == true) {

                        Toast.makeText(getBaseContext(), "Sucessfullt updated details", Toast.LENGTH_LONG).show();

                    } else if (isEdited == false) {
                        Toast.makeText(getBaseContext(), "Invalid Appointment Number" + listNum, Toast.LENGTH_LONG).show();
                    }
                    finish();
                    startActivity(getIntent());

                }catch(IndexOutOfBoundsException e){
                    Toast.makeText(getBaseContext(), "Invalid Appointment,couldn't find ", Toast.LENGTH_SHORT).show();


                }catch(Exception e){
                Toast.makeText(getBaseContext(), "Invalid Appointment,couldn't find ", Toast.LENGTH_SHORT).show();
                searchView.setText("");
                } editpopup.dismiss();

        }
    });

}catch (Exception e){
e.printStackTrace();
}

 }
 public void movePopupBox(View v){


try{

    LayoutInflater inflater = (LayoutInflater) DelEditMoveAppoinmentActivity.this
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    final   View popUpLayout =  inflater.inflate(R.layout.activity_move_popup,
            (ViewGroup) findViewById(R.id.movepopup));

    editpopup = new PopupWindow(popUpLayout,900,1000,true);
    editpopup.showAtLocation(v, Gravity.CENTER,0,0);

    calendarView = (CalendarView) popUpLayout.findViewById(R.id.calendarView);
    moveBtn = (Button)popUpLayout.findViewById(R.id.moveBtn);

    calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
        @Override
        public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String datePicked = simpleDateFormat.format(new GregorianCalendar(year, month, dayOfMonth).getTime());
            date =datePicked;
            Toast.makeText(getBaseContext(),datePicked,Toast.LENGTH_SHORT).show();
        }
    });


    moveBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
try {
    boolean isMoved = User_DBHelper.move(appointmentList.get(Integer.parseInt(listNum) - 1), date);

    if (isMoved == false) {

    } else if (isMoved == true) {
        Toast.makeText(getBaseContext(), "Sucussfully moved the Appointment", Toast.LENGTH_SHORT).show();
        finish();
        startActivity(getIntent());
    }

}catch (IndexOutOfBoundsException ex){
    Toast.makeText(getBaseContext(),"You Have Picked a inavalid appointment number,moving has failed " , Toast.LENGTH_LONG).show();
    editpopup.dismiss();
}
        }
    });









}catch (Exception e){
    e.printStackTrace();
}

 }


}
