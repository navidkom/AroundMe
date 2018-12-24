package ir.artapps.aroundme;

import android.app.Application;
import android.arch.persistence.room.Room;

import ir.artapps.aroundme.data.network.FoursquareApi;


/**
 * Created by navid on 22,December,2018
 */
public class Main extends Application {

    public FoursquareApi foursquareApi;

    @Override
    public void onCreate() {
        super.onCreate();

//        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
//                AppDatabase.class, "database-name").build();

    }
}
