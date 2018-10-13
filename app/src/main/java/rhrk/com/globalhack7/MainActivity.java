package rhrk.com.globalhack7;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        record = findViewById(R.id.record);
        record.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == record){
            Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG).show();
        }
    }
}
