package rhrk.com.globalhack7;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.Locale;
import android.speech.tts.TextToSpeech;


public class ChatActivity extends AppCompatActivity  {

    TextToSpeech tts;
    Button clickButton;
    EditText inputChatText;
    EditText outputChatText;
    private boolean enableTTS = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        inputChatText = (EditText) findViewById((R.id.chatTextInput));
        outputChatText = (EditText) findViewById((R.id.chatTextInput));
        clickButton = (Button) findViewById(R.id.btnSubmitChat);

        final String inText = inputChatText.getText().toString();
        String outText = inputChatText.getText().toString();

        tts=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.US);
                }
            }
        });

        tts.speak("hey speak now!!", TextToSpeech.QUEUE_FLUSH, null);

        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder a_builder = new AlertDialog.Builder(ChatActivity.this);
                a_builder.setMessage("Do you want to submit the chat message ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = a_builder.create();
                alertDialog.setTitle("Aleart !");
                alertDialog.show();

//                String str = inputChatText.getText().toString();
//                Toast msg = Toast.makeText(getBaseContext(),str,Toast.LENGTH_LONG);
//                msg.show();
            }
        });

    }
    public void onPause(){
        if(tts !=null){
            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }




    }





