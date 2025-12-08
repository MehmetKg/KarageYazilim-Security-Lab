# 🛡️ Karage Security Lab (KSL) - Ultimate Android Pentest Suite

> **"The Mobile Cyber Warstation. No Root. No Limits."**
>
> *Android cihazlar için geliştirilmiş, Root gerektirmeyen yeni nesil Hibrit Siber Güvenlik, Sızma Testi ve Kırmızı Takım (Red Team) Laboratuvarı.*

![Platform](https://img.shields.io/badge/Platform-Android%207.0%2B-green?logo=android&style=for-the-badge)
![Language](https://img.shields.io/badge/Language-Java%20%7C%20Python%20%7C%20Shell-orange?logo=java&style=for-the-badge)
![Security](https://img.shields.io/badge/Focus-Red%20Team%20%26%20Bug%20Bounty-red?logo=kalilinux&style=for-the-badge)
![Version](https://img.shields.io/badge/Version-v5.0%20Legend-blue?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-lightgrey?style=for-the-badge)

## 📖 Proje Hakkında (Overview)

**Karage Security Lab (KSL)**, etik hackerlar, güvenlik araştırmacıları, sistem yöneticileri ve Bug Bounty avcıları için tasarlanmış kapsamlı bir **mobil siber güvenlik platformudur.**

Standart araçların aksine KSL, cihazınızı rootlamanıza (garanti dışı bırakmanıza) gerek kalmadan, **Saf Java Soketleri** ve **Termux Köprüsü (Bridge)** teknolojisini birleştirerek gerçek bir bilgisayar gücünde tarama ve analiz yapmanızı sağlar.

**Öne Çıkan Özellikler:**
* 🚀 **Hibrit Motor:** Java'nın hızı ve Python scriptlerinin esnekliği tek çatı altında.
* 🔒 **Root Gerektirmez:** Herhangi bir Android cihazda (telefon/tablet) çalışır.
* 📄 **Otomatik Raporlama:** Yapılan taramaları anlık olarak loglar ve profesyonel PDF raporuna dönüştürür.
* 🛠️ **50+ Araç:** OSINT, Ağ Analizi, Web Pentest, Kriptografi ve Sosyal Mühendislik modülleri.

---

## 📥 Kurulum (Installation)

KSL'yi kullanmaya başlamak için karmaşık kurulumlara ihtiyacınız yok.

1.  **APK İndir:** Bu reponun **[Releases](https://github.com/kullaniciadi/projeadi/releases)** kısmından en güncel `app-release.apk` dosyasını indirin.
2.  **Yükle:** İndirdiğiniz dosyayı telefonunuza kurun. (Bilinmeyen kaynaklara izin verin).
3.  **İzinler:** Uygulamayı ilk açtığınızda dosya kaydetme izni isteyecektir. Raporlama (PDF) özelliği için **İzin Ver** demeniz gerekmektedir.
4.  **Başlat:** `help` yazarak tüm komutları görün.

---

## 💻 Kullanım Rehberi (Usage Guide)

Uygulama açıldığında sizi bir **Terminal Arayüzü** karşılar. Komutları buraya yazıp `EXEC` butonuna basarak (veya klavyeden enter) çalıştırırsınız.

### 🕵️ 1. OSINT & Sosyal Mühendislik Modülleri
*Hedef kişi veya kurum hakkında pasif bilgi toplama ve farkındalık testleri.*

| Komut | Kullanım | Açıklama |
| :--- | :--- | :--- |
| **`sherlock`** | `sherlock <kullanici_adi>` | **Kullanıcı Avı:** 25+ sosyal medya platformunda (Instagram, Twitter, GitHub vb.) verilen kullanıcı adını tarar. |
| **`mailcheck`** | `mailcheck <email>` | **E-posta Analizi:** Mail formatını doğrular ve Gravatar profil resmi olup olmadığını (aktiflik durumu) kontrol eder. |
| **`phone`** | `phone <+905xxxx>` | **Telefon Analizi:** Numaranın hangi ülkeye ait olduğunu bulur ve WhatsApp/Telegram direkt linklerini üretir. |
| **`reputation`** | `reputation <ip>` | **IP İtibar Kontrolü:** Bir IP adresinin kara listede (blacklist) olup olmadığını AbuseIPDB üzerinden sorgular. |
| **`fakeid`** | `fakeid` | **Sahte Kimlik:** Sosyal mühendislik senaryoları için rastgele isim, adres, TC ve Kredi Kartı numarası üretir. |
| **`phish`** | `phish <site.com>` | **Oltalama Testi:** Eğitim amaçlı benzer domain (Typosquatting) varyasyonları üretir. |

### 📡 2. Red Team & Ağ Analizi (Network)
*Yerel ve dış ağlardaki cihazları keşfetmek ve analiz etmek için.*

| Komut | Kullanım | Açıklama |
| :--- | :--- | :--- |
| **`subnet`** | `subnet` | **Ağ Tarayıcı:** Bağlı olduğunuz WiFi ağındaki diğer cihazları (IP ve Hostname) ARP/Ping ile tespit eder. |
| **`clickjack`** | `clickjack <url>` | **Clickjacking Testi:** Sitenin bir `iframe` içinde çalıştırılıp çalıştırılamayacağını (UI Redress açığı) test eder. |
| **`netinfo`** | `netinfo <site.com>` | **Hedef Analizi:** Hedef sitenin gerçek IP adresini, Sunucu Lokasyonunu ve Servis Sağlayıcısını (ISP) bulur. |
| **`myip`** | `myip` | **Kimlik Kontrolü:** Kendi dış (Public) IP adresinizi gösterir. |

### ⚔️ 3. Saldırı & İstismar (Attack & Exploit)
*Sistemlerdeki güvenlik açıklarını doğrulamak için (Sadece yetkili olduğunuz sistemlerde kullanın).*

| Komut | Kullanım | Açıklama |
| :--- | :--- | :--- |
| **`autopwn`** | `autopwn <url>` | **Otomatik Saldırı:** Hedefin teknolojisini (WP, Joomla) tanır ve uygun saldırı zincirini otomatik başlatır. |
| **`sqlmap`** | `sqlmap <url?id=1>` | **SQL Enjeksiyonu:** URL parametrelerinde `Time-Based Blind SQLi` zafiyeti arar. |
| **`xss`** | `xss <url?q=test>` | **XSS Tarayıcı:** URL parametrelerine zararlı JS kodu (`<script>`) enjekte ederek yansıma arar. |
| **`stress`** | `stress <url>` | **Yük Testi:** Hedef sunucuya çoklu HTTP isteği göndererek dayanıklılığını ölçer (Stres Testi). |
| **`payload`** | `payload python <ip> <port>` | **Backdoor Üretici:** Hedef sisteme sızmak için Reverse Shell kodları (Python, Bash, PHP) üretir. |

### 🐞 4. Keşif & Bug Bounty (Recon)
*Web uygulamalarındaki gizli dosyaları ve yapılandırma hatalarını bulmak için.*

| Komut | Kullanım | Açıklama |
| :--- | :--- | :--- |
| **`tech`** | `tech <url>` | **Teknoloji Tespiti:** Sitenin kullandığı CMS, Sunucu (Nginx/Apache) ve Yazılım Dillerini tespit eder. |
| **`miner`** | `miner <url>` | **Veri Madencisi:** Kaynak kod içine gizlenmiş API Key, Email ve Telefon numaralarını regex ile kazır. |
| **`dirsearch`** | `dirsearch <url>` | **Dizin Avcısı:** `admin`, `backup`, `.env`, `config` gibi kritik dosyaları brute-force ile arar. |
| **`takeover`** | `takeover <sub.site.com>` | **Subdomain Takeover:** Boşa düşmüş bulut servislerini (Heroku, AWS, GitHub Pages) kontrol eder. |
| **`s3`** | `s3 <domain>` | **Bucket Leaker:** Hedefe ait, yanlış yapılandırılmış Amazon S3 depolarını ifşa eder. |

### 👻 5. Gizlilik & Atlatma (Stealth)
*Güvenlik duvarlarını (WAF) aşmak ve iz gizlemek için.*

| Komut | Kullanım | Açıklama |
| :--- | :--- | :--- |
| **`bypass`** | `bypass <url>` | **403 Bypass:** Yasaklı sayfalara erişmek için `X-Forwarded-For` gibi başlıklarla IP Spoofing dener. |
| **`tamper`** | `tamper <tip> <kod>` | **WAF Encoder:** Saldırı kodlarını şifreleyerek (URL, Double URL, Hex) WAF'tan kaçırır. |
| **`cors`** | `cors <url>` | **CORS Testi:** Siteler arası veri paylaşımı (Cross-Origin) yapılandırma hatalarını test eder. |
| **`wayback`** | `wayback <site.com>` | **Zaman Makinesi:** Sitenin geçmişini tarayarak silinmiş ancak arşivlenmiş dosyaları bulur. |

### 🛠️ 6. Sistem & Araçlar (System Tools)
| Komut | Kullanım | Açıklama |
| :--- | :--- | :--- |
| **`pdf`** | `pdf` | **Raporla:** Tüm terminal oturumunu ve tarama sonuçlarını **İndirilenler** klasörüne PDF olarak kaydeder. |
| **`rootcheck`** | `rootcheck` | **Güvenlik:** Cihazın rootlu olup olmadığını kontrol eder. |
| **`storage`** | `storage` | **Kaynaklar:** Cihazın RAM, Disk ve İşlemci kullanım durumunu gösterir. |
| **`termux`** | `termux <komut>` | **Bridge:** Komutu cihazdaki Termux uygulamasına gönderir ve çalıştırır. |

---

## ⚠️ Yasal Uyarı (Disclaimer)

**Karage Security Lab (KSL)**, tamamen **eğitim**, **ağ yönetimi** ve **yetkili güvenlik testleri (Pentest)** amacıyla geliştirilmiştir.

* ❌ Bu yazılımı, sahibi olmadığınız veya yazılı izniniz olmayan sistemler üzerinde kullanmak yasa dışıdır ve suç teşkil eder.
* 🛡️ Geliştirici (**Karage Yazılım**), bu aracın kötü niyetli kullanımından doğacak hiçbir zarardan sorumlu tutulamaz. Kullanıcı, tüm eylemlerinden kendisi sorumludur.

---

## 🏷️ Etiketler (SEO Keywords)
`android pentest tool`, `mobile hacking`, `siber güvenlik`, `sızma testi`, `network scanner`, `bug bounty tool`, `termux alternative`, `sqlmap android`, `red team tools`, `yerli yazılım`, `rootless hacking`, `white hat hacker`, `karage security lab`.

---

<p align="center">
  Developed with ❤️ by <b>Karage Yazılım</b>
  <br>
  <i>"Watch the Web. Silent Hunter."</i>
</p>
