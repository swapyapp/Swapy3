package com.app.muhammadgamal.swapy;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Common {

    public static boolean isNetworkAvailable(Context ctx) {
        NetworkInfo info = getActiveNetworkInfo(ctx);
        return info != null && info.isAvailable();
    }

    public static boolean isWifiAvailable(Context ctx) {
        NetworkInfo info = getActiveNetworkInfo(ctx);
        return info != null && info.isAvailable()
                && info.getType() == ConnectivityManager.TYPE_WIFI;
    }

    private static NetworkInfo getActiveNetworkInfo(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info;
    }

}
