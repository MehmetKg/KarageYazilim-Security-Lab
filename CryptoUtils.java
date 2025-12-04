package com.example.karageyazilimsecuritylab;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

public class CryptoUtils {

    public static String hash(String algo, String text) {
        try {
            MessageDigest md = MessageDigest.getInstance(algo);
            byte[] digest = md.digest(text.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) sb.append(String.format("%02x", b));
            return algo + ": " + sb.toString();
        } catch (Exception e) { return "Algoritma hatası."; }
    }

    public static String base64(String mode, String text) {
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                if (mode.equals("enc")) return Base64.getEncoder().encodeToString(text.getBytes());
                else return new String(Base64.getDecoder().decode(text));
            } else return "Android sürümü yetersiz.";
        } catch (Exception e) { return "Base64 Hatası."; }
    }

    public static String urlEncode(String text) {
        try { return URLEncoder.encode(text, StandardCharsets.UTF_8.toString()); }
        catch (Exception e) { return "Encode hatası."; }
    }
    // CryptoUtils.java içine eklenecek:

    public static String rot13(String input) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if       (c >= 'a' && c <= 'm') c += 13;
            else if  (c >= 'A' && c <= 'M') c += 13;
            else if  (c >= 'n' && c <= 'z') c -= 13;
            else if  (c >= 'N' && c <= 'Z') c -= 13;
            sb.append(c);
        }
        return "ROT13: " + sb.toString();
    }
}
