package com.example.karageyazilimsecuritylab; // PAKET İSMİNİ KONTROL ET

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Html;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat; // BU EKLENDİ

public class MainActivity extends AppCompatActivity {

    private TextView tvConsole;
    private AutoCompleteTextView etCommand;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // --- KRİTİK: UYGULAMA AÇILINCA İZİN İSTE ---
        verifyStoragePermissions();
        // -------------------------------------------

        try { ReportUtils.init(this); } catch (Exception e) {}

        tvConsole = findViewById(R.id.tvConsole);
        etCommand = findViewById(R.id.etCommand);
        scrollView = findViewById(R.id.scrollView);
        Button btnSubmit = findViewById(R.id.btnSubmit);

        String[] commands = {
                "subnet", "clickjack", "sysinfo", "myip",
                "tech", "nmap", "dirsearch", "subdomain", "headers", "getip", "whois", "dork",
                "nikto", "fuzz", "adminfind", "ssl", "linkfinder", "miner", "s3",
                "autopwn", "sqlmap", "xss", "commix", "beef", "takeover", "payload",
                "bypass", "tamper", "cors", "wayback",
                "hash", "base64", "termux", "script", "wordlist", "pdf", "report", "help", "clear",
                "hardware", "crack", "stego", "crypto",
                "sherlock", "reputation", "fakeid", "phish",
                "rootcheck", "storage", "netinfo", "gitrecon", "mailcheck", "phone", "stress"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, commands);
        etCommand.setAdapter(adapter);

        printColor("=== KARAGE SECURITY LAB v5.0 [ULTIMATE] ===", "#00FF00");
        printColor("System: Hybrid Core | Logs: Active", "#888888");
        printColor("Type 'help' for full command list.", "#FFFFFF");

        btnSubmit.setOnClickListener(v -> handleInput());
    }

    // --- YENİ EKLENEN İZİN FONKSİYONU ---
    private void verifyStoragePermissions() {
        // İzinleri kontrol et
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // İzin yoksa kullanıcıdan iste
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    1
            );
        }
    }
    // -------------------------------------

    private void handleInput() {
        String input = etCommand.getText().toString().trim();
        if (input.isEmpty()) return;
        etCommand.setText("");

        printColor("root@ksl:~# " + input, "#00FF00");
        ReportUtils.addLog("CMD: " + input);

        String[] parts = input.split("\\s+");
        String cmd = parts[0].toLowerCase();
        String arg1 = parts.length > 1 ? parts[1] : "";
        String arg2 = parts.length > 2 ? parts[2] : "";

        RequestManager.Callback uiCallback = result -> print(result);

        switch (cmd) {
            // === OSINT & SOCIAL ===
            case "sherlock": if(!arg1.isEmpty()) OsintTools.searchUsername(arg1, uiCallback); else printErr("Usage: sherlock <username>"); break;
            case "reputation": if(!arg1.isEmpty()) OsintTools.checkIpReputation(arg1, uiCallback); else printErr("Usage: reputation <ip>"); break;
            case "fakeid": SocialEngTools.generateFakeID(uiCallback); break;
            case "phish": if(!arg1.isEmpty()) SocialEngTools.generatePhishLink(arg1, uiCallback); else printErr("Usage: phish <domain>"); break;
            case "gitrecon": if(!arg1.isEmpty()) GithubTools.runGithubRecon(arg1, uiCallback); else printErr("Usage: gitrecon <domain>"); break;
            case "mailcheck": if(!arg1.isEmpty()) OsintTools.analyzeEmail(arg1, uiCallback); else printErr("Usage: mailcheck <email>"); break;
            case "phone": if(!arg1.isEmpty()) OsintTools.analyzePhone(arg1, uiCallback); else printErr("Usage: phone <+90...>"); break;

            // === RED TEAM & NETWORK ===
            case "subnet": RedTeamTools.scanLocalNetwork(uiCallback); break;
            case "clickjack": if(!arg1.isEmpty()) RedTeamTools.checkClickJacking(arg1, uiCallback); else printErr("Usage: clickjack <url>"); break;
            case "sysinfo": LabModule.showSystemStatus(uiCallback); break;
            case "myip": LabModule.getPublicIP(uiCallback); break;
            case "netinfo": if(!arg1.isEmpty()) NetworkPro.resolveHost(arg1, uiCallback); else printErr("Usage: netinfo <domain>"); break;
            case "stress": if(!arg1.isEmpty()) StressTestModule.runStressTest(arg1, uiCallback); else printErr("Usage: stress <url>"); break;

            // === RECON ===
            case "tech": if(!arg1.isEmpty()) ReconModule.runWappalyzer(arg1, uiCallback); else printErr("Usage: tech <url>"); break;
            case "nmap": if(!arg1.isEmpty()) ReconModule.runNmap(arg1, uiCallback); else printErr("Usage: nmap <ip>"); break;
            case "dirsearch": if(!arg1.isEmpty()) ReconModule.runDirsearch(arg1, uiCallback); else printErr("Usage: dirsearch <url>"); break;
            case "getip": if(!arg1.isEmpty()) ReconModule.getIP(arg1, uiCallback); else printErr("Usage: getip <url>"); break;
            case "subdomain": if(!arg1.isEmpty()) ReconModule.runSubdomainEnum(arg1, uiCallback); else printErr("Usage: subdomain <domain>"); break;
            case "headers": if(!arg1.isEmpty()) ReconModule.getHeaders(arg1, uiCallback); else printErr("Usage: headers <url>"); break;
            case "whois": if(!arg1.isEmpty()) SpecialModule.runWhois(arg1, uiCallback); else printErr("Usage: whois <domain>"); break;
            case "dork": if(!arg1.isEmpty()) SpecialModule.runGoogleDork(arg1, uiCallback); else printErr("Usage: dork <site>"); break;

            // === PENTEST ===
            case "adminfind": if(!arg1.isEmpty()) SpecialModule.findAdminPanel(arg1, uiCallback); else printErr("Usage: adminfind <url>"); break;
            case "nikto": if(!arg1.isEmpty()) PentestModule.runNikto(arg1, uiCallback); else printErr("Usage: nikto <url>"); break;
            case "fuzz": if(!arg1.isEmpty()) PentestModule.runWfuzz(arg1, uiCallback); else printErr("Usage: fuzz <url>"); break;
            case "ssl": if(!arg1.isEmpty()) ProModule.runSSLScan(arg1, uiCallback); else printErr("Usage: ssl <url>"); break;
            case "linkfinder": if(!arg1.isEmpty()) ProModule.runLinkFinder(arg1, uiCallback); else printErr("Usage: linkfinder <url>"); break;
            case "miner": if(!arg1.isEmpty()) HunterModule.extractData(arg1, uiCallback); else printErr("Usage: miner <url>"); break;
            case "takeover": if(!arg1.isEmpty()) HunterModule.checkTakeover(arg1, uiCallback); else printErr("Usage: takeover <sub>"); break;
            case "s3": if(!arg1.isEmpty()) HunterModule.findS3Buckets(arg1, uiCallback); else printErr("Usage: s3 <domain>"); break;

            // === EXPLOIT ===
            case "autopwn": if(!arg1.isEmpty()) ApocalypseModule.startChainAttack(arg1, uiCallback); else printErr("Usage: autopwn <url>"); break;
            case "sqlmap": if(!arg1.isEmpty()) ExploitModule.runSqlMap(arg1, uiCallback); else printErr("Usage: sqlmap <url>"); break;
            case "xss": if(!arg1.isEmpty()) ExploitModule.runXSStrike(arg1, uiCallback); else printErr("Usage: xss <url>"); break;
            case "payload": if(parts.length > 3) ApocalypseModule.generatePayload(arg1, parts[2], parts[3], uiCallback); else printErr("Usage: payload <type> <ip> <port>"); break;
            case "beef": if(!arg1.isEmpty()) SpecialModule.generateBeefHook(arg1, uiCallback); else printErr("Usage: beef <ip>"); break;
            case "commix": if(!arg1.isEmpty()) ExploitModule.runCommix(arg1, uiCallback); else printErr("Usage: commix <url>"); break;

            // === EVASION ===
            case "bypass": if(!arg1.isEmpty()) ApocalypseModule.runWafBypass(arg1, uiCallback); else printErr("Usage: bypass <url>"); break;
            case "tamper": if(!arg1.isEmpty() && !arg2.isEmpty()) GhostModule.runTamper(arg2, arg1, uiCallback); else printErr("Usage: tamper <type> <payload>"); break;
            case "cors": if(!arg1.isEmpty()) GhostModule.checkCORS(arg1, uiCallback); else printErr("Usage: cors <url>"); break;
            case "wayback": if(!arg1.isEmpty()) TimeMachineModule.getArchivedUrls(arg1, uiCallback); else printErr("Usage: wayback <domain>"); break;

            // === SYSTEM & CRYPTO ===
            case "rootcheck": SystemTools.checkRoot(uiCallback); break;
            case "storage": SystemTools.checkStorage(this, uiCallback); break;
            case "hardware": HardwareTools.getDetails(this, uiCallback); break;
            case "crack": if(!arg1.isEmpty()) CrackerTools.crackHash(arg1, uiCallback); else printErr("Usage: crack <hash>"); break;
            case "stego": if(!arg1.isEmpty()) StegoTools.checkImage(arg1, uiCallback); else printErr("Usage: stego <path>"); break;
            case "crypto": if(!arg1.isEmpty()) AdvancedCrypto.analyze(arg1, uiCallback); else printErr("Usage: crypto <text>"); break;

            case "pdf":
                print("📄 PDF Hazırlanıyor...");
                String pdfPath = PdfGenerator.createPdf(this, ReportUtils.getLogs());
                if(pdfPath.startsWith("Hata")) printErr(pdfPath);
                else {
                    print("<font color='#00FF00'>[✔] KAYDEDİLDİ!</font>");
                    print("Dosya: " + pdfPath);
                }
                break;

            case "termux": if(!arg1.isEmpty()) TermuxBridge.executeCommand(this, input.substring(7), uiCallback); else printErr("Usage: termux <cmd>"); break;
            case "script": if(!arg1.isEmpty()) ScriptEngine.runScript(this, arg1, arg2, uiCallback); else printErr("Usage: script <file>"); break;
            case "wordlist": if(!arg1.isEmpty()) WordlistManager.downloadWordlist(this, arg1, uiCallback); else printErr("Usage: wordlist <name>"); break;
            case "hash": if(!arg1.isEmpty()) ProModule.analyzeHash(arg1, uiCallback); else printErr("Usage: hash <string>"); break;
            case "base64": if(!arg1.isEmpty() && !arg2.isEmpty()) ProModule.runBase64(arg2, arg1, uiCallback); else printErr("Usage: base64 <enc/dec> <text>"); break;

            case "report": print("Log Dosyası: " + ReportUtils.getPath()); break;
            case "clear": tvConsole.setText(""); break;
            case "help": showHelp(); break;

            default: printErr("Unknown command: " + cmd);
        }
    }

    public void print(String text) {
        if(!text.contains("<font")) {
            if(text.contains("[!]")) text = "<font color='#FF5555'>" + text + "</font>";
            else if(text.contains("[+]") || text.contains("[✔]")) text = "<font color='#00FF00'>" + text + "</font>";
            else text = "<font color='#FFFFFF'>" + text + "</font>";
        }
        printHtml(text);
        ReportUtils.addLog(text);
    }
    public void printColor(String t, String c) { printHtml("<font color='" + c + "'>" + t + "</font>"); }
    public void printErr(String t) { printColor(t, "#FF5555"); }
    private void printHtml(String h) {
        RequestManager.runOnUI(() -> {
            tvConsole.append(Html.fromHtml(h + "<br>", Html.FROM_HTML_MODE_LEGACY));
            scrollView.post(() -> scrollView.fullScroll(ScrollView.FOCUS_DOWN));
        });
    }

    private void showHelp() {
        print("███ KSL v5.0 ULTIMATE HELP ███");
        print("OSINT:   sherlock, reputation, fakeid, phish");
        print("REDTEAM: subnet, clickjack, netinfo, stress");
        print("ATTACK:  autopwn, sqlmap, xss, beef, payload");
        print("RECON:   tech, nmap, dirsearch, miner, gitrecon");
        print("SYSTEM:  pdf, report, rootcheck, hardware");
    }
}