package com.example.karageyazilimsecuritylab;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HunterModule {

    // 1. SUBDOMAIN TAKEOVER (Düzeltilmiş)
    public static void checkTakeover(String url, RequestManager.Callback callback) {
        callback.onResult("🏴 TAKEOVER ANALİZİ: " + url);
        RequestManager.submit(() -> {
            String res = RequestManager.makeHttpRequest(url);
            String body = res.contains("||BODY:") ? res.split("\\|\\|BODY:")[1] : "";

            if (body.contains("There is no app configured at that hostname") ||
                    body.contains("NoSuchBucket") ||
                    body.contains("There isn't a GitHub Pages site here")) {
                callback.onResult("<font color='#FF0000'>[!!!] TAKEOVER MÜMKÜN! (Servis Hatası Bulundu)</font>");
            } else {
                callback.onResult("[-] Takeover riski yok.");
            }
        });
    }

    // 2. SENSITIVE DATA MINER (Geliştirilmiş Regex)
    public static void extractData(String url, RequestManager.Callback callback) {
        callback.onResult("⛏️ VERİ MADENCİLİĞİ: " + url);
        RequestManager.submit(() -> {
            String res = RequestManager.makeHttpRequest(url);
            if(res.startsWith("ERROR")) { callback.onResult("Hata: " + res); return; }
            String body = res.split("\\|\\|BODY:")[1];

            // Gelişmiş Regex Kalıpları
            Matcher mEmail = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+").matcher(body);
            // Telefon: +90 veya 05 ile başlayanlar
            Matcher mPhone = Pattern.compile("(?:\\+90|0)\\s?[0-9]{3}\\s?[0-9]{3}\\s?[0-9]{2}\\s?[0-9]{2}").matcher(body);
            Matcher mApi = Pattern.compile("AIza[0-9A-Za-z-_]{35}").matcher(body);

            boolean found = false;
            while(mEmail.find()) { callback.onResult("[EMAIL] " + mEmail.group()); found = true; }
            while(mPhone.find()) { callback.onResult("[PHONE] " + mPhone.group()); found = true; }
            while(mApi.find()) { callback.onResult("<font color='#FF0000'>[API KEY] " + mApi.group() + "</font>"); found = true; }

            if(!found) callback.onResult("[-] Kritik veri sızıntısı bulunamadı.");
        });
    }

    // 3. S3 BUCKET LEAKER
    public static void findS3Buckets(String domain, RequestManager.Callback callback) {
        String clean = domain.replace("http://", "").replace("https://", "").replace("www.", "").split("/")[0];
        String name = clean.split("\\.")[0];
        callback.onResult("☁️ S3 BUCKET TARAMASI: " + name);

        String[] patterns = {name, name + "-dev", name + "-prod", name + "-assets", name + "-public", "backup-" + name};

        for (String bucket : patterns) {
            RequestManager.submit(() -> {
                String target = "http://" + bucket + ".s3.amazonaws.com";
                String res = RequestManager.makeHttpRequest(target);
                if (res.startsWith("CODE:200")) callback.onResult("<font color='#FF0000'>[!!!] AÇIK BUCKET: " + target + "</font>");
                else if (res.startsWith("CODE:403")) callback.onResult("<font color='#FFFF00'>[!] KİLİTLİ BUCKET: " + target + "</font>");
            });
        }
    }
}