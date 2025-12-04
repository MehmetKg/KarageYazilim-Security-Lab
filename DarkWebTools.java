package com.example.karageyazilimsecuritylab;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DarkWebTools {

    // ÇOKLU GATEWAY LİSTESİ (Biri çalışmazsa diğeri denenir)
    private static final String[] GATEWAYS = {
            ".onion.ws",      // En stabil
            ".onion.ly",      // Hızlı
            ".onion.pet",     // Alternatif
            ".tor2web.io"     // Yedek
    };

    // 1. ONION SİTE ÇEKİCİ (Smart Gateway)
    public static String fetchOnionSite(String onionUrl) {
        if (!onionUrl.contains(".onion")) return "Hata: Bu bir .onion linki değil.";

        // Linki temizle (http://...onion şekline getir)
        String cleanUrl = onionUrl;
        if(cleanUrl.startsWith("https")) cleanUrl = cleanUrl.replace("https://", "http://");
        if(!cleanUrl.startsWith("http")) cleanUrl = "http://" + cleanUrl;

        // .onion kısmını bul ve ayır
        String baseOnion = cleanUrl.split(".onion")[0] + ".onion";
        String path = cleanUrl.contains(".onion/") ? cleanUrl.split(".onion")[1] : "";

        StringBuilder report = new StringBuilder("--- DARK WEB BAĞLANTISI ---\n");
        report.append("Hedef: ").append(baseOnion).append("\n");

        // Gatewayleri sırayla dene
        for (String gateway : GATEWAYS) {
            String targetUrl = baseOnion.replace(".onion", gateway) + path;
            report.append("Deneleniyor: ").append(gateway).append("... ");

            try {
                String content = WebSecurityTools.getSourceCode(targetUrl);
                if (!content.contains("Hata") && !content.contains("Kaynak kod çekilemedi")) {
                    report.append("[BAŞARILI] ✅\n\n");
                    report.append("--- SİTE İÇERİĞİ (Kaynak Kod) ---\n");
                    report.append(content);
                    return report.toString();
                } else {
                    report.append("[BAŞARISIZ] ❌\n");
                }
            } catch (Exception e) {
                report.append("[HATA] ❌\n");
            }
        }

        return report.append("\nSONUÇ: Hiçbir gateway üzerinden siteye ulaşılamadı (Site kapalı olabilir).").toString();
    }

    // 2. TOR BAĞLANTI KONTROLÜ
    public static String checkTorConnection() {
        try {
            URL url = new URL("https://check.torproject.org/api/ip");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = in.readLine();

            if (response.contains("\"IsTor\":true")) {
                return "[+] GÜVENLİ: Cihazınız Tor Ağına bağlı. (Tam Anonim)";
            } else {
                return "[-] RİSKLİ: Normal internettesiniz. (Gateway kullanılıyor)";
            }
        } catch (Exception e) { return "Tor kontrolü yapılamadı."; }
    }

    // 3. DARK WEB ARAMA (Ahmia)
    public static String searchDarkWeb(String query) {
        try {
            String searchUrl = "https://ahmia.fi/search/?q=" + query.replace(" ", "+");
            URL url = new URL(searchUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder results = new StringBuilder("--- DARK WEB ARAMA (" + query + ") ---\n");

            int count = 0;
            while ((line = in.readLine()) != null) {
                if (line.contains("<cite>") && count < 15) {
                    String rawLink = line.replaceAll(".*<cite>", "").replaceAll("</cite>.*", "");
                    if(rawLink.contains(".onion")) {
                        results.append("LINK: ").append(rawLink).append("\n");
                        count++;
                    }
                }
            }
            if (count == 0) return "Sonuç bulunamadı.";
            return results.toString();
        } catch (Exception e) { return "Arama hatası."; }
    }
}