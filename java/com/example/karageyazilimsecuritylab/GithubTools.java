package com.example.karageyazilimsecuritylab;

public class GithubTools {

    // GITHUB DORK SCANNER (Kod Sızıntısı Arayıcı)
    public static void runGithubRecon(String target, RequestManager.Callback callback) {
        callback.onResult("🐙 GITHUB ISTİHBARATI (LEAK SCAN): " + target);
        callback.onResult("Aşağıdaki linkler GitHub üzerinde sızdırılmış verileri arar:");

        String[] dorks = {
                "password", "secret", "api_key", "client_secret",
                "access_token", "config", "db_password", "auth"
        };

        for (String dork : dorks) {
            String link = "https://github.com/search?q=%22" + target + "%22+" + dork + "&type=Code";
            callback.onResult("[LINK] " + dork.toUpperCase() + ": " + link);
        }

        callback.onResult("[*] Linklere tıklayarak tarayıcıda açın.");
    }
}