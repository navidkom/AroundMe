package ir.artapps.aroundme.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

public class ImageUtil {

    public static void setImage(String url, ImageView imageView) {
        Bitmap bmp = BitmapMemCache.getBitmapFromMemCache(url);
        if (bmp != null) {
            imageView.setImageBitmap(bmp);
        } else {
            new DownloadImageTask(imageView).execute(url);
        }
    }

    public static void setImage(String url, ImageCallback callback) {
        Bitmap bmp = BitmapMemCache.getBitmapFromMemCache(url);
        if (bmp != null) {
            callback.bitmapIsReady(bmp);
        } else {
            new DownloadImageTask(callback).execute(url);
        }
    }

    public interface ImageCallback {
        void bitmapIsReady(Bitmap bitmap);
    }

    private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        ImageView imageView = null;
        ImageCallback callback = null;

        public DownloadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        public DownloadImageTask(ImageCallback callback) {
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
            if(imageView != null) {
                imageView.setImageBitmap(result);
            }

            if(callback != null) {
                callback.bitmapIsReady(result);
            }
        }
    }
}
