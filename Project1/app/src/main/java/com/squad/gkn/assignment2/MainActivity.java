package com.squad.gkn.assignment2;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ListMenuItemView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity implements appDialog.dListener{
    public static ListView lview;
    Menu temp;
    myAdapter custom;
    // List for songs artist and other information
    ArrayList<String> artistname = new ArrayList<String>();
    ArrayList<Integer> albumcover = new ArrayList<Integer>();
    static ArrayList<String> songname = new ArrayList<String>();
    ArrayList<String> urls = new ArrayList<String>();
    ArrayList<String> artisturl = new ArrayList<String>();
    ArrayList<String> songurl = new ArrayList<String>();

    // create default lists
    public void createLists(){
        artisturl.add("https://en.wikipedia.org/wiki/A._R._Rahman");
        artisturl.add("https://en.wikipedia.org/wiki/A._R._Rahman");
        artisturl.add("https://en.wikipedia.org/wiki/Ilaiyaraaja");
        artisturl.add("https://en.wikipedia.org/wiki/Yuvan_Shankar_Raja");
        artisturl.add("https://en.wikipedia.org/wiki/Harris_Jayaraj");

        songurl.add("https://en.wikipedia.org/wiki/Roja");
        songurl.add( "https://en.wikipedia.org/wiki/Muthu_(1995_film)");
        songurl.add("https://en.wikipedia.org/wiki/Nayakan");
        songurl.add("https://en.wikipedia.org/wiki/Mankatha");
        songurl.add("https://en.wikipedia.org/wiki/Minnale");

        urls.add("https://youtu.be/h-B8_WDl2MU");
        urls.add("https://youtu.be/NXiD9eGaEEI");
        urls.add("https://youtu.be/AUany3iewJI");
        urls.add("https://youtu.be/LpTmp-JTzs0");
        urls.add("https://www.saavn.com/p/song/tamil/Minnale/Ore-Nyabagam/SA8jdABVekk");

        songname.add("Pudhu Vellai Mazhai");
        songname.add("Oruvan Oruvan Mudhalali");
        songname.add("Thenpandi Chemayile");
        songname.add("Mangatha BGM");
        songname.add("Ore Nyabagam");

        albumcover.add(R.drawable.roja);
        albumcover.add(R.drawable.muthu);
        albumcover.add(R.drawable.nayagan);
        albumcover.add(R.drawable.mangatha);
        albumcover.add(R.drawable.minnale);

        artistname.add("AR Rahman");
        artistname.add("AR Rahman");
        artistname.add("Ilayaraja");
        artistname.add("Yuvan Shankar Raja");
        artistname.add("Harris Jayraj");
    }

    // oncreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createLists();
        lview= findViewById(R.id.myList);
        custom = new myAdapter();
        lview.setAdapter(custom);

        registerForContextMenu(lview);

        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                i.putExtra("url",urls.get(position));
                startActivity(i);
            }
        });
    }

    // options submenu realtime
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.mainmenu,menu);
        MenuItem del = menu.findItem(R.id.delsong);
        SubMenu s1 = del.getSubMenu();
        for(int i = 0; i<songname.size();i++){
            s1.add(Menu.NONE,i,i,songname.get(i));
        }
        temp = menu;
        return super.onCreateOptionsMenu(menu);
    }

    // action performed when each menu option selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String t1 = (String) item.getTitle();
        myAdapter  mad = new myAdapter();
        if(songname.contains(t1) && songname.size() > 1){
            int i = songname.indexOf(t1);
            songname.remove(i);
            urls.remove(i);
            artistname.remove(i);
            albumcover.remove(i);
            artisturl.remove(i);
            songurl.remove(i);
            lview.setAdapter(mad);
            Toast toast1 = Toast.makeText(getApplicationContext(),"Song Deleted",Toast.LENGTH_SHORT);
            toast1.show();
            MenuItem m1 = temp.findItem(R.id.delsong);
            SubMenu s1 = m1.getSubMenu();
            s1.removeItem(item.getItemId());
        }
        if(songname.contains(t1) && songname.size() == 1){
            Toast toast1 = Toast.makeText(getApplicationContext(),"Delete Cannot be performed",Toast.LENGTH_SHORT);
            toast1.show();
        }
        if(item.getItemId()== R.id.exitapp){
            finish();
        }

        if(item.getItemId() == R.id.addsong){
            showMyDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    //dialog for add song
    private void showMyDialog() {
        appDialog newd= new appDialog();
        newd.show(getSupportFragmentManager(),"example");
    }

    // create context menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE,0,0,"View/Play Song");
        menu.add(Menu.NONE,1,0,"Song Info");
        menu.add(Menu.NONE,2,0,"Artist Info");
    }

    //actions performed when for each action in context menu
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        if(menuItemIndex == 0){
            Intent i = new Intent(MainActivity.this, Main2Activity.class);
            i.putExtra("url",urls.get(info.position));
            startActivity(i);
        }
        if (menuItemIndex == 1){
            Intent i = new Intent(MainActivity.this, Main2Activity.class);
            i.putExtra("url",songurl.get(info.position));
            startActivity(i);
        }
        if (menuItemIndex == 2){
            Intent i = new Intent(MainActivity.this, Main2Activity.class);
            i.putExtra("url",artisturl.get(info.position));
            startActivity(i);
        }
        return true;
    }
    // return from dialog
    @Override
    public void getTexts(String songName, String songURL, String artistName, String artistURL, String albumURL) {
        songname.add(songName);
        urls.add(songURL);
        artistname.add(artistName);
        artisturl.add(artistURL);
        songurl.add(albumURL);
        albumcover.add(R.drawable.noalbum);
        invalidateOptionsMenu();
        custom.notifyDataSetChanged();
        Toast toast1 = Toast.makeText(getApplicationContext(),"Song Added",Toast.LENGTH_SHORT);
        toast1.show();
    }
    // adapter class
    class myAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return songname.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View adapV = getLayoutInflater().inflate(R.layout.songlayout, null);
            TextView artist = adapV.findViewById(R.id.textView2);
            TextView song = adapV.findViewById(R.id.textView1);
            ImageView album = adapV.findViewById(R.id.imageView1);
            artist.setText(artistname.get(position));
            song.setText(songname.get(position));
            album.setImageResource(albumcover.get(position));

            return adapV;
        }
    }
}
