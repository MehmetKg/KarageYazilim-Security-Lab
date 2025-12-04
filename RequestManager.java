package com.example.karageyazilimsecuritylab;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.os.Handler;
import android.os.Looper;

public class RequestManager {
    // 30 Thread'lik Dev Havuz
    private static final ExecutorService engine = Executors.newFixedThreadPool(30);
    private static final Handler mainHandler = new Handler(Looper.getMainLooper());

    // Arayüzü güncellemek için Callback Arayüzü
    public interface Callback {
        void onResult(String result);
    }

    // İşleri Havuza Atan Metot
    public static void submit(Runnable task) {
        engine.execute(task);
    }

    // Güvenli HTTP İsteği (Tüm Web Modülleri Bunu Kullanır)
    public static String makeHttpRequest(String targetUrl) {
        try {
            URL url = new URL(targetUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000); // 5sn Timeout
            conn.setReadTimeout(5000);
            conn.setRequestProperty("User-Agent", "KarageLab/8.0 (Android Security Scanner)");

            // Redirect takibi (301/302)
            conn.setInstanceFollowRedirects(true);

            int code = conn.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) content.append(line).append("\n");
            in.close();

            return "CODE:" + code + "||BODY:" + content.toString(); // Özel format
        } catch (Exception e) {
            return "ERROR:" + e.getMessage();
        }
    }

    // UI Thread'de Yazı Yazdırma Yardımcısı
    public static void runOnUI(Runnable action) {
        mainHandler.post(action);
    }
}