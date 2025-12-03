package com.example.karageyazilimsecuritylab;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.SystemClock;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class HardwareTools {

    // 1. WiFi Analizi (Wardriving Simülasyonu)
    public static String getWifiInfo(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifiManager.getConnectionInfo();

        if (info == null) return "WiFi bağlı değil.";

        int rssi = info.getRssi(); // Sinyal Gücü (dBm)
        int level = WifiManager.calculateSignalLevel(rssi, 100); // % Kalite
        int speed = info.getLinkSpeed(); // Bağlantı Hızı (Mbps)

        // IP Adresini formatla
        int ip = info.getIpAddress();
        String ipStr = String.format("%d.%d.%d.%d", (ip & 0xff), (ip >> 8 & 0xff), (ip >> 16 & 0xff), (ip >> 24 & 0xff));

        return "--- WIFI ANALİZÖRÜ ---\n" +
                "SSID (Ağ Adı): " + info.getSSID() + "\n" +
                "BSSID (MAC)  : " + info.getBSSID() + "\n" +
                "Sinyal (RSSI): " + rssi + " dBm (Kalite: %" + level + ")\n" +
                "Link Hızı    : " + speed + " Mbps\n" +
                "Yerel IP     : " + ipStr;
    }

    // 2. Batarya ve Sistem Uptime
    public static String getBatteryStatus(Context context) {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float batteryPct = level * 100 / (float)scale;

        // Şarj durumu
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;

        // Uptime (Cihaz ne kadar süredir açık?)
        long uptimeMillis = SystemClock.elapsedRealtime();
        long hours = (uptimeMillis / (1000 * 60 * 60));
        long minutes = (uptimeMillis / (1000 * 60)) % 60;

        return "--- DONANIM DURUMU ---\n" +
                "Batarya: %" + (int)batteryPct + (isCharging ? " (Şarj Oluyor ⚡)" : " (Pil)") + "\n" +
                "Voltaj : " + batteryStatus.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1) + "mV\n" +
                "Uptime : " + hours + " saat, " + minutes + " dakika";
    }

    // 3. Basit SpeedTest (İndirme Hızı)
    public static String runSpeedTest() {
        try {
            long startTime = System.currentTimeMillis();
            // Test için Google'ın logosunu indiriyoruz (Küçük test) veya daha büyük dosya
            URL url = new URL("http://speedtest.ftp.otenet.gr/files/test100k.db");
            URLConnection con = url.openConnection();
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            InputStream is = con.getInputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            long totalBytes = 0;

            while ((bytesRead = is.read(buffer)) != -1) {
                totalBytes += bytesRead;
            }

            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime; // milisaniye

            if (duration == 0) duration = 1;

            // Hız Hesabı: (Bayt / 1024) / (Saniye) = KB/s
            double speed = (totalBytes / 1024.0) / (duration / 1000.0);

            return String.format("HIZ TESTİ SONUCU:\nDosya: 100KB\nSüre: %d ms\nHız: %.2f KB/s", duration, speed);

        } catch (Exception e) {
            return "Hız testi hatası: " + e.getMessage();
        }
    }
}