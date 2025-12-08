package com.example.karageyazilimsecuritylab;

import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class OsintTools {

    // --- 1. IP REPUTATION (EKSİK OLAN METOT EKLENDİ) ---
    // MainActivity 'reputation' komutu bunu çağırır
    public static void checkIpReputation(String ip, RequestManager.Callback callback) {
        callback.onResult("🌍 IP İTİBAR KONTROLÜ: " + ip);

        RequestManager.submit(() -> {
            // AbuseIPDB ve VirusTotal linkleri oluştur
            String abuseLink = "https://www.abuseipdb.com/check/" + ip;
            String vtLink = "https://www.virustotal.com/gui/ip-address/" + ip;

            callback.onResult("Bu IP adresini şu veritabanlarında kontrol et:");
            callback.onResult("[LINK] AbuseIPDB: " + abuseLink);
            callback.onResult("[LINK] VirusTotal: " + vtLink);
            callback.onResult("[*] Eğer IP 'blacklist'te ise bu linklerde kırmızı uyarı görürsün.");
        });
    }

    // --- 2. USERNAME SCANNER (25+ Platform) ---
    public static final Map<String, String> PLATFORMS = new HashMap<String, String>() {{
        put("Instagram", "https://www.instagram.com/%s/");
        put("Twitter", "https://twitter.com/%s");
        put("Facebook", "https://www.facebook.com/%s");
        put("TikTok", "https://www.tiktok.com/@%s");
        put("Github", "https://github.com/%s");
        put("Reddit", "https://www.reddit.com/user/%s");
        put("Pinterest", "https://www.pinterest.com/%s/");
        put("Spotify", "https://open.spotify.com/user/%s");
        put("Steam", "https://steamcommunity.com/id/%s");
        put("Telegram", "https://t.me/%s");
        put("GitLab", "https://gitlab.com/%s");
        put("Twitch", "https://www.twitch.tv/%s");
        put("Medium", "https://medium.com/@%s");
        put("Patreon", "https://www.patreon.com/%s");
        put("Behance", "https://www.behance.net/%s");
        put("Vimeo", "https://vimeo.com/%s");
        put("SoundCloud", "https://soundcloud.com/%s");
        put("About.me", "https://about.me/%s");
        put("Wattpad", "https://www.wattpad.com/user/%s");
        put("Flickr", "https://www.flickr.com/people/%s");
        put("Pastebin", "https://pastebin.com/u/%s");
        put("Wikipedia", "https://en.wikipedia.org/wiki/User:%s");
    }};

    public static void searchUsername(String username, RequestManager.Callback callback) {
        callback.onResult("🕵️ OSINT TARAMASI: @" + username);
        callback.onResult("Platformlar taranıyor (Biraz sürebilir)...");

        RequestManager.submit(() -> {
            int foundCount = 0;
            for (Map.Entry<String, String> entry : PLATFORMS.entrySet()) {
                String platform = entry.getKey();
                String urlStr = String.format(entry.getValue(), username);

                try {
                    URL url = new URL(urlStr);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(3000);
                    conn.setRequestProperty("User-Agent", "Mozilla/5.0");

                    if (conn.getResponseCode() == 200) {
                        callback.onResult("<font color='#00FF00'>[+] BULUNDU: " + platform + "</font>");
                        callback.onResult("    Link: " + urlStr);
                        foundCount++;
                    }
                } catch (Exception e) {}
            }
            if (foundCount == 0) callback.onResult("[-] Profil bulunamadı.");
            else callback.onResult("✅ Tarama Bitti. Toplam: " + foundCount);
        });
    }

    // --- 3. EMAIL OSINT ---
    public static void analyzeEmail(String email, RequestManager.Callback callback) {
        callback.onResult("📧 EMAIL ANALİZİ: " + email);
        RequestManager.submit(() -> {
            if (!email.contains("@")) { callback.onResult("Hata: Geçersiz format."); return; }

            String domain = email.split("@")[1];
            callback.onResult("Domain: " + domain);

            String hash = md5(email.trim().toLowerCase());
            String gravatarUrl = "https://www.gravatar.com/avatar/" + hash + "?d=404";

            try {
                URL url = new URL(gravatarUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                if (conn.getResponseCode() == 200) {
                    callback.onResult("<font color='#00FF00'>[!] GRAVATAR PROFİLİ VAR!</font>");
                    callback.onResult("Profil Resmi: " + gravatarUrl);
                } else {
                    callback.onResult("[-] Gravatar profili yok.");
                }
            } catch (Exception e) {}
        });
    }

    // --- 4. PHONE OSINT ---
    public static void analyzePhone(String rawNumber, RequestManager.Callback callback) {
        String number = rawNumber.replaceAll("\\s+", "").replace("-", "");
        if (!number.startsWith("+")) {
            callback.onResult("Hata: Numara '+' ve ülke kodu ile başlamalı.");
            return;
        }

        callback.onResult("📱 TELEFON ANALİZİ: " + number);

        String country = "Bilinmiyor";
        if (number.startsWith("+90")) country = "Türkiye 🇹🇷";
        else if (number.startsWith("+1")) country = "ABD/Kanada 🇺🇸";
        else if (number.startsWith("+44")) country = "İngiltere 🇬🇧";
        else if (number.startsWith("+49")) country = "Almanya 🇩🇪";
        else if (number.startsWith("+994")) country = "Azerbaycan 🇦🇿";

        callback.onResult("Konum: " + country);

        String cleanNumber = number.replace("+", "");
        callback.onResult("[WhatsApp] https://wa.me/" + cleanNumber);
        callback.onResult("[Telegram] https://t.me/+" + cleanNumber);
    }

    // Yardımcı: MD5 Hash
    private static String md5(String s) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String h = Integer.toHexString(0xFF & b);
                while (h.length() < 2) h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();
        } catch (Exception e) { return ""; }
    }
}