package indi.aljet.mycrash_master;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import indi.aljet.mycrash_master.service.IMyAidlInterface2;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    private TextView mTextView;
    private MyApplication myApplication;
    private long mExitTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myApplication = (MyApplication)getApplication();
        myApplication.init();
        myApplication.addActivity(this);

        mButton = findViewById(R.id.btn);
        mTextView = findViewById(R.id.show);

        Intent i = new Intent(MainActivity.this,
                IMyAidlInterface2.class);
        startService(i);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pressed();
            }
        });

    }


    private void pressed(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mTextView.setText("beng....");
            }
        }).start();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if((System.currentTimeMillis() - mExitTime) > 2000){
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            }else{
                android.os.Process.killProcess(android.os.Process
                .myPid());
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
