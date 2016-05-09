package com.example.zx.myapplication;

import android.Manifest;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.net.URI;
import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String TAG ="zhaoxia";
    Button calendar,smsButton,notifiButton;
    String defaultSmsApp = null;
    private static final int WRITE_CALENDAR_CODE =1;
    Uri uri = CalendarContract.Events.CONTENT_URI;
    public static Uri mSmsUri = Uri.parse("content://sms/inbox");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendar = (Button) findViewById(R.id.calendar);
        smsButton = (Button)findViewById(R.id.button);
        notifiButton = (Button)findViewById(R.id.notifi);
        defaultSmsApp = Telephony.Sms.getDefaultSmsPackage(this);
    }

    public void addCalendar(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, WRITE_CALENDAR_CODE);
        }else
            addevent();

    }

    public void addevent(){
        Calendar calendar = Calendar.getInstance();
        ContentResolver cr = getContentResolver();
        ContentValues event = new ContentValues();
        event.put(CalendarContract.Events.TITLE, "event");
        Calendar mCalendar = Calendar.getInstance();
        long startMillis = calendar.getTimeInMillis();
        event.put(CalendarContract.Events.DTSTART, startMillis);
        event.put(CalendarContract.Events.DTEND, startMillis);
        event.put(CalendarContract.Events.CALENDAR_ID, 1);
        event.put(CalendarContract.Events.EVENT_TIMEZONE,"GMT+8");
        cr.insert(uri,event);
    }

    //添加短信
    public void addSms(View view){
        setDefalutSms();
        ContentValues values = new ContentValues();
        values.put("address", "13898878776");
        values.put("body", "您好!");
        values.put("date", 20111101);
        values.put("read", 0);
        values.put("type", 1);
        values.put("service_center", "+8613010776500");
        getContentResolver().insert(mSmsUri, values);
    }

    public void setDefalutSms(){
        if(android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.KITKAT){
            String mPackageName = getPackageName();
            Log.v(TAG,mPackageName);
            if(!defaultSmsApp.equals(mPackageName)){
                Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
                intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, getPackageName());
                startActivity(intent);
            }
        }
    }
    //添加联系人
    public void testAddContact() throws Exception{
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        ContentResolver resolver = getContentResolver();
        ContentValues values = new ContentValues();
        long contactid = ContentUris.parseId(resolver.insert(uri, values));
        uri = Uri.parse("content://com.android.contacts/data");

        //添加姓名
        values.put("raw_contact_id", contactid);
        values.put(ContactsContract.Data.MIMETYPE, "vnd.android.cursor.item/name");
        values.put("data1", "xiaoming");
        resolver.insert(uri, values);
        values.clear();

        //添加电话
        values.put("raw_contact_id", contactid);
        values.put(ContactsContract.Data.MIMETYPE, "vnd.android.cursor.item/phone_v2");
        values.put("data1", "1234120155");
        resolver.insert(uri, values);
        values.clear();

        //添加Email
        values.put("raw_contact_id", contactid);
        values.put(ContactsContract.Data.MIMETYPE, "vnd.android.cursor.item/email_v2");
        values.put("data1", "1234120155@qq.com");
        resolver.insert(uri, values);
    }
    //添加闹钟
    public void creatalarm(int hour,int minutes){
        Intent localIntent = new Intent("android.intent.action.SET_ALARM");
        localIntent.putExtra("android.intent.extra.alarm.SKIP_UI", true);
        localIntent.putExtra("android.intent.extra.alarm.VIBRATE", false);
        localIntent.putExtra("android.intent.extra.alarm.RINGTONE", "silent");
        localIntent.putExtra("android.intent.extra.alarm.HOUR", hour);
        localIntent.putExtra("android.intent.extra.alarm.MINUTES", minutes);
        MainActivity.this.startActivity(localIntent);
    }

    public void addNotification(View view){
        NotificationManager mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("emily")
                .setContentText("haha")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis());
        mNotificationManager.notify(1,mBuilder.build());

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v(TAG, "permission ok");
            addevent();
        }
        else
            Log.v(TAG,"permission denied");
    }

}
