package com.example.karageyazilimsecuritylab;

import android.content.Context;
import android.media.ExifInterface; // DÜZELTİLDİ: AndroidX yerine Standart Android kütüphanesi
import java.io.File;
import java.io.IOException;

public class ForensicsTools {

    public static String getExifData(Context context, String filename) {
        File file = new File(context.getFilesDir(), filename);
        if (!file.exists()) return "Hata: Dosya bulunamadı. Önce dosyayı oluşturun veya taşıyın.";

        try {
            // Standart Android Exif Okuyucusu (Harici kütüphane gerektirmez)
            ExifInterface exif = new ExifInterface(file.getAbsolutePath());

            StringBuilder sb = new StringBuilder("--- EXIF ANALİZ RAPORU ---\n");
            sb.append("Dosya: ").append(filename).append("\n");

            // Kritik Veriler
            String date = exif.getAttribute(ExifInterface.TAG_DATETIME);
            String model = exif.getAttribute(ExifInterface.TAG_MODEL);
            String make = exif.getAttribute(ExifInterface.TAG_MAKE);
            String lat = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
            String lon = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);

            sb.append("Tarih/Saat : ").append(date != null ? date : "Bilinmiyor").append("\n");
            sb.append("Cihaz      : ").append(make != null ? make : "").append(" ").append(model != null ? model : "Bilinmiyor").append("\n");

            if (lat != null && lon != null) {
                // Koordinatları hesapla
                float[] latLong = new float[2];
                if (exif.getLatLong(latLong)) {
                    sb.append("KONUM [!!!]: ").append(latLong[0]).append(", ").append(latLong[1]).append("\n");
                    sb.append("Harita Linki: https://www.google.com/maps?q=").append(latLong[0]).append(",").append(latLong[1]).append("\n");
                }
            } else {
                sb.append("Konum      : GPS verisi bulunamadı.\n");
            }

            return sb.toString();
        } catch (Exception e) {
            return "Exif Hatası: " + e.getMessage();
        }
    }
}