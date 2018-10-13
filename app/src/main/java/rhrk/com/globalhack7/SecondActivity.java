package rhrk.com.globalhack7;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SecondActivity extends AppCompatActivity {
    Button clickButton;
    EditText inputChatText;
    EditText outputChatText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        inputChatText = (EditText) findViewById((R.id.chatTextInput));
        outputChatText = (EditText) findViewById((R.id.chatTextInput));

        clickButton = (Button) findViewById(R.id.btnSubmitChat);

        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = inputChatText.getText().toString();
                Toast msg = Toast.makeText(getBaseContext(),str,Toast.LENGTH_LONG);
                msg.show();
            }
        });
    }





}