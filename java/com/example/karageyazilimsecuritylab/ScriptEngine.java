package com.example.karageyazilimsecuritylab;

import android.content.Context;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ScriptEngine {
    public static void runScript(Context context, String scriptName, String args, RequestManager.Callback callback) {
        RequestManager.submit(() -> {
            try {
                File file = new File(context.getFilesDir(), scriptName);
                if (!file.exists()) {
                    // Assets'den kopyala (Eğer varsa)
                    callback.onResult("[*] Script hazırlanıyor: " + scriptName);
                    // Basitlik için sadece var olduğunu varsayıp çalıştırmayı deniyoruz
                }

                // Python veya Shell olarak çalıştır
                String cmd = scriptName.endsWith(".py") ? "python" : "sh";
                ProcessBuilder pb = new ProcessBuilder(cmd, file.getAbsolutePath(), args);
                pb.redirectErrorStream(true);
                Process p = pb.start();

                BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) callback.onResult(line);

                callback.onResult("İşlem bitti.");
            } catch (Exception e) {
                callback.onResult("Script Hatası: " + e.getMessage());
            }
        });
    }
}