package com.example.karageyazilimsecuritylab;

import android.os.Build;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.function.Consumer; // Bu import önemli

public class AdvancedNetworkTools {

    // --- YENİ EKLENEN: NETCAT LISTENER (TCP SERVER) ---
    public static void startNetcatListener(String portStr, Consumer<String> onMessageReceived) {
        try {
            int port = Integer.parseInt(portStr);
            ServerSocket serverSocket = new ServerSocket(port);
            onMessageReceived.accept("DİNLİYOR: Port " + port + " açıldı. Bağlantı bekleniyor...");

            // Bağlantı bekle (Bloklayıcı işlem)
            Socket clientSocket = serverSocket.accept();
            String clientIp = clientSocket.getInetAddress().getHostAddress();
            onMessageReceived.accept("BAĞLANTI GELDİ: " + clientIp);

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                onMessageReceived.accept("[" + clientIp + "]: " + inputLine);
            }

            in.close();
            clientSocket.close();
            serverSocket.close();
            onMessageReceived.accept("Bağlantı kapandı.");

        } catch (Exception e) {
            onMessageReceived.accept("Netcat Hatası: " + e.getMessage());
        }
    }

    // --- YENİ EKLENEN: ARP TABLE READER ---
    public static String getArpTable() {
        StringBuilder sb = new StringBuilder("--- ARP TABLOSU (MAC Adresleri) ---\nIP Adresi       | MAC Adresi        | Interface\n");
        try {
            BufferedReader br = new BufferedReader(new FileReader("/proc/net/arp"));
            String line;
            br.readLine(); // Başlığı atla
            while ((line = br.readLine()) != null) {
                // Okunabilir formatlama
                String[] parts = line.split("\\s+");
                if (parts.length >= 6) {
                    sb.append(String.format("%-15s | %-17s | %s\n", parts[0], parts[3], parts[5]));
                }
            }
            br.close();
            return sb.toString();
        } catch (Exception e) {
            return "ARP Tablosu okunamadı (Android 10+ kısıtlaması olabilir).";
        }
    }

    // --- ESKİ TOOL'LAR (AYNEN KORUNDU) ---

    // 1. Wake-On-LAN
    public static String wakeOnLan(String macStr) {
        try {
            byte[] macBytes = getMacBytes(macStr);
            byte[] bytes = new byte[6 + 16 * macBytes.length];
            for (int i = 0; i < 6; i++) bytes[i] = (byte) 0xff;
            for (int i = 6; i < bytes.length; i += macBytes.length) System.arraycopy(macBytes, 0, bytes, i, macBytes.length);

            InetAddress address = InetAddress.getByName("255.255.255.255");
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, 9);
            DatagramSocket socket = new DatagramSocket();
            socket.send(packet);
            socket.close();
            return "Magic Packet gönderildi -> " + macStr;
        } catch (Exception e) { return "WOL Hatası: " + e.getMessage(); }
    }

    // 2. Port Check
    public static String checkPort(String ip, int port) {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(ip, port), 2000);
            socket.close();
            return "[AÇIK] Port " + port + " (TCP)";
        } catch (Exception e) { return ""; }
    }

    // 3. DNS Lookup
    public static String dnsLookup(String domain) {
        try {
            InetAddress[] addresses = InetAddress.getAllByName(domain);
            StringBuilder sb = new StringBuilder("DNS Kayıtları:\n");
            for (InetAddress addr : addresses) sb.append(" - ").append(addr.getHostAddress()).append("\n");
            return sb.toString();
        } catch (Exception e) { return "DNS Çözülemedi."; }
    }

    // 4. TraceRoute Simülasyonu
    public static String traceRoute(String host) {
        return "Traceroute başlatılıyor...\n" + pingHost(host);
    }

    // 5. Native Ping
    public static String pingHost(String host) {
        try {
            Process p = Runtime.getRuntime().exec("ping -c 3 " + host);
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = in.readLine()) != null) sb.append(line).append("\n");
            return sb.toString();
        } catch (Exception e) { return "Ping hatası."; }
    }

    public static String getPublicIp() {
        try {
            URL url = new URL("https://api.ipify.org");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            return "WAN IP: " + in.readLine();
        } catch (Exception e) { return "IP alınamadı."; }
    }

    public static String getSysInfo() {
        return "MODEL: " + Build.MANUFACTURER + " " + Build.MODEL + "\n" +
                "ANDROID: " + Build.VERSION.RELEASE + "\n" +
                "ARCH: " + System.getProperty("os.arch");
    }

    private static byte[] getMacBytes(String macStr) throws IllegalArgumentException {
        byte[] bytes = new byte[6];
        String[] hex = macStr.split("(\\:|\\-)");
        if (hex.length != 6) throw new IllegalArgumentException("Hatalı MAC adresi.");
        try {
            for (int i = 0; i < 6; i++) bytes[i] = (byte) Integer.parseInt(hex[i], 16);
        } catch (NumberFormatException e) { throw new IllegalArgumentException("Hatalı hex değeri."); }
        return bytes;
    }
}