package com.example.practica.webview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import com.example.practica.R;

public class WebView extends AppCompatActivity {
    android.webkit.WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        myWebView = (android.webkit.WebView) findViewById(R.id.myWebView);

        WebSettings webSettings = myWebView.getSettings();
        webSettings.setBuiltInZoomControls(true);
        webSettings.setJavaScriptEnabled(true);

        myWebView.setWebViewClient(new Callback());
        myWebView.loadUrl("https://www.youtube.com");

    }

    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(android.webkit.WebView view, WebResourceRequest request) {
            return false;
        }
    }

    @Override
    public void onBackPressed() {

        if(myWebView != null && myWebView.canGoBack()){
            myWebView.goBack();
        }else{
            super.onBackPressed();
        }
    }
}