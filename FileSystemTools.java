package com.example.karageyazilimsecuritylab;

import android.content.Context;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class FileSystemTools {

    // ls: Dosyaları listele
    public static String listFiles(Context context) {
        File dir = context.getFilesDir();
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) return "Dizin boş.";

        StringBuilder sb = new StringBuilder("Dizin: " + dir.getAbsolutePath() + "\n");
        sb.append("--------------------------------------------------\n");
        for (File file : files) {
            String size = file.length() + " B";
            sb.append(String.format("%-20s | %s\n", file.getName(), size));
        }
        return sb.toString();
    }

    // touch: Dosya oluştur ve yaz
    public static String createFile(Context context, String fileName, String content) {
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(content.getBytes());
            fos.close();
            return "Dosya oluşturuldu: " + fileName;
        } catch (Exception e) {
            return "Hata: Dosya yazılamadı.";
        }
    }

    // cat: Dosya oku
    public static String readFile(Context context, String fileName) {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            fis.close();
            return "--- " + fileName + " ---\n" + sb.toString();
        } catch (Exception e) {
            return "Hata: Dosya bulunamadı.";
        }
    }

    // rm: Dosya sil
    public static String deleteFile(Context context, String fileName) {
        File dir = context.getFilesDir();
        File file = new File(dir, fileName);
        if (file.exists() && file.delete()) {
            return "Silindi: " + fileName;
        } else {
            return "Hata: Dosya silinemedi veya yok.";
        }
    }
}