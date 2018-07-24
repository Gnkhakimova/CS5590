package com.example.karthik.myorder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarActivity extends AppCompatActivity {

    int day1;
    int month1;
    int year1;
    String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        final TextView dateText = (TextView)findViewById(R.id.date);
        //dateText.setText();
        final CalendarView calendarActivity = (CalendarView)findViewById(R.id.simpleCalendarView);
        ImageButton butinsert = (ImageButton) findViewById(R.id.create_eventbut);

        butinsert.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                insert();
            }
        });

        //CalendarView v = new CalendarView( this );
        calendarActivity.setOnDateChangeListener( new CalendarView.OnDateChangeListener() {
            //public GregorianCalendar calendar;

            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                day1 = dayOfMonth;
                month1 = month+1;
                year1 = year;
                date = month1+"/"+day1+"/"+year;
                dateText.setText(date);
            }//met
        });

    }
    @SuppressLint("NewApi")
    public void insert() {
        Intent intent = new Intent(Intent.ACTION_INSERT,
                CalendarContract.Events.CONTENT_URI);
        // Add the calendar event details
        intent.putExtra(CalendarContract.Events.TITLE, "Launch!");
        intent.putExtra(CalendarContract.Events.DESCRIPTION,
                "Learn Java Android Coding");
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION,
                "UMKC.com");
        Calendar startTime = Calendar.getInstance();
        startTime.set(2018, 7, 24);
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                startTime.getTimeInMillis());
        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
        // Use the Calendar app to add the new event.
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



}
