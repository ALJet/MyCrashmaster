package indi.aljet.mycrash_master;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {

    List<Activity> list = new ArrayList<>();
    Context context ;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
        Log.e("MYCRASH-_onCrate" , "" +
        System.currentTimeMillis());
    }


    public void init(){
        MyUncaughtExceptionHandler catchException = new
                MyUncaughtExceptionHandler(this);
        Thread.setDefaultUncaughtExceptionHandler(catchException);
    }

    public void removeActivity(Activity a){
        list.remove(a);
    }


    public void addActivity(Activity a){
        list.add(a);
    }


    public void finishActivity(){
        for(Activity activity : list){
            if(activity != null){
                activity.finish();
            }
        }

        android.os.Process.killProcess(android.os.Process
        .myPid());
    }
}
