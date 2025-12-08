package com.example.karageyazilimsecuritylab;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.SystemClock;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HardwareTools {

    // ANA FONKSİYON: MainActivity'den çağrılan kısım
    public static void getDetails(Context context, RequestManager.Callback callback) {
        callback.onResult("⚙️ DONANIM VE SİSTEM ANALİZİ BAŞLATILIYOR...");

        RequestManager.submit(() -> {
            try {
                // 1. Cihaz ve İşlemci Bilgisi
                callback.onResult(getDeviceInfo());

                // 2. WiFi Analizi
                callback.onResult(getWifiInfo(context));

                // 3. Batarya ve Uptime
                callback.onResult(getBatteryStatus(context));

                // 4. CPU Kullanımı (Gerçek zamanlı okuma)
                callback.onResult("CPU Yükü: %" + (int)(readCpuUsage() * 100));

                // 5. Hız Testi (Opsiyonel - Uzun sürdüğü için en sona koyduk)
                callback.onResult("⏳ Ağ Hız Testi Yapılıyor (Lütfen bekleyin)...");
                callback.onResult(runSpeedTest());

                callback.onResult("✅ DONANIM ANALİZİ TAMAMLANDI.");

            } catch (Exception e) {
                callback.onResult("Hata: " + e.getMessage());
            }
        });
    }

    // --- ALT FONKSİYONLAR ---

    // 1. Temel Cihaz Bilgileri
    private static String getDeviceInfo() {
        return "--- CİHAZ KİMLİĞİ ---\n" +
                "Model      : " + Build.MODEL + " (" + Build.PRODUCT + ")\n" +
                "Üretici    : " + Build.MANUFACTURER.toUpperCase() + "\n" +
                "Android    : " + Build.VERSION.RELEASE + " (API " + Build.VERSION.SDK_INT + ")\n" +
                "İşlemci    : " + Build.HARDWARE + "\n" +
                "Bootloader : " + Build.BOOTLOADER;
    }

    // 2. WiFi Analizi (Senin kodun entegre edildi)
    private static String getWifiInfo(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifiManager.getConnectionInfo();

            if (info == null) return "[-] WiFi Bağlı Değil.";

            int rssi = info.getRssi();
            int level = WifiManager.calculateSignalLevel(rssi, 100);
            int speed = info.getLinkSpeed();

            // IP Dönüşümü
            int ip = info.getIpAddress();
            String ipStr = String.format("%d.%d.%d.%d", (ip & 0xff), (ip >> 8 & 0xff), (ip >> 16 & 0xff), (ip >> 24 & 0xff));

            return "--- WIFI DURUMU ---\n" +
                    "SSID   : " + info.getSSID().replace("\"", "") + "\n" +
                    "BSSID  : " + info.getBSSID() + "\n" +
                    "Sinyal : " + rssi + " dBm (Kalite: %" + level + ")\n" +
                    "Hız    : " + speed + " Mbps\n" +
                    "Local IP: " + ipStr;
        } catch (Exception e) {
            return "WiFi bilgisi alınamadı (İzin gerekebilir).";
        }
    }

    // 3. Batarya ve Uptime (Senin kodun entegre edildi)
    private static String getBatteryStatus(Context context) {
        try {
            IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent batteryStatus = context.registerReceiver(null, ifilter);

            if (batteryStatus == null) return "Batarya bilgisi yok.";

            int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            float batteryPct = level * 100 / (float) scale;

            int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;

            int voltage = batteryStatus.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1);
            int temp = batteryStatus.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);

            // Uptime
            long uptimeMillis = SystemClock.elapsedRealtime();
            long hours = (uptimeMillis / (1000 * 60 * 60));
            long minutes = (uptimeMillis / (1000 * 60)) % 60;

            return "--- GÜÇ YÖNETİMİ ---\n" +
                    "Batarya: %" + (int) batteryPct + (isCharging ? " ⚡ (Şarjda)" : "") + "\n" +
                    "Voltaj : " + voltage + "mV\n" +
                    "Sıcaklık: " + (temp / 10.0) + "°C\n" +
                    "Uptime : " + hours + "sa " + minutes + "dk";
        } catch (Exception e) {
            return "Güç bilgisi hatası.";
        }
    }

    // 4. CPU Kullanımı Okuma (Proc Stats)
    private static float readCpuUsage() {
        try {
            RandomAccessFile reader = new RandomAccessFile("/proc/stat", "r");
            String load = reader.readLine();
            String[] toks = load.split(" +");
            long idle1 = Long.parseLong(toks[4]);
            long cpu1 = Long.parseLong(toks[2]) + Long.parseLong(toks[3]) + Long.parseLong(toks[5])
                    + Long.parseLong(toks[6]) + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);

            try {
                Thread.sleep(360);
            } catch (Exception e) {}

            reader.seek(0);
            load = reader.readLine();
            reader.close();

            toks = load.split(" +");
            long idle2 = Long.parseLong(toks[4]);
            long cpu2 = Long.parseLong(toks[2]) + Long.parseLong(toks[3]) + Long.parseLong(toks[5])
                    + Long.parseLong(toks[6]) + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);

            return (float)(cpu2 - cpu1) / ((cpu2 + idle2) - (cpu1 + idle1));
        } catch (Exception ex) {
            return 0;
        }
    }

    // 5. Hız Testi (Senin kodun entegre edildi)
    private static String runSpeedTest() {
        try {
            long startTime = System.currentTimeMillis();
            // Test için küçük bir dosya (100KB) indiriyoruz
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
            long duration = endTime - startTime;

            if (duration == 0) duration = 1;

            double speed = (totalBytes / 1024.0) / (duration / 1000.0);
            return String.format("HIZ TESTİ: %.2f KB/s (Süre: %d ms)", speed, duration);

        } catch (Exception e) {
            return "Hız testi yapılamadı (İnternet yok).";
        }
    }
}