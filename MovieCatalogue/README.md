**SUBSMISSION**

Anda sudah mempelajari beberapa hal tentang **Background Service**, salah satunya adalah bagaimana cara mendapatkan data dari internet menggunakan `AsyncTaskLoader`. Untuk lanjut mempelajari modul berikutnya, kirimkan proyek aplikasi dengan tema Anda perlu **Movie Catalogue (API)**. Jika pada submission sebelumnya Anda hanya memanfaatkan data lokal untuk ditampilkan, kali ini data yang akan ditampilkan adalah data dari API, yaitu The [Movie DB](https://www.themoviedb.org/).


---

## Kriteria

Fitur yang harus ada pada aplikasi:

1. **Daftar Film**
    - Terdapat 2 (dua) halaman yang menampilkan daftar film `(Movies dan Tv Show)`.
    - Menggunakan **Fragment** untuk menampung halaman `Movies` dan `Tv Show`.
    - Menggunakan **RecyclerView** untuk menampilkan daftar film.
    - Menggunakan **TabLayout, BottomNavigationView**, atau yang lainnya sebagai navigasi antara halaman `Movies` dan `Tv Show`.
    - Menampilkan indikator loading ketika data sedang dimuat.
2. **Detail Film**
    - Menampilkan poster dan informasi film pada halaman detail film.
    - Menggunakan `ConstraintLayout` untuk menyusun layout.
    - Menampilkan indikator loading ketika data sedang dimuat.
3. **Localization**
    - Aplikasi harus mendukung bahasa Indonesia dan bahasa Inggris.
4. **Configuration Changes**
    - Aplikasi harus bisa menjaga data yang sudah dimuat ketika terjadi pergantian orientasi dari `potrait` ke `landscape atau sebaliknya`.
    
Berikut kerangka tampilan yang bisa Anda gunakan sebagai referensi:

![alt text](https://dicodingacademy.blob.core.windows.net/academies/20190304105622bf155a6cc53796c48877ad20d603e099.png)

Kesempatan untuk submission Anda diterima, akan lebih besar jika:

- Menggunakan library pihak ketiga seperti `Retrofit, Fast Android Networking, dsb`.
- Menerapkan design pattern seperti `MVP, MVVM, Arch Component, dsb`.
- Aplikasi bisa memberikan pesan eror jika data tidak berhasil ditampilkan.
- Menuliskan kode dengan bersih.

Submission Anda akan ditolak jika:

- Tidak menggunakan `RecyclerView` untuk menampilkan daftar film.
- List yang ditampilkan kurang dari **10 item**.
- Tidak menggunakan `Fragment` untuk menampung halaman `Movies` dan `Tv Show`.
- Tidak menggunakan `TabLayout, BottomNavigationView`, atau yang lainnya sebagai navigasi antara `Movies` dan `Tv Show`.
- Tidak menggunakan `ConstraintLayout` untuk menyusun layout pada halaman detail film.
- Tidak terdapat indikator loading.
- Aplikasi tidak mendukung **bahasa Indonesia dan bahasa Inggris**.
- Aplikasi tidak bisa menjaga data yang sudah dimuat ketika terjadi pergantian orientasi dari potrait ke landscape atau sebaliknya.
- Menangani perubahan konfigurasi menggunakan tag `android:configChanges` pada berkas `AndroidManifest.xml`
- Poster tidak berhasil ditampilkan.
- Informasi yang ditampilkan pada daftar ataupun detail film, tidak relevan.
- Proyek tidak bisa di-build.
- Aplikasi force closed.
- Mengirimkan file selain proyek Android Studio.
- Mengirimkan proyek yang bukan karya sendiri.

## Resource

- Untuk mendapatkan API Key silakan ikuti tutorial pada tautan [berikut:](https://blog.dicoding.com/registrasi-testing-themoviedb-api/). 
- Gunakan endpoint berikut untuk mendapatkan data [Movies:](https://api.themoviedb.org/3/discover/movie?api_key={API KEY}&language=en-US)
- Gunakan endpoint berikut untuk mendapatkan data [Tv Show:](https://api.themoviedb.org/3/discover/tv?api_key={API KEY}&language=en-US)
- Gunakan url berikut untuk mendapatkan poster [film.](https://image.tmdb.org/t/p/{POSTER SIZE}/{POSTER FILENAME})

**POSTER SIZE** di atas adalah ukuran dari poster yang ingin didapatkan. Tersedia beberapa ukuran yang dapat digunakan `w92, w154, w185, w342, w500, w780,` dan `original`. Sedangkan **POSTER FILENAME** adalah path poster yang bisa didapatkan dari response JSON dengan key `poster_path`.

Contoh:

![alt text](https://image.tmdb.org/t/p/w185/kSBXou5Ac7vEqKd97wotJumyJvU.jpg)

- Penjelasan mengenai poster dapat Anda lihat pada tautan [berikut:](https://developers.themoviedb.org/3/configuration/get-api-configuration)

## Ketentuan

Beberapa ketentuan umum dari proyek aplikasi:

- Menggunakan `Android Studio`.
- Menggunakan bahasa pemrograman `Kotlin` atau `Java`.
- Mengirimkan pekerjaan Anda dalam bentuk **folder Proyek** Android Studio yang telah diarsipkan (`ZIP`).
- Tim penilai akan mengulas submission Anda dalam waktu `selambatnya 3 (tiga) hari kerja` (tidak termasuk Sabtu, Minggu, dan hari libur nasional).
- Tidak disarankan untuk melakukan submit berkali-kali karena akan memperlama proses penilaian.
- Anda akan mendapat notifikasi hasil pengumpulan submission Anda via email, atau Anda dapat mengecek status submission pada akun Dicoding Anda.

### Tips

Sebelum mengirimkan proyek, pastikan Anda sudah mengekspornya dengan benar.

Bagaimana cara ekspor proyek ke dalam berkas ZIP?

1. Pilih menu **File → Export to ZIP File...** pada Android Studio.
2. Pilih direktori penyimpanan dan klik **OK**.


### Ref Design

![alt text](https://cdn.dribbble.com/users/835462/screenshots/3550576/event-app-dribble-800x600-gif.gif)