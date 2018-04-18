package com.squad.gkn.assignment2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Main2Activity extends AppCompatActivity {
    WebView ww;
    // create web view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ww = findViewById(R.id.web1);
        ww.setWebViewClient(new WebViewClient());
        ww.getSettings().setJavaScriptEnabled(true);
        ww.getSettings().setDomStorageEnabled(true);
        String someVariable = "https://www.google.com";

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            someVariable = extras.getString("url");
        }

        if(someVariable.equals("")){
            someVariable = "https://www.google.com";
        }
        ww.loadUrl(someVariable);
    }

    //close activity on back pressed
    @Override
    public void onBackPressed() {
        ww.destroy();
        ww=null;
        super.onBackPressed();
        finish();
    }


}
