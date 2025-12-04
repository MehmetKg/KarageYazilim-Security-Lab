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

        // Termux stili açılış
        printColor("--- KARAGE SECURITY LAB v7.0 [ULTIMATE] ---", "#00FF00");
        printColor("Modules Loaded: 20+ Classes Active.", "#888888");
        printColor("Type 'help' for the full arsenal.", "#CCCCCC");

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
        printColor("root@karage:~# " + input, "#FF4444");

        String[] parts = input.split("\\s+");
        String cmd = parts[0].toLowerCase();
        String arg1 = parts.length > 1 ? parts[1] : "";
        String arg2 = parts.length > 2 ? parts[2] : "";
        String arg3 = parts.length > 3 ? parts[3] : "";

        processCommand(cmd, arg1, arg2, arg3);
    }

    // --- MERKEZİ KOMUT İŞLEME ---
    private void processCommand(String cmd, String arg1, String arg2, String arg3) {
        switch (cmd) {
            // === TEMEL SİSTEM ===
            case "help": showHelp(); break;
            case "clear": tvConsole.setText(""); break;
            case "sysinfo": print(AdvancedNetworkTools.getSysInfo()); break;

            // === AĞ & KEŞİF (AdvancedNetworkTools) ===
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

            // === WEB PENTEST (WebPentestSuite & WebSecurityTools) ===
            case "waf": if(!arg1.isEmpty()) executor.execute(() -> print(WebPentestSuite.detectWAF(arg1))); else printErr("waf <url>"); break;
            case "lfi": if(!arg1.isEmpty()) executor.execute(() -> print(WebPentestSuite.scanLFI(arg1))); else printErr("lfi <url>"); break;
            case "spider": if(!arg1.isEmpty()) executor.execute(() -> print(WebPentestSuite.crawlSite(arg1))); else printErr("spider <url>"); break;
            case "buster": if(!arg1.isEmpty()) executor.execute(() -> print(WebPentestSuite.dirBuster(arg1))); else printErr("buster <url>"); break;
            case "headers": if(!arg1.isEmpty()) executor.execute(() -> print(WebSecurityTools.getHeaders(arg1))); else printErr("headers <url>"); break;
            case "robots": if(!arg1.isEmpty()) executor.execute(() -> print(WebSecurityTools.getRobotsTxt(arg1))); else printErr("robots <url>"); break;
            case "source": if(!arg1.isEmpty()) executor.execute(() -> print(WebSecurityTools.getSourceCode(arg1))); else printErr("source <url>"); break;
            case "admin": if(!arg1.isEmpty()) execAdminFinder(arg1); else printErr("admin <site>"); break;

            // === RED TEAM & HARDCORE (RedTeamTools & HardcoreTools & CrackerTools) ===
            case "cms": if(!arg1.isEmpty()) executor.execute(() -> print(RedTeamTools.detectCMS(arg1))); else printErr("cms <url>"); break;
            case "vuln": if(!arg1.isEmpty()) execVulnScan(arg1); else printErr("vuln <url>"); break;
            case "crack": if(!arg1.isEmpty()) executor.execute(() -> print(CrackerTools.crackHash(arg1))); else printErr("crack <hash>"); break;
            case "takeover": if(!arg1.isEmpty()) executor.execute(() -> print(HardcoreTools.checkTakeover(arg1))); else printErr("takeover <domain>"); break;
            case "blindsqli": if(!arg1.isEmpty()) executor.execute(() -> print(HardcoreTools.checkBlindSql(arg1))); else printErr("blindsqli <url>"); break;
            case "clickjack": if(!arg1.isEmpty()) executor.execute(() -> print(HardcoreTools.checkClickjacking(arg1))); else printErr("clickjack <url>"); break;
            case "sslscan": if(!arg1.isEmpty()) executor.execute(() -> print(HardcoreTools.analyzeSSL(arg1))); else printErr("sslscan <domain>"); break;

            // === SALDIRI & MALWARE (AttackTools & MalwareTools) ===
            case "flood": if(!arg1.isEmpty()) execFlood(arg1); else printErr("flood <url>"); break;
            case "stop": AttackTools.stopAttack(); print("Saldırı durduruldu."); break;
            case "knocker": if(!arg1.isEmpty()) executor.execute(() -> print(AttackTools.portKnock(arg1))); else printErr("knocker <ip>"); break;
            case "eicar": print(MalwareTools.createVirusTestFile(this)); break;
            case "ransom": print(MalwareTools.createRansomNote(this)); break;
            case "payload": // Hem AttackTools hem MalwareTools'da payload olabilir, MalwareTools'u kullanıyoruz
                if(!arg1.isEmpty() && !arg2.isEmpty() && !arg3.isEmpty()) print(MalwareTools.generatePayload(arg1, arg2, arg3));
                else printErr("Kullanım: payload <ip> <port> <bash/python/php>"); break;

            // === OSINT & SOSYAL MÜHENDİSLİK (OsintTools & SocialEngTools) ===
            case "scan": if(!arg1.isEmpty()) execSherlock(arg1); else printErr("scan <user>"); break;
            case "email": if(!arg1.isEmpty()) executor.execute(() -> print(OsintTools.analyzeEmail(arg1))); else printErr("email <mail>"); break;
            case "phone": if(!arg1.isEmpty()) executor.execute(() -> print(OsintTools.analyzePhone(arg1))); else printErr("phone <numara>"); break;
            case "fakeid": print(SocialEngTools.generateIdentity()); break;

            // === SPY & FİZİKSEL (SpyTools & StegoTools) ===
            case "ble": executor.execute(() -> SpyTools.scanBleDevices(this, result -> print(result))); break;
            case "emf": SpyTools.startEMFDetector(this, result -> printColor(result, "#FFFF00")); break;
            case "mask": if(!arg1.isEmpty() && !arg2.isEmpty()) print(SpyTools.maskLink(arg1, arg2)); else printErr("mask <link> <fake>"); break;
            case "stego":
                if(arg1.equals("hide") && !arg2.isEmpty()) print(StegoTools.hideMessage(this, arg2, arg3));
                else if(arg1.equals("read") && !arg2.isEmpty()) print(StegoTools.readMessage(this, arg2));
                else printErr("stego <hide/read> <file> [msg]"); break;

            // === DARK WEB (DarkWebTools) ===
            case "onion": if(!arg1.isEmpty()) executor.execute(() -> print(DarkWebTools.fetchOnionSite(arg1))); else printErr("onion <site.onion>"); break;
            case "torcheck": executor.execute(() -> print(DarkWebTools.checkTorConnection())); break;
            case "market": if(!arg1.isEmpty()) executor.execute(() -> print(DarkWebTools.searchDarkWeb(arg1))); else printErr("market <query>"); break;

            // === SİSTEM & DONANIM (SystemTools & HardwareTools & FileSystemTools) ===
            case "ls": print(FileSystemTools.listFiles(this)); break;
            case "touch": if(!arg1.isEmpty() && !arg2.isEmpty()) print(FileSystemTools.createFile(this, arg1, arg2)); break;
            case "cat": if(!arg1.isEmpty()) print(FileSystemTools.readFile(this, arg1)); break;
            case "rm": if(!arg1.isEmpty()) print(FileSystemTools.deleteFile(this, arg1)); break;
            case "monitor": print(SystemTools.getSystemResources(this)); break;
            case "netstat": print(SystemTools.getNetstat()); break;
            case "battery": print(HardwareTools.getBatteryStatus(this)); break;
            case "wifi": print(HardwareTools.getWifiInfo(this)); break;
            case "speedtest": print("Hız testi başlatılıyor..."); executor.execute(() -> print(HardwareTools.runSpeedTest())); break;

            // === KRİPTO & ADLİ BİLİŞİM (CryptoUtils & ForensicsTools & AdvancedCrypto) ===
            case "md5": if(!arg1.isEmpty()) print(CryptoUtils.hash("MD5", arg1)); break;
            case "base64": if(!arg1.isEmpty()) print(CryptoUtils.base64(arg1, arg2)); break;
            case "rot13": if(!arg1.isEmpty()) print(CryptoUtils.rot13(arg1)); break;
            case "tohex": if(!arg1.isEmpty()) print(DataConverter.stringToHex(arg1)); break;
            case "exif": if(!arg1.isEmpty()) print(ForensicsTools.getExifData(this, arg1)); else printErr("exif <resim.jpg>"); break;
            case "rsa": print(AdvancedCrypto.generateRSAKeys()); break;
            case "encrypt": if(!arg1.isEmpty() && !arg2.isEmpty()) print(AdvancedCrypto.encryptFile(this, arg1, arg2)); else printErr("encrypt <file> <pass>"); break;
            case "decrypt": if(!arg1.isEmpty() && !arg2.isEmpty()) print(AdvancedCrypto.decryptFile(this, arg1, arg2)); else printErr("decrypt <file> <pass>"); break;

            default: printErr("Komut yok: " + cmd);
        }
    }

    // --- ÖZEL FONKSİYON YÖNETİCİLERİ ---

    private void execPortScan(String ip) {
        print("Hızlı Port Taraması Başladı: " + ip);
        int[] ports = {21, 22, 23, 25, 53, 80, 110, 135, 139, 443, 445, 1433, 3306, 3389, 8080};
        for (int p : ports) executor.execute(() -> { String r = AdvancedNetworkTools.checkPort(ip, p); if(!r.isEmpty()) print(r); });
    }

    private void execSherlock(String user) {
        print("Sherlock Taraması: " + user);
        // OsintTools listesini kullan (Map yapısı)
        for(String p : OsintTools.PLATFORMS.keySet()) {
            executor.execute(() -> {
                String r = OsintTools.checkUserUrl(p, user);
                if(!r.isEmpty()) print(r);
            });
        }
    }

    private void execFlood(String url) {
        printColor("⚠️ FLOOD SALDIRISI BAŞLADI: " + url, "#FF0000");
        print("Durdurmak için 'stop' yazın.");
        AttackTools.startHttpFlood(url, 30); // 30 Thread
    }

    private void execVulnScan(String url) {
        print("Hassas Dosya Taraması...");
        String[] files = {".env", ".git/config", "backup.sql", "id_rsa", "web.config", "dump.sql"};
        for(String f : files) executor.execute(() -> { String r = RedTeamTools.checkSensitiveFile(url, f); if(!r.isEmpty()) printColor(r, "#FF0000"); });
    }

    private void execAdminFinder(String d) {
        print("Admin Avcısı...");
        String[] paths = {"admin", "login", "wp-login.php", "panel", "cpanel", "yonetim"};
        for(String p : paths) executor.execute(() -> { String r = WebSecurityTools.checkUrl(d, p); if(!r.isEmpty()) printColor(r, "#00FFFF"); });
    }

    // --- UI YARDIMCILARI ---
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
    public void printErr(String t) { printColor(t, "#FF4444"); }

    private void showHelp() {
        print("--- KATEGORİLER ---\n" +
                "NET: ping, portscan, wol, arp, nc, myip, dns, trace\n" +
                "WEB: waf, lfi, takeover, blindsqli, clickjack, sslscan, spider, buster\n" +
                "RED: flood, stop, vuln, cms, crack, knocker\n" +
                "SPY: ble, emf, mask, stego\n" +
                "DARK: onion, torcheck, market\n" +
                "OSINT: scan, email, phone, fakeid\n" +
                "SYS: ls, touch, cat, rm, monitor, netstat, battery, wifi, speedtest\n" +
                "CRYPTO: exif, rsa, encrypt, decrypt, md5, base64, rot13");
    }
}