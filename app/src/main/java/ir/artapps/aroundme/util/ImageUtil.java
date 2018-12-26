package ir.artapps.aroundme.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

public class ImageUtil {

    public static void setImage(String url, ImageView imageView){
        Bitmap bmp = BitmapMemCache.getBitmapFromMemCache(url);
        if(bmp != null) {
            imageView.setImageBitmap(bmp);
        } else {
            new DownloadImageTask(imageView).execute(url);
        }
    }

    private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        ImageView imageView;
        public DownloadImageTask(ImageView imageView) {
            this.imageView = imageView;
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
            imageView.setImageBitmap(result);
        }
    }
}
