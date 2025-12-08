package com.example.karageyazilimsecuritylab;

import android.content.Context;
import android.content.Intent;

public class TermuxBridge {
    public static void executeCommand(Context context, String command, RequestManager.Callback callback) {
        callback.onResult("🚀 Termux Komutu Gönderiliyor: " + command);
        try {
            Intent intent = new Intent();
            intent.setClassName("com.termux", "com.termux.app.RunCommandService");
            intent.setAction("com.termux.RUN_COMMAND");
            intent.putExtra("com.termux.RUN_COMMAND_PATH", "/data/data/com.termux/files/usr/bin/bash");
            intent.putExtra("com.termux.RUN_COMMAND_ARGUMENTS", new String[]{"-c", command});
            intent.putExtra("com.termux.RUN_COMMAND_WORKDIR", "/data/data/com.termux/files/home");
            intent.putExtra("com.termux.RUN_COMMAND_BACKGROUND", true);
            context.startService(intent);
            callback.onResult("[✔] Komut Termux'a iletildi.");
        } catch (Exception e) {
            callback.onResult("[!] Hata: Termux yüklü değil veya izin verilmedi.");
        }
    }
}