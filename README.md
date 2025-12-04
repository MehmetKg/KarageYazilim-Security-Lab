# 🛡️ Karage Security Lab v7.0 [ARMAGEDDON SUITE]

**Android Cihazlar İçin Root Yetkisi Gerektirmeyen, Gelişmiş Siber Güvenlik ve İstihbarat Terminali.**

[![Java](https://img.shields.io/badge/Language-Java-orange?style=for-the-badge&logo=java)]() [![Platform](https://img-shields.io/badge/Platform-Android-green?style=for-the-badge&logo=android)]() [![Root](https://img.shields.io/badge/Root-Not%20Required-brightgreen?style=for-the-badge)]() [![Tools](https://img.shields.io/badge/Tools-40%2B%20Modules-blue?style=for-the-badge)]()

## 💡 Proje Mimarisi ve Güç Çekirdeği

Karage Security Lab, piyasadaki basit terminal uygulamalarından farklıdır. Uygulamanın gücü, **Java Socket** motorunu, Android sistem API'leriyle birleştiren hibrit mimarisinden gelir:

* **⚡ Multi-Threading (Çoklu İş Parçacığı):** Ağ taraması (`portscan`, `flood`) gibi zaman alan tüm işlemler, 50 iş parçacığına sahip (`ExecutorService`) bir havuza gönderilir. Bu, uygulamanın **asla donmamasını** ve saniyeler içinde binlerce işlem yapmasını garanti eder.
* **🔌 Native Motor:** Tüm ağ ve web istekleri, harici bir binary dosyaya ihtiyaç duymadan, saf Java `Socket` ve `HttpURLConnection` sınıfları üzerinden yönetilir.
* **Procfs Erişimi:** Termux gibi, Android'in alt katmanındaki `/proc/net/arp` gibi sistem dosyalarını okuyarak gizli ağ bilgilerini çeker.

---

## 🛠️ Modüller ve Kullanım Kılavuzu

Uygulama, saldırı ve savunma senaryolarına yönelik 10 ana modüle ayrılmıştır.

### 1. ⚔️ Ağ ve Hardcore Saldırıları

| Komut | Kullanım Örneği | Nasıl Çalışır? (Teknik Açıklama) |
| :--- | :--- | :--- |
| **`portscan`** | `portscan 192.168.1.1` | Hedef IP'de kritik portlara çoklu thread üzerinden TCP bağlantı isteği atar (`Socket.connect`). |
| **`wol`** | `wol AA:BB:CC:DD:EE:FF` | Yerel ağa UDP "Magic Packet" göndererek kapalı bir bilgisayarı uzaktan başlatır. |
| **`nc`** | `nc 4444` | Telefonu bir **TCP Sunucusu** yapar (`ServerSocket`) ve dışarıdan gelen bağlantıları dinler. |
| **`arp`** | `arp` | Cihazın önbelleğindeki (ARP Cache) diğer cihazların IP ve MAC adreslerini listeler. |
| **`flood`** | `flood hedef.com` | Hedef siteye eşzamanlı HTTP/S GET istekleri göndererek Yük Testi (Stress Test) yapar. |
| **`trace`** | `trace google.com` | Paketin hedefe giderken izlediği tüm hop noktalarını kaydeder. |

### 2. 🛡️ Web Zafiyet Avcılığı

| Komut | Kullanım Örneği | Nasıl Çalışır? |
| :--- | :--- | :--- |
| **`takeover`** | `takeover sub.site.com` | Subdomain Takeover zafiyetini (CNAME kaydının boşta kalmasını) kontrol eder. |
| **`blindsqli`** | `blindsqli site.com?id=1` | Sunucuya `SLEEP(5)` (uyuma) komutu enjekte eder. Sunucunun gecikmeli yanıt verip vermediğini analiz eder. |
| **`lfi`** | `lfi site.com?page=` | **Local File Inclusion** zafiyetini arar (`../../etc/passwd` payloadları dener). |
| **`waf`** | `waf site.com` | Hedef sitenin Cloudflare, ModSecurity gibi bir **Güvenlik Duvarı** tarafından korunup korunmadığını HTTP yanıt kodları ile tespit eder. |
| **`cms`** | `cms site.com` | Sitenin **WordPress, Joomla** veya **Drupal** olup olmadığını kaynak koddan analiz eder. |
| **`vuln`** | `vuln site.com` | Sunucuda unutulmuş kritik dosyaları (`.env`, `.git/config`, `backup.sql`) arar. |
| **`buster`** | `buster site.com` | Sitedeki gizli dizinleri ve klasörleri bulur. |

### 3. 🔦 Spy & Fiziksel Güvenlik

| Komut | Kullanım Örneği | Nasıl Çalışır? |
| :--- | :--- | :--- |
| **`ble`** | `ble` | **Bluetooth LE (Low Energy) Radarını** başlatır. Etraftaki IoT cihazlarını, AirTag’leri ve Akıllı Saatleri sinyal gücüne göre listeler. |
| **`emf`** | `emf` | **EMF Dedektörü:** Telefonun manyetik sensörünü (pusula) kullanarak, çevredeki gizli kamera veya mikrofonların yaydığı manyetik alanı tespit eder. |
| **`stego`** | `stego hide resim.png mesaj` | Bir resim dosyasının sonuna gizli metin ekler ve okur. |
| **`exif`** | `exif foto.jpg` | Bir fotoğrafın içine gömülü GPS koordinatlarını, cihaz modelini ve çekim tarihini çıkarır (Adli Bilişim). |
| **`mask`** | `mask site.com secure` | Oltalama (Phishing) testleri için maskelenmiş URL üretir. |

### 4. 🧅 Dark Web & OSINT (İstihbarat)

| Komut | Kullanım Örneği | Açıklama |
| :--- | :--- | :--- |
| **`onion`** | `onion hiddenwiki.onion` | `.onion` sitelerinin içeriğini **Tor2Web Gateway** üzerinden çekerek terminalde gösterir. |
| **`market`** | `market bitcoin` | Dark Web arama motorlarında (`Ahmia.fi`) arama yapar. |
| **`checkuser`** | `checkuser user_ad` | **Sherlock Modülü:** 25'ten fazla popüler platformda kullanıcı adını arar. |
| **`email`** | `email mail@mail.com` | E-postanın Gravatar profilini ve geçerliliğini kontrol eder. |
| **`payload`** | `payload 10.0.0.1 4444 bash` | Hedef sistemlere sızmak için gerekli **Reverse Shell** kodlarını (Bash, Python, Netcat) üretir. |
| **`rsa`** | `rsa` | 2048-bit Public ve Private anahtar çifti üretir. |

---

## ⚠️ Güvenlik ve Yasal Uyarı

Bu uygulama, zararlı kod üretme simülasyonları (`payload`, `eicar`) ve ağ testleri yaptığı için, telefonunuzdaki **Google Play Protect** veya diğer Antivirüs yazılımları tarafından **"Truva Atı (Trojan)"** olarak algılanabilir.

* **Bu bir Yanlış Pozitiftir (False Positive).** Uygulama, zararlı yazılım değildir; zararlı yazılımın kodunu ürettiği için tetiklenir.
* Uygulama, verilerinizi hiçbir sunucuya göndermez. Tüm işlemler cihazınızda lokal olarak yapılır.
* Kullanımdan önce **Yasal Sorumluluk Reddi** ve **Eğitim Amaçlı Kullanım** kurallarını okuyun.

---

### 👨‍💻 Kurulum ve İletişim

1.  Proje kodlarını Android Studio'ya import edin.
2.  **Build** menüsünden **Signed APK** oluşturun.
3.  Telefonunuzda **"Bilinmeyen Kaynaklardan Yükle"** izni ile yükleyin.

*Geliştirici: Mehmet Karagülle (Karage Yazilim)*
