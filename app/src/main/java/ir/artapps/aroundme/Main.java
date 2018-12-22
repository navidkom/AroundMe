package ir.artapps.aroundme;

import android.app.Application;

import ir.artapps.aroundme.data.network.FoursquareApi;


/**
 * Created by navid on 22,December,2018
 */
public class Main extends Application {

    public FoursquareApi foursquareApi;

    @Override
    public void onCreate() {
        super.onCreate();

    }
}
