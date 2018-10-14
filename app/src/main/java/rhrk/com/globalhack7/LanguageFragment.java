package rhrk.com.globalhack7;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.HashMap;

public class LanguageFragment extends Fragment implements ListView.OnItemClickListener{

    ListView languages;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_language, null);
        languages = view.findViewById(R.id.languages);
        languages.setClickable(true);
        languages.setOnItemClickListener(this);
        ArrayAdapter<CharSequence> aa = ArrayAdapter.createFromResource(getContext(), R.array.language_array, android.R.layout.simple_list_item_1);
        languages.setAdapter(aa);
        return view;
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
        SharedPreferences mDefaultPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = mDefaultPreferences.edit();
        editor.putBoolean("first_launch", false);
        editor.putString("source_language", codes.get(values[i]));
        editor.commit();
        Intent a = new Intent(getContext(), BottomNavigation.class);
        startActivity(a);
    }
}
