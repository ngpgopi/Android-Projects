package com.gkn.squad.fragmentassignment;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class webFragement extends Fragment {
    int mcurrentPosition;
    private static WebView mWebView;
    private String[] urlArray;
    public webFragement() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        urlArray = bundle.getStringArray("url");

        setRetainInstance((true));
        if(savedInstanceState !=null){
            mcurrentPosition = savedInstanceState.getInt("index");}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v1 = inflater.inflate(R.layout.webview,container,false);
        mWebView = (WebView) v1.findViewById(R.id.webItem);
        return v1;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState != null){
            WebSettings webSettings = mWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            mWebView.loadUrl(urlArray[mcurrentPosition]);
        }
    }

    public void onLoadURL(int postion){
        mcurrentPosition = postion;
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.loadUrl(urlArray[postion]);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("index",mcurrentPosition);
    }
}
