package com.example.karageyazilimsecuritylab;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class SystemTools {

    // 1. ROOT KONTROLÜ (Context gerekmez)
    public static void checkRoot(RequestManager.Callback callback) {
        callback.onResult("📱 ROOT YETKİSİ KONTROLÜ...");
        RequestManager.submit(() -> {
            String[] paths = {
                    "/system/app/Superuser.apk",
                    "/sbin/su",
                    "/system/bin/su",
                    "/system/xbin/su",
                    "/data/local/xbin/su",
                    "/data/local/bin/su",
                    "/system/sd/xbin/su",
                    "/system/bin/failsafe/su",
                    "/data/local/su"
            };

            boolean isRooted = false;
            for (String path : paths) {
                if (new File(path).exists()) {
                    isRooted = true;
                    callback.onResult("<font color='#FF0000'>[!] ROOT BULUNDU: " + path + "</font>");
                }
            }

            if (isRooted) callback.onResult("SONUÇ: Cihaz Rootlu (Riskli).");
            else callback.onResult("<font color='#00FF00'>SONUÇ: Cihaz Rootlu Değil (Güvenli).</font>");
        });
    }

    // 2. DEPOLAMA VE KAYNAK ANALİZİ
    // DÜZELTME: Buraya 'Context context' parametresi eklendi.
    public static void checkStorage(Context context, RequestManager.Callback callback) {
        callback.onResult("💾 SİSTEM KAYNAKLARI ANALİZİ...");

        RequestManager.submit(() -> {
            try {
                StringBuilder sb = new StringBuilder();

                // RAM KULLANIMI (Context burada kullanılıyor)
                ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
                ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

                if (activityManager != null) {
                    activityManager.getMemoryInfo(mi);
                    long totalMem = mi.totalMem / (1024 * 1024);
                    long availMem = mi.availMem / (1024 * 1024);
                    long usedMem = totalMem - availMem;
                    sb.append("RAM: ").append(usedMem).append("MB / ").append(totalMem).append("MB\n");
                }

                // DİSK KULLANIMI
                File path = Environment.getDataDirectory();
                StatFs stat = new StatFs(path.getPath());
                long blockSize = stat.getBlockSizeLong();
                long totalBlocks = stat.getBlockCountLong();
                long availableBlocks = stat.getAvailableBlocksLong();

                long totalStorage = (totalBlocks * blockSize) / (1024 * 1024);
                long freeStorage = (availableBlocks * blockSize) / (1024 * 1024);

                sb.append("DİSK: ").append(totalStorage - freeStorage).append("MB Kullanılan / ").append(totalStorage).append("MB Toplam\n");

                // İŞLEMCİ
                int cores = Runtime.getRuntime().availableProcessors();
                sb.append("CPU Çekirdekleri: ").append(cores);

                callback.onResult(sb.toString());

            } catch (Exception e) {
                callback.onResult("Hata: " + e.getMessage());
            }
        });
    }

    // 3. NETSTAT (Aktif Bağlantılar)
    public static void getNetstat(RequestManager.Callback callback) {
        callback.onResult("🌐 AKTİF BAĞLANTILAR (TCP) TARANIYOR...");

        RequestManager.submit(() -> {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("DURUM       | UZAK ADRES\n");
                sb.append("----------------------------\n");

                BufferedReader in = new BufferedReader(new FileReader("/proc/net/tcp"));
                String line;
                in.readLine(); // Başlığı atla
                while ((line = in.readLine()) != null) {
                    line = line.trim();
                    String[] parts = line.split("\\s+");
                    if (parts.length > 3) {
                        String state = getTcpState(parts[3]);
                        String remoteAddr = parseIp(parts[2]);

                        if (!state.equals("LISTEN")) {
                            sb.append(String.format("%-11s | %s\n", state, remoteAddr));
                        }
                    }
                }
                in.close();
                callback.onResult(sb.toString());
            } catch (Exception e) {
                callback.onResult("Netstat okunamadı (Android 10+ kısıtlaması olabilir).");
            }
        });
    }

    // --- YARDIMCI METOTLAR ---
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