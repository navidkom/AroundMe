package ir.artapps.aroundme.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;

import ir.artapps.aroundme.GenericCallback;

public class ImageUtil {

    public static void setImage(String url, GenericCallback<Bitmap> callback){
        Bitmap bmp = BitmapMemCache.getBitmapFromMemCache(url);
        if(bmp != null) {
            callback.response(bmp);
        } else {
            new DownloadImageTask(callback).execute(url);
        }
    }

    private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        GenericCallback<Bitmap> callback;
        public DownloadImageTask(GenericCallback<Bitmap> callback) {
            this.callback = callback;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap bmp = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                bmp = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            BitmapMemCache.addBitmapToMemCache(urldisplay, bmp);
            return bmp;
        }
        protected void onPostExecute(Bitmap result) {
            callback.response(result);
        }
    }
}
