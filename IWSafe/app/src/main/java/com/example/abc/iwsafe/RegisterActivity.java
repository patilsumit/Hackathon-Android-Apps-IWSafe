package com.example.abc.iwsafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Arti on 3/10/2018.
 */

public class RegisterActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button register=(Button)findViewById(R.id.btnRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserRegister(view);
            }
        });

    }

    public void UserRegister(View view) {
        EditText name=(EditText)findViewById(R.id.etName);
        EditText address=(EditText)findViewById(R.id.etAddress);
        EditText city=(EditText)findViewById(R.id.etCity);
        EditText state=(EditText)findViewById(R.id.etState);
        EditText contactNo=(EditText)findViewById(R.id.etContactNo);
        EditText guardianName=(EditText)findViewById(R.id.etGuardianName);
        EditText guardianNo=(EditText)findViewById(R.id.etGuardianNo);

            String tempName=name.getText().toString();
            String tempAddress=address.getText().toString();
            String tempCity=city.getText().toString();
            String tempState=state.getText().toString();
            String tempContactNo=contactNo.getText().toString();
            String tempGuardianName=guardianName.getText().toString();
            String tempGuardianNo=guardianNo.getText().toString();

        //if(tempName.length()!=0 && tempAddress.length()!=0 && tempCity.length()!=0 && tempState.length()!=0 && tempContactNo.length()!=0 && tempGuardianName.length()!=0 && tempGuardianNo.length()!=0)
        //{
            Toast.makeText(this, "Processing...", Toast.LENGTH_SHORT).show();
        new RegisterServerActivity(this).execute(tempName,tempAddress,tempCity,tempState,tempContactNo,tempGuardianName,tempGuardianNo);


        //}
        //else {
        //Toast.makeText(getApplicationContext(),"Fill the empty field.",Toast.LENGTH_LONG);
        //}


    }
}
