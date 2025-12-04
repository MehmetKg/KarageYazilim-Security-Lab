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

    // 1. RSA KEY GENERATOR (Anahtar Çifti Üretici)
    public static String generateRSAKeys() {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048);
            KeyPair kp = kpg.generateKeyPair();

            String pub = Base64.encodeToString(kp.getPublic().getEncoded(), Base64.DEFAULT);
            String priv = Base64.encodeToString(kp.getPrivate().getEncoded(), Base64.DEFAULT);

            return "--- RSA 2048-BIT ANAHTAR ÇİFTİ ---\n\n[PUBLIC KEY]:\n" + pub +
                    "\n[PRIVATE KEY] (Sakla!):\n" + priv;
        } catch (Exception e) { return "Keygen Hatası: " + e.getMessage(); }
    }

    // 2. AES DOSYA ŞİFRELEME
    public static String encryptFile(Context context, String filename, String password) {
        return processFile(context, filename, filename + ".enc", password, Cipher.ENCRYPT_MODE);
    }

    // 3. AES DOSYA ŞİFRE ÇÖZME
    public static String decryptFile(Context context, String filename, String password) {
        String outFile = filename.replace(".enc", "");
        if(outFile.equals(filename)) outFile = filename + ".dec";
        return processFile(context, filename, outFile, password, Cipher.DECRYPT_MODE);
    }

    // Ortak Şifreleme Motoru (AES-128)
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

            // İşlemi yap
            byte[] inputBytes = new byte[(int) fin.length()];
            fis.read(inputBytes);
            byte[] outputBytes = cipher.doFinal(inputBytes);

            fos.write(outputBytes);
            fis.close();
            fos.close();

            // Güvenlik: Şifrelerken orijinal dosyayı sil (Opsiyonel, şimdilik kapalı kalsın)
            // if(mode == Cipher.ENCRYPT_MODE) fin.delete();

            return (mode == Cipher.ENCRYPT_MODE ? "KİLİTLENDİ 🔒: " : "AÇILDI 🔓: ") + outFile;

        } catch (Exception e) {
            return "Kripto İşlem Hatası: " + e.getMessage();
        }
    }
}