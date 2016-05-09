package com.example.zhaoxia.lockscreen;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    private DevicePolicyManager policyManager;
    private ComponentName componentName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        LockScreen();
    }
    public void LockScreen() {
        policyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        componentName = new ComponentName(this, LockReceiver.class);
        if (policyManager.isAdminActive(componentName)) {
            policyManager.lockNow();
            android.os.Process.killProcess(android.os.Process.myPid());
        } else {
            activeManager();
        }
    }
    public void Bind(){
        if (componentName!=null){
            policyManager.removeActiveAdmin(componentName);
            activeManager();
        }
    }

    @Override
    protected void onResume() {
        if (policyManager!=null&& policyManager.isAdminActive(componentName)){
            policyManager.lockNow();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
        super.onResume();
    }

    public void activeManager(){
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,componentName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"哥们开启我可以一键锁屏，你的按钮就不会经常失灵");
        startActivity(intent);
    }
PowerManager
    public void shutdown(View view){
        try {

            //获得ServiceManager类
            Class ServiceManager = Class
                    .forName("android.os.ServiceManager");

            //获得ServiceManager的getService方法
            Method getService = ServiceManager.getMethod("getService", java.lang.String.class);

            //调用getService获取RemoteService
            Object oRemoteService = getService.invoke(null, Context.POWER_SERVICE);

            //获得IPowerManager.Stub类
            Class cStub = Class
                    .forName("android.os.IPowerManager$Stub");
            //获得asInterface方法
            Method asInterface = cStub.getMethod("asInterface", android.os.IBinder.class);
            //调用asInterface方法获取IPowerManager对象
            Object oIPowerManager = asInterface.invoke(null, oRemoteService);
            //获得shutdown()方法
            Method shutdown = oIPowerManager.getClass().getMethod("shutdown",boolean.class,boolean.class);
            //调用shutdown()方法
            shutdown.invoke(oIPowerManager,false,true);

        } catch (Exception e) {
            Log.e("zhaoxia", e.toString(), e);
        }

    }

}
