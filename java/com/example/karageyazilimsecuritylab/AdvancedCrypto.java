package com.example.karageyazilimsecuritylab;

import android.content.Context;
import android.util.Base64;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AdvancedCrypto {

    // MAINACTIVITY'NİN ÇAĞIRDIĞI ANA METOT
    // crypto <string> komutu buraya gelir.
    public static void analyze(String input, RequestManager.Callback callback) {

        // 1. RSA ANAHTAR İSTEĞİ Mİ?
        if (input.equalsIgnoreCase("keygen") || input.equalsIgnoreCase("rsa")) {
            generateRSAKeys(callback);
            return;
        }

        // 2. BASİT HASH/ŞİFRE ANALİZİ
        callback.onResult("🔐 KRİPTO ANALİZİ: " + input);

        if (input.endsWith("=")) {
            callback.onResult("Tespit: Base64 Şifreleme");
            try {
                String decoded = new String(Base64.decode(input, Base64.DEFAULT));
                callback.onResult("Çözüldü: " + decoded);
            } catch (Exception e) {}
        }
        else if (input.length() == 32) callback.onResult("Tespit: MD5 Hash");
        else if (input.length() == 40) callback.onResult("Tespit: SHA-1 Hash");
        else if (input.length() == 64) callback.onResult("Tespit: SHA-256 Hash");
        else callback.onResult("Tespit: Bilinmeyen format veya düz metin.");
    }

    // --- SENİN YAZDIĞIN GELİŞMİŞ ÖZELLİKLER (ASYNC HALE GETİRİLDİ) ---

    // 1. RSA KEY GENERATOR (Arka planda çalışır)
    public static void generateRSAKeys(RequestManager.Callback callback) {
        callback.onResult("🔑 RSA-2048 ANAHTAR ÇİFTİ ÜRETİLİYOR...");

        RequestManager.submit(() -> {
            try {
                KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
                kpg.initialize(2048);
                KeyPair kp = kpg.generateKeyPair();

                String pub = Base64.encodeToString(kp.getPublic().getEncoded(), Base64.DEFAULT);
                String priv = Base64.encodeToString(kp.getPrivate().getEncoded(), Base64.DEFAULT);

                callback.onResult("✅ BAŞARILI!\n\n[PUBLIC KEY]:\n" + pub + "\n\n[PRIVATE KEY] (Sakla!):\n" + priv);
            } catch (Exception e) {
                callback.onResult("Keygen Hatası: " + e.getMessage());
            }
        });
    }

    // 2. AES DOSYA ŞİFRELEME (Gelecekteki 'encrypt' komutu için hazır)
    public static void encryptFile(Context context, String filename, String password, RequestManager.Callback callback) {
        callback.onResult("🔒 DOSYA ŞİFRELENİYOR: " + filename);
        RequestManager.submit(() -> {
            String res = processFile(context, filename, filename + ".enc", password, Cipher.ENCRYPT_MODE);
            callback.onResult(res);
        });
    }

    // 3. AES DOSYA ŞİFRE ÇÖZME (Gelecekteki 'decrypt' komutu için hazır)
    public static void decryptFile(Context context, String filename, String password, RequestManager.Callback callback) {
        callback.onResult("🔓 ŞİFRE ÇÖZÜLÜYOR: " + filename);
        RequestManager.submit(() -> {
            String outFile = filename.replace(".enc", "");
            if(outFile.equals(filename)) outFile = filename + ".dec";

            String res = processFile(context, filename, outFile, password, Cipher.DECRYPT_MODE);
            callback.onResult(res);
        });
    }

    // Ortak Motor (String döndürmeye devam edebilir, çünkü background thread içinde çağrılıyor)
    private static String processFile(Context context, String inFile, String outFile, String password, int mode) {
        try {
            File fin = new File(context.getFilesDir(), inFile);
            if(!fin.exists()) return "Hata: Dosya bulunamadı -> " + inFile;

            File fout = new File(context.getFilesDir(), outFile);

            // Şifreden Key ve IV türet
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] keyBytes = sha.digest(password.getBytes("UTF-8"));
            byte[] key16 = new byte[16];
            byte[] iv16 = new byte[16];
            System.arraycopy(keyBytes, 0, key16, 0, 16);
            System.arraycopy(keyBytes, 16, iv16, 0, 16);

            SecretKeySpec secretKey = new SecretKeySpec(key16, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv16);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(mode, secretKey, ivSpec);

            FileInputStream fis = new FileInputStream(fin);
            FileOutputStream fos = new FileOutputStream(fout);

            byte[] inputBytes = new byte[(int) fin.length()];
            fis.read(inputBytes);
            byte[] outputBytes = cipher.doFinal(inputBytes);

            fos.write(outputBytes);
            fis.close();
            fos.close();

            return (mode == Cipher.ENCRYPT_MODE ? "KİLİTLENDİ [Encrypted]: " : "AÇILDI [Decrypted]: ") + outFile;

        } catch (Exception e) {
            return "Kripto İşlem Hatası: " + e.getMessage();
        }
    }
}