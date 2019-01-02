package com.example.abc.iwsafe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Arti on 3/11/2018.
 */

public class StopButtonActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopbtn);

        Button stopbtn=(Button)findViewById(R.id.btnStop);
        stopbtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                NavigateRegister(view);
                return false;
            }
        });
    }

    public void NavigateRegister(View view) {
        SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String tempMobileNo=pref.getString("Uname",null);

        Toast.makeText(this, "Processing...", Toast.LENGTH_SHORT).show();
        new StopButtonServerActivity(this).execute(tempMobileNo);


    }
}
