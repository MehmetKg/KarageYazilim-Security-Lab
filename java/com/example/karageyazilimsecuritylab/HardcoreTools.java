package com.example.karageyazilimsecuritylab;

import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;

public class HardcoreTools {

    // 1. SUBDOMAIN TAKEOVER (Domain Ele Geçirme Analizi)
    public static String checkTakeover(String domain) {
        try {
            if(!domain.startsWith("http")) domain = "http://" + domain;
            URL url = new URL(domain);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3000);
            conn.getResponseCode(); // Tetikle

            // Kaynak kodu çekip hata mesajlarına bakıyoruz
            String response = WebSecurityTools.getSourceCode(domain);

            if (response.contains("There is no app configured at that hostname") || // Heroku
                    response.contains("NoSuchBucket") || // AWS S3
                    response.contains("No such app") ||
                    response.contains("Trying to access your store")) {
                return "[!!!] TAKEOVER MÜMKÜN: " + domain + "\nSebep: Servis (Heroku/AWS) silinmiş ama DNS duruyor.";
            }
            return "[-] Takeover riski düşük.";
        } catch (Exception e) { return "Hata: Domain çözülemedi."; }
    }

    // 2. BLIND SQL INJECTION (Time-Based Attack)
    public static String checkBlindSql(String urlStr) {
        if (!urlStr.contains("=")) return "Hata: Parametre gerekli (?id=)";
        // Sunucuyu 5 saniye uyutmaya çalışıyoruz
        String payload = "' AND SLEEP(5)--";
        String target = urlStr + payload.replace(" ", "%20");

        try {
            long start = System.currentTimeMillis();
            URL url = new URL(target);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            conn.getResponseCode();
            long duration = System.currentTimeMillis() - start;

            if (duration >= 4500) return "[!!!] BLIND SQLi TESPİT EDİLDİ! (Sunucu " + duration + "ms uyudu)";
            else return "[-] Blind SQLi başarısız.";
        } catch (Exception e) { return "Bağlantı hatası."; }
    }

    // 3. CLICKJACKING (iFrame Koruması Var mı?)
    public static String checkClickjacking(String urlStr) {
        try {
            if (!urlStr.startsWith("http")) urlStr = "http://" + urlStr;
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");

            Map<String, List<String>> headers = conn.getHeaderFields();
            if (!headers.containsKey("X-Frame-Options") && !headers.containsKey("Content-Security-Policy")) {
                return "[!!!] CLICKJACKING AÇIĞI VAR! (X-Frame-Options Eksik)";
            }
            return "[+] Güvenli: Clickjacking koruması var.";
        } catch (Exception e) { return "Hata."; }
    }

    // 4. SSL ANALİZİ (Sertifika Detayları)
    public static String analyzeSSL(String domain) {
        try {
            if (!domain.startsWith("https")) domain = "https://" + domain;
            URL url = new URL(domain);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.connect();
            Certificate[] certs = conn.getServerCertificates();
            X509Certificate x509 = (X509Certificate) certs[0];

            return "--- SSL RAPORU ---\n" +
                    "Sahip: " + x509.getSubjectDN() + "\n" +
                    "Veren: " + x509.getIssuerDN() + "\n" +
                    "Algoritma: " + x509.getSigAlgName() + "\n" +
                    "Bitiş: " + x509.getNotAfter();
        } catch (Exception e) { return "SSL Hatası (Sertifika geçersiz olabilir)."; }
    }
}