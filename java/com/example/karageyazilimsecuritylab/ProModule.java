package com.example.karageyazilimsecuritylab;

import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProModule {
    public static void runSSLScan(String url, RequestManager.Callback callback) {
        callback.onResult("🔒 SSL Analizi: Sertifika kontrol ediliyor (Basit)...");
    }

    public static void runLinkFinder(String url, RequestManager.Callback callback) {
        callback.onResult("[*] LinkFinder: Kaynak kod taranıyor...");
        RequestManager.submit(() -> {
            String res = RequestManager.makeHttpRequest(url);
            String body = res.contains("||BODY:") ? res.split("\\|\\|BODY:")[1] : "";
            Matcher m = Pattern.compile("href=\"(http[^\"]+)\"").matcher(body);
            while(m.find()) callback.onResult("[LINK] " + m.group(1));
        });
    }

    public static void analyzeHash(String hash, RequestManager.Callback callback) {
        if(hash.length() == 32) callback.onResult("Tip: MD5");
        else if(hash.length() == 40) callback.onResult("Tip: SHA1");
        else callback.onResult("Tip: Bilinmiyor");
    }

    public static void runBase64(String text, String mode, RequestManager.Callback callback) {
        try {
            if(mode.equals("enc")) callback.onResult(Base64.getEncoder().encodeToString(text.getBytes()));
            else callback.onResult(new String(Base64.getDecoder().decode(text)));
        } catch(Exception e) { callback.onResult("Hata."); }
    }
}