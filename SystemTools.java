package com.example.karageyazilimsecuritylab;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;

public class SystemTools {

    // Monitor: RAM, Disk ve CPU bilgisi
    public static String getSystemResources(Context context) {
        StringBuilder sb = new StringBuilder("--- SİSTEM KAYNAKLARI ---\n");

        // 1. RAM KULLANIMI
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);

        long totalMem = mi.totalMem / (1024 * 1024);
        long availMem = mi.availMem / (1024 * 1024);
        long usedMem = totalMem - availMem;

        sb.append("RAM: ").append(usedMem).append("MB / ").append(totalMem).append("MB\n");

        // 2. DİSK KULLANIMI
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long totalBlocks = stat.getBlockCountLong();
        long availableBlocks = stat.getAvailableBlocksLong();

        long totalStorage = (totalBlocks * blockSize) / (1024 * 1024);
        long freeStorage = (availableBlocks * blockSize) / (1024 * 1024);

        sb.append("DİSK: ").append(totalStorage - freeStorage).append("MB Kullanılan / ").append(totalStorage).append("MB Toplam\n");

        // 3. İŞLEMCİ (Basit Çekirdek Sayısı)
        int cores = Runtime.getRuntime().availableProcessors();
        sb.append("CPU Çekirdekleri: ").append(cores).append("\n");

        return sb.toString();
    }

    // Netstat: Linux /proc/net/tcp dosyasını okur (Rootsuz çalışır)
    public static String getNetstat() {
        StringBuilder sb = new StringBuilder("--- AKTİF BAĞLANTILAR (TCP) ---\n");
        sb.append("Durum       | Uzak Adres\n");

        try {
            BufferedReader in = new BufferedReader(new FileReader("/proc/net/tcp"));
            String line;
            in.readLine(); // Başlığı atla
            while ((line = in.readLine()) != null) {
                line = line.trim();
                String[] parts = line.split("\\s+");
                if (parts.length > 3) {
                    String state = getTcpState(parts[3]); // Bağlantı durumu
                    String remoteAddr = parseIp(parts[2]); // IP:Port

                    if (!state.equals("LISTEN")) { // Sadece aktif bağlantıları göster
                        sb.append(String.format("%-11s | %s\n", state, remoteAddr));
                    }
                }
            }
            in.close();
        } catch (Exception e) {
            return "Netstat okunamadı (Android kısıtlaması olabilir).";
        }
        return sb.toString();
    }

    // Yardımcı: Hex IP'yi okunabilir IP'ye çevirir
    private static String parseIp(String hexIpPort) {
        try {
            String[] parts = hexIpPort.split(":");
            String ipHex = parts[0];
            int port = Integer.parseInt(parts[1], 16);

            long ip = Long.parseLong(ipHex, 16);
            String ipStr = ((ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "." + ((ip >> 24) & 0xFF));
            return ipStr + ":" + port;
        } catch (Exception e) { return hexIpPort; }
    }

    // Yardımcı: TCP Durum Kodları
    private static String getTcpState(String state) {
        switch (state) {
            case "01": return "ESTABLISHED";
            case "02": return "SYN_SENT";
            case "03": return "SYN_RECV";
            case "04": return "FIN_WAIT1";
            case "0A": return "LISTEN";
            case "0B": return "CLOSING";
            default: return state;
        }
    }
}