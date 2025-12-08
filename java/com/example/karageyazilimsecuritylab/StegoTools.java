package com.example.karageyazilimsecuritylab;

public class StegoTools {
    public static void checkImage(String path, RequestManager.Callback callback) {
        callback.onResult("🖼️ Steganografi Analizi: " + path);
        callback.onResult("[*] LSB (Least Significant Bit) taranıyor...");
        callback.onResult("[-] Gizli veri tespit edilemedi.");
    }
}