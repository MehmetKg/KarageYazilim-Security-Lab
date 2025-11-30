package com.example.karageyazilimsecuritylab; 

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private TextView tvConsole;
    private EditText etCommand;
    private ScrollView scrollView;

    private final List<String> commandHistory = new ArrayList<>();

  
    private final ExecutorService executor = Executors.newFixedThreadPool(50);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvConsole = findViewById(R.id.tvConsole);
        etCommand = findViewById(R.id.etCommand);
        scrollView = findViewById(R.id.scrollView);
        Button btnSubmit = findViewById(R.id.btnSubmit);

        
        printColor("--- KARAGE YAZILIM LAB v3.1 [ONLINE] ---", "#00FF00");
        printColor("System initialized. Type 'help' for tools.", "#CCCCCC");

      
        etCommand.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEND) {
                handleInput();
                return true;
            }
            return false;
        });

        
        btnSubmit.setOnClickListener(v -> handleInput());
    }

    private void handleInput() {
        String input = etCommand.getText().toString().trim();
        if (input.isEmpty()) return;

        etCommand.setText(""); 
        printColor("root@karage:~$ " + input, "#FFFFFF");
        commandHistory.add(input);

        String[] parts = input.split("\\s+");
        String cmd = parts[0].toLowerCase();
        String arg1 = parts.length > 1 ? parts[1] : "";
        String arg2 = parts.length > 2 ? parts[2] : "";

        // Komutları işle
        processCommand(cmd, arg1, arg2);
    }

    private void processCommand(String cmd, String arg1, String arg2) {
        switch (cmd) {
            case "help": showHelp(); break;
            case "clear":
                tvConsole.setText("");
                printColor("--- Console Cleared ---", "#00FF00");
                break;
            case "sysinfo": showSysInfo(); break;
            case "history": showHistory(); break;

            // NETWORK
            case "myip": execMyIp(); break;
            case "ping": if(!arg1.isEmpty()) execPing(arg1); else printErr("Kullanım: ping <host>"); break;
            case "portscan": if(!arg1.isEmpty()) execPortScan(arg1); else printErr("Kullanım: portscan <ip>"); break;
            case "lanscan": if(!arg1.isEmpty()) execLanScan(arg1); else printErr("Kullanım: lanscan <192.168.1>"); break;
            case "whois": if(!arg1.isEmpty()) execWhois(arg1); else printErr("Kullanım: whois <domain>"); break;
            case "dns": if(!arg1.isEmpty()) execDnsLookup(arg1); else printErr("Kullanım: dns <domain>"); break;

            // WEB
            case "headers": if(!arg1.isEmpty()) execHeaders(arg1); else printErr("Kullanım: headers <url>"); break;
            case "robots": if(!arg1.isEmpty()) execRobots(arg1); else printErr("Kullanım: robots <url>"); break;
            case "adminfinder": if(!arg1.isEmpty()) execAdminFinder(arg1); else printErr("Kullanım: adminfinder <site.com>"); break;
            case "spider": if(!arg1.isEmpty()) execSpider(arg1); else printErr("Kullanım: spider <url>"); break;
            case "sqli": if(!arg1.isEmpty()) execSqlScanner(arg1); else printErr("Kullanım: sqli <url>"); break;
            case "xss": if(!arg1.isEmpty()) execXssScanner(arg1); else printErr("Kullanım: xss <url>"); break;
            case "subdomain": if(!arg1.isEmpty()) execSubdomain(arg1); else printErr("Kullanım: subdomain <domain>"); break;

            // TOOLS
            case "hash": if(!arg1.isEmpty() && !arg2.isEmpty()) execHash(arg1, arg2); else printErr("Kullanım: hash <md5/sha1> <text>"); break;
            case "base64": if(!arg1.isEmpty() && !arg2.isEmpty()) execBase64(arg1, arg2); else printErr("Kullanım: base64 <enc/dec> <text>"); break;
            case "maclookup": if(!arg1.isEmpty()) execMacLookup(arg1); else printErr("Kullanım: maclookup <mac_adres>"); break;
            case "passgen": execPassGen(arg1); break;

            default: printErr("Komut bulunamadı: " + cmd);
        }
    }

   
    // Bu metod UI Thread üzerinde çalışmaya zorlar.
    private void printColor(String text, String colorHex) {
        runOnUiThread(() -> {
            try {
                // HTML formatı ile renkli yazı
                String coloredText = "<font color='" + colorHex + "'>" + text + "</font><br>";
                tvConsole.append(Html.fromHtml(coloredText, Html.FROM_HTML_MODE_LEGACY));

                // Otomatik aşağı kaydırma (Scroll)
                scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
            } catch (Exception e) {
                // Eğer HTML hatası olursa düz yaz
                tvConsole.append("\n" + text);
            }
        });
    }

    private void print(String text) { printColor(text, "#00FF00"); } // Varsayılan Yeşil
    private void printErr(String text) { printColor(text, "#FF0000"); } // Hata Kırmızı

    // --- TOOL IMPLEMENTASYONLARI ---

    private void execPing(String host) {
        print("Ping başlatılıyor: " + host);
        executor.execute(() -> {
            try {
                // -c 3: 3 paket gönder
                Process p = Runtime.getRuntime().exec("ping -c 3 " + host);
                BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    print(line);
                }
            } catch (Exception e) { printErr("Ping Hatası: " + e.getMessage()); }
        });
    }

    private void execPortScan(String ip) {
        print(ip + " üzerinde port taraması başladı...");
        int[] ports = {21,22,23,25,53,80,110,135,139,443,445,3306,3389,8080};
        for (int port : ports) {
            executor.execute(() -> {
                try {
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress(ip, port), 2000); // 2 sn timeout
                    printColor("[AÇIK] Port " + port + " is OPEN!", "#00FFFF");
                    socket.close();
                } catch (Exception ignored) {}
            });
        }
    }

    private void execLanScan(String subnet) {
        print("Ağ taranıyor: " + subnet + ".1-255");
        for (int i = 1; i < 255; i++) {
            String host = subnet + "." + i;
            executor.execute(() -> {
                try {
                    InetAddress addr = InetAddress.getByName(host);
                    if (addr.isReachable(1000)) {
                        printColor("[AKTİF] " + host + " (" + addr.getHostName() + ")", "#00FF00");
                    }
                } catch (Exception ignored) {}
            });
        }
    }

    private void execMyIp() {
        executor.execute(() -> {
            try {
                print("IP Sorgulanıyor...");
                URL url = new URL("https://api.ipify.org");
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                printColor("Public IP: " + in.readLine(), "#FFFF00");
            } catch (Exception e) { printErr("İnternet bağlantısı yok."); }
        });
    }

    private void execWhois(String domain) {
        executor.execute(() -> {
            try {
                print("Whois: " + domain);
                Socket s = new Socket("whois.iana.org", 43);
                PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                out.println(domain);
                String line;
                int limit = 0;
                while ((line = in.readLine()) != null && limit < 20) {
                    print(line);
                    limit++;
                }
                s.close();
            } catch (Exception e) { printErr("Whois hatası."); }
        });
    }

    private void execDnsLookup(String domain) {
        executor.execute(() -> {
            try {
                InetAddress[] addresses = InetAddress.getAllByName(domain);
                for (InetAddress addr : addresses) {
                    print("DNS (A): " + addr.getHostAddress());
                }
            } catch (Exception e) { printErr("DNS çözülemedi."); }
        });
    }

    private void execAdminFinder(String domain) {
        print("Admin paneli aranıyor: " + domain);
        String[] paths = {"admin", "login", "wp-login.php", "administrator", "cpanel", "user"};
        for (String path : paths) {
            executor.execute(() -> {
                try {
                    URL url = new URL("http://" + domain + "/" + path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("HEAD");
                    conn.setConnectTimeout(3000);
                    int code = conn.getResponseCode();
                    if (code == 200 || code == 301 || code == 302 || code == 403) {
                        printColor("[BULUNDU] " + path + " (Code: " + code + ")", "#00FFFF");
                    }
                } catch (Exception ignored) {}
            });
        }
    }

    // Basit Örümcek (Link Bulucu)
    private void execSpider(String urlString) {
        executor.execute(() -> {
            try {
                print("Site taranıyor...");
                URL url = new URL(urlString);
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String line;
                StringBuilder content = new StringBuilder();
                while((line = in.readLine()) != null) content.append(line);

                Matcher m = Pattern.compile("href=\"(.*?)\"").matcher(content.toString());
                int count = 0;
                while(m.find() && count < 10) {
                    print("Link: " + m.group(1));
                    count++;
                }
                if(count == 0) print("Link bulunamadı.");
            } catch (Exception e) { printErr("Spider hatası: " + e.getMessage()); }
        });
    }

    private void execSqlScanner(String url) {
        executor.execute(() -> {
            print("SQL testi yapılıyor...");
            try {
                URL u = new URL(url + "'");
                BufferedReader in = new BufferedReader(new InputStreamReader(u.openStream()));
                String line;
                boolean vuln = false;
                while((line=in.readLine())!=null) {
                    if(line.contains("SQL") || line.contains("syntax")) { vuln = true; break; }
                }
                if(vuln) printColor("[KRİTİK] SQL Açığı Olabilir!", "#FF0000");
                else print("[-] Basit hata mesajı dönmedi.");
            } catch(Exception e) { print("Bağlantı hatası."); }
        });
    }

    private void execSubdomain(String domain) {
        print("Subdomain brute-force...");
        String[] subs = {"www", "mail", "ftp", "test", "api", "dev"};
        for(String sub : subs) {
            executor.execute(() -> {
                try {
                    InetAddress.getByName(sub + "." + domain);
                    printColor("[+] " + sub + "." + domain + " AKTİF", "#00FF00");
                } catch(Exception ignored){}
            });
        }
    }

    // Diğer araçlar (Boş kontrolü yapıldı)
    private void execHeaders(String u) { print("Headers (Simüle) çalıştı."); }
    private void execRobots(String u) { print("Robots (Simüle) çalıştı."); }
    private void execXssScanner(String u) { print("XSS (Simüle) çalıştı."); }
    private void execMacLookup(String m) { print("MAC Lookup servisi şu an devre dışı."); }

    private void execHash(String algo, String text) {
        try {
            MessageDigest md = MessageDigest.getInstance(algo.toUpperCase());
            byte[] digest = md.digest(text.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) sb.append(String.format("%02x", b));
            printColor("HASH: " + sb.toString(), "#FFFF00");
        } catch (Exception e) { printErr("Algoritma yok."); }
    }

    private void execBase64(String mode, String text) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if(mode.equals("enc")) print(Base64.getEncoder().encodeToString(text.getBytes()));
                else print(new String(Base64.getDecoder().decode(text)));
            }
        } catch(Exception e) { printErr("Hata."); }
    }

    private void execPassGen(String len) {
        printColor("Şifre: " + new SecureRandom().nextInt(999999), "#00FF00");
    }

    private void showHelp() {
        print("Mevcut Komutlar:\n" +
                "ping, portscan, lanscan, myip, whois, dns\n" +
                "adminfinder, spider, sqli, subdomain\n" +
                "hash, base64, sysinfo, clear");
    }

    private void showSysInfo() {
        print("Model: " + Build.MODEL + "\nAndroid: " + Build.VERSION.RELEASE);
    }

    private void showHistory() {
        for(String s : commandHistory) print(s);
    }
}
