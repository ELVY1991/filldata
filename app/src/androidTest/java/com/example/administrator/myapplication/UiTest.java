package com.example.administrator.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by Administrator on 2016/4/10.
 */
@RunWith(AndroidJUnit4.class)
public class UiTest {
    private static final String BASIC_SAMPLE_PACKAGE
            = "com.android.mms";

    private static final int LAUNCH_TIMEOUT = 5000;

    UiDevice mDevice;
    @Before
    public  void start() throws RemoteException {
/*        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mDevice.wakeUp();
        mDevice.swipe(50, mDevice.getDisplayHeight() / 2, mDevice.getDisplayWidth() - 50, mDevice.getDisplayHeight() / 2, 20);
        Intent intent = new Intent();
        intent.setClassName("com.example.android.testing.uiautomator.BasicSample", "com.example.android.testing.uiautomator.BasicSample.MainActivity");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        InstrumentationRegistry.getInstrumentation().startActivitySync(intent);*/

        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Start from the home screen
        mDevice.pressHome();

/*        // Wait for launcher
        final String launcherPackage = getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(getLauncherPackageName()).depth(0)), LAUNCH_TIMEOUT);

        // Launch the blueprint app
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);    // Clear out any previous instances
        context.startActivity(intent);

        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)), LAUNCH_TIMEOUT);*/

    }

    @Test
    public void testButton() throws RemoteException, InterruptedException, UiObjectNotFoundException {
        mDevice.pressBack();
/*        mDevice.findObject(By.res(BASIC_SAMPLE_PACKAGE,"avatar")).click();
        SystemClock.sleep(3000);
        mDevice.findObject(By.res("com.android.contacts","oneplus_quickcontact_menu")).click();
        SystemClock.sleep(1000);
        mDevice.findObject(By.text("删除联系人")).click();
        SystemClock.sleep(1000);
        mDevice.findObject(By.text("确定")).click();*/
//        mDevice.findObject(By.res(BASIC_SAMPLE_PACKAGE, "avatar")).click();
//        mDevice.findObject(new UiSelector().resourceId("android:id/button1")).click();

    }


    private String getLauncherPackageName() {
        // Create launcher Intent
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        // Use PackageManager to get the launcher package name
        PackageManager pm = InstrumentationRegistry.getContext().getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.activityInfo.packageName;

    }
}
