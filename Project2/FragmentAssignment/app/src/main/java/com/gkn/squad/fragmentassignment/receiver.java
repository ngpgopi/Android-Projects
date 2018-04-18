package com.gkn.squad.fragmentassignment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

public class receiver  extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if(extras.getInt("button") == 1) {
            Intent i = new Intent(context, MainActivity.class);
            context.startActivity(i);
        } else if(extras.getInt("button") == 2) {
            Intent i = new Intent(context, Main2Activity.class);
            context.startActivity(i);
        }
        Toast.makeText(context, "Welcome:)", Toast.LENGTH_LONG).show();
    }
}
