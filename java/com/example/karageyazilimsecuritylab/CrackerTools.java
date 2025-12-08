package com.example.karageyazilimsecuritylab;

import java.security.MessageDigest;

public class CrackerTools {

    // En çok kullanılan 50 şifre (Sözlük Saldırısı için)
    private static final String[] COMMON_PASSWORDS = {
            "123456", "password", "12345678", "qwerty", "12345", "123456789", "football", "aaaaaa",
            "admin", "1234567", "princess", "iloveyou", "master", "123123", "666666", "111111",
            "google", "test", "1234", "login", "user", "pass", "admin123", "root", "toor",
            "hello", "turkiye", "galatasaray", "fenerbahce", "besiktas", "istanbul", "1905", "1907", "1903",
            "kayseri", "sifre", "1234567890", "letmein", "sunshine", "monkey", "dragon"
    };

    // MainActivity ile uyumlu hale getirilen metod
    // String döndürmek yerine "callback" kullanıyor
    public static void crackHash(String inputHash, RequestManager.Callback callback) {

        callback.onResult("🔨 HASH KIRICI BAŞLATILDI...");
        callback.onResult("Hedef: " + inputHash);

        // İşlemi arka plana at (RequestManager ile)
        RequestManager.submit(() -> {
            try {
                String targetHash = inputHash.toLowerCase().trim();

                // Hedef Hash'in türünü tahmin et (Uzunluğa göre)
                String algo = "MD5";
                if (targetHash.length() == 40) algo = "SHA-1";
                else if (targetHash.length() == 64) algo = "SHA-256";
                else if (targetHash.length() == 32) algo = "MD5";
                else {
                    callback.onResult("[-] Hata: Bilinmeyen Hash formatı (Uzunluk: " + targetHash.length() + ")");
                    return;
                }

                callback.onResult("[*] Algoritma Tespiti: " + algo);
                callback.onResult("[*] Sözlük saldırısı deneniyor (" + COMMON_PASSWORDS.length + " kelime)...");

                MessageDigest md = MessageDigest.getInstance(algo);

                for (String pass : COMMON_PASSWORDS) {
                    // Her şifreyi hashle
                    byte[] digest = md.digest(pass.getBytes());
                    StringBuilder sb = new StringBuilder();
                    for (byte b : digest) sb.append(String.format("%02x", b));
                    String calculatedHash = sb.toString();

                    // Eşleşme var mı?
                    if (calculatedHash.equals(targetHash)) {
                        callback.onResult("<font color='#00FF00'>[✔] BAŞARILI! Şifre Kırıldı: [" + pass + "]</font>");
                        return; // Bulunca döngüden çık
                    }
                }

                // Döngü bitti ama bulunamadıysa
                callback.onResult("[-] BAŞARISIZ. Şifre wordlist içinde yok.");

            } catch (Exception e) {
                callback.onResult("Algoritma hatası: " + e.getMessage());
            }
        });
    }
}