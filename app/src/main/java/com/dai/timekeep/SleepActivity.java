package com.dai.timekeep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import java.sql.Time;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import android.content.SharedPreferences;


public class SleepActivity extends AppCompatActivity {

    private TimePicker tp;
    private SharedPreferences sharedPreferences;

    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_sleep);
        tp = findViewById(R.id.sleepPicker);
        sharedPreferences = getSharedPreferences(getString(R.string.sharedPrefs), MODE_PRIVATE);
        setupPicker();
    }

    private void setupPicker() {
        int hour = sharedPreferences.getInt( getString(R.string.sleepHourKey), 0);
        int minute = sharedPreferences.getInt( getString(R.string.sleepMinuteKey), 0);
        tp.setHour(hour);
        tp.setMinute(minute);
        tp.setIs24HourView(false);
    }

    public void divide(View view) {
        Date now = new Date();
        int nowMinutes = now.getHours() * 60 + now.getMinutes();
        int thenMinutes = tp.getHour() * 60 + tp.getMinute();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getString(R.string.sleepHourKey), tp.getHour());
        editor.putInt(getString(R.string.sleepMinuteKey), tp.getMinute());
        editor.commit();
        int duration = (24*60 + thenMinutes - nowMinutes)%(24*60);
        duration *=  60 * 1000; //minutes to milliseconds
        //fixme CALENDAR BIZ
//        if(sharedPreferences.contains(getString(R.string.calendarIdKey))){
//            int calendarID = sharedPreferences.getInt(getString(R.string.calendarIdKey), 0);
//            Uri.Builder builder = CalendarContract.Instances.CONTENT_URI.buildUpon();
//            long startMillis = now.getTime();
//            long endMillis = startMillis + duration;
//            ContentUris.appendId(builder, startMillis);
//            ContentUris.appendId(builder, endMillis);
//            Cursor eventCursor = getContentResolver().query(builder.build(), new String[]{CalendarContract.Instances.TITLE,
//                            CalendarContract.Instances.BEGIN, CalendarContract.Instances.END, CalendarContract.Instances.DESCRIPTION},
//                    CalendarContract.Instances.CALENDAR_ID + " = ?", new String[]{Integer.toString(calendarID)}, null);
//            int sum = 0;
//            while (eventCursor.moveToNext()) {
//                //fixme NOT DONE, NEED TO DEDUCT BETTER
//                //final String title = eventCursor.getString(0);
//                final Date begin = new Date(eventCursor.getLong(1));
//                final Date end = new Date(eventCursor.getLong(2));
//                //final String description = eventCursor.getString(3);
//                sum += end.getTime() - begin.getTime();
//            }
//            duration -= sum;
//        }
        Intent i1 = new Intent(this, TaskActivity.class);
        i1.putExtra(getString(R.string.taskBooleanExtra), true);
        i1.putExtra(getString(R.string.taskSleepLengthExtra), duration);
        startActivity(i1);
    }

}