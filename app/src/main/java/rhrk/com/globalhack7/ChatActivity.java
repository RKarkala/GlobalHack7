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
import android.widget.Toast;
import java.util.Locale;

import android.speech.tts.TextToSpeech;


public class ChatActivity extends AppCompatActivity {
    private int MY_DATA_CHECK_CODE=0;
    TextToSpeech myTTS;

    Button clickButton;
    EditText inputChatText;
    EditText outputChatText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent checkTTSIntent= new Intent();
        checkTTSIntent.setAction
                (TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent,MY_DATA_CHECK_CODE);

        inputChatText = (EditText) findViewById((R.id.chatTextInput));
        outputChatText = (EditText) findViewById((R.id.chatTextInput));

        clickButton = (Button) findViewById(R.id.btnSubmitChat);

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


    private void speakWords(String speech){
        myTTS.speak(speech, TextToSpeech.QUEUE_FLUSH, null);

        }


    public void onInit(int initStatus)
    {
        if(initStatus==TextToSpeech.SUCCESS)
        {
            myTTS.setLanguage(Locale.US);
        }
        else if(initStatus==TextToSpeech.ERROR)
        {Toast.makeText(this,"Sorry!TextToSpeech failed...",
                Toast.LENGTH_LONG).show();}
    }




    }





