package com.example.karageyazilimsecuritylab;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class StressTestModule {

    // HTTP FLOOD (Yük Testi)
    // Uyarı: Bu sadece stres testi içindir, DoS saldırısı için kullanmayın.
    public static void runStressTest(String targetUrl, RequestManager.Callback callback) {
        callback.onResult("🔥 STRES TESTİ BAŞLATILIYOR: " + targetUrl);
        callback.onResult("UYARI: Bu işlem bataryayı hızlı tüketir ve CPU ısınabilir.");
        callback.onResult("[*] 500 İstek paketi hazırlanıyor...");

        // 20 Thread aynı anda saldıracak
        ExecutorService executor = Executors.newFixedThreadPool(20);

        RequestManager.submit(() -> {
            for (int i = 0; i < 500; i++) { // 500 İstek Limiti (Cihazı korumak için)
                executor.execute(() -> {
                    try {
                        URL url = new URL(targetUrl);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        conn.setConnectTimeout(2000);
                        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (KSL-Stress-Test)");

                        int code = conn.getResponseCode();
                        // Sadece ekrana spam yapmamak için her 50 istekte bir veya hata durumunda yaz
                        if (code >= 500) {
                            callback.onResult("<font color='#FF0000'>[!] SUNUCU HATASI (500)! Hedef zorlanıyor.</font>");
                        }
                    } catch (Exception e) {
                        callback.onResult("[-] Bağlantı koptu (Sunucu düşmüş olabilir).");
                    }
                });
            }

            // İşlemlerin bitmesini bekle
            executor.shutdown();
            try {
                if (executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    callback.onResult("✅ STRES TESTİ TAMAMLANDI.");
                } else {
                    callback.onResult("[-] Test zaman aşımına uğradı.");
                }
            } catch (InterruptedException e) {}
        });
    }
}