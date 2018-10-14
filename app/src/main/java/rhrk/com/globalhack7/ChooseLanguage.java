package rhrk.com.globalhack7;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.HashMap;

public class ChooseLanguage extends AppCompatActivity implements ListView.OnItemClickListener{

    ListView languages;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_language);
        languages = findViewById(R.id.languages);
        languages.setClickable(true);
        languages.setOnItemClickListener(this);
        ArrayAdapter<CharSequence> aa = ArrayAdapter.createFromResource(this, R.array.language_array, android.R.layout.simple_list_item_1);
        languages.setAdapter(aa);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String[] values = getResources().getStringArray(R.array.language_array);
        HashMap<String, String> codes = new HashMap<>();
        codes.put("中文", "zh");
        codes.put("English", "en");
        codes.put("हिंदी", "hi");
        codes.put("Español", "es");
        codes.put("Melayu", "ms");
        codes.put("русский", "ru");
        codes.put("বাঙালি", "bn");
        codes.put("Português", "pt");
        codes.put("Français", "fr");
        SharedPreferences mDefaultPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mDefaultPreferences.edit().putBoolean("first_launch", false).commit();
        mDefaultPreferences.edit().putString("source_language", codes.get(values[i])).apply();
        Intent a = new Intent(this, BottomNavigation.class);
        startActivity(a);
    }
}
