package com.example.lab4;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ImageIntentService extends IntentService {

    public ImageIntentService() {
        super("ImageIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent != null) {
            final String param = intent.getStringExtra(WebsearchActivity.EXTRA_URL);
            handleDownloadAction(param);
        }
    }

    private void handleDownloadAction(String url) {

        try {
            String longURL = URLTools.getLongUrl(url);
            Bitmap bmp = null;
            try {
                InputStream in = new URL(longURL).openStream();
                bmp = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }

            Thread.sleep(5000);

            ((MyApplication) getApplication()).setBitmap(bmp);

            Intent intent = new Intent(getApplicationContext(), ImageActivity.class);
            startActivity(intent);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
