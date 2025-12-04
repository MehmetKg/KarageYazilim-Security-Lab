package com.example.karageyazilimsecuritylab;

import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class OsintTools {

    // --- 1. USERNAME SCANNER (25+ Platform) ---
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
        put("Dribbble", "https://dribbble.com/%s");
        put("Vimeo", "https://vimeo.com/%s");
        put("SoundCloud", "https://soundcloud.com/%s");
        put("About.me", "https://about.me/%s");
        put("Wattpad", "https://www.wattpad.com/user/%s");
        put("Canva", "https://www.canva.com/p/%s");
        put("Flickr", "https://www.flickr.com/people/%s");
        put("Pastebin", "https://pastebin.com/u/%s");
        put("Roblox", "https://www.roblox.com/user.aspx?username=%s");
        put("Wikipedia", "https://en.wikipedia.org/wiki/User:%s");
    }};

    public static String checkUserUrl(String platform, String username) {
        String urlStr = String.format(PLATFORMS.get(platform), username);
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(4000);
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");

            int code = conn.getResponseCode();
            if (code == 200) {
                return "[+] BULUNDU: " + platform + " -> " + urlStr;
            }
        } catch (Exception ignored) {}
        return "";
    }

    // --- 2. EMAIL OSINT (Gravatar Check) ---
    public static String analyzeEmail(String email) {
        StringBuilder sb = new StringBuilder("📧 EMAIL RAPORU: " + email + "\n");

        // A. Format Kontrolü
        if (!email.contains("@") || !email.contains(".")) return "Hata: Geçersiz e-posta formatı.";
        String domain = email.split("@")[1];
        sb.append("   - Domain: ").append(domain).append("\n");

        // B. Gravatar Kontrolü (Profil Resmi Var mı?)
        // Gravatar, emailin MD5 hash'ini kullanır.
        String hash = md5(email.trim().toLowerCase());
        String gravatarUrl = "https://www.gravatar.com/avatar/" + hash + "?d=404";

        try {
            URL url = new URL(gravatarUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200) {
                sb.append("   - [!] GRAVATAR PROFİLİ VAR! (Bu mail aktif)\n");
                sb.append("   - Profil Resmi: ").append(gravatarUrl).append("\n");
            } else {
                sb.append("   - Gravatar profili bulunamadı.\n");
            }
        } catch (Exception e) { sb.append("   - Gravatar kontrolü yapılamadı.\n"); }

        // C. Breach Hint (Simülasyon - API key olmadan gerçek kontrol zordur)
        sb.append("   - Breach Check: 'HaveIBeenPwned' API anahtarı girilmedi.\n");

        return sb.toString();
    }

    // --- 3. PHONE OSINT (Numara Analizi) ---
    public static String analyzePhone(String number) {
        // Numara temizleme (boşlukları sil)
        number = number.replaceAll("\\s+", "").replace("-", "");

        if (!number.startsWith("+")) return "Hata: Numara ülke kodu ile başlamalı (Örn: +905...)";

        StringBuilder sb = new StringBuilder("📱 TELEFON RAPORU: " + number + "\n");

        // A. Ülke Tespiti (Basit Veritabanı)
        String country = "Bilinmiyor";
        if (number.startsWith("+90")) country = "Türkiye 🇹🇷";
        else if (number.startsWith("+1")) country = "ABD / Kanada 🇺🇸";
        else if (number.startsWith("+44")) country = "Birleşik Krallık 🇬🇧";
        else if (number.startsWith("+49")) country = "Almanya 🇩🇪";
        else if (number.startsWith("+33")) country = "Fransa 🇫🇷";
        else if (number.startsWith("+7")) country = "Rusya 🇷🇺";
        else if (number.startsWith("+994")) country = "Azerbaycan 🇦🇿";

        sb.append("   - Tahmini Konum: ").append(country).append("\n");

        // B. Direkt Mesaj Linkleri (OSINT için kritik)
        String cleanNumber = number.replace("+", "");
        sb.append("   - WhatsApp Linki: https://wa.me/").append(cleanNumber).append("\n");
        sb.append("   - Telegram Linki: https://t.me/+").append(cleanNumber).append("\n");
        sb.append("   - Viber Linki   : viber://chat?number=").append(cleanNumber).append("\n");

        return sb.toString();
    }

    // Yardımcı: MD5 Hash (Gravatar için)
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