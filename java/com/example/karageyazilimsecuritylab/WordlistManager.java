package com.example.karageyazilimsecuritylab;

import android.content.Context;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class WordlistManager {
    public static void downloadWordlist(Context context, String name, RequestManager.Callback callback) {
        callback.onResult("⬇️ İndiriliyor: " + name);
        RequestManager.submit(() -> {
            try {
                String link = name.equals("rockyou") ? "https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt" : "";
                if(link.isEmpty()) { callback.onResult("Bilinmeyen liste."); return; }

                URL url = new URL(link);
                File file = new File(context.getFilesDir(), name + ".txt");
                // Basit indirme mantığı (Stream ile daha iyi olur ama bu yeterli)
                try (java.io.InputStream in = url.openStream()) {
                    java.nio.file.Files.copy(in, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
                callback.onResult("✅ İndirildi: " + file.getAbsolutePath());
            } catch (Exception e) { callback.onResult("Hata: " + e.getMessage()); }
        });
    }
}