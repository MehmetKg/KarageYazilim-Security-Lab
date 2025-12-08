package com.example.karageyazilimsecuritylab;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class PdfGenerator {

    public static String createPdf(Context context, List<String> logs) {
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(12);

        int x = 10, y = 25;

        // Başlık
        paint.setFakeBoldText(true);
        paint.setTextSize(18);
        page.getCanvas().drawText("KARAGE SECURITY LAB - RAPOR", x, y, paint);
        y += 30;

        paint.setFakeBoldText(false);
        paint.setTextSize(10);

        // Logları Yazdır
        for (String line : logs) {
            String cleanLine = line.replaceAll("<[^>]*>", ""); // HTML temizle

            // Satır çok uzunsa kes (Basit çözüm)
            if (cleanLine.length() > 80) cleanLine = cleanLine.substring(0, 80) + "...";

            page.getCanvas().drawText(cleanLine, x, y, paint);
            y += 14;

            if (y > 800) { // Sayfa dolduysa dur (Tek sayfa limiti)
                page.getCanvas().drawText("... (Rapor Devamı Kesildi) ...", x, y, paint);
                break;
            }
        }

        document.finishPage(page);

        // DOSYAYI "DOWNLOADS" (İNDİRİLENLER) KLASÖRÜNE KAYDET
        String fileName = "KSL_Rapor_" + System.currentTimeMillis() + ".pdf";
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);

        try {
            document.writeTo(new FileOutputStream(file));
            document.close();
            return file.getAbsolutePath(); // Dosya yolunu döndür
        } catch (Exception e) {
            document.close();
            return "Hata: " + e.getMessage();
        }
    }
}