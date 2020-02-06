**SUBSMISSION**

Selamat, Anda telah berada di akhir pembelajaran dalam akademi ini. Anda telah belajar banyak tentang pengembangan aplikasi berbasis Android. Untuk bisa lulus dari akademi ini Anda akan memodifikasi proyek submission **Aplikasi Movie Catalogue (Local Storage)** dengan menambahkan beberapa fitur. Anda bisa memanfaatkan beberapa materi yang telah dipelajari. Selamat berkreasi! 


---

## Kriteria

Fitur yang harus ada pada aplikasi:

1. **Pencarian film**
    - Pengguna dapat melakukan pencarian `Movies`.
    - Pengguna dapat melakukan pencarian `Tv Show`.
2. **Widget**
    - Pengguna dapat menampilkan widget dari film favorite ke halaman utama smartphone.
    - Tipe widget yang diterapkan adalah `Stack Widget`.
3. **Reminder**
    - Daily Reminder, mengirimkan notifikasi ke pengguna untuk kembali ke `Aplikasi Movie Catalogue`. Daily reminder harus selalu berjalan tiap jam 7 pagi.
    - Release Today Reminder, mengirimkan notifikasi ke pengguna semua film yang rilis hari ini `(wajib menggunakan endpoint seperti yang telah disediakan pada bagian Resources di bawah)`. Release reminder harus selalu berjalan tiap jam 8 pagi.
    - Terdapat halaman pengaturan untuk mengaktifkan dan menonaktifkan reminder.
4. **Aplikasi Favorite**
    - Membuat aplikasi atau modul baru yang menampilkan daftar film favorite.
    - Menggunakan `Content Provider` sebagai mekanisme untuk mengakses data dari satu aplikasi ke aplikasi lain.
    
Berikut kerangka tampilan yang bisa Anda gunakan sebagai referensi:

![alt text](https://dicodingacademy.blob.core.windows.net/academies/201902180856320073cc3b59071a00e26d5be08eecc67d.png)

Kesempatan untuk submission Anda diterima, akan lebih besar jika:

- Notifikasi dapat berjalan pada perangkat `Oreo dan setelahnya`
- Data pada `widget` dapat diperbarui secara otomatis ketika terdapat perubahan pada data favorite.
- Menggunakan `SearchView` pada fitur pencarian film.
- Menggunakan library penyimpanan lokal pihak ketiga seperti Room, Realm, dsb.
- Menggunakan library pihak ketiga seperti Retrofit, Fast Android Networking, dsb.
- Menerapkan design pattern seperti MVP, MVVM, Arch Component, dsb.
- Aplikasi bisa memberikan pesan eror jika data tidak berhasil ditampilkan.
- Menuliskan kode dengan bersih.

Submission Anda akan ditolak jika:

- Fitur pencarian tidak berjalan dengan baik.
- Fitur pencarian tidak memanfaatkan endpoint dari TheMovieDB.
- Fitur release today reminder tidak memanfaatkan endpoint dari TheMovieDB.
- Notifikasi reminder tidak dapat ditampilkan pada device Oreo dan di atasnya.
- Tidak dapat menampilkan data favorite ke dalam Stack Widget.
- Tidak terdapat aplikasi atau modul baru yang menampilkan data favorite.
- Tidak menerapkan Content Provider sebagai mekanisme untuk mengakses data dari satu aplikasi ke aplikasi lain.
- Tidak Mempertahankan semua fitur aplikasi dan komponen yang digunakan pada aplikasi Movie Catalogue (Local Storage).
- Informasi yang ditampilkan pada daftar ataupun detail film, tidak relevan.
- Aplikasi force closed.
- Project tidak bisa di-build.
- Mengirimkan file selain proyek Android Studio.
- Mengirimkan proyek yang bukan karya sendiri.

## Resource

- Untuk melakukan pencarian, gunakan endpoint berikut [Movies]{https://api.themoviedb.org/3/search/movie?api_key={API KEY}&language=en-US&query={MOVIE NAME}}
- Untuk melakukan pencarian, gunakan endpoint berikut [Tv Show]{https://api.themoviedb.org/3/search/tv?api_key={API KEY}&language=en-US&query={TV SHOW NAME}}
- Contoh penggunaan, ` https://api.themoviedb.org/3/search/movie?api_key=123456789&language=en-US&query=Avenger`
- Untuk mendapatkan film yang rilis hari ini, gunakan endpoint berikut [Movies release]{https://api.themoviedb.org/3/discover/movie?api_key={API KEY}&primary_release_date.gte={TODAY DATE}&primary_release_date.lte={TODAY DATE}}
- Contoh penggunaan, `https://api.themoviedb.org/3/discover/movie?api_key=123456789&primary_release_date.gte=2019-01-31&primary_release_date.lte=2019-01-31`

**Catatan** Pastikan format tanggal yang kalian gunakan benar. Format tanggal yang digunakan adalah **"yyyy-MM-dd"**.

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