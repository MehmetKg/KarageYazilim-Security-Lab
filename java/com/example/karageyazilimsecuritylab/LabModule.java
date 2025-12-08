package com.example.karageyazilimsecuritylab;

import android.os.Build;

public class LabModule {
    public static void showSystemStatus(RequestManager.Callback callback) {
        callback.onResult("🖥️ SYSTEM INFO");
        callback.onResult("Model: " + Build.MODEL);
        callback.onResult("Android: " + Build.VERSION.RELEASE);
        callback.onResult("Board: " + Build.BOARD);
    }

    public static void getPublicIP(RequestManager.Callback callback) {
        RequestManager.submit(() -> {
            String ip = RequestManager.makeHttpRequest("https://api.ipify.org");
            if(ip.contains("CODE:200")) callback.onResult("Public IP: " + ip.split("\\|\\|BODY:")[1]);
            else callback.onResult("IP alınamadı.");
        });
    }
}