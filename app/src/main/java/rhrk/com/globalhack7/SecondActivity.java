package rhrk.com.globalhack7;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class SecondActivity extends AppCompatActivity {
    Button clickButton;

    Button.OnClickListener buttonListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        clickButton = (Button) findViewById(R.id.button1);
        clickButton.setOnClickListener(buttonListener);
    }




}