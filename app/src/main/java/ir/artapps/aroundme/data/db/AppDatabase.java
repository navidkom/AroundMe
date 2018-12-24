package ir.artapps.aroundme.data.db;

/**
 * Created by navid on 21,December,2018
 */

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import ir.artapps.aroundme.data.entities.Venue;

@TypeConverters(DatabseConverter.class)
@Database(entities = {Venue.class}, version = 1)
abstract class AppDatabase extends RoomDatabase{
    abstract VenuesDAO getVenuesDAO();

    private static volatile AppDatabase INSTANCE;

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
