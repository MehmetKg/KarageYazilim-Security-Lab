package com.example.karageyazilimsecuritylab;

import java.net.InetSocketAddress;
import java.net.Socket;

public class ReconModule {

    public static void runWappalyzer(String url, RequestManager.Callback callback) {
        callback.onResult("[*] Teknoloji Analizi: " + url);
        RequestManager.submit(() -> {
            String res = RequestManager.makeHttpRequest(url);
            String body = res.contains("||BODY:") ? res.split("\\|\\|BODY:")[1].toLowerCase() : "";
            if(body.contains("wp-content")) callback.onResult("[+] CMS: WordPress");
            else if(body.contains("joomla")) callback.onResult("[+] CMS: Joomla");
            else callback.onResult("[-] CMS tespit edilemedi.");
        });
    }

    public static void runNmap(String ip, RequestManager.Callback callback) {
        callback.onResult("[*] Port Taraması (Hızlı): " + ip);
        int[] ports = {21, 22, 53, 80, 443, 3306, 8080};
        for(int p : ports) {
            RequestManager.submit(() -> {
                try {
                    Socket s = new Socket();
                    s.connect(new InetSocketAddress(ip, p), 1000);
                    s.close();
                    callback.onResult("<font color='#00FF00'>[+] AÇIK PORT: " + p + "</font>");
                } catch (Exception e) {}
            });
        }
    }

    public static void runDirsearch(String url, RequestManager.Callback callback) {
        callback.onResult("[*] Dizin Taraması: " + url);
        String[] paths = {"admin", "login", "robots.txt", "sitemap.xml", ".env", "config"};
        for(String p : paths) {
            RequestManager.submit(() -> {
                String target = url + "/" + p;
                String res = RequestManager.makeHttpRequest(target);
                if(res.startsWith("CODE:200")) callback.onResult("<font color='#00FF00'>[+] BULUNDU: /" + p + "</font>");
            });
        }
    }

    public static void runSubdomainEnum(String domain, RequestManager.Callback callback) {
        callback.onResult("[*] Subdomain Taraması...");
        String[] subs = {"www", "mail", "ftp", "cpanel", "webmail", "dev"};
        for(String sub : subs) {
            RequestManager.submit(() -> {
                try {
                    java.net.InetAddress.getByName(sub + "." + domain);
                    callback.onResult("<font color='#00FF00'>[+] Subdomain: " + sub + "." + domain + "</font>");
                } catch(Exception e){}
            });
        }
    }

    public static void getIP(String url, RequestManager.Callback callback) {
        RequestManager.submit(() -> {
            try {
                String domain = url.replace("https://", "").replace("http://", "").split("/")[0];
                String ip = java.net.InetAddress.getByName(domain).getHostAddress();
                callback.onResult("[+] IP Adresi: " + ip);
            } catch (Exception e) { callback.onResult("[-] IP Bulunamadı."); }
        });
    }

    public static void getHeaders(String url, RequestManager.Callback callback) {
        RequestManager.submit(() -> {
            String res = RequestManager.makeHttpRequest(url);
            if(res.contains("||HEAD:")) callback.onResult(res.split("\\|\\|HEAD:")[1].split("\\|\\|BODY:")[0]);
        });
    }
}