package com.gkn.squad.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();
    Bitmap bitMap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
    Bitmap bitMapWin = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
    Bitmap bitMapCat = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
    Bitmap winningBitMap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
    ListView l1;
    public static Handler maHandler;
    public static Handler p1Handler;
    public static Handler p2Handler;
    public String flag;
    public boolean firstp1 = true;
    public boolean firstp2 = true;
    public int winningHole;
    ArrayList<Integer> player1Holes = new ArrayList<>();
    ArrayList<Integer> player2Holes = new ArrayList<>();
    int winningHoleGroup;
    int prevP1, prevP2;
    int max,min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final myAdapter arr = new myAdapter();
        createBitMap();
        Random random = new Random();
        winningHole = random.nextInt(50 - 0) + 0;
        winningHoleGroup = winningHole / 10;
        Log.i("Winning hole" , String.valueOf(winningHole));
        for(int i = 0; i <= 50 ; i++){
            if( i != winningHole){
                bitmapArray.add(i,bitMap);
            }
            else{
                bitmapArray.add(i,winningBitMap);
            }
        }
        Toast t1 = Toast.makeText(getApplicationContext(),"Game Started", Toast.LENGTH_SHORT);
        t1.show();

        l1 = findViewById(R.id.list1);
        l1.setAdapter(arr);

        maHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                int what = msg.what;
                switch (what){
                    case 0:
                        if(msg.arg1 == winningHole){
                            addCapturedHoles(msg.arg1);
                            Toast t1 = Toast.makeText(getApplicationContext(),flag + " Won the game", Toast.LENGTH_SHORT);
                            t1.show();
                            // quit the looper in both player threads since the game is completed.
                            Message msg1 = p1Handler.obtainMessage(1);
                            p1Handler.sendMessage(msg1);
                            msg1 = p2Handler.obtainMessage(1);
                            p2Handler.sendMessage(msg1);
                            bitmapArray.set(winningHole,bitMapWin);
                            arr.notifyDataSetChanged();
                            l1.smoothScrollToPosition(msg.arg1);
                            Log.i("status",flag + " Won and Game Finished");
                            break;
                        }
                        else{
                            boolean cc = false;
                            if(!firstp2){
                                cc = checkCatastrophe(msg.arg1);
                                bitmapArray.set(msg.arg1,bitMapCat);
                                arr.notifyDataSetChanged();
                                l1.smoothScrollToPosition(msg.arg1);
                            }
                            if(!cc){
                                addCapturedHoles(msg.arg1);
                                Bitmap bitMap2 = (Bitmap) msg.obj;
                                bitmapArray.set(msg.arg1,bitMap2);
                                arr.notifyDataSetChanged();
                                l1.smoothScrollToPosition(msg.arg1);
                                sendMessagetoRespectivePlayer(msg.arg1);
                                startNextPlayer();
                            }
                            else {
                                Toast t1 = Toast.makeText(getApplicationContext(),"Catastrophe for " + flag, Toast.LENGTH_SHORT);
                                t1.show();
                                if(flag.equals("player1")){
                                    t1 = Toast.makeText(getApplicationContext(),"Player2 Won",Toast.LENGTH_SHORT);
                                    t1.show();
                                }
                                else{
                                    t1 = Toast.makeText(getApplicationContext(),"Player2 Won",Toast.LENGTH_SHORT);
                                    t1.show();
                                }
                            }
                        }
                        break;
                    case 1:
                        Toast t1 = Toast.makeText(getApplicationContext(),"Player 1 turn", Toast.LENGTH_SHORT);
                        t1.show();
                        flag = "player1";
                        //This if statement can be uncommented in order to display the entire list of previous holes of player1
                        if(!firstp1){
                            bitmapArray.set(prevP1,bitMap);
                            arr.notifyDataSetChanged();
                        }
                        strategy();
                        break;
                    case 2:
                        t1 = Toast.makeText(getApplicationContext(),"Player 2 turn", Toast.LENGTH_SHORT);
                        t1.show();
                        flag = "player2";
                        //This if statement can be uncommented in order to display the entire list of previous holes of player2
                        if(!firstp2){
                            bitmapArray.set(prevP2,bitMap);
                            arr.notifyDataSetChanged();
                        }
                        strategy();
                        break;
                    case 3:
                        if (flag == "player1") {
                            firstp1 = (boolean) msg.obj;
                        }
                        else{
                            firstp2 = (boolean) msg.obj;
                        }
                }
            }
        };
        l1.setSelection(winningHole);
        player1Thread p1 = new player1Thread();
        p1.start();
        player2Thread p2 = new player2Thread();
        p2.start();
    }

    // main Thread sends an respective message to worker threads based on the new position
    private void sendMessagetoRespectivePlayer(int num) {
        Message msg1;
        if(flag == "player1"){
            prevP1 = num;
            int newPositionGroup = num / 10;
            if(winningHoleGroup == newPositionGroup){
                msg1 = p1Handler.obtainMessage(3);
            }
            else if((winningHoleGroup == newPositionGroup-1) || (winningHoleGroup == newPositionGroup+1)){
                msg1 = p1Handler.obtainMessage(4);
            }
            else{
                msg1 = p1Handler.obtainMessage(5);
            }
            p1Handler.sendMessage(msg1);
        }
        else{
            prevP2 = num;
            int newPositionGroup = num / 10;
            if(winningHoleGroup == newPositionGroup){
                msg1 = p2Handler.obtainMessage(3);
            }
            else if((winningHoleGroup == newPositionGroup-1) || (winningHoleGroup == newPositionGroup+1)){
                msg1 = p2Handler.obtainMessage(4);
            }
            else{
                msg1 = p2Handler.obtainMessage(5);
            }
            p2Handler.sendMessage(msg1);
        }
    }

    //adds the new holes to the occupied holes array of the two players
    private void addCapturedHoles(int num){
        if(flag.equals("player1")){
            player1Holes.add(num);
            Message msg = p1Handler.obtainMessage(2);
            msg.arg1 = num;
            p1Handler.sendMessage(msg);
        }
        else {
            player2Holes.add(num);
            Message msg = p2Handler.obtainMessage(2);
            msg.arg1 = num;
            p2Handler.sendMessage(msg);
        }
    }

    // Start next player based on the flag; if flag is player1 , call player two and vice versa
    private void startNextPlayer() {
        if(flag.equals("player1")){
            p2Handler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message msg = maHandler.obtainMessage(2);
                    maHandler.sendMessage(msg);
                }
            });
        }
        if(flag.equals("player2")){
            p1Handler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message msg = maHandler.obtainMessage(1);
                    maHandler.sendMessage(msg);
                }
            });
        }
    }

    // the main strategy of the game for a respective player
    // posts runnables to respective player threads.
    // Once the runnable is executed it sends an message with new position and bitmap to main thread
    private void strategy() {
        if (flag.equals("player1")) {
            p1Handler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int p;
                    Random random = new Random();
                    if(firstp1) {
                       max = 50;
                       min = 0;
                       Message msg = maHandler.obtainMessage(3);
                       msg.obj = false;
                       maHandler.sendMessage(msg);
                       Log.i("P1status","First");
                    }
                    else if(player1Thread.nearMiss){
                        Log.i("P1status","NearMiss");
                        sameGroup();
                    }
                    else if(player1Thread.nearGroup){
                        Log.i("P1status","nearGroup");
                        closeGroup();
                    }
                    else if(player1Thread.bigMiss){
                        Log.i("P1status","bigMiss");
                        max = 50;
                        min = 0;
                    }

                    p  = random.nextInt(max-min ) + min;
                    // choose different p if the choosen value is already present in the p1 history array
                    while (player1Holes.contains(p)){
                        p  = random.nextInt(max-min) + min;
                    }
                    Log.i("P1" , String.valueOf(p));

                    Bitmap bitMap1;
                    bitMap1 = bitmapArray.get(p).copy(bitmapArray.get(p).getConfig(), true);
                    Canvas canvas = new Canvas(bitMap1);
                    Paint paint = new Paint();
                    paint.setColor(Color.BLUE);
                    paint.setAntiAlias(true);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setStrokeWidth(4.5f);
                    canvas.drawCircle(50, 50, 30, paint);
                    Message msg = maHandler.obtainMessage(0);
                    msg.arg1 = p;
                    msg.obj = bitMap1;
                    maHandler.sendMessage(msg);
                }
            });
        }
        else{
            p2Handler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Random random = new Random();
                    int p;
                    if(firstp2) {
                        max = 50;
                        min = 0;
                        Message msg = maHandler.obtainMessage(3);
                        msg.obj = false;
                        maHandler.sendMessage(msg);
                        Log.i("P2status","First");
                    }
                    else if(player2Thread.nearMiss){
                        Log.i("P2status","NearMiss");
                        sameGroup();
                    }
                    else if(player2Thread.nearGroup){
                        Log.i("P2status","nearGroup");
                        closeGroup();
                    }
                    else if(player2Thread.bigMiss){
                        Log.i("P2status","bigMiss");
                        max = 50;
                        min = 0;
                    }
                    p  = random.nextInt(max-min) + min;
                    // choose different p if the choosen value is already present in the p2 history array
                    while (player2Holes.contains(p)){
                        p  = random.nextInt(max-min) + min;
                    }
                    Log.i("P2" , String.valueOf(p));
                    checkCatastrophe(p);
                    Bitmap bitMap1;
                    bitMap1 = bitmapArray.get(p).copy(bitmapArray.get(p).getConfig(), true);
                    Canvas canvas = new Canvas(bitMap1);
                    Paint paint = new Paint();
                    paint.setColor(Color.RED);
                    paint.setAntiAlias(true);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setStrokeWidth(4.5f);
                    canvas.drawCircle(50, 50, 30, paint);
                    Message msg = maHandler.obtainMessage(0);
                    msg.arg1 = p;
                    msg.obj = bitMap1;
                    maHandler.sendMessage(msg);
                }
            });
        }
    }

    // strategy for same group
    private void sameGroup() {
        if(winningHoleGroup == 0){
            max = 9;
            min = 0;
        }
        if(winningHoleGroup == 1){
            max = 19;
            min = 10;
        }
        if(winningHoleGroup == 2){
            max = 29;
            min = 20;
        }
        if(winningHoleGroup == 3){
            max = 39;
            min = 30;
        }
        if(winningHoleGroup == 4){
            max = 49;
            min = 40;
        }
    }

    // strategy for close group
    private void closeGroup(){
        if(winningHoleGroup ==0){
            max = 19;
            min = 0;
        }
        if(winningHoleGroup ==1){
            max = 29;
            min = 0;
        }
        if(winningHoleGroup ==2){
            max = 39;
            min = 10;
        }
        if(winningHoleGroup ==3){
            max = 49;
            min = 20;
        }
        if(winningHoleGroup ==4){
            max = 49;
            min = 30;
        }
    }

    // Check whether a catastrophe has occurred
    // If occurred send a message to both threads for their looper to quit
    private boolean checkCatastrophe(int num) {
        if(flag.equals("player1")){
            if(player2Thread.position == num){
                Log.i("P1status","Catastrophe");
                Message msg = p1Handler.obtainMessage(1);
                p1Handler.sendMessage(msg);
                msg = p2Handler.obtainMessage(1);
                p2Handler.sendMessage(msg);
                return  true;
            }
        }
        if(flag.equals(("player2"))){
            if(player1Thread.position == num){
                Log.i("P2status","Catastrophe");
                Message msg = p2Handler.obtainMessage(1);
                p2Handler.sendMessage(msg);
                msg = p1Handler.obtainMessage(1);
                p1Handler.sendMessage(msg);
                return true;
            }
        }
        return false;
    }

    //Adapter class to set the list view
    class myAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return bitmapArray.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View adapV = getLayoutInflater().inflate(R.layout.listitem, null);
            ImageView album = adapV.findViewById(R.id.img);
            album.setImageBitmap(bitmapArray.get(position));
            return adapV;
        }
    }

    // Creates the bit map for various scenarios(This procedure creates circles and set colors)
    private void createBitMap() {
        bitMap = bitMap.copy(bitMap.getConfig(), true);
        Canvas canvas = new Canvas(bitMap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4.5f);
        canvas.drawCircle(50, 50, 30, paint);
        winningBitMap = winningBitMap.copy(winningBitMap.getConfig(), true);
        Canvas canvas2 = new Canvas(winningBitMap);
        paint.setAntiAlias(true);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(4.5f);
        canvas2.drawCircle(50, 50, 30, paint);
        bitMapWin = winningBitMap.copy(winningBitMap.getConfig(), true);
        Canvas canvas3 = new Canvas(bitMapWin);
        paint.setAntiAlias(true);
        paint.setColor(Color.MAGENTA);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(4.5f);
        canvas3.drawCircle(50, 50, 30, paint);
        bitMapCat = winningBitMap.copy(winningBitMap.getConfig(), true);
        Canvas canvas4 = new Canvas(bitMapCat);
        paint.setAntiAlias(true);
        paint.setColor(Color.DKGRAY);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(4.5f);
        canvas4.drawCircle(50, 50, 30, paint);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }
}