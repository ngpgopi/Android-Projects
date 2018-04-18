package com.gkn.squad.game;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

public class player2Thread extends Thread {

    static int position ;
    static boolean nearMiss = false;
    static boolean nearGroup = false;
    static boolean bigMiss = false;

    @Override
    public void run() {
        Looper.prepare();

        MainActivity.p2Handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                int what = msg.what;
                switch (what){
                    case 1:
                        quitLoop();
                        break;
                    case 2:
                        position = msg.arg1;
                        break;
                    case 3:
                        nearMiss = true;
                        nearGroup = false;
                        bigMiss = false;
                        break;
                    case 4:
                        nearMiss = false;
                        nearGroup = true;
                        bigMiss = false;
                        break;
                    case 5:
                        nearMiss = false;
                        nearGroup = false;
                        bigMiss = true;
                        break;
                }
            }
        };

        Looper.loop();
    }

    public void quitLoop(){
        Looper myloop = Looper.myLooper();
        myloop.quit();
    }
}
