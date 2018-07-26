package com.example.mkagaju.earthquakeapp_20;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class WebViewActivity extends AppCompatActivity {
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webview = new WebView(this);
        setContentView(webview);
        Intent intent = getIntent();
        url = intent.getStringExtra("URL");
        webview.loadUrl(url);
    }
}
