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

    public static String crackHash(String targetHash) {
        // Hedef Hash'in türünü tahmin et (Uzunluğa göre)
        String algo = "MD5";
        if (targetHash.length() == 40) algo = "SHA-1";
        else if (targetHash.length() == 64) algo = "SHA-256";

        targetHash = targetHash.toLowerCase();

        try {
            MessageDigest md = MessageDigest.getInstance(algo);

            for (String pass : COMMON_PASSWORDS) {
                // Her şifreyi hashle
                byte[] digest = md.digest(pass.getBytes());
                StringBuilder sb = new StringBuilder();
                for (byte b : digest) sb.append(String.format("%02x", b));
                String calculatedHash = sb.toString();

                // Eşleşme var mı?
                if (calculatedHash.equals(targetHash)) {
                    return "BAŞARILI! Şifre Kırıldı: [" + pass + "] (" + algo + ")";
                }
            }
        } catch (Exception e) {
            return "Algoritma hatası.";
        }
        return "BAŞARISIZ. Şifre wordlist içinde bulunamadı.";
    }
}