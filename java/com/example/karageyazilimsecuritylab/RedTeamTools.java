package com.example.karageyazilimsecuritylab; // PAKET İSMİN

import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RedTeamTools { // SINIF ADI ARTIK DOSYA ADIYLA AYNI

    // 1. SUBNET SCANNER (Yerel Ağ Keşfi)
    public static void scanLocalNetwork(RequestManager.Callback callback) {
        callback.onResult("📡 YEREL AĞ TARAMASI BAŞLATILIYOR (ARP/Ping)...");
        callback.onResult("Bu işlem ağ hızına göre 10-20 saniye sürebilir.");

        ExecutorService subnetPool = Executors.newFixedThreadPool(20);

        RequestManager.submit(() -> {
            try {
                String subnet = "192.168.1.";

                for (int i = 1; i < 255; i++) {
                    String host = subnet + i;
                    subnetPool.execute(() -> {
                        try {
                            InetAddress inet = InetAddress.getByName(host);
                            if (inet.isReachable(1000)) {
                                callback.onResult("<font color='#00FF00'>[+] AKTİF CİHAZ: " + host + " (" + inet.getHostName() + ")</font>");
                            }
                        } catch (Exception e) {}
                    });
                }
                subnetPool.shutdown();
                while (!subnetPool.isTerminated()) { }
                callback.onResult("✅ Ağ Taraması Tamamlandı.");
            } catch (Exception e) {
                callback.onResult("Hata: " + e.getMessage());
            }
        });
    }

    // 2. CLICKJACKING TESTER
    public static void checkClickJacking(String url, RequestManager.Callback callback) {
        callback.onResult("🖼️ CLICKJACKING TESTİ: " + url);

        RequestManager.submit(() -> {
            String res = RequestManager.makeHttpRequest(url);
            String headers = res.contains("||HEAD:") ? res.split("\\|\\|HEAD:")[1] : "";

            if (headers.toLowerCase().contains("x-frame-options") || headers.toLowerCase().contains("content-security-policy")) {
                callback.onResult("[-] Site Güvenli. (X-Frame-Options Header var).");
            } else {
                callback.onResult("<font color='#FF0000'>[!!!] CLICKJACKING AÇIĞI VAR!</font>");
                callback.onResult("Bu site başka bir sitenin içinde (iframe) çalıştırılabilir.");
            }
        });
    }
}