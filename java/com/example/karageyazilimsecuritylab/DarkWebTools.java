package com.example.karageyazilimsecuritylab;

public class DarkWebTools {
    public static void searchOnion(String query, RequestManager.Callback callback) {
        callback.onResult("🧅 Dark Web Araması: " + query);
        callback.onResult("[*] Tor Ağına Bağlanılıyor (Simülasyon)...");
        callback.onResult("[LINK] http://xmh57jrzrnw6insl.onion/search?q=" + query);
        callback.onResult("[LINK] http://hss3uro2hsxfogfq.onion/results");
    }
}