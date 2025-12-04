package com.example.karageyazilimsecuritylab;

import android.content.Context;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportUtils {
    private static final List<String> logs = new ArrayList<>();

    public static void addLog(String log) {
        // HTML etiketlerini temizle (dosyaya yazarken)
        logs.add(log.replaceAll("<[^>]*>", ""));
    }

    public static String exportReport(Context context) {
        try {
            File file = new File(context.getExternalFilesDir(null), "Karage_Report_" + System.currentTimeMillis() + ".txt");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(("--- KARAGE SECURITY LAB RAPORU ---\nTarih: " + new Date() + "\n\n").getBytes());

            for (String line : logs) {
                fos.write((line + "\n").getBytes());
            }
            fos.close();
            return file.getAbsolutePath();
        } catch (Exception e) {
            return "Rapor Hatası: " + e.getMessage();
        }
    }
}