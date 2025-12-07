# 🛡️ Karage Security Lab (KSL) - Ultimate Android Pentest Suite

> **"The Mobile Cyber Warstation. No Root. No Limits."**
> *Android için Geliştirilmiş, Root Gerektirmeyen Yeni Nesil Hibrit Sızma Testi ve Ağ Güvenliği Laboratuvarı.*

![Platform](https://img.shields.io/badge/Platform-Android%207.0%2B-green?logo=android&style=for-the-badge)
![Language](https://img.shields.io/badge/Tech-Java%20%7C%20Python%20%7C%20Shell-orange?logo=java&style=for-the-badge)
![Engine](https://img.shields.io/badge/Engine-Hybrid%20Core-blue?style=for-the-badge)
![Security](https://img.shields.io/badge/Focus-Red%20Team%20%26%20Bug%20Bounty-red?logo=kalilinux&style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-lightgrey?style=for-the-badge)

---

## 📖 Proje Hakkında (Overview)

**Karage Security Lab (KSL)**, etik hackerlar, güvenlik araştırmacıları ve sistem yöneticileri için özel olarak tasarlanmış, **Android tabanlı bir sızma testi (Pentest) ve keşif platformudur.**

Benzerlerinin aksine KSL, cihazınızı rootlamanıza gerek kalmadan, **Saf Java Soketleri** ve **Termux Köprüsü (Bridge)** teknolojisini birleştirerek gerçek bilgisayar gücünde taramalar yapmanızı sağlar. Cebinizde bir **Kali Linux** taşıyor gibi hissedeceksiniz.

### ⚡ Neden KSL?
* **Root Yok:** Cihaz garantisini bozmadan tam yetki.
* **Hibrit Motor:** Java'nın hızı + Python scriptlerinin esnekliği.
* **Otomatik Raporlama:** Her tarama anlık olarak loglanır ve PDF'e dönüştürülebilir.
* **40+ Araç:** Keşiften sömürüye (Exploitation) kadar tam kapsamlı arsenal.

---

## 🛠️ Kullanılan Teknolojiler (Tech Stack)

Bu proje, yüksek performans ve stabilite için aşağıdaki teknolojilerle inşa edilmiştir:

| Teknoloji | Açıklama |
| :--- | :--- |
| **Java (Native Android)** | Uygulamanın ana motoru (Core Engine). UI ve Thread yönetimi. |
| **Multi-Threading** | `ExecutorService` havuzları ile aynı anda 50+ HTTP isteği ve port taraması. |
| **Raw Sockets** | `java.net.Socket` kullanılarak yapılan düşük seviyeli port ve ağ taramaları. |
| **Termux Bridge API** | Android `Intent` sistemi üzerinden Termux terminaline komut gönderme yeteneği. |
| **Regex Parsing** | HTML kaynak kodundan hassas veri (Email, API Key) madenciliği. |
| **PDF Generation** | Android `PdfDocument` API ile vektörel rapor oluşturma. |

---

## 🚀 Modüller ve Kullanım Rehberi (Arsenal)

KSL terminalini açtığınızda aşağıdaki komutları kullanarak sistemleri analiz edebilirsiniz.

### 🔴 1. Red Team & Network (Ağ Saldırıları)
*Yerel ağdaki cihazları keşfetmek ve istemci taraflı açıkları bulmak için.*

| Komut | Kullanım | Açıklama |
| :--- | :--- | :--- |
| **`subnet`** | `subnet` | **ARP/Ping Tarayıcı:** WiFi ağındaki diğer cihazları (IP/Hostname) bulur. |
| **`clickjack`** | `clickjack <url>` | **UI Redress:** Sitenin "Clickjacking" saldırısına açık olup olmadığını test eder. |
| **`myip`** | `myip` | **WAN Analizi:** Gerçek (Public) IP adresinizi ve ISP bilgisini gösterir. |

### 🕵️ 2. Reconnaissance (Keşif ve İstihbarat)
*Hedef hakkında pasif ve aktif bilgi toplama.*

| Komut | Kullanım | Açıklama |
| :--- | :--- | :--- |
| **`tech`** | `tech <url>` | **Wappalyzer:** Hedefin CMS'ini (WP, Joomla), sunucusunu ve dilini tanır. |
| **`nmap`** | `nmap <ip>` | **Port Scanner:** Kritik portları (21, 22, 80, 443, 3306 vb.) çok hızlı tarar. |
| **`dirsearch`** | `dirsearch <url>` | **Dizin Avcısı:** Gizli klasörleri (`/admin`, `/backup`, `.env`) brute-force ile arar. |
| **`gitrecon`** | `gitrecon <site>` | **GitHub Dork:** GitHub üzerinde sızdırılmış şifreleri ve API keyleri arar. |
| **`subdomain`** | `subdomain <url>` | **Alt Alan Adı:** Hedefe ait `dev.`, `api.`, `test.` gibi subdomainleri bulur. |

### 💰 3. Hunter Module (Bug Bounty & Veri Madenciliği)
*Para ödülü kazandıran (P1/P2) kritik açıklar için.*

| Komut | Kullanım | Açıklama |
| :--- | :--- | :--- |
| **`takeover`** | `takeover <url>` | **Subdomain Takeover:** Boşa düşmüş bulut servislerini (AWS, Heroku) tespit eder. |
| **`s3`** | `s3 <domain>` | **Bucket Leaker:** Hedefe ait açık Amazon S3 depolarını ifşa eder. |
| **`miner`** | `miner <url>` | **Data Scraper:** Kaynak kodda unutulmuş Telefon, Email ve API Key'leri kazır. |
| **`linkfinder`**| `linkfinder <url>`| **JS Endpoint:** JavaScript dosyaları içindeki gizli API yollarını çıkarır. |

### ☠️ 4. Exploit & Apocalypse (Saldırı ve İmha)
*Sistemlere sızma ve yetki yükseltme.*

| Komut | Kullanım | Açıklama |
| :--- | :--- | :--- |
| **`autopwn`** | `autopwn <url>` | **Zincirleme Saldırı:** Hedefi analiz eder ve otomatik saldırı senaryosu başlatır. |
| **`sqlmap`** | `sqlmap <url>` | **Blind SQLi:** Veritabanı zafiyetlerini (Time-based) test eder. |
| **`xss`** | `xss <url>` | **XSS Hunter:** Sayfaya zararlı kod gömerek yansıma (reflection) arar. |
| **`beef`** | `beef <ip>` | **Hook Generator:** Tarayıcı ele geçirmek için zararlı JS kodu üretir. |
| **`payload`** | `payload <tür> <ip> <port>`| **Backdoor Factory:** Reverse Shell (Python, Bash, PHP) kodları üretir. |

### 👻 5. Ghost & Evasion (Gizlilik)
*Güvenlik duvarlarını (WAF) atlatma.*

| Komut | Kullanım | Açıklama |
| :--- | :--- | :--- |
| **`bypass`** | `bypass <url>` | **IP Spoofing:** 403 yasaklı sayfalara girmek için sahte IP başlıkları gönderir. |
| **`tamper`** | `tamper <tür> <kod>` | **WAF Encoder:** Saldırı kodunu şifreler (URL, Double, Hex). |
| **`cors`** | `cors <url>` | **CORS Misconfig:** Cross-Origin veri sızıntısı açıklarını test eder. |

### 🛠️ 6. System & Reporting (Sistem)
| Komut | Kullanım | Açıklama |
| :--- | :--- | :--- |
| **`pdf`** | `pdf` | **Raporlama:** Tüm oturumu profesyonel bir PDF raporu olarak kaydeder. |
| **`termux`** | `termux <cmd>` | **Bridge:** Komutu Termux uygulamasına gönderir ve çalıştırır. |
| **`sysinfo`** | `sysinfo` | **Dashboard:** RAM, CPU ve Android sürüm bilgisini gösterir. |


---

## ⚠️ Yasal Uyarı (Disclaimer)

**Karage Security Lab (KSL)**, tamamen **eğitim**, **ağ yönetimi** ve **yetkili güvenlik testleri** amacıyla geliştirilmiştir.

* Bu yazılımı, sahibi olmadığınız veya test izniniz olmayan sistemlerde kullanmak suçtur.
* Geliştirici (**Karage Yazılım**), bu aracın yasa dışı kullanımından doğacak zararlardan sorumlu tutulamaz.
* **"Watch the Web. Silent Hunter."**
* Uygulamayı kurarken virüs veya Turuvaatı uyarısı verebilir bunun nedeni içinde çalışan scriptlerdir herhangibi bir virüs yoktur açık kaynak kodludur kodları inceleyebilirsiniz.

---

### 👨‍💻 Geliştirici & İletişim

**Mehmet Karagülle (Karage Yazilim)**
* Cyber Security Researcher & Android Developer
* [GitHub Profilim](https://github.com/MehmetKg)

---
<p align="center">Made with ❤️ and ☕ in Kayseri/Turkey</p>
