# 🛡️ Karage Security Lab (KSL) - Ultimate Android Pentest Framework v7.0

> **"Mobile Cyber Warstation. Hybrid Engine. AI Powered."**
>
> *Android Cihazlar İçin Geliştirilmiş; Hibrit Motor (Java + Python 3.11), Yapay Zeka Destekli (AI) ve Donanım Tabanlı (BLE/NFC/WiFi) Nihai Sızma Testi Laboratuvarı.*

![Platform](https://img.shields.io/badge/Platform-Android%208.0%2B-green?logo=android&style=for-the-badge)
![Core](https://img.shields.io/badge/Core-Java%20%7C%20Python%203.11-blue?logo=openjdk&style=for-the-badge)
![License](https://img.shields.io/badge/License-GNU%20GPLv3-red?logo=gnu&style=for-the-badge)
![Focus](https://img.shields.io/badge/Focus-Red%20Team%20%26%20Hardware-orange?style=for-the-badge)
![Version](https://img.shields.io/badge/Version-v7.0%20Ultimate-purple?style=for-the-badge)

---

## 📖 Proje Hakkında (Overview)

**Karage Security Lab (KSL)**, sıradan terminal emülatörlerinin ötesine geçen, Android ekosistemi için tasarlanmış **"Hepsi Bir Arada" (All-in-One)** siber güvenlik platformudur.

KSL v7.0, **Chaquopy** teknolojisi ile Android çekirdeğine **Python 3.11** entegre eder. Bu sayede harici bir kök erişimine (Root) veya Termux'a ihtiyaç duymadan karmaşık saldırı senaryolarını gerçekleştirebilir. Ayrıca **JGit** motoru ile GitHub projelerini klonlar, **AI Asistanı** ile logları yorumlar ve **Donanım Modülleri** (NFC/BLE) ile fiziksel güvenliği test eder.

### ⚡ Temel Özellikler
* 🐍 **Native Python 3.11:** Uygulama içinde gömülü tam Python motoru.
* 🧠 **AI Cyber Assistant:** Saldırı çıktılarını analiz eden ve strateji öneren Yapay Zeka.
* 📡 **Hardware Hacking:** Bluetooth (BLE), WiFi Analyzer ve NFC Kart Dumper.
* 🐙 **Git Entegrasyonu:** `git clone` ile GitHub depolarını indir ve yönet.
* 📊 **Live Dashboard:** CPU, RAM ve Ağ trafiğini izleyen Cyberpunk HUD.
* 🎙️ **Spy Mode:** Ortam dinlemesi ve ağ trafiği analizi.

---

## 💻 Komut Cephaneliği (The Arsenal)

KSL v7.0, **65+ Adet** özelleştirilmiş siber güvenlik aracı içerir.

### 🛠️ 1. Sistem & Çekirdek (System Core)
*Dosya sistemi, paket yönetimi ve motor kontrolleri.*

| Komut | Parametre | Açıklama |
| :--- | :--- | :--- |
| **`python`** | `<file.py>` | **Hibrit Motor:** Python scriptlerini çalıştırır (Termux veya Dahili Motor). |
| **`git`** | `clone <url>` | **JGit:** GitHub depolarını `/ksl_repos` dizinine indirir. |
| **`ls`** | - | Bulunulan dizindeki dosya ve klasörleri listeler. |
| **`cd`** | `<folder>` | Dizinler arasında geçiş yapar (Geri için: `cd ..`). |
| **`cat`** | `<file>` | Dosya içeriğini terminalde okur. |
| **`rm`** | `<file>` | Dosya siler. |
| **`touch`** | `<file>` | Yeni dosya oluşturur. |
| **`termux`** | `<cmd>` | Komutu harici Termux uygulamasına yönlendirir. |
| **`clear`** | - | Terminal ekranını temizler. |

### 🚀 2. Otomasyon & Saldırı (Red Team Automation)
*Tek komutla hedef üzerinde tam hakimiyet.*

| Komut | Parametre | Açıklama |
| :--- | :--- | :--- |
| **`fullscan`** | `<url>` | **Ultimate Scan:** Whois -> Nmap -> Spider -> SQLi -> XSS zincirleme saldırısı. |
| **`autopwn`** | `<url>` | **Smart Exploit:** Hedef CMS'i (WP/Joomla) tanır ve özel exploit dener. |
| **`venom`** | `<os> <ip> <p>` | **Payload Gen:** Windows/Linux/Android için FUD Reverse Shell üretir. |
| **`stress`** | `<url>` | Hedef sunucuya HTTP Flood ile yük/stres testi uygular. |

### 📡 3. Donanım & Fiziksel Ağ (Hardware & Physical)
*Sanal dünyadan fiziksel dünyaya geçiş.*

| Komut | Parametre | Açıklama |
| :--- | :--- | :--- |
| **`ble`** | - | **Bluetooth Low Energy:** Etraftaki akıllı cihazları (Saat, Tag) ve sinyal güçlerini tarar. |
| **`wifiscan`** | - | **WiFi Analyzer:** Gizli/Açık ağları, şifreleme (WPA2/3) ve mesafeyi ölçer. |
| **`traffic`** | - | Anlık ağ trafiğini (Upload/Download hızı) izler. |
| **`listen`** | `start/stop` | **Ortam Dinleme:** Mikrofonu aktif edip ortam sesini `.3gp` olarak kaydeder. |
| **`subnet`** | - | Yerel ağdaki (LAN) tüm cihazları ve IP adreslerini haritalar. |
| **`nfc`** | *(Otomatik)* | Kart dokundurulduğunda verileri okur ve PDF olarak kaydeder. |

### ⚔️ 4. Web Hacking & Exploitation
*Web uygulamalarındaki kritik zafiyetleri avlayın.*

| Komut | Parametre | Açıklama |
| :--- | :--- | :--- |
| **`sqlmap`** | `<url>` | SQL Enjeksiyonu ile veritabanı tespiti ve veri çekme. |
| **`xss`** | `<url>` | Cross-Site Scripting (Reflected/Stored) taraması. |
| **`lfi`** | `<url>` | Local File Inclusion (Sunucu dosyalarını okuma) testi. |
| **`commix`** | `<url>` | OS Command Injection (Komut Enjeksiyonu) taraması. |
| **`beef`** | `<ip>` | BeEF Framework için zararlı JavaScript (Hook) üretir. |
| **`paramhunter`**| `<url>` | Gizli URL parametrelerini (debug, admin, test) bulur. |
| **`spider`** | `<url>` | Web sitesini gezerek (Crawler) haritasını çıkarır. |

### 🌎 5. OSINT & İstihbarat (Intelligence)
*İz bırakmadan pasif bilgi toplama.*

| Komut | Parametre | Açıklama |
| :--- | :--- | :--- |
| **`shodan`** | `<key> <q>` | **Shodan API:** Dünyadaki açık IP kameraları ve sunucuları arar. |
| **`sherlock`** | `<user>` | 25+ Sosyal medya platformunda kullanıcı adı taraması. |
| **`whois`** | `<domain>` | Domain sahiplik bilgileri (Raw Socket, API'siz). |
| **`dark`** | `<query>` | Tor ağına girmeden Dark Web (.onion) üzerinde arama yapar. |
| **`phone`** | `<no>` | Telefon numarasından operatör ve konum analizi. |
| **`mailcheck`** | `<mail>` | E-posta adresi sızıntı ve doğrulama kontrolü. |
| **`fakeid`** | - | Sosyal mühendislik için sahte kimlik verileri üretir. |
| **`phish`** | `<domain>` | Oltalama saldırıları için benzer domainleri üretir. |

### 🐞 6. Bug Bounty & Recon
*Ödül avcıları için keşif araçları.*

| Komut | Parametre | Açıklama |
| :--- | :--- | :--- |
| **`subdomain`** | `<domain>` | Alt alan adlarını (subdomain) keşfeder. |
| **`dirsearch`** | `<url>` | Gizli dosya ve klasörleri (admin, backup, .env) tarar. |
| **`tech`** | `<url>` | Sitede kullanılan teknolojileri (Wappalyzer) tespit eder. |
| **`gitrecon`** | `<domain>` | GitHub üzerinde sızdırılmış API anahtarlarını arar. |
| **`s3`** | `<domain>` | Açık Amazon S3 Bucket'larını tarar. |
| **`takeover`** | `<sub>` | Subdomain Takeover zafiyetini kontrol eder. |
| **`ssl`** | `<url>` | SSL/TLS sertifika güvenlik analizi. |

### 📱 7. Adli Bilişim & Kriptografi (Forensics)
*Veri analizi ve şifre kırma.*

| Komut | Parametre | Açıklama |
| :--- | :--- | :--- |
| **`metadata`** | `<file>` | Fotoğraflardan GPS konumu ve EXIF verilerini çıkarır. |
| **`apkscan`** | - | Telefondaki riskli (casus) uygulamaları tespit eder. |
| **`qrvenom`** | `<url>` | Yönlendirmeli/Zararlı QR kod üretir. |
| **`malware`** | `<url>` | URL'in zararlı yazılım içerip içermediğini tarar. |
| **`crack`** | `<hash>` | MD5/SHA1 şifrelerini wordlist ile kırmaya çalışır. |
| **`crypto`** | `<text>` | Şifrelenmiş metnin türünü (Base64, Hex, Hash) analiz eder. |
| **`base64`** | `<enc/dec>`| Base64 şifreleme ve çözme işlemleri. |

### ⚙️ 8. Sistem & Raporlama
| Komut | Açıklama |
| :--- | :--- |
| **`sysinfo`** | Cihaz donanım ve yazılım detayları. |
| **`hardware`** | Gelişmiş donanım analizi (Sensörler, Hız Testi). |
| **`rootcheck`**| Cihazın Root durumunu kontrol eder. |
| **`netstat`** | Aktif ağ bağlantılarını listeler. |
| **`pdf`** | Tüm terminal çıktılarını **PDF Raporu** olarak kaydeder. |
| **`html`** | Tüm terminal çıktılarını **Renkli HTML** olarak kaydeder. |

---

## 📜 Lisans (License)

Bu proje **GNU General Public License v3.0 (GPLv3)** ile lisanslanmıştır.

* ✅ **Özgür Yazılım:** Kodu kullanabilir, değiştirebilir ve dağıtabilirsiniz.
* ✅ **Copyleft:** Eğer bu projeyi değiştirip dağıtırsanız, kaynak kodunu da aynı lisansla (GPLv3) açmak zorundasınız.
* ❌ **Kapalı Kaynak:** Bu kodları alıp kapalı kaynaklı (ticari gizli) bir projede kullanamazsınız.

---

## ⚠️ Yasal Uyarı (Legal Disclaimer)

**Karage Security Lab (KSL)**, yalnızca **eğitim**, **ağ yönetimi** ve **yetkili güvenlik testleri (Authorized Pentesting)** amacıyla geliştirilmiştir.

* ❌ Bu yazılımı, sahibi olmadığınız veya yazılı izniniz olmayan sistemler üzerinde kullanmak **YASA DIŞIDIR** ve suç teşkil eder.
* 🛡️ Geliştirici (**Karage Yazılım**), bu aracın kötü niyetli kullanımından doğacak hiçbir maddi/manevi zarardan sorumlu tutulamaz. Kullanıcı, tüm eylemlerinden kendisi sorumludur.

---

## 🏷️ SEO & Keywords
`android pentest framework` `mobile hacking tools` `python 3.11 android` `chaquopy` `nfc card reader` `ble scanner` `wifi analyzer` `sqlmap android` `shodan client` `red team tools` `rootless hacking` `cybersecurity` `siber güvenlik` `yerli yazılım` `git client` `payload generator` `fud` `gplv3`

---

<p align="center">
  Developed with 💀 by <b>Karage Yazılım</b><br>
  <i>"Silent Hunter. Digital Ghost."</i>
</p>

