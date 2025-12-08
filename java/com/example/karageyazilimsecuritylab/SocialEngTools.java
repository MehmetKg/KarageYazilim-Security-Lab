package com.example.karageyazilimsecuritylab;

import java.util.Random;

public class SocialEngTools {

    // Senin tanımladığın zengin veri setleri
    private static final String[] NAMES = {"Ahmet", "Mehmet", "Ayşe", "Fatma", "John", "Smith", "Alice", "Bob", "Can", "Zeynep"};
    private static final String[] SURNAMES = {"Yılmaz", "Demir", "Kaya", "Doe", "Brown", "Wilson", "Öztürk", "Çelik", "Arslan"};
    private static final String[] CITIES = {"Istanbul", "Ankara", "New York", "London", "Berlin", "Kayseri", "Izmir", "Tokyo"};
    private static final String[] EMAILS = {"gmail.com", "yahoo.com", "hotmail.com", "protonmail.com", "outlook.com"};

    // 1. SAHTE KİMLİK OLUŞTURUCU (MainActivity 'fakeid' komutu bunu çağırır)
    public static void generateFakeID(RequestManager.Callback callback) {
        Random r = new Random();

        String name = NAMES[r.nextInt(NAMES.length)];
        String surname = SURNAMES[r.nextInt(SURNAMES.length)];
        String city = CITIES[r.nextInt(CITIES.length)];
        String emailDomain = EMAILS[r.nextInt(EMAILS.length)];

        // Rastgele Kredi Kartı (Görsel amaçlı)
        String cc = "4" + (1000 + r.nextInt(8999)) + " " + (1000 + r.nextInt(8999)) + " " + (1000 + r.nextInt(8999)) + " " + (1000 + r.nextInt(8999));

        // Rastgele Telefon
        String phone = "+90 5" + (30 + r.nextInt(20)) + " " + (100 + r.nextInt(899)) + " " + (10 + r.nextInt(89)) + " " + (10 + r.nextInt(89));

        // Sonuçları ekrana bas (Callback ile)
        callback.onResult("🎭 SAHTE KİMLİK (Test Amaçlı Oluşturuldu):");
        callback.onResult("------------------------------------------------");
        callback.onResult("İsim     : " + name + " " + surname);
        callback.onResult("Şehir    : " + city);
        callback.onResult("Email    : " + name.toLowerCase() + "." + surname.toLowerCase() + "@" + emailDomain);
        callback.onResult("Telefon  : " + phone);
        callback.onResult("CC No    : " + cc + " (CVV: " + (100 + r.nextInt(899)) + ")");
        callback.onResult("------------------------------------------------");
    }

    // 2. OLTALAMA LİNK ÜRETİCİ (MainActivity 'phish' komutu bunu çağırır)
    public static void generatePhishLink(String site, RequestManager.Callback callback) {
        callback.onResult("🎣 OLTALAMA (PHISHING) SİMÜLASYONU: " + site);

        String clean = site.replace("https://", "").replace("http://", "").replace("www.", "");

        callback.onResult("Kurbanı kandırmak için potansiyel linkler:");
        callback.onResult("[1] http://" + clean + "-secure-login.com");
        callback.onResult("[2] http://verify-account." + clean + ".net");
        callback.onResult("[3] http://support." + clean + ".update-v2.org");

        callback.onResult("<font color='#FF0000'>[UYARI] Bu araç sadece eğitim ve farkındalık testleri içindir.</font>");
    }
}