package com.example.a1126;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    
    SwitchCompat aSwitch;
    Button save;
    Button delete;
    TextView textView;
    EditText editText;
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF = "SHARED_PREF";
    private static final String TOGGLE = "SWITCH_TOGGLE";
    private static final String USER_INPUT = "STRING_USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("SHARED_PREF",MODE_PRIVATE); //다른 앱에서 여기에 접근 못함, name은 key 값, 전역으로 하자
        
        aSwitch = findViewById(R.id.SW_Switch);
        save = findViewById(R.id.BT_Save);
        delete = findViewById(R.id.BT_Delete);
        textView = findViewById(R.id.TV_TextView);
        editText = findViewById(R.id.ET_EditText);

        save.setOnClickListener(this);
        delete.setOnClickListener(this);

        boolean isChecked = false;
        String getInput = null;

        isChecked = sharedPreferences.getBoolean(TOGGLE,false);
        // TOGGLE인에 있으면 그 값을 가져오고 없으면 기본값 false로 반환해라
        getInput = sharedPreferences.getString(USER_INPUT,"");

        aSwitch.setChecked(isChecked);
        textView.setText(getInput);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(MainActivity.this, "On", Toast.LENGTH_SHORT).show();

                }
                else
                    Toast.makeText(MainActivity.this, "Off", Toast.LENGTH_SHORT).show();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(TOGGLE, isChecked);
                editor.apply();
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.BT_Save:
                String getInput = editText.getText().toString();
                textView.setText(getInput);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(USER_INPUT,getInput);
                editor.apply();

                break;

            case R.id.BT_Delete:

                textView.setText("");
                SharedPreferences.Editor deleteEditor = sharedPreferences.edit();
                deleteEditor.remove(USER_INPUT);
                deleteEditor.apply();

                break;
        }
    }
}