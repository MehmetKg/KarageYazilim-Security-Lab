package com.example.karageyazilimsecuritylab;

import java.util.Random;

public class SocialEngTools {

    private static final String[] NAMES = {"Ahmet", "Mehmet", "Ayşe", "Fatma", "John", "Smith", "Alice", "Bob"};
    private static final String[] SURNAMES = {"Yılmaz", "Demir", "Kaya", "Doe", "Brown", "Wilson", "Öztürk"};
    private static final String[] CITIES = {"Istanbul", "Ankara", "New York", "London", "Berlin", "Kayseri"};
    private static final String[] EMAILS = {"gmail.com", "yahoo.com", "hotmail.com", "protonmail.com"};

    public static String generateIdentity() {
        Random r = new Random();
        String name = NAMES[r.nextInt(NAMES.length)];
        String surname = SURNAMES[r.nextInt(SURNAMES.length)];
        String city = CITIES[r.nextInt(CITIES.length)];
        String emailDomain = EMAILS[r.nextInt(EMAILS.length)];

        // Rastgele Kredi Kartı (Luhn Algoritması olmadan, sadece görsel)
        String cc = "4" + (1000 + r.nextInt(8999)) + " " + (1000 + r.nextInt(8999)) + " " + (1000 + r.nextInt(8999)) + " " + (1000 + r.nextInt(8999));

        return "--- SAHTE KİMLİK (Test Amaçlı) ---\n" +
                "İsim     : " + name + " " + surname + "\n" +
                "Şehir    : " + city + "\n" +
                "Email    : " + name.toLowerCase() + "." + surname.toLowerCase() + "@" + emailDomain + "\n" +
                "Telefon  : +90 5" + (30 + r.nextInt(20)) + " " + (100 + r.nextInt(899)) + " " + (10 + r.nextInt(89)) + " " + (10 + r.nextInt(89)) + "\n" +
                "CC No    : " + cc + " (CVV: " + (100 + r.nextInt(899)) + ")";
    }
}