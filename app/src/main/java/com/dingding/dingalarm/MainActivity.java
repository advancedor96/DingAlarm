package com.dingding.dingalarm;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Calendar cal;
    FloatingActionButton fab;
    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;
    EditText et_min;
    SharedPreferences pref ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
    }

    private void initView() {
        pref = getSharedPreferences("DingAlarm", MODE_PRIVATE);
        et_min = findViewById(R.id.et_min);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCalendar();

            }
        });

        String lastMinute = pref.getString("MINUTE", "");
        if(!lastMinute.equals("")){
            Log.d("xding", "有上一次的資料");
            et_min.setText(lastMinute);
        } else {
            et_min.setText("");
            Log.d("xding", "上一次沒有資料");

        }

    }
    public void showCalendar(){
        cal = Calendar.getInstance();
        int hourOfDay = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                Log.d("xding", "first:"+ i);
                Log.d("xding", "second:"+ i1);
            }
        }, hourOfDay, minute, true);
        timePickerDialog.show();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void runIt(View view) {
        String leftSecond =  et_min.getText().toString();
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, Integer.valueOf(leftSecond));

        Intent intent = new Intent(this, PlayReceiver.class);
        intent.putExtra("msg", leftSecond);
        PendingIntent pi = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_ONE_SHOT);
        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);

        pref.edit().putString("MINUTE", leftSecond).commit();
    }
    public void playAfter(View view) {
        if (view.getId() == R.id.button) {
            et_min.setText("" + 3*60);
//            AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//            cal = Calendar.getInstance();
//            // 設定於 3 分鐘後執行
//            cal.add(Calendar.SECOND, 3);
//
//
//            Intent intent = new Intent(this, PlayReceiver.class);
//            intent.putExtra("msg", "3");
//            long atTimeInMillis = System.currentTimeMillis() + 3000;
//
//            PendingIntent pi = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_ONE_SHOT);
//
//            am.set(AlarmManager.RTC_WAKEUP, atTimeInMillis, pi);

        } else if (view.getId() == R.id.button2) {
            et_min.setText("" + 5*60);
//            AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//            cal = Calendar.getInstance();
//            cal.clear();
//            // 設定於 3 分鐘後執行
//            cal.add(Calendar.SECOND, 5);
//
//            Intent intent = new Intent(this, PlayReceiver.class);
//            intent.putExtra("msg", "5");
//
//            PendingIntent pi = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_ONE_SHOT);
//
//            am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);

        } else if (view.getId() == R.id.button3) {
            et_min.setText("" + 10*60);

        }


    }

}
