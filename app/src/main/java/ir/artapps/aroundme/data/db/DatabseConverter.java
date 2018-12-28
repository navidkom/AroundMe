package ir.artapps.aroundme.data.db;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import ir.artapps.aroundme.entities.Category;
import ir.artapps.aroundme.entities.Contact;
import ir.artapps.aroundme.entities.Location;


public class DatabseConverter {
    private static final Gson gson = new Gson();

    @TypeConverter
    public static String locationToString(Location location) {
        return gson.toJson(location);
    }

    @TypeConverter
    public static Location stringToLocation(String data) {
        Type type = new TypeToken<Location>() {
        }.getType();
        return gson.fromJson(data, type);
    }

    @TypeConverter
    public static String contactToString(Contact contact) {
        return gson.toJson(contact);
    }

    @TypeConverter
    public static Contact stringToContact(String data) {
        Type type = new TypeToken<Contact>() {
        }.getType();
        return gson.fromJson(data, type);
    }

    @TypeConverter
    public static String categoriesToString(List<Category> categories){
        return gson.toJson(categories);
    }

    @TypeConverter
    public static List<Category>stringToCategories(String data){
        Type type = new TypeToken<List<Category>>() {
        }.getType();
        return gson.fromJson(data, type);
    }
}
