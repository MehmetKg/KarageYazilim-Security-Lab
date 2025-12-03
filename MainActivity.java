package com.example.karageyazilimsecuritylab;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private TextView tvConsole;
    private EditText etCommand;
    private ScrollView scrollView;

    // 50 Thread: Aynı anda 50 işlem yapabilen devasa bir havuz
    private final ExecutorService executor = Executors.newFixedThreadPool(50);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvConsole = findViewById(R.id.tvConsole);
        etCommand = findViewById(R.id.etCommand);
        scrollView = findViewById(R.id.scrollView);
        Button btnSubmit = findViewById(R.id.btnSubmit);

        // Başlangıç mesajı
        printColor("--- KARAGE SECURITY LAB v5.0 [ULTIMATE] ---", "#00FF00");
        printColor("System ready. Type 'help' for full command list.", "#CCCCCC");

        etCommand.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_DONE) {
                handleInput(); return true;
            } return false;
        });
        btnSubmit.setOnClickListener(v -> handleInput());
    }

    private void handleInput() {
        String input = etCommand.getText().toString().trim();
        if (input.isEmpty()) return;
        etCommand.setText("");
        printColor("root@karage:~# " + input, "#FFFFFF");

        String[] parts = input.split("\\s+");
        String cmd = parts[0].toLowerCase();
        String arg1 = parts.length > 1 ? parts[1] : "";
        String arg2 = parts.length > 2 ? parts[2] : "";

        processCommand(cmd, arg1, arg2);
    }

    // --- MERKEZİ KOMUT İŞLEME ---
    private void processCommand(String cmd, String arg1, String arg2) {
        switch (cmd) {
            // === TEMEL SİSTEM ===
            case "help": showHelp(); break;
            case "clear": tvConsole.setText(""); break;
            case "sysinfo": print(AdvancedNetworkTools.getSysInfo()); break;

            // === AĞ (NETWORK) ===
            case "myip": executor.execute(() -> print(AdvancedNetworkTools.getPublicIp())); break;
            case "ping":
                if(!arg1.isEmpty()) executor.execute(() -> print(AdvancedNetworkTools.pingHost(arg1)));
                else printErr("Kullanım: ping <host>"); break;
            case "portscan":
                if(!arg1.isEmpty()) execPortScan(arg1);
                else printErr("Kullanım: portscan <ip>"); break;
            case "wol":
                if(!arg1.isEmpty()) executor.execute(() -> print(AdvancedNetworkTools.wakeOnLan(arg1)));
                else printErr("Kullanım: wol <mac_adres>"); break;
            case "dns":
                if(!arg1.isEmpty()) executor.execute(() -> print(AdvancedNetworkTools.dnsLookup(arg1)));
                else printErr("Kullanım: dns <domain>"); break;
            case "trace":
                if(!arg1.isEmpty()) executor.execute(() -> print(AdvancedNetworkTools.traceRoute(arg1)));
                else printErr("Kullanım: trace <host>"); break;
            case "arp": // ARP Tablosu
                print(AdvancedNetworkTools.getArpTable()); break;
            case "nc": // Netcat Listener
                if(!arg1.isEmpty()) {
                    print("Netcat Dinleyicisi Başlatılıyor (Port: " + arg1 + ")...");
                    executor.execute(() -> AdvancedNetworkTools.startNetcatListener(arg1, result -> print(result)));
                } else printErr("Kullanım: nc <port>"); break;

            // === WEB GÜVENLİK ===
            case "headers":
                if(!arg1.isEmpty()) executor.execute(() -> print(WebSecurityTools.getHeaders(arg1)));
                else printErr("Kullanım: headers <url>"); break;
            case "robots":
                if(!arg1.isEmpty()) executor.execute(() -> print(WebSecurityTools.getRobotsTxt(arg1)));
                else printErr("Kullanım: robots <url>"); break;
            case "admin":
                if(!arg1.isEmpty()) execAdminFinder(arg1);
                else printErr("Kullanım: admin <site.com>"); break;
            case "source":
                if(!arg1.isEmpty()) executor.execute(() -> print(WebSecurityTools.getSourceCode(arg1)));
                else printErr("Kullanım: source <url>"); break;

            // === RED TEAM (ZAFİYET & SALDIRI) ===
            case "cms": // CMS Dedektörü
                if(!arg1.isEmpty()) executor.execute(() -> print(RedTeamTools.detectCMS(arg1)));
                else printErr("Kullanım: cms <url>"); break;
            case "vuln": // Hassas Dosya Avcısı
                if(!arg1.isEmpty()) execVulnScan(arg1);
                else printErr("Kullanım: vuln <url>"); break;
            case "crack": // Hash Kırıcı
                if(!arg1.isEmpty()) executor.execute(() -> print(CrackerTools.crackHash(arg1)));
                else printErr("Kullanım: crack <hash>"); break;
            case "flood": // Stress Testi
                if(!arg1.isEmpty()) execFlood(arg1);
                else printErr("Kullanım: flood <url>"); break;
            case "stop": // Saldırı Durdur
                AttackTools.stopAttack(); print("Saldırı durduruldu."); break;
            case "payload": // Exploit Kodları
                if(!arg1.isEmpty()) print(AttackTools.generatePayload(arg1));
                else printErr("Kullanım: payload <xss/sqli/cmd>"); break;
            case "knocker": // Port Knocking
                if(!arg1.isEmpty()) executor.execute(() -> print(AttackTools.portKnock(arg1)));
                else printErr("Kullanım: knocker <ip>"); break;

            // === OSINT & SOSYAL MÜHENDİSLİK ===
            case "checkuser": // Sherlock
                if(!arg1.isEmpty()) execSherlock(arg1);
                else printErr("Kullanım: checkuser <kullanici_adi>"); break;
            case "fakeid": // Sahte Kimlik
                print(SocialEngTools.generateIdentity()); break;

            // === DOSYA SİSTEMİ & MONİTÖR ===
            case "ls": print(FileSystemTools.listFiles(this)); break;
            case "touch":
                if(!arg1.isEmpty() && !arg2.isEmpty()) print(FileSystemTools.createFile(this, arg1, arg2));
                else printErr("Kullanım: touch <dosya> <icerik>"); break;
            case "cat":
                if(!arg1.isEmpty()) print(FileSystemTools.readFile(this, arg1));
                else printErr("Kullanım: cat <dosya>"); break;
            case "rm":
                if(!arg1.isEmpty()) print(FileSystemTools.deleteFile(this, arg1));
                else printErr("Kullanım: rm <dosya>"); break;
            case "monitor": print(SystemTools.getSystemResources(this)); break;
            case "netstat": print(SystemTools.getNetstat()); break;

            // === DONANIM & PERFORMANS ===
            case "battery": print(HardwareTools.getBatteryStatus(this)); break;
            case "wifi": print(HardwareTools.getWifiInfo(this)); break;
            case "speedtest":
                print("Hız testi başlatılıyor...");
                executor.execute(() -> print(HardwareTools.runSpeedTest())); break;

            // === KRİPTO & DATA ===
            case "md5": if(!arg1.isEmpty()) print(CryptoUtils.hash("MD5", arg1)); else printErr("md5 <text>"); break;
            case "sha1": if(!arg1.isEmpty()) print(CryptoUtils.hash("SHA-1", arg1)); else printErr("sha1 <text>"); break;
            case "base64":
                if(!arg1.isEmpty() && !arg2.isEmpty()) print(CryptoUtils.base64(arg1, arg2));
                else printErr("base64 <enc/dec> <text>"); break;
            case "urlenc": if(!arg1.isEmpty()) print(CryptoUtils.urlEncode(arg1)); else printErr("urlenc <text>"); break;
            case "rot13": if(!arg1.isEmpty()) print(CryptoUtils.rot13(arg1)); else printErr("rot13 <text>"); break;
            case "tohex": if(!arg1.isEmpty()) print(DataConverter.stringToHex(arg1)); else printErr("tohex <text>"); break;
            case "tobin": if(!arg1.isEmpty()) print(DataConverter.stringToBinary(arg1)); else printErr("tobin <text>"); break;
            case "reverse": if(!arg1.isEmpty()) print(new StringBuilder(arg1).reverse().toString()); else printErr("reverse <text>"); break;

            // processCommand içine eklenecekler (Eskilerin üzerine yaz):

// --- OSINT PRO ---
            case "scan": // Genişletilmiş Sherlock (Tüm Siteler)
                if(!arg1.isEmpty()) execFullScan(arg1);
                else printErr("Kullanım: scan <kullanici_adi>");
                break;

            case "email": // E-Posta İstihbaratı
                if(!arg1.isEmpty()) executor.execute(() -> print(OsintTools.analyzeEmail(arg1)));
                else printErr("Kullanım: email <adres@site.com>");
                break;

            case "phone": // Telefon Analizi
                if(!arg1.isEmpty()) executor.execute(() -> print(OsintTools.analyzePhone(arg1)));
                else printErr("Kullanım: phone <+905xxxxxxxxx>");
                break;
            default: printErr("Komut bulunamadı: " + cmd + ". 'help' yazın.");
        }
    }

    // --- ÖZEL FONKSİYON YÖNETİCİLERİ ---

    private void execPortScan(String ip) {
        print("Hızlı Port Taraması Başladı: " + ip);
        int[] ports = {21, 22, 23, 25, 53, 80, 110, 135, 139, 443, 445, 1433, 3306, 3389, 8080};
        for (int port : ports) {
            executor.execute(() -> {
                String res = AdvancedNetworkTools.checkPort(ip, port);
                if (!res.isEmpty()) print(res);
            });
        }
    }

    private void execAdminFinder(String domain) {
        print("Admin Paneli Taraması: " + domain);
        String[] paths = {"admin", "login", "wp-login.php", "administrator", "panel", "cpanel", "user", "yonetim", "dashboard"};
        for (String path : paths) {
            executor.execute(() -> {
                String res = WebSecurityTools.checkUrl(domain, path);
                if (!res.isEmpty()) printColor(res, "#00FFFF");
            });
        }
    }

    // DÜZELTİLMİŞ METOT (MainActivity.java içine)
    private void execSherlock(String username) {
        print("OSINT Taraması (Sherlock): " + username);

        // OsintTools artık bir liste (Map) tutuyor, onu döngüye sokuyoruz
        for (String platform : OsintTools.PLATFORMS.keySet()) {
            executor.execute(() -> {
                // ESKİSİ: OsintTools.checkUsername(...) -> HATALI
                // YENİSİ: OsintTools.checkUserUrl(...) -> DOĞRU
                String result = OsintTools.checkUserUrl(platform, username);
                if (!result.isEmpty()) print(result);
            });
        }
    }
    // Toplu Tarama Başlatıcı (20+ Site)
    private void execFullScan(String username) {
        printColor("🔍 GENİŞLETİLMİŞ OSINT TARAMASI: " + username, "#FFFF00");
        print("Hedef 25+ platformda aranıyor. Bu işlem biraz sürebilir...");

        // OsintTools sınıfından site listesini alıp dönüyoruz
        for (String platform : OsintTools.PLATFORMS.keySet()) {
            executor.execute(() -> {
                String result = OsintTools.checkUserUrl(platform, username);
                if (!result.isEmpty()) printColor(result, "#00FF00"); // Bulunanlar YEŞİL
            });
        }
    }

    private void execFlood(String url) {
        printColor("⚠️ HTTP STRESS TESTİ BAŞLATILDI: " + url, "#FF0000");
        print("Durdurmak için 'stop' yazın.");
        AttackTools.startHttpFlood(url, 20);
    }

    private void execVulnScan(String url) {
        printColor("⚠️ HASSAS DOSYA TARAMASI: " + url, "#FF0000");
        String[] files = {".env", ".git/config", "backup.sql", "database.sql", "config.php.bak", "id_rsa", "web.config"};
        for (String file : files) {
            executor.execute(() -> {
                String res = RedTeamTools.checkSensitiveFile(url, file);
                if (!res.isEmpty()) printColor(res, "#FF0000");
            });
        }
    }

    // --- UI YARDIMCILARI ---

    private void showHelp() {
        print("--- KOMUT KATEGORİLERİ ---\n" +
                "AĞ: ping, portscan, trace, wol, dns, myip, arp, nc\n" +
                "WEB: headers, robots, admin, source, cms, vuln\n" +
                "SALDIRI: flood, stop, payload, knocker, crack\n" +
                "OSINT: checkuser (sherlock), fakeid\n" +
                "SİSTEM: ls, touch, cat, rm, monitor, netstat\n" +
                "DONANIM: battery, wifi, speedtest\n" +
                "DATA: md5, sha1, base64, hex, bin, rot13");
    }

    public void printColor(String text, String colorHex) {
        runOnUiThread(() -> {
            try {
                String c = "<font color='" + colorHex + "'>" + text + "</font><br>";
                tvConsole.append(Html.fromHtml(c, Html.FROM_HTML_MODE_LEGACY));
                scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
            } catch (Exception e) { tvConsole.append("\n" + text); }
        });
    }
    public void print(String t) { printColor(t, "#00FF00"); }
    public void printErr(String t) { printColor(t, "#FF5555"); }
}