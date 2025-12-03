# 🛡️ Karage Security Lab v5.0 [Ultimate Edition]

**Android İçin Yeni Nesil, Root Gerektirmeyen Siber Güvenlik ve Ağ Analiz Terminali.**

![Java](https://img.shields.io/badge/Language-Java-orange) ![Platform](https://img.shields.io/badge/Platform-Android-green) ![License](https://img.shields.io/badge/License-GPLv3-blue) ![Root](https://img.shields.io/badge/Root-Not%20Required-brightgreen)

## 📖 Proje Hakkında

**Karage Security Lab**, Android cihazlar için geliştirilmiş, CLI (Komut Satırı Arayüzü) tabanlı bir siber güvenlik laboratuvarıdır. Termux gibi ağır Linux emülasyonlarına ihtiyaç duymadan, **Saf Java** gücüyle ağ analizi, web zafiyet taraması, OSINT ve sistem izleme işlemlerini gerçekleştirir.

**Temel Felsefe:** Root yetkisine ihtiyaç duymadan, Android'in standart API sınırlarını zorlayarak maksimum işlevsellik sunmak.

---

## ⚙️ Nasıl Çalışır? (Teknik Mimari)

Uygulama, klasik bir Android uygulaması gibi görünse de arka planda güçlü bir **Multi-Threading (Çoklu İş Parçacığı)** motoru çalıştırır.

1.  **Komut İşleme:** Kullanıcı arayüzüne girilen komutlar (`processCommand`), ana iş parçacığını (UI Thread) dondurmamak için anında analiz edilir.
2.  **ThreadPool Executor:** Ağ taraması (Portscan, Flood, Sherlock) gibi ağır işlemler, **50 eşzamanlı iş parçacığına** sahip bir havuzda (`ExecutorService`) işlenir. Bu sayede uygulama asla donmaz ve saniyeler içinde binlerce işlem yapabilir.
3.  **Socket & HTTP Motoru:** * **Ağ:** Java `Socket` ve `DatagramSocket` sınıfları kullanılarak TCP/UDP paketleri (Ping, WOL, Port Knocking) oluşturulur.
    * **Web:** `HttpURLConnection` kullanılarak HTTP Header manipülasyonu ve kaynak kod analizi yapılır.
    * **Sistem:** Android `Procfs` (`/proc/net/arp` vb.) ve `SystemServices` okunarak donanım bilgileri çekilir.

---

## 🛠️ Araçlar ve Kullanım Kılavuzu

Uygulama içerisindeki tüm araçlar kategorize edilmiştir. İşte komut listesi ve teknik çalışma mantıkları:

### 1. 🌐 Ağ ve Keşif (Network Recon)

| Komut | Kullanım | Nasıl Çalışır? |
| :--- | :--- | :--- |
| **`ping`** | `ping google.com` | ICMP paketleri göndererek hedefin erişilebilirliğini ve gecikme süresini ölçer. |
| **`portscan`** | `portscan 192.168.1.1` | Hedef IP üzerindeki kritik 20 TCP portuna (21, 22, 80, 443 vb.) `Socket.connect` isteği atar. Bağlantı kabul edilirse port açıktır. |
| **`trace`** | `trace google.com` | Paketin hedefe giderken izlediği yolu ve hop noktalarını simüle eder. |
| **`wol`** | `wol 00:11:22:33:44:55` | Yerel ağa UDP port 9 üzerinden "Magic Packet" göndererek kapalı bilgisayarları açar (Wake-on-LAN). |
| **`arp`** | `arp` | Cihazın önbelleğindeki (ARP Cache) diğer cihazların IP ve MAC adreslerini listeler. |
| **`nc`** | `nc 4444` | Belirtilen portta bir **TCP Sunucusu** başlatır (Netcat Listener). Gelen bağlantıları ve mesajları ekrana basar. |
| **`dns`** | `dns site.com` | Alan adının tüm IP (A Records) kayıtlarını DNS sunucusundan çeker. |

### 2. 🕵️‍♂️ OSINT (İstihbarat)

| Komut | Kullanım | Nasıl Çalışır? |
| :--- | :--- | :--- |
| **`checkuser`** | `checkuser ahmet123` | **Sherlock Mantığı:** 25+ popüler platforma (Instagram, GitHub, TikTok vb.) istek atar. Eğer profil sayfası "404" dönmezse kullanıcı orada var demektir. |
| **`email`** | `email test@mail.com` | E-postanın formatını doğrular ve **Gravatar** veritabanında MD5 hash sorgusu yaparak o maile ait bir profil resmi olup olmadığını (gerçek kişi analizi) kontrol eder. |
| **`phone`** | `phone +90555...` | Numaranın ülke kodunu çözer ve WhatsApp, Telegram, Viber için direkt sohbet linkleri üretir. |
| **`fakeid`** | `fakeid` | Test amaçlı rastgele isim, adres, telefon ve kredi kartı (Luhn algoritması simülasyonu) verisi üretir. |

### 3. 🔴 Red Team (Saldırı ve Zafiyet)

| Komut | Kullanım | Nasıl Çalışır? |
| :--- | :--- | :--- |
| **`flood`** | `flood http://site.com` | **HTTP Stress Test:** Hedef siteye çoklu thread üzerinden seri GET istekleri göndererek sunucunun yük altındaki tepkisini ölçer. (Durdurmak için `stop`). |
| **`vuln`** | `vuln http://site.com` | Sunucuda unutulmuş kritik dosyaları (`.env`, `.git/config`, `backup.sql`) tarar. Bu dosyalar genelde şifreleri barındırır. |
| **`cms`** | `cms http://site.com` | Sitenin kaynak kodunu (HTML) analiz ederek WordPress, Joomla, Drupal gibi altyapıları tespit eder. |
| **`crack`** | `crack 5f4dcc3...` | **Dictionary Attack:** Verilen Hash'i (MD5/SHA1), dahili "En çok kullanılan şifreler" listesiyle karşılaştırarak kırmaya çalışır. |
| **`knocker`** | `knocker 192.168.1.5` | Güvenlik duvarlarını aşmak için belirli portlara (7000, 8000, 9000) sırayla "Vur-Kaç" (Connect-Close) işlemi yapar. |
| **`payload`** | `payload xss` | XSS, SQLi ve Reverse Shell için hazır saldırı kodları üretir. |

### 4. 💻 Sistem ve Dosya (System)

| Komut | Kullanım | Açıklama |
| :--- | :--- | :--- |
| **`ls`** | `ls` | Uygulamanın özel dizinindeki dosyaları listeler. |
| **`touch`** | `touch not.txt selam` | Dosya oluşturur ve içine yazar. |
| **`cat`** | `cat not.txt` | Dosya içeriğini okur. |
| **`rm`** | `rm not.txt` | Dosya siler. |
| **`monitor`** | `monitor` | Anlık RAM kullanımı, Disk durumu ve CPU çekirdek bilgisini gösterir. |
| **`netstat`** | `netstat` | Cihazın arka planda bağlı olduğu tüm TCP bağlantılarını (`/proc/net/tcp`) listeler. |

### 5. 🔐 Kriptografi ve Dönüştürme

* **`md5 / sha1`**: Metinlerin özet (hash) değerini çıkarır.
* **`base64`**: Metni Base64 formatına çevirir (`enc`) veya çözer (`dec`).
* **`rot13`**: CTF yarışmaları için basit kaydırmalı şifreleme.
* **`tohex / tobin`**: Metni Hexadecimal veya Binary kodlara çevirir.

---

## ⚠️ Yasal Uyarı (Disclaimer)

**Karage Security Lab**, tamamen **EĞİTİM** ve **AĞ YÖNETİMİ** amacıyla geliştirilmiştir.

* **Yetkili Kullanım:** Bu araçları yalnızca kendi ağınızda veya yazılı izniniz olan sistemlerde (Penetration Testing kapsamı) kullanmalısınız.
* **Sorumluluk Reddi:** Geliştirici, uygulamanın yasa dışı amaçlarla kullanılmasından doğacak sonuçlardan sorumlu değildir. Kullanıcı, tüm eylemlerinden kendisi sorumludur.

---

## 📥 Kurulum

1.  **Releases** kısmından en son `app-release.apk` dosyasını indirin.
2.  Android cihazınızda "Bilinmeyen Kaynaklar" iznini vererek yükleyin.
3.  Uygulamayı açın ve `help` yazarak başlayın.

---

### 👨‍💻 Geliştirici

**Karage Yazilim** tarafından, siber güvenlik topluluğu için ❤️ ile geliştirildi.
