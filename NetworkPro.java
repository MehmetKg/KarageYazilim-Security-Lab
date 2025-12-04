package com.example.karageyazilimsecuritylab;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;

public class NetworkPro {

    // 1. PORT SCANNER (TCP Connect - Safe)
    public static void scanPorts(String ip, RequestManager.Callback callback) {
        callback.onResult("🌐 PORT TARAMASI BAŞLATILDI: " + ip);
        int[] ports = {21, 22, 23, 25, 53, 80, 110, 135, 139, 443, 445, 1433, 3306, 3389, 5900, 8080};

        for (int port : ports) {
            RequestManager.submit(() -> {
                try {
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress(ip, port), 2000); // 2sn timeout
                    socket.close();
                    callback.onResult("<font color='#00FF00'>[+] AÇIK PORT: " + port + "</font>");
                } catch (Exception e) {
                    // Kapalı portlar sessiz
                }
            });
        }
    }

    // 2. SSL ANALYZER
    public static void analyzeSSL(String domain, RequestManager.Callback callback) {
        RequestManager.submit(() -> {
            try {
                String target = domain.startsWith("http") ? domain : "https://" + domain;
                URL url = new URL(target);
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.connect();

                X509Certificate cert = (X509Certificate) conn.getServerCertificates()[0];

                callback.onResult("🔐 SSL RAPORU:");
                callback.onResult(" - Sahip: " + cert.getSubjectDN());
                callback.onResult(" - Veren: " + cert.getIssuerDN());
                callback.onResult(" - Geçerlilik: " + cert.getNotAfter());
                callback.onResult(" - Algoritma: " + cert.getSigAlgName());

            } catch (Exception e) {
                callback.onResult("SSL Hatası: " + e.getMessage());
            }
        });
    }
}