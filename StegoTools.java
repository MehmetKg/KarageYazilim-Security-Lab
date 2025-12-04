package com.example.karageyazilimsecuritylab;

import android.content.Context;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;

public class StegoTools {

    // Resmin içine mesaj gizle
    public static String hideMessage(Context context, String imageName, String message) {
        try {
            // Önce örnek bir resim oluşturalım (Yoksa)
            File file = new File(context.getFilesDir(), imageName);
            if (!file.exists()) {
                FileOutputStream fos = new FileOutputStream(file);
                // Basit bir BMP header (Boş resim)
                byte[] bmpHeader = new byte[]{0x42, 0x4D, 0x36, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x36, 0x00, 0x00, 0x00};
                fos.write(bmpHeader);
                fos.close();
            }

            // Mesajı özel bir etiketle ekle
            String secret = "###KARAGE_SECRET###" + message;
            FileOutputStream fos = new FileOutputStream(file, true); // Append mode
            fos.write(secret.getBytes());
            fos.close();

            return "Mesaj gizlendi -> " + imageName + "\nBoyut: " + file.length() + " bytes";
        } catch (Exception e) { return "Stego Hatası: " + e.getMessage(); }
    }

    // Resimden mesajı oku
    public static String readMessage(Context context, String imageName) {
        try {
            File file = new File(context.getFilesDir(), imageName);
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();

            String content = new String(data);
            if (content.contains("###KARAGE_SECRET###")) {
                String[] parts = content.split("###KARAGE_SECRET###");
                return "GİZLİ MESAJ ÇÖZÜLDÜ:\n" + parts[parts.length - 1];
            } else {
                return "Bu dosyada gizli mesaj yok.";
            }
        } catch (Exception e) { return "Dosya okunamadı."; }
    }
}