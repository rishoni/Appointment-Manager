package com.example.asus.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.asus.myapplication.SQLiteActivity.DBHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button createAppoint,view_editAppoint,delAppoint,moveAppoint,searchAppoint, delete ,deleteAll;;

    CalendarView calendarView;
    String date;
    DBHelper DBHelperObj;

    PopupWindow deleteWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing the buttons and adding onclick
        createAppoint = (Button) findViewById(R.id.createAppoint);
        Log.i("ddf","fdfdfdfd");
        createAppoint.setOnClickListener(this);

        view_editAppoint = (Button) findViewById(R.id.view_editAppoint);
        view_editAppoint.setOnClickListener(this);

        delAppoint = (Button) findViewById(R.id.delAppoint);
        delAppoint.setOnClickListener(this);

        moveAppoint = (Button) findViewById(R.id.moveAppoint);
        moveAppoint.setOnClickListener(this);

        searchAppoint = (Button) findViewById(R.id.searchAppoint);
        searchAppoint.setOnClickListener(this);


        calendarView = (CalendarView) findViewById(R.id.calendarView);
calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
    @Override
    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String datePicked = simpleDateFormat.format(new GregorianCalendar(year, month, dayOfMonth).getTime());
        date =datePicked;
        Toast.makeText(getBaseContext(),datePicked,Toast.LENGTH_SHORT).show();
    }
});


        //if the user havent pressed any date
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String datePicked = simpleDateFormat.format(new Date(calendarView.getDate()));
        date = datePicked;

        DBHelperObj = new DBHelper(this,null,null,1);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.createAppoint:
               Intent intentCA = new Intent(getBaseContext(),CreateAppointmentActivity.class);
                intentCA.putExtra("Date",date);
               startActivity(intentCA);
                break;

            case R.id.view_editAppoint:
                Intent intentVE = new Intent(getBaseContext(),DelEditMoveAppoinmentActivity.class);
                intentVE.putExtra("Date",date);
                intentVE.putExtra("Option","edit");
                startActivity(intentVE);
                break;
            case R.id.delAppoint:
                deleteAppointmentPopupWindow(v);
                break;
            case R.id.moveAppoint:
                Intent intentMA = new Intent(getBaseContext(),DelEditMoveAppoinmentActivity.class);
                intentMA.putExtra("Date",date);
                intentMA.putExtra("Option","move");
                startActivity(intentMA);
                break;
            case R.id.searchAppoint:
                Intent intentSA = new Intent(getBaseContext(),SearchAppointmentActivity.class);
               startActivity(intentSA);
                break;
        }

    }

    /**
     * when clicks delete button this will appear as a pop up window
     * with two buttons
     *
     * delete and delete all
     */

    private void deleteAppointmentPopupWindow(View v){


        try {

            LayoutInflater inflater = (LayoutInflater) MainActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

          final   View popUpLayout =  inflater.inflate(R.layout.activity_delete_selection,
                    (ViewGroup) findViewById(R.id.popUpBox));

        deleteWindow = new PopupWindow(popUpLayout,900,900,true);
        deleteWindow.showAtLocation(v, Gravity.CENTER,0,0);

        delete = (Button) popUpLayout.findViewById(R.id.delete);
         deleteAll = (Button) popUpLayout.findViewById(R.id.deleteAll);

            deleteAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Log.i("dateeeee",date);
    Toast.makeText(getBaseContext(),"delete appoinments on " + date,Toast.LENGTH_LONG).show();
              DBHelperObj.deleteAllAppointment(date);
               deleteWindow.dismiss();

                }
            });


            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("done","true");
            Intent intentNew = new Intent(getBaseContext(),DelEditMoveAppoinmentActivity.class);
                    intentNew.putExtra("Date",date);
                    Log.i("date --",date);
                    intentNew.putExtra("Option","delete");
                    startActivity(intentNew);
              deleteWindow.dismiss();
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }



    }
}
