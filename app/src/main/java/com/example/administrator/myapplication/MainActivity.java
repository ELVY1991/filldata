package com.example.administrator.myapplication;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.os.PowerManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button mButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button)findViewById(R.id.button);

    }
    public void reboot(View view){
        Log.v("zx", "click");
//        PowerManager powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);
//        powerManager.reboot("reboot");
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);
        devicePolicyManager.lockNow();

    }
}
