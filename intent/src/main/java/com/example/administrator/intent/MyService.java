package com.example.administrator.intent;

import android.Manifest;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.BatteryManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/5/6.
 */
public class MyService extends Service{
    int percent,tempre;
    String status;
    File file = new File("/sdcard/bty.txt");
    BroadcastReceiver btyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)){
                percent = intent.getIntExtra("level", 0);
                tempre = intent.getIntExtra("temperature",0);

                writeFile();
                switch (intent.getIntExtra("health", BatteryManager.BATTERY_HEALTH_UNKNOWN))
                {
                    case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                        status = "未知错误";
                        break;
                    case BatteryManager.BATTERY_HEALTH_GOOD:
                        status = "状态良好";
                        break;
                    case BatteryManager.BATTERY_HEALTH_DEAD:
                        status = "电池没电";
                        break;
                    case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                        status = "电压过高";
                        break;
                    case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                        status =  "电池过热";
                        break;
                }
            }
        }
    };
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        registerReceiver(btyReceiver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(btyReceiver);
    }

    public void writeFile(){

        try {
            FileOutputStream fos = new FileOutputStream(file,true);//true  追加
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(getTime()+","+percent+"%,"+tempre);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStreamWriter.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd,HH:mm");
        Date date = new Date(System.currentTimeMillis());
        String time = simpleDateFormat.format(date);
        return  time;
    }
}
