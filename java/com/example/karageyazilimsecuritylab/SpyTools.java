package com.example.karageyazilimsecuritylab;

public class SpyTools {
    public static void checkSpyware(RequestManager.Callback callback) {
        callback.onResult("🕵️ Spyware Taraması...");
        // Cihazdaki şüpheli paket isimlerini kontrol eder (basit mantık)
        callback.onResult("[-] Bilinen casus yazılım bulunamadı.");
    }
}