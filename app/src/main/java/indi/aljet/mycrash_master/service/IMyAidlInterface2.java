package indi.aljet.mycrash_master.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import indi.aljet.mycrash_master.IMyAidlInterface_2;

public class IMyAidlInterface2 extends Service {

    private String TAG = getClass().getName();
    private String Process_Name = "indi.aljet.mycrash_master:IMyAidlInterface_2";
    private IMyAidlInterface_2 service_2 = new IMyAidlInterface_2.Stub() {
        @Override
        public void startService() throws RemoteException {
            Intent i = new Intent(IMyAidlInterface2.this,
                    IMyAidlInterface1.class);
            IMyAidlInterface2.this.startService(i);
        }

        @Override
        public void stopService() throws RemoteException {
            Intent i = new Intent(IMyAidlInterface2.this,
                    IMyAidlInterface1.class);
            IMyAidlInterface2.this.stopService(i);
        }
    };

    @Override
    public void onCreate() {
        Log.e("indi.aljet_service 2" ,"开启服务 2");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    boolean isRun = isProcessRunning(IMyAidlInterface2
                    .this,Process_Name);
                    if(!isRun){
                        try{
                            Log.i(TAG,"重新启动服务1 ：" +
                            service_2);
                            service_2.startService();
                        }catch (RemoteException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return (IBinder) service_2;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }


    public static boolean isProcessRunning(Context context,
                                           String proessName){
        boolean isRunning = false;
        ActivityManager am = (ActivityManager)context
                .getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo>  lists = am
                .getRunningAppProcesses();
        for(ActivityManager.RunningAppProcessInfo info : lists){
            Log.i("Service2  总进程", ""+info.processName);
            if(info.processName.equals(proessName)){
                Log.i("Service2进程", ""+info.processName);
                isRunning = true;
            }
        }

        return isRunning;
    }
}
