# 🛡️ Karage Security Lab (KSL) - Ultimate Android Pentest Suite v6.0

> **"Mobile Cyber Warstation. No Root. No Limits."**
>
> *Android Cihazlar İçin Geliştirilmiş; Root Gerektirmeyen Hibrit Sızma Testi, Kırmızı Takım (Red Team) ve Siber İstihbarat (OSINT) Laboratuvarı.*

![Platform](https://img.shields.io/badge/Platform-Android%207.0%2B-green?logo=android&style=for-the-badge)
![Security](https://img.shields.io/badge/Focus-Red%20Team%20%26%20Pentest-red?logo=kalilinux&style=for-the-badge)
![Engine](https://img.shields.io/badge/Engine-Hybrid%20Core%20(Java)-blue?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-lightgrey?style=for-the-badge)
![Version](https://img.shields.io/badge/Version-v6.0%20Ultimate-purple?style=for-the-badge)

---

## 📖 Proje Hakkında (Overview)

**Karage Security Lab (KSL)**, siber güvenlik uzmanları, etik hackerlar ve bug bounty avcıları için tasarlanmış, **hepsi bir arada (All-in-One)** mobil siber güvenlik platformudur.

KSL, sıradan terminal emülatörlerinden farklıdır. Cihazınızı rootlamanıza (garanti dışı bırakmanıza) gerek kalmadan, **Saf Java Soketleri** ve **Multi-Thread** mimarisini kullanarak masaüstü araçlarının (Nmap, SQLMap, Hydra) gücünü cebinize getirir.

### ⚡ Neden KSL?
* 🚀 **Hibrit Motor:** Java'nın hızı ile Python scriptlerinin esnekliği bir arada.
* 🔒 **Root Gerektirmez:** Herhangi bir Android cihazda çalışır.
* 📊 **Görsel Raporlama:** Taramaları **PDF** veya Renkli **HTML** formatında raporlar.
* 🌍 **Global İstihbarat:** Shodan, Dark Web ve OSINT araçlarıyla dünyayı tarar.
* 🤖 **Otomasyon:** Tek komutla (`fullscan`) hedef üzerinde tam saldırı zinciri başlatır.

---

## 📥 Kurulum (Installation)

1.  **İndir:** Bu reponun **[Releases](https://github.com/kullaniciadi/projeadi/releases)** kısmından `app-release.apk` dosyasını indirin.
2.  **Yükle:** APK dosyasını kurun (Bilinmeyen kaynaklara izin verin).
3.  **İzin Ver:** Uygulamayı ilk açtığınızda **Depolama İzni** isteyecektir. Raporları kaydedebilmek için izin verin.
4.  **Başlat:** Terminale `help` yazarak cephaneliği görüntüleyin.

---

## 💻 Modüller ve Kullanım (Arsenal)

KSL v6.0, **50'den fazla** özelleştirilmiş araç içerir. İşte kategorilerine göre kullanım rehberi:

### 🚀 1. Otomasyon & Saldırı (Automation)
*Tek komutla karmaşık saldırı senaryolarını yönetin.*

| Komut | Kullanım Örneği | Açıklama |
| :--- | :--- | :--- |
| **`fullscan`** | `fullscan site.com` | **Tam Otomatik Tarama:** Sırasıyla Whois, Nmap, Spider, SQLi ve Brute-Force saldırılarını gerçekleştirir. |
| **`autopwn`** | `autopwn site.com` | **Akıllı Saldırı:** Hedefin CMS'ini (WP, Joomla) tanır ve ona özel exploit dener. |
| **`stress`** | `stress site.com` | **Stres Testi:** Hedef sunucuya HTTP Flood yaparak yük altındaki dayanıklılığını ölçer. |

### 🌎 2. Global İstihbarat (OSINT)
*Dünya genelinde pasif bilgi toplama.*

| Komut | Kullanım Örneği | Açıklama |
| :--- | :--- | :--- |
| **`shodan`** | `shodan <key> webcam` | **Shodan Arama:** Shodan API kullanarak dünyadaki açık kameraları ve sunucuları arar. |
| **`dark`** | `dark kiralik hacker` | **Dark Web Search:** Tor ağına girmeden `.onion` sitelerinde arama yapar. |
| **`sherlock`** | `sherlock username` | **Kullanıcı Avı:** 25+ Sosyal medya platformunda kullanıcı adı taraması yapar. |
| **`whois`** | `whois google.com` | **Domain Analizi:** API kullanmadan (Raw Socket) domain sahiplik bilgilerini çeker. |
| **`reputation`**| `reputation 8.8.8.8`| **IP İtibar:** IP adresinin kara listede olup olmadığını sorgular. |

### ⚔️ 3. Web Hacking & Exploit
*Web uygulamalarındaki kritik açıkları bulun ve istismar edin.*

| Komut | Kullanım Örneği | Açıklama |
| :--- | :--- | :--- |
| **`sqlmap`** | `sqlmap site.com` | **SQL Injection:** URL'e otomatik parametre ekler ve veritabanını (`user`, `db`) çeker. |
| **`xss`** | `xss site.com` | **XSS Hunter:** Reflected XSS açıklarını tespit eder. |
| **`lfi`** | `lfi site.com` | **Dosya Okuma:** Sunucudaki `/etc/passwd` gibi dosyaları okumaya çalışır (LFI). |
| **`paramhunter`**| `paramhunter site.com`| **Gizli Parametre:** `debug`, `admin` gibi gizli URL parametrelerini keşfeder. |
| **`beef`** | `beef 192.168.1.5` | **Hook Generator:** Tarayıcı ele geçirmek için zararlı JavaScript kodu üretir. |

### 📡 4. Red Team & Ağ Savaşları
*Ağ üzerindeki cihazları ele geçirin ve servisleri durdurun.*

| Komut | Kullanım Örneği | Açıklama |
| :--- | :--- | :--- |
| **`slowloris`** | `slowloris 192.168.1.5` | **DoS Saldırısı:** Hedef sunucuyu yavaş bağlantılarla kilitleyerek hizmet veremez hale getirir. |
| **`ftphydra`** | `ftphydra 192.168.1.1` | **FTP Kırıcı:** FTP servisine kaba kuvvet (Brute-Force) saldırısı yapar. |
| **`subnet`** | `subnet` | **Ağ Haritası:** Yerel ağdaki (WiFi) tüm cihazları TCP Connect ile tespit eder. |
| **`clickjack`** | `clickjack site.com` | **UI Redress:** Sitenin Clickjacking saldırısına açık olup olmadığını test eder. |

### 📱 5. Adli Bilişim (Forensics) & Sistem
*Dosya analizi, malware tespiti ve sistem sağlığı.*

| Komut | Kullanım Örneği | Açıklama |
| :--- | :--- | :--- |
| **`metadata`** | `metadata /sdcard/img.jpg`| **EXIF Analizi:** Fotoğraflardan GPS konumu ve cihaz bilgisini çıkarır. |
| **`apkscan`** | `apkscan` | **Uygulama Analizi:** Telefonda yüklü riskli (Kamera/SMS izni olan) uygulamaları listeler. |
| **`qrvenom`** | `qrvenom site.com` | **Zehirli QR:** Sosyal mühendislik için yönlendirmeli QR kod üretir. |
| **`forensic`** | `forensic dosya.exe` | **Magic Number:** Dosya uzantısı değiştirilse bile gerçek türünü (EXE, JPG, PDF) bulur. |

### 🛠️ 6. Raporlama & Araçlar
| Komut | Kullanım | Açıklama |
| :--- | :--- | :--- |
| **`html`** | `html` | **Renkli Rapor:** Tüm sonuçları Matrix temalı, renkli bir HTML dosyası olarak kaydeder. |
| **`pdf`** | `pdf` | **Resmi Rapor:** Sonuçları çok sayfalı PDF dökümanı olarak kaydeder. |
| **`rootcheck`**| `rootcheck` | **Root Kontrol:** Cihazın root durumunu analiz eder. |

---

## ⚠️ Yasal Uyarı (Disclaimer)

**Karage Security Lab (KSL)**, yalnızca **eğitim**, **ağ yönetimi** ve **yetkili güvenlik testleri (Pentest)** amacıyla geliştirilmiştir.

* ❌ Bu yazılımı, sahibi olmadığınız veya yazılı izniniz olmayan sistemler üzerinde kullanmak **yasa dışıdır** ve suç teşkil eder.
* 🛡️ Geliştirici (**Karage Yazılım**), bu aracın kötü niyetli kullanımından doğacak hiçbir zarardan sorumlu tutulamaz. Kullanıcı, tüm eylemlerinden kendisi sorumludur.

---

## 🏷️ Etiketler (SEO Keywords)
`android pentest framework` `mobile hacking tools` `sqlmap for android` `shodan client` `dark web search` `red team android` `network scanner` `termux alternative` `automated vulnerability scanner` `siber güvenlik` `sızma testi` `yerli yazılım` `rootless hacking` `slowloris android` `osint tools`

---

<p align="center">
  Developed with ❤️ by <b>Karage Yazılım</b><br>
  <i>"Watch the Web. Silent Hunter."</i>
</p>
