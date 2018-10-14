package rhrk.com.globalhack7;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TimerTask task = new TimerTask() {
            @Override
            public void run(){
                finish();
                SharedPreferences mDefaultPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                if (mDefaultPreferences.getBoolean("first_launch", true))
                {
                    Intent intent = new Intent(SplashActivity.this, FirstHomeScreen.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }

            }
        };

        Timer opening = new Timer();
        opening.schedule(task, 1000);
    }
}
