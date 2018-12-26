package ir.artapps.aroundme.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;


public class PermissionUtil {

    public static boolean isPermissionAllow(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    public static void showPermissionDialog(Activity activity, String permission, int requestCode) {

        if (!isPermissionAllow(activity, permission)) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{permission},
                    requestCode);
        }
    }

    public static boolean isLocationPermissionAllow(Context context) {

        return (isPermissionAllow(context, Manifest.permission.ACCESS_FINE_LOCATION) && isPermissionAllow(context, Manifest.permission.ACCESS_COARSE_LOCATION));
    }

    public static void showLocationPermissionDialog(Activity activity, int requestCode) {

        if (!isLocationPermissionAllow(activity)) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    requestCode);
        }
    }

}