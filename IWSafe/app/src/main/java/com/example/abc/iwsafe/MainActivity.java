package com.example.abc.iwsafe;

import android.content.Context;
import android.content.Intent;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = (ImageView) findViewById(R.id.logo);

        SharedPreferences prf = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        final String str = prf.getString("Uname", "none");

        if (str.equals("none")) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Intent in = new Intent(MainActivity.this, RegisterActivity.class);
                    startActivity(in);
                }


            }).start();
        /*imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigateRegister(view);
            }
        });*/
        } else {
            Intent in = new Intent(MainActivity.this, StartButtonActivity.class);
            startActivity(in);
        }
    }
    public void NavigateRegister(View view) {
        Intent intent = new Intent(getBaseContext(), RegisterActivity.class);
        startActivity(intent);
    }

}
