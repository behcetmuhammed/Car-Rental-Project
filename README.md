# Araç Kiralama Uygulaması

Bu proje, Java ve Swing kullanılarak geliştirilen bir araç kiralama uygulamasıdır. Uygulama, araçların kaydedilmesi, müsaitlik durumlarının kontrol edilmesi ve rezervasyon işlemlerinin yapılmasını sağlar.

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
- **Entity**: Veritabanı tablolarını temsil eden sınıflar.
- **DAO**: Veritabanı erişim ve işlemleri için sınıflar.
- **Business**: İş mantığını yöneten sınıflar.
- **View**: Kullanıcı arayüzünü yöneten sınıflar.

## Katkıda Bulunma
Katkıda bulunmak için:
1. Bu depoyu forklayın.
2. Bir özellik dalı oluşturun (`git checkout -b ozellik/AmazingFeature`).
3. Değişikliklerinizi commit edin (`git commit -m 'Add some AmazingFeature'`).
4. Dalınıza push edin (`git push origin ozellik/AmazingFeature`).
5. Bir Pull Request oluşturun.

## Lisans
Bu proje MIT Lisansı ile lisanslanmıştır. Daha fazla bilgi için `LICENSE` dosyasına bakın.
