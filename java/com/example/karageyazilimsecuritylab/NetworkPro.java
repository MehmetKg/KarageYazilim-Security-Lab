package com.example.karageyazilimsecuritylab;

import java.net.InetAddress;
import org.json.JSONObject;

public class NetworkPro {

    // Domain'den IP ve Lokasyon Çözümleme
    public static void resolveHost(String url, RequestManager.Callback callback) {
        // URL Temizliği (https://www.google.com -> www.google.com)
        String domain = url.replace("http://", "").replace("https://", "").split("/")[0];

        callback.onResult("📡 AĞ ANALİZİ: " + domain);

        RequestManager.submit(() -> {
            try {
                // 1. IP Adresini Bul (DNS)
                InetAddress address = InetAddress.getByName(domain);
                String ip = address.getHostAddress();
                callback.onResult("<font color='#00FF00'>[+] IP ADRESİ: " + ip + "</font>");

                // 2. IP Detaylarını Çek (API Kullanarak)
                // ip-api.com ücretsiz ve key gerektirmez
                String apiUrl = "http://ip-api.com/json/" + ip;
                String response = RequestManager.makeHttpRequest(apiUrl);

                if (response.contains("BODY:")) {
                    String jsonStr = response.split("\\|\\|BODY:")[1];
                    JSONObject json = new JSONObject(jsonStr);

                    if (json.getString("status").equals("success")) {
                        callback.onResult("🌍 Ülke: " + json.getString("country"));
                        callback.onResult("🏙️ Şehir: " + json.getString("city"));
                        callback.onResult("🏢 ISP: " + json.getString("isp"));
                        callback.onResult("📍 Organizasyon: " + json.getString("org"));
                    }
                }

            } catch (Exception e) {
                callback.onResult("Analiz Hatası: " + e.getMessage());
            }
        });
    }
}