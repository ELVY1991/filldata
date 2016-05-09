package com.example.administrator.intent;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button mButton,batteryButton;
    private static final String BC_ACTION = "com.example.zx.intentdemo.BC_ACTION";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button)findViewById(R.id.button);
        batteryButton = (Button)findViewById(R.id.button2);
        setUpAutoCompleteText();
    }

    public void alarm(View view){
        final AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        Intent intent = new Intent();

        intent.setAction(BC_ACTION);
        intent.putExtra("msg", "meeting time");
        final PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this,0,intent,0);
        long time =System.currentTimeMillis();
//        am.setRepeating(AlarmManager.RTC_WAKEUP,time,8*1000,pi);
        am.set(AlarmManager.RTC_WAKEUP, time + 20000, pi);
    }

    public void setUpAutoCompleteText(){
        String[] complete = getResources().getStringArray(R.array.bodies_of_water);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,complete);
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView.setAdapter(arrayAdapter);

    }

    public void startBattery(View view){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
        Intent mIntent = new Intent();
        mIntent.setClass(MainActivity.this,MyService.class);
        startService(mIntent);
    }

}
