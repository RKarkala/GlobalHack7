package rhrk.com.globalhack7;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class BottomNavigation extends AppCompatActivity {

    private TextView mTextMessage;
    private final int ALL_CODE = 11;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment fragment = null;

            switch (item.getItemId()) {
                case R.id.translate:
                    fragment = new MainActivity();
                    return loadFragment(fragment);
                case R.id.chat:
                    fragment = new NewChatActivity();
                    return loadFragment(fragment);
                case R.id.clinics:
                    System.out.println("clinics");
                    fragment = new MapsActivity();
                    return loadFragment(fragment);
                case R.id.settings:
                    fragment = new LanguageFragment();
                    return loadFragment(fragment);
            }
            return true;

        }
    };

    private boolean loadFragment(Fragment fragment){
        if(fragment!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            return true;
        }
        System.out.println(false);
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        mTextMessage = (TextView) findViewById(R.id.message);
        String[] permissions = {android.Manifest.permission.RECORD_AUDIO,
                android.Manifest.permission.INTERNET,
                android.Manifest.permission.ACCESS_NETWORK_STATE,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION};
        if(!hasPermissions(this, permissions)){
            ActivityCompat.requestPermissions(this, permissions, ALL_CODE);
        }
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String sourceLanguageCode = sharedPrefs.getString("source_language", "zh");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        String[] translateStrings = new String[] {navigation.getMenu().getItem(0).getTitle().toString(), sourceLanguageCode};
        String[] chatString = new String[] {navigation.getMenu().getItem(1).getTitle().toString(), sourceLanguageCode};
        String[] mapString = new String[] {navigation.getMenu().getItem(2).getTitle().toString(), sourceLanguageCode};
        try {
            navigation.getMenu().getItem(0).setTitle(new Translate().execute(translateStrings).get());
            navigation.getMenu().getItem(1).setTitle(new Translate().execute(chatString).get());
            navigation.getMenu().getItem(2).setTitle(new Translate().execute(mapString).get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        loadFragment(new MainActivity());
    }
    protected boolean hasPermissions(Context context, String permissions[]) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case ALL_CODE:
                if(grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

                }else{
                    Toast.makeText(this, "Permissions required", Toast.LENGTH_LONG).show();
                }
        }
    }

}
