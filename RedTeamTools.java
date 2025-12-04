package com.example.karageyazilimsecuritylab;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RedTeamTools {

    // 1. CMS DEDEKTÖRÜ (WordPress mi? Joomla mı?)
    public static String detectCMS(String urlStr) {
        try {
            if (!urlStr.startsWith("http")) urlStr = "http://" + urlStr;
            String source = WebSecurityTools.getSourceCode(urlStr).toLowerCase(); // Kaynak kodu çek

            if (source.contains("wp-content") || source.contains("wordpress"))
                return "CMS TESPİTİ: [WORDPRESS] tespit edildi.";

            if (source.contains("joomla") || source.contains("/templates/"))
                return "CMS TESPİTİ: [JOOMLA] tespit edildi.";

            if (source.contains("drupal") || source.contains("sites/all"))
                return "CMS TESPİTİ: [DRUPAL] tespit edildi.";

            if (source.contains("shopify"))
                return "CMS TESPİTİ: [SHOPIFY] E-Ticaret altyapısı.";

            return "CMS TESPİTİ: Bilinen bir CMS bulunamadı (Özel Yazılım olabilir).";
        } catch (Exception e) {
            return "Hata: Siteye erişilemedi.";
        }
    }

    // 2. HASSAS DOSYA KONTROLÜ
    public static String checkSensitiveFile(String domain, String file) {
        try {
            if (!domain.startsWith("http")) domain = "http://" + domain;
            String target = domain + "/" + file;

            URL url = new URL(target);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET"); // HEAD değil GET yapıyoruz ki dosya var mı emin olalım
            conn.setConnectTimeout(3000);
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Security-Test)");

            int code = conn.getResponseCode();

            // Eğer dosya varsa ve boyutu 0 değilse
            if (code == 200 && conn.getContentLength() > 0) {
                return "[!!!] KRİTİK BULGU: " + target + " (Bu dosya dışarıya açık!)";
            }
        } catch (Exception ignored) {}
        return "";
    }
}