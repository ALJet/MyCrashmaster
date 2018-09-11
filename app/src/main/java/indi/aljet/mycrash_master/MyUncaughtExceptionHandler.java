package indi.aljet.mycrash_master;

import android.content.Intent;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import indi.aljet.mycrash_master.service.IMyAidlInterface1;

public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    private MyApplication myApplication;
    private Thread.UncaughtExceptionHandler mUncaughtExceptionHandler;

    public MyUncaughtExceptionHandler(MyApplication myApplication) {
        // 获取系统默认的异常处理器
        this.myApplication = myApplication;
        mUncaughtExceptionHandler = Thread
                .getDefaultUncaughtExceptionHandler();

    }


    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        Log.e("indi.aljet.crash","" + System
        .currentTimeMillis() + throwable.getMessage());
        if(!handleException(throwable) &&
                mUncaughtExceptionHandler != null){
            mUncaughtExceptionHandler.uncaughtException(thread,
                    throwable);
        }else{
            Intent i = new Intent(myApplication.getApplicationContext(),
                    IMyAidlInterface1.class);
            i.putExtra("crash",true);
            myApplication.startService(i);
            myApplication.finishActivity();
        }

    }


    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex){
        if(ex == null){
            return false;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(myApplication.getApplicationContext()
                , "很抱歉,程序出现异常,一秒钟后重启.",Toast.LENGTH_SHORT)
                        .show();
                Looper.loop();
            }
        }).start();
        return true;
    }
}
