# 🛡️ Karage Security Lab (KSL) - Ultimate v7.0

> **"Mobile Cyber Warstation. Hybrid Engine. Field Ready."**
>
> *Android cihazlar için geliştirilmiş; Hibrit Motor (Java + Python), Yapay Zeka Destekli, Adli Bilişim (Forensics) ve Saha Gözetim (Surveillance) yeteneklerine sahip Nihai Sızma Testi Laboratuvarı.*

![Platform](https://img.shields.io/badge/Platform-Android%2010.0%2B-green?logo=android&style=for-the-badge)
![Core](https://img.shields.io/badge/Core-Java%20%7C%20Python%203.11-blue?logo=openjdk&style=for-the-badge)
![License](https://img.shields.io/badge/License-GNU%20GPLv3-red?logo=gnu&style=for-the-badge)
![Focus](https://img.shields.io/badge/Focus-Red%20Team%20%26%20Surveillance-orange?style=for-the-badge)

---

## 📖 Proje Hakkında (Overview)

**Karage Security Lab (KSL)**, sıradan terminal emülatörlerinin ötesine geçen, Android ekosistemi için tasarlanmış **"Hepsi Bir Arada"** siber güvenlik ve siber istihbarat platformudur.

Sadece sanal saldırılar değil, **Fiziksel Güvenlik** ve **Adli Bilişim** üzerine odaklanan modülleriyle (NFC Analizi, Ortam Dinleme, Yüz Tarama, Steganografi) sahada çalışan siber güvenlik uzmanları için bir "İsviçre Çakısı" görevi görür. **Root erişimi zorunlu değildir.**

### ⚡ Öne Çıkan Özellikler
* 🐍 **Hibrit Motor:** Android içinde gömülü **Python 3.11** ve **Java** motoru birlikte çalışır.
* 🕵️‍♂️ **Adli Bilişim (Forensics):** Resimlerden GPS/EXIF verisi çıkarma ve Yüz Arama (Face Search).
* 📡 **Sinyal İstihbaratı (SIGINT):** Wifi Deauth Saldırı Tespiti, BLE Tarama ve NFC Analizi.
* 🎙️ **Ortam Gözetimi:** Belirli desibeli geçen sesleri otomatik kaydeden "Audio Sentry" modu.
* 🔐 **Kriptografi:** Resimlerin içine gizli mesaj saklama (Steganografi).
* 🚀 **Turbo Recon:** Çok iş parçacıklı (Multi-Threaded) Port ve Ağ Tarayıcı.

---

## 💻 Komut Cephaneliği (The Arsenal)

KSL v7.0, kategorize edilmiş **70+ Araç** içerir.

### 🕵️‍♂️ 1. Fiziksel Gözetim & Casusluk (Surveillance)
*Saha operasyonları ve fiziksel güvenlik testleri.*

| Komut | Parametre | Açıklama |
| :--- | :--- | :--- |
| **`spy`** | `start / stop` | **Audio Sentry:** Ortam sesini (dB) dinler. Konuşma algılarsa otomatik ses kaydı başlatır (.3gp). |
| **`wifiguard`** | `start / stop` | **Deauth Detector:** Wifi bağlantınızı izler. Sizi ağdan atmaya çalışan (Deauth Saldırısı) biri varsa uyarır. |
| **`listen`** | `start / stop` | Manuel ortam dinlemesi ve ses kaydı. |
| **`ble`** | - | **Bluetooth Low Energy:** Çevredeki akıllı cihazları (Saat, Kulaklık, Tag) sinyal gücüne göre haritalar. |
| **`nfc`** | *(Otomatik)* | **Device Fingerprinting:** Dokundurulan kartın (Kredi Kartı/Pasaport) veya telefonun türünü (Android/iOS) analiz eder. |

### 🧩 2. Adli Bilişim & İstihbarat (Forensics & OSINT)
*Dijital izleri takip edin ve analiz edin.*

| Komut | Parametre | Açıklama |
| :--- | :--- | :--- |
| **`facesearch`** | - | **Reverse Image Search:** Galeriden seçilen kişinin fotoğrafını analiz eder (GPS/EXIF) ve internette (PimEyes/Google) aratır. |
| **`stego`** | `hide <msg>` | **Steganografi (Gizle):** Seçilen bir resmin piksellerine gizli bir metin şifreler. |
| **`stego`** | `read` | **Steganografi (Oku):** Şifreli resmi analiz eder ve gizli mesajı çözer. |
| **`metadata`** | `<dosya>` | Fotoğrafların içindeki gizli Meta verileri (Konum, Cihaz Modeli, Tarih) çıkarır. |
| **`apkscan`** | - | Telefondaki riskli ve casus yazılım (Spyware) potansiyeli taşıyan uygulamaları tarar. |

### 🚀 3. Ağ Keşfi & Saldırı (Recon & Attack)
*Hedef sistemleri haritalayın ve zafiyet arayın.*

| Komut | Parametre | Açıklama |
| :--- | :--- | :--- |
| **`portscan`** | `<ip>` | **Turbo Scanner:** 50 Thread ile hedef üzerindeki 100+ kritik portu saniyeler içinde tarar. |
| **`fullscan`** | `<url>` | Whois -> Nmap -> Subdomain -> CMS Detect zincirleme tam tarama yapar. |
| **`sqlmap`** | `<url>` | SQL Enjeksiyonu ile veritabanı tespiti ve veri çekme (Python Motoru). |
| **`wifiscan`** | - | Çevredeki Wifi ağlarını, şifreleme türlerini ve sinyal seviyelerini analiz eder. |
| **`subnet`** | - | Yerel ağdaki (LAN) diğer cihazları tespit eder (ARP/Ping Sweep). |

### 🌐 4. Web İstihbaratı (Web Recon)
*Web siteleri hakkında derinlemesine bilgi toplayın.*

| Komut | Parametre | Açıklama |
| :--- | :--- | :--- |
| **`tech`** | `<url>` | **Wappalyzer:** Sitede kullanılan teknolojileri (CMS, Server, Framework) tespit eder. |
| **`dirsearch`** | `<url>` | Gizli yönetim panellerini ve dosyaları (admin, backup, .env) tarar. |
| **`headers`** | `<url>` | HTTP Başlıklarını analiz eder ve güvenlik eksiklerini raporlar. |
| **`subdomain`** | `<domain>` | Hedef sitenin alt alan adlarını (subdomain) keşfeder. |
| **`whois`** | `<domain>` | Alan adı sahiplik bilgilerini ve sunucu detaylarını çeker. |

### 🛠️ 5. Sistem & Çekirdek (System Core)
*Dosya yönetimi ve sistem araçları.*

| Komut | Açıklama |
| :--- | :--- |
| **`sysinfo`** | Cihaz donanım kimliği, işlemci mimarisi ve güvenlik yaması bilgileri. |
| **`netstat`** | Aktif ağ bağlantılarını ve dinlenen yerel portları listeler. |
| **`rootcheck`**| Cihazın Root (Kök) erişim durumunu ve güvenliğini kontrol eder. |
| **`python`** | `.py` uzantılı scriptleri dahili motor veya Termux üzerinden çalıştırır. |
| **`git`** | GitHub depolarını doğrudan telefona klonlar (`git clone`). |
| **`pdf`** | Tüm oturum çıktılarını profesyonel bir **PDF Raporu** olarak kaydeder. |

---

## 📥 Kurulum (Installation)

1.  **Releases** sekmesinden en son `v7.0-Ultimate.apk` dosyasını indirin.
2.  Android cihazınıza yükleyin (Bilinmeyen kaynaklara izin verin).
3.  **İzinler:** Uygulama açılışta Kamera, Mikrofon, Konum ve Depolama izinleri isteyecektir. Tüm özelliklerin (Spy, Wifi, NFC) çalışması için onaylayın.
4.  Konsola `help` yazarak başlayın.

---

## ⚠️ Yasal Uyarı (Legal Disclaimer)

**Karage Security Lab (KSL)**, yalnızca **eğitim**, **ağ yönetimi** ve **yetkili güvenlik testleri (Authorized Pentesting)** amacıyla geliştirilmiştir.

* ❌ Bu yazılımı, sahibi olmadığınız veya yazılı izniniz olmayan sistemler üzerinde kullanmak **YASA DIŞIDIR**.
* 🛡️ Geliştirici (**Karage Yazılım**), bu aracın kötü niyetli kullanımından doğacak hiçbir yasal sorumluluğu kabul etmez. Kullanıcı, tüm eylemlerinden kendisi sorumludur.

---

## 🏷️ Etiketler (SEO Tags)
`android pentest framework` `mobile hacking` `cyber surveillance` `steganography android` `nfc analysis` `wifi deauth detector` `audio spy` `face search forensics` `port scanner` `red team tools` `python for android` `chaquopy` `siber güvenlik` `yerli yazılım`

---

<p align="center">
  Developed with 💀 by <b>Karage Yazılım</b><br>
  <i>"Silent Hunter. Digital Ghost."</i>
</p>
