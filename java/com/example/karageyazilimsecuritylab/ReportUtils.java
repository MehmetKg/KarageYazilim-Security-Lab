package com.example.karageyazilimsecuritylab;

import android.content.Context;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReportUtils {
    private static final List<String> logs = new ArrayList<>();
    private static File logFile;

    // Uygulama açılınca çağrılır
    public static void init(Context context) {
        String time = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String fileName = "KSL_Log_" + time + ".txt";
        logFile = new File(context.getExternalFilesDir(null), fileName);
    }

    // Log ekle ve dosyaya yaz
    public static void addLog(String text) {
        String cleanText = text.replaceAll("<[^>]*>", ""); // HTML temizliği
        logs.add(cleanText);

        if (logFile != null) {
            try (FileOutputStream fos = new FileOutputStream(logFile, true)) {
                fos.write((cleanText + "\n").getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static List<String> getLogs() { return logs; }
    public static String getPath() { return logFile != null ? logFile.getAbsolutePath() : "Hata"; }
}
