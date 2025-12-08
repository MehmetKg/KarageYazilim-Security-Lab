package com.example.karageyazilimsecuritylab;

public class TimeMachineModule {
    public static void getArchivedUrls(String domain, RequestManager.Callback callback) {
        callback.onResult("⏳ Arşiv Taranıyor: " + domain);
        RequestManager.submit(() -> {
            String api = "http://web.archive.org/cdx/search/cdx?url=" + domain + "/*&output=txt&fl=original&limit=20";
            String res = RequestManager.makeHttpRequest(api);
            if(res.startsWith("CODE:200")) {
                String[] lines = res.split("\\|\\|BODY:")[1].split("\n");
                for(String l : lines) callback.onResult("[ARCHIVE] " + l);
            } else {
                callback.onResult("[-] Arşivde veri bulunamadı.");
            }
        });
    }
}