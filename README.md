
🛡️ KARAGE YAZILIM SECURITY LAB (KSL)
CLI tabanlı, root yetkisi gerektirmeyen, mobil cihazlar için geliştirilmiş açık kaynaklı (FOSS) ağ analizi ve siber güvenlik test aracı.
💡 Proje Hakkında
KarageYazilim Security Lab (KSL), Android cihazlar üzerinde sistem yöneticileri, öğrenciler ve güvenlik araştırmacıları için tasarlanmış taşınabilir bir terminal emülatörüdür. Uygulama, karmaşık JNI (Java Native Interface) veya root yetkisi gerektirmeden, Saf Java Soket Programlama ve Android Runtime kullanarak gerçek zamanlı ağ keşfi ve zafiyet kontrolü yapma yeteneğine sahiptir.
Uygulama, temel güvenlik testlerini cihazınızın ağında veya izinli olduğunuz hedefler üzerinde hızlıca gerçekleştirmek için idealdir.
✨ Temel Özellikler (Tools)
KSL, aşağıdaki kategorilerde zenginleştirilmiş komut satırı araçları sunar:
🌐 Ağ Keşfi (Network Reconnaissance)
 * portscan: Hedef IP üzerinde kritik TCP portlarını tarar.
 * lanscan: Yerel ağdaki (Subnet) aktif cihazları multi-threading ile hızlıca keşfeder.
 * whois: Alan adı kayıt bilgilerini sorgular.
 * ping / dns: Temel ağ erişimi ve isim çözünürlüğü testleri.
🕸️ Web Güvenlik Taraması (Web Security Fuzzing)
 * adminfinder: Yaygın kullanılan yönetim paneli yollarını HTTP HEAD istekleriyle arar.
 * sqli: Basit hata tabanlı (Error-based) SQL Injection zafiyet kontrolü yapar.
 * xss: Reflected XSS zafiyetinin yansımasını kontrol eder.
 * subdomain: Yaygın alt alan adlarını (subdomain) DNS brute-force ile keşfeder.
 * spider: Belirtilen URL'den HTML içeriğini çekip linkleri ayrıştırır.
🔐 Kriptografi ve Yardımcı Araçlar
 * hash: MD5, SHA-1, SHA-256 algoritmalarıyla metinleri özetler.
 * base64: Veriyi Base64 formatında kodlar ve çözer.
 * passgen: Güçlü ve rastgele şifreler üretir.
🚀 Kurulum ve Kullanım
APK İndirme (GitHub Releases)
Uygulamanın en son imzalı APK dosyasını aşağıdaki linkten indirebilirsiniz:
 * [APK İNDİRME LİNKİ] (Buraya gelecekteki app-release.apk linkini koyun.)
F-Droid (Yakında)
Uygulama şu anda F-Droid kataloğuna eklenme sürecindedir ve yakında F-Droid istemcisi üzerinden ulaşılabilir olacaktır.
Kaynaktan Derleme
Projeyi Android Studio'da açarak Build > Generate Signed Bundle / APK yolunu izleyip kendi cihazınız için derleyebilirsiniz.
⚠️ Önemli Güvenlik Uyarısı (Disclaimer)
KarageYazilim Security Lab, sadece EĞİTİM AMAÇLI ve ETİK HACKING (Sızma Testi) faaliyetleri için tasarlanmıştır.
 * Uygulamayı yalnızca kendi sistemlerinizde veya yasal olarak izin aldığınız ağlar üzerinde kullanın.
 * Bu uygulamanın yasa dışı, izinsiz veya kötü amaçlı kullanımı kesinlikle yasaktır ve tüm sorumluluk kullanıcıya aittir. Geliştiriciler, uygulamanın kötüye kullanımından sorumlu tutulamaz.
💻 Teknolojiler
 * Dil: Java
 * Platform: Android SDK
 * Lisans: GPL-3.0-only (Tüm kod açık kaynaktır.)
🤝 Katkıda Bulunma
Projenin geliştirilmesine katkıda bulunmak, yeni özellikler eklemek veya hata düzeltmeleri önermek isterseniz, lütfen bir Pull Request oluşturun veya Issues (Sorunlar) sekmesinden bir hata raporu bildirin.
Geleceği Kodluyoruz, Güvenliği İnşa Ediyoruz.
