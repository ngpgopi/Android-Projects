package com.gkn.squad.fragmentassignment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class MainActivity extends AppCompatActivity implements listFragment.onListItemSelectInterface{

    public static String[] nameArray;
    public static String[] webUrlArray;

    listFragment listFragmentInstance;
    public webFragement webFragementInstance;

    private FrameLayout listFrame , webViewFrame;

    private FragmentManager fragmentManager;

    public int mCurrentIndex;
    public Menu menu;
    public int orientation;
    public Bundle bundle;

    private static final String TAG_LIST_FRAGMENT = "listFragment";
    private static final String TAG_WEB_FRAGMENT = "webFragment";
    public static BroadcastReceiver r1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        nameArray = getResources().getStringArray(R.array.places);
        webUrlArray = getResources().getStringArray(R.array.placeDetails);

        bundle = new Bundle();
        bundle.putStringArray("name",nameArray);
        bundle.putStringArray("url",webUrlArray);

        listFrame = findViewById(R.id.mainFrame);
        webViewFrame = findViewById(R.id.subFrame);

        orientation = getApplicationContext().getResources().getConfiguration().orientation;

        fragmentManager = getFragmentManager();

        listFragmentInstance = (listFragment) fragmentManager.findFragmentByTag("listFragment");
        webFragementInstance = (webFragement) fragmentManager.findFragmentByTag("webFragment");

        if(listFragmentInstance == null){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            listFragmentInstance = new listFragment();
            listFragmentInstance.setArguments(bundle);
            fragmentTransaction.replace(R.id.mainFrame,listFragmentInstance,"listFragment");
            fragmentTransaction.commit();
        }

        if(webFragementInstance == null){
            Log.i("fragmentinstance", "null");
            webFragementInstance = new webFragement();
        }
        else{
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                setLayout1();
            } else {
                setLayout2();
            }
        }

        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                    setLayout1();
                } else {
                    setLayout2();
                }
            }
        });
    }

    @Override
    public void onListItemSelect(int index) {
        mCurrentIndex = index;
        if(!webFragementInstance.isAdded()){
            webFragementInstance.setArguments(bundle);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.subFrame,webFragementInstance,"webFragment");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            fragmentManager.executePendingTransactions();
        }
        webFragementInstance.onLoadURL(index);
    }

    public void setLayout1(){
        if(!webFragementInstance.isAdded()){
            listFrame.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT, 1f));
            webViewFrame.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT, 0f));
        }
        else{
            listFrame.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT, 0f));
            webViewFrame.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT, 1f));
        }
    }

    public void setLayout2(){
        if(!webFragementInstance.isAdded()){
            listFrame.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT, 1f));
            webViewFrame.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT, 0f));
        }
        else{
        listFrame.setLayoutParams(new LinearLayout.LayoutParams(0,
                MATCH_PARENT, 1f));

        webViewFrame.setLayoutParams(new LinearLayout.LayoutParams(0,
                MATCH_PARENT, 2f));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.overflowmenu,menu);
        this.menu = menu;
        updateMenuTitle();
        return true;
    }

    public void updateMenuTitle(){
        MenuItem m1 = menu.findItem(R.id.item1);
        m1.setTitle("Restaurants");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(getApplicationContext(),Main2Activity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}
