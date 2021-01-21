package com.example.lab4;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WebsearchActivity extends AppCompatActivity {

    private WebView webView;
    final static String EXTRA_URL = "url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webview);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl("https://www.google.ro/search?hl=en&authuser=0&tbm=isch&sxsrf=ALeKk01iJDRT2XIsU0p-5Qj6KUmIUAS7Tg%3A1610820685669&source=hp&biw=1258&bih=742&ei=TSwDYJeJJuCEjLsPsqua0AE&q=cats&oq=cats&gs_lcp=CgNpbWcQAzIECCMQJzICCAAyAggAMgIIADICCAAyAggAMgIIADICCAAyAggAMgIIAFC4AVj7CGCsDGgBcAB4AIABaogBhwSSAQMwLjWYAQCgAQGqAQtnd3Mtd2l6LWltZw&sclient=img&ved=0ahUKEwjX443WhqHuAhVgAmMBHbKVBhoQ4dUDCAc&uact=5");
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack(); 
        }
        else {
            super.onBackPressed();
        }
    }

    public void loadImage(View view) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData abc = clipboardManager.getPrimaryClip();
        ClipData.Item item = abc.getItemAt(0);
        String url = item.getText().toString();

        if (!url.contains("https://images.app.goo.gl/"))
            Toast.makeText(this, "Url not valid.", Toast.LENGTH_SHORT).show();
        else {
            if (view.getId() == R.id.bLoadBackground) {
                Intent intent = new Intent(this, ImageIntentService.class);
                intent.putExtra(EXTRA_URL, url);
                startService(intent);
            } else if (view.getId() == R.id.bLoadForeground) {
                Intent startIntent = new Intent(this, ForegroundImageService.class);
                startIntent.setAction(ForegroundImageService.STARTFOREGROUND_ACTION);
                startIntent.putExtra(EXTRA_URL, url);
                startService(startIntent);
            }
        }
    }
}
