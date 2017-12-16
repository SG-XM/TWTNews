package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by 上官轩明 on 2017/10/23.
 */

public class ImageLoader extends AsyncTask<String, Void, Bitmap> {

    private String url;
    private ImageView imageView;
    public static LruCache<String, Bitmap> lruCache = new LruCache<>((int) (Runtime.getRuntime().maxMemory() / 4));

    public ImageLoader(ImageView imageView) {
        this.imageView = imageView;
    }

//    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            imageView.setImageBitmap((Bitmap) msg.obj);
//        }
//    };

    public Bitmap getBitmapFromUrl(String url) {
        Bitmap bitmap = null;
        InputStream is;
        try {
            if (url.contains("https")) {
                is = new URL(url).openStream();
            } else {
                is = new URL(url.replaceFirst("http", "https")).openStream();
            }

            BufferedInputStream bis = new BufferedInputStream(is);
            bitmap = BitmapFactory.decodeStream(bis);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return bitmap;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        this.url = strings[0];
        Bitmap bitmap = null;
        if (lruCache.size() != 0) {
            if ((bitmap = lruCache.get(url)) != null) {
                return bitmap;
            }
        }

        return getBitmapFromUrl(strings[0]);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        if (bitmap != null) {
            if (imageView.getTag().equals(url)) {
                imageView.setImageBitmap(bitmap);
                //已经是主线程了，可以修改UI了
                //Message message = Message.obtain();
                //message.obj = bitmap;
                //handler.sendMessage(message);
            }
            if (lruCache.get(url) == null) {
                lruCache.put(url, bitmap);
            }
        }

    }
}
