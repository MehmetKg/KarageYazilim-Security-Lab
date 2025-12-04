# 🛡️ Karage Security Lab v7.0 [ULTIMATE]

**Android İçin Root Gerektirmeyen, Yeni Nesil Siber Güvenlik ve Sızma Testi Terminali.**

![Java](https://img.shields.io/badge/Language-Java-orange?style=for-the-badge&logo=java) ![Platform](https://img.shields.io/badge/Platform-Android-green?style=for-the-badge&logo=android) ![License](https://img.shields.io/badge/License-GPLv3-blue?style=for-the-badge) ![Root](https://img.shields.io/badge/Root-Not%20Required-brightgreen?style=for-the-badge)

## 📖 Proje Hakkında

**Karage Security Lab (KSL)**, mobil cihazları tam donanımlı bir siber güvenlik istasyonuna dönüştüren açık kaynaklı bir terminal emülatörüdür. Termux gibi ağır Linux emülasyonlarına ihtiyaç duymadan, **Saf Java** gücüyle ve Android API sınırlarını zorlayarak maksimum saldırı ve savunma kapasitesi sunar.

**Temel Felsefe:** "Cebinizdeki Siber Ordu." Root yetkisine ihtiyaç duymadan gerçek siber güvenlik araçları.

---

## ⚙️ Nasıl Çalışır? (Teknik Mimari)

Uygulama arka planda 3 temel motor üzerinde çalışır:

1.  **Multi-Threading Motoru:** Ağ taraması ve yük testleri gibi ağır işlemler, **50 eşzamanlı iş parçacığına** sahip bir havuzda işlenir. Uygulama donmaz.
2.  **Socket & HTTP Motoru:** Java `Socket` ve `DatagramSocket` sınıfları ile ham TCP/UDP paketleri oluşturulur. `HttpURLConnection` ile web manipülasyonu yapılır.
3.  **Sistem & Donanım Erişimi:** Android sistem dosyaları (`/proc/`) ve donanım sensörleri okunarak derin analiz yapılır.

---

## 🛠️ Araçlar ve Kullanım Kılavuzu

### 1. 🌐 Ağ ve Keşif (Network Recon)
* **`portscan`** (Kullanım: `portscan 192.168.1.1`)
    * Hedef IP üzerindeki kritik 20 TCP portuna çoklu thread ile bağlantı isteği atar ve açık portları listeler.
* **`wol`** (Kullanım: `wol 00:11:22:33:44:55`)
    * Yerel ağa UDP "Magic Packet" göndererek uyuyan bilgisayarları açar (Wake-on-LAN).
* **`arp`**
    * Cihazın önbelleğindeki (ARP Cache) diğer cihazların IP ve MAC adreslerini listeler.
* **`nc`** (Kullanım: `nc 4444`)
    * Telefonu bir TCP sunucusuna dönüştürür (Netcat Listener). Gelen bağlantıları kabul eder ve mesajları okur.
* **`trace`** (Kullanım: `trace google.com`)
    * Paketin hedefe giderken izlediği yolu (Hop noktaları) analiz eder.
* **`ping`**, **`dns`**, **`myip`**
    * Temel bağlantı testleri, DNS sorgulama ve dış IP öğrenme araçları.

### 2. 🕷️ Web Sızma Testi (Web Pentest)
* **`waf`** (Kullanım: `waf site.com`)
    * Sitede Cloudflare, ModSecurity gibi güvenlik duvarları olup olmadığını tespit eder.
* **`lfi`** (Kullanım: `lfi site.com?p=`)
    * Sunucuda yerel dosya okuma (Local File Inclusion) açığı arar (`/etc/passwd` vb.).
* **`buster`** (Kullanım: `buster site.com`)
    * Sitedeki gizli klasörleri (backup, db, admin, logs) brute-force yöntemiyle bulur.
* **`spider`** (Kullanım: `spider site.com`)
    * Site haritasını çıkarır ve sayfadaki tüm linkleri toplar.
* **`admin`** (Kullanım: `admin site.com`)
    * WordPress, Joomla gibi sistemlerin yönetim panellerini otomatik tarar.
* **`headers`**
    * HTTP başlıklarını analiz ederek güvenlik açıklarını raporlar.

### 3. 🔴 Red Team & Hardcore (İleri Seviye Saldırı)
* **`takeover`** (Kullanım: `takeover site.com`)
    * Subdomain Takeover (Alan adı ele geçirme) zafiyetini kontrol eder.
* **`blindsqli`** (Kullanım: `blindsqli site.com?id=`)
    * Zaman tabanlı (Time-Based) Blind SQL Injection testi yapar (Sunucuyu uyutma taktiği).
* **`vuln`** (Kullanım: `vuln site.com`)
    * Sunucuda unutulmuş kritik dosyaları (`.env`, `.git`, `backup.sql`) avlar.
* **`flood`** (Kullanım: `flood site.com`)
    * Hedef siteye HTTP Stress Testi (Yük testi) uygular.
* **`crack`** (Kullanım: `crack 5f4dcc3...`)
    * MD5 ve SHA1 hashlerini dahili sözlük saldırısı ile kırmaya çalışır.
* **`knocker`** (Kullanım: `knocker 192.168.1.5`)
    * Port Knocking (Gizli port açma) sekansını uygular.

### 4. 🕵️‍♂️ OSINT & İstihbarat
* **`scan`** (Kullanım: `scan kullaniciadi`)
    * **Sherlock Modülü:** 25+ popüler platformda kullanıcı adı taraması yapar.
* **`email`** (Kullanım: `email test@mail.com`)
    * E-postanın Gravatar profili olup olmadığını analiz eder (Gerçek kişi doğrulaması).
* **`phone`** (Kullanım: `phone +90555...`)
    * Telefon numarasını analiz eder, WhatsApp/Telegram direkt linklerini üretir.
* **`fakeid`**
    * Sosyal mühendislik testleri için tutarlı sahte kimlik verisi üretir.

### 5. 🧅 Dark Web (Tor Network)
* **`onion`** (Kullanım: `onion site.onion`)
    * `.onion` sitelerinin kaynak kodunu Tor Gateway üzerinden çeker.
* **`market`** (Kullanım: `market database`)
    * Dark Web arama motorlarında illegal market/forum araması yapar.
* **`torcheck`**
    * Cihazın Tor ağına güvenli bir şekilde bağlı olup olmadığını kontrol eder.

### 6. 📡 Spy & Fiziksel Güvenlik (Hardware)
* **`ble`**
    * **IoT Radar:** Etraftaki Bluetooth cihazları, AirTag'leri ve Akıllı Saatleri tespit eder.
* **`emf`**
    * **Böcek Arama:** Manyetik sensörü kullanarak duvardaki gizli kamera/mikrofonları (metal) bulur.
* **`stego`** (Kullanım: `stego hide resim.png mesaj`)
    * Bir resim dosyasının içine gizli metin saklar ve okur.
* **`mask`**
    * Oltalama (Phishing) testleri için maskelenmiş URL üretir.

### 7. 🦠 Malware Simülasyonu & Kriptografi
* **`eicar`**: Antivirüs test dosyası oluşturur.
* **`payload`**: Reverse Shell (Sızma) kodları üretir.
* **`ransom`**: Fidye yazılımı notu simülasyonu yapar.
* **`exif`**: Fotoğraflardan gizli GPS konumunu çıkarır.
* **`encrypt` / `decrypt`**: Dosyaları AES-128 ile şifreler.
* **`rsa`**: Güvenli iletişim için anahtar çifti üretir.

---

## 📥 Kurulum

1.  GitHub **Releases** sayfasından en son `app-release.apk` dosyasını indirin.
2.  Android cihazınızda "Bilinmeyen Kaynaklardan Yükle" iznini verin.
3.  Uygulamayı yükleyin ve açın.
4.  Komut listesi için `help` yazın.

---

## ⚠️ Yasal Uyarı (Disclaimer)

**Karage Security Lab**, tamamen **EĞİTİM**, **AĞ YÖNETİMİ** ve **GÜVENLİK ARAŞTIRMALARI** amacıyla geliştirilmiştir.

* **Yetkili Kullanım:** Bu araçları yalnızca kendi ağınızda veya yazılı izniniz olan sistemlerde kullanmalısınız.
* **Sorumluluk Reddi:** Geliştirici, uygulamanın yasa dışı amaçlarla kullanılmasından sorumlu tutulamaz. Kullanıcı, tüm eylemlerinden kendisi sorumludur.
* **Virüs Uyarısı Hakkında:** Uygulama içerisinde saldırı simülasyonu ve payload üretici modüller bulunduğu için, Google Play Protect veya Antivirüs yazılımları uyarı verebilir. Bu beklenen bir durumdur ve uygulamanın doğası gereğidir.

---

### 👨‍💻 Geliştirici

**Mehmet Karagülle (Karage Yazilim)**
*Geleceği Kodluyoruz, Güvenliği İnşa Ediyoruz.*
