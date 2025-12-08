package com.example.karageyazilimsecuritylab;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class GhostModule {
    public static void runTamper(String payload, String type, RequestManager.Callback callback) {
        try {
            String res = "";
            if (type.equals("url")) res = URLEncoder.encode(payload, "UTF-8");
            else if (type.equals("double")) res = URLEncoder.encode(URLEncoder.encode(payload, "UTF-8"), "UTF-8");
            else res = payload.replace(" ", "/**/");
            callback.onResult("👻 Tampered: " + res);
        } catch (Exception e) {}
    }

    public static void checkCORS(String url, RequestManager.Callback callback) {
        callback.onResult("☠️ CORS Analizi: " + url);
        RequestManager.submit(() -> {
            try {
                URL u = new URL(url);
                HttpURLConnection c = (HttpURLConnection) u.openConnection();
                c.setRequestProperty("Origin", "https://evil.com");
                if ("https://evil.com".equals(c.getHeaderField("Access-Control-Allow-Origin")))
                    callback.onResult("<font color='#FF0000'>[!!!] CORS AÇIĞI VAR!</font>");
                else callback.onResult("[-] Güvenli.");
            } catch (Exception e) { callback.onResult("Hata: " + e.getMessage()); }
        });
    }
}