# Araç Kiralama Uygulaması

Bu proje, Java ve Swing kullanılarak geliştirilen bir araç kiralama uygulamasıdır. Bu proje, Java programlama bilgilerinizi pekiştirmenin yanı sıra veritabanı işlemleri ve kullanıcı arayüzü geliştirme konularında da önemli deneyimler kazanmanızı sağlar. Swing kullanarak arayüz tasarlamayı bu proje ile pratik edebilirsiniz.

## İçindekiler
- [Özellikler](#özellikler)
- [Kurulum](#kurulum)
- [Kullanım](#kullanım)
- [Proje Yapısı](#proje-yapısı)
- [Katkıda Bulunma](#katkıda-bulunma)
- [Lisans](#lisans)

## Özellikler
- Araç kaydı ve plaka bilgilerinin tutulması
- Belirli kriterlere göre uygun araçların listelenmesi
- Rezervasyon işlemlerinin yapılması

## Kurulum

### Gereksinimler
- Java JDK 8 veya üstü
- Maven
- PostgreSQL

### Adımlar
1. Depoyu klonlayın:
    ```sh
    git clone https://github.com/kullanici-adi/arac-kiralama.git
    cd arac-kiralama
    ```

2. Veritabanını oluşturun ve gerekli tabloları oluşturun:
    ```sql
    CREATE DATABASE arac_kiralama;
    ```

3. Veritabanı bağlantı ayarlarını yapın:
    `src/main/resources/application.properties` dosyasındaki veritabanı bağlantı ayarlarını güncelleyin:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/arac_kiralama
    spring.datasource.username=postgres
    spring.datasource.password=sifre
    ```

4. Projeyi derleyin ve çalıştırın:
    ```sh
    mvn clean install
    mvn spring-boot:run
    ```

## Kullanım
- Uygulama çalıştırıldığında, Swing arayüzü üzerinden araçları kaydedebilir, uygun araçları listeleyebilir ve rezervasyon yapabilirsiniz.

## Proje Yapısı
Proje genel olarak dört ana modülden oluşur: `entity`, `dao`, `business` ve `view`. Bu modüller projenin farklı katmanlarını temsil eder ve her biri belirli bir görevi yerine getirir.

- **Entity Modülü**
  - Veritabanı tablolarını ve bu tablolara ait varlık nesnelerini tanımlar.
  - Kullanıcı, marka, model, araç, rezervasyon gibi temel entity nesnelerini içerir.
  - Bu nesneler arasındaki ilişkileri belirler.

- **DAO (Veri Erişim Nesnesi) Modülü**
  - Veritabanı erişimi ve işlemleri için bir arayüz sağlar.
  - Entity modülündeki nesnelerin veritabanına kaydedilmesi, güncellenmesi ve silinmesi süreçlerini yönetir.
  - Veritabanından veri alma işlemlerini gerçekleştirir.

- **Business Modülü**
  - İş mantığını yönetir ve uygulama içindeki temel işlemleri gerçekleştirir.
  - Fiyatlandırma ve araç kiralama hesaplamaları gibi iş mantığı operasyonlarını yönetir.
  - Veritabanı işlemleri için DAO modülü ile etkileşime girer.

- **View Modülü**
  - Kullanıcı arayüzünü (UI) yönetir ve kullanıcıyla etkileşimi sağlar.
  - Araç listesi ve kiralama ekranı gibi kullanıcı bilgilerini gösterir.
  - Kullanıcının girdiği bilgileri iş katmanına ileterek işlemleri başlatır.

Bu modüller, projenin katmanlı mimarisini oluşturarak kodun düzenli, modüler ve bakımı kolay olmasını sağlar. Entity nesnelerini, veritabanı erişimini, iş mantığını ve kullanıcı arayüzünü ayrı ayrı ele almak, geliştirme sürecini daha yönetilebilir hale getirir ve ek işlevler eklemeyi kolaylaştırır.

## Katkıda Bulunma
Katkıda bulunmak için:
1. Bu depoyu forklayın.
2. Bir özellik dalı oluşturun (`git checkout -b ozellik/AmazingFeature`).
3. Değişikliklerinizi commit edin (`git commit -m 'Add some AmazingFeature'`).
4. Dalınıza push edin (`git push origin ozellik/AmazingFeature`).
5. Bir Pull Request oluşturun.

## Lisans
Bu proje MIT Lisansı ile lisanslanmıştır. Daha fazla bilgi için `LICENSE` dosyasına bakın.

## Ek Bilgiler
Swing, Java programlama dilinde GUI (Graphical User Interface - Grafiksel Kullanıcı Arayüzü) bileşenlerini oluşturmak ve yönetmek için kullanılan bir kütüphanedir. Kullanıcı arayüzü oluşturmak için geniş bir bileşen seti sunar. Bu bileşenler arasında düğmeler, metin alanları, liste kutuları, tablolar, menüler ve çeşitli diğer arayüz öğeleri bulunur. Swing, bu bileşenleri düzenlemek, olayları işlemek ve kullanıcı etkileşimlerini yönetmek için gelişmiş bir olay tabanlı model sunar.

Swing, masaüstü uygulamaları geliştirmek için ideal bir seçenektir. Web uygulamaları için genellikle kullanılmaz, çünkü bu tür uygulamalar genellikle tarayıcılar aracılığıyla çalışır ve Swing, tarayıcılarla uyumlu değildir. Swing, masaüstü uygulamaları geliştirmek için kullanılan birçok Java IDE'si ve geliştirme ortamı tarafından desteklenir.

Projenin temel isterleri şunlardır:
- Firmadaki tüm araçların sisteme kaydedilmesi.
- Her araca ait plaka bilgilerinin tutulması.
- Sistem üzerinden belirli kriterlere göre uygun ve müsait araçların listelenmesi.
- Rezervasyon işleminin yapılması.

Yani, kullanıcılar belirli bir tarih aralığındaki araçların müsaitlik durumlarını kontrol edebilecekler. Örneğin, "Renault Clio" veya "Volkswagen Polo" gibi kriterlere göre arama yapabilecekler ve uygun araçlardan rezervasyon yapabilecekler.

Projenin amacı, hem araç takibini yapabilmek, hem de müsaitlik durumlarını görebilme ve iş yerini yönetebilme imkanı sağlamaktır.

Araç kiralama projesinin geliştirilmesi, Java katmanlı mimarisi ile yazılım geliştirme, veritabanı işlemlerini yapma, kullanıcı arayüzü oluşturma gibi konularda deneyim sahibi olmanızı sağlayacaktır.

Kolay gelsin, iyi öğrenmeler.
