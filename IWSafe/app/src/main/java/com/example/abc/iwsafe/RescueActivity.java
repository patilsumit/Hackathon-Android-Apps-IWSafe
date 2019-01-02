package com.example.abc.iwsafe;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by admin on 23-03-2018.
 */

public class RescueActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rescue);

        Button rescuebtn = (Button) findViewById(R.id.btnRescue);
        rescuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RescueOK(v);
            }
        });
    }
    public void RescueOK(View view) {
        EditText rescueCode = (EditText) findViewById(R.id.etRCode);
        EditText rescueDetails = (EditText) findViewById(R.id.etReport);
        TextView mobileNo = (TextView) findViewById(R.id.tvMobileNo);
        SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String tempMobileNo=pref.getString("Uname",null);
        //TextView mobileNo = (TextView) findViewById(R.id.tvMobileNo);

        String tempRCode = rescueCode.getText().toString();
        String tempRDetails=rescueDetails.getText().toString();
        //String tempMobileNo = mobileNo.getText().toString();

        //if (tempOTP != null && tempMobileNo != null) {
        Toast.makeText(this, "Processing...", Toast.LENGTH_SHORT).show();
        new RescueServerActivity(this).execute(tempRCode, tempMobileNo,tempRDetails);
        //} else {
        //Toast.makeText(this, "Fill the empty field.", Toast.LENGTH_SHORT).show();
        //}
    }
}
