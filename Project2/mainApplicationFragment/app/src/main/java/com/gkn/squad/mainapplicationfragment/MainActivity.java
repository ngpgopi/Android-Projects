package com.gkn.squad.mainapplicationfragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    int buttonselected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt1 = findViewById(R.id.button1);
        Button bt2 = findViewById(R.id.button2);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonselected = 1;
                checkPermission();
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonselected = 2;
                checkPermission();
            }
        });


    }

    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, "edu.uic.cs478.sp18.project3")
                == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, getClass().getSimpleName() + "Permission available" );
            sendbroadcastinfo();
        }
        else {
            Log.i(TAG, getClass().getSimpleName() + "Requesting permission" );
            ActivityCompat.requestPermissions(this, new String[]{"edu.uic.cs478.sp18.project3"}, 0) ;
        }
    }
    public void onRequestPermissionsResult(int code, String[] permissions, int[] results) {
        if (results.length > 0) {
            if (results[0] == PackageManager.PERMISSION_GRANTED) {
                sendbroadcastinfo();
            }
            else {
                Toast.makeText(this, "Bummer: No permission", Toast.LENGTH_SHORT) ;
            }
        }
    }

    public void sendbroadcastinfo() {
        if(buttonselected == 1) {
            Intent intent = new Intent();
            intent.setAction("com.gkn.squad.intent.fragmentassignment.MainActivity");
            intent.putExtra("button",1);
            Toast toast = Toast.makeText(getApplicationContext(), "Attractions was clicked!", Toast.LENGTH_SHORT);
            toast.show();
            sendBroadcast(intent);
        } else if(buttonselected == 2){
            Intent intent = new Intent();
            intent.setAction("com.gkn.squad.intent.fragmentassignment.Main2Activity");
            intent.putExtra("button",2);
            Toast toast = Toast.makeText(getApplicationContext(), "Restaurants was clicked!", Toast.LENGTH_SHORT);
            toast.show();
            sendBroadcast(intent);
        }
    }
}
