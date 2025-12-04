package com.example.karageyazilimsecuritylab;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class WebSecurityTools {

    public static String getHeaders(String urlStr) {
        try {
            if(!urlStr.startsWith("http")) urlStr = "http://" + urlStr;
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");

            StringBuilder sb = new StringBuilder("HEADERS [" + urlStr + "]:\n");
            for (Map.Entry<String, List<String>> entries : conn.getHeaderFields().entrySet()) {
                if(entries.getKey() != null)
                    sb.append(entries.getKey()).append(": ").append(entries.getValue().get(0)).append("\n");
            }
            return sb.toString();
        } catch (Exception e) { return "Header alınamadı."; }
    }

    public static String getRobotsTxt(String urlStr) {
        try {
            if(!urlStr.startsWith("http")) urlStr = "http://" + urlStr;
            return getSourceCode(urlStr + "/robots.txt");
        } catch (Exception e) { return "Robots.txt bulunamadı."; }
    }

    public static String getSourceCode(String urlStr) {
        try {
            if(!urlStr.startsWith("http")) urlStr = "http://" + urlStr;
            URL url = new URL(urlStr);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            int limit = 0;
            while ((line = in.readLine()) != null && limit < 50) { // İlk 50 satır (Performans için)
                sb.append(line).append("\n");
                limit++;
            }
            return sb.toString() + "\n... (Kısaltıldı)";
        } catch (Exception e) { return "Kaynak kod çekilemedi."; }
    }

    public static String checkUrl(String domain, String path) {
        try {
            URL url = new URL("http://" + domain + "/" + path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");
            conn.setConnectTimeout(2000);
            int code = conn.getResponseCode();
            if (code == 200 || code == 301 || code == 403) return "[BULUNDU] " + path + " (Kod: " + code + ")";
        } catch (Exception ignored) {}
        return "";
    }
}