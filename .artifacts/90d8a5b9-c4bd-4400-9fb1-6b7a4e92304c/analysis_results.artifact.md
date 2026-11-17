# Panduan Bedah Kode FinMates (Persiapan Sidang / Tanya Jawab Dosen)

Dokumen ini merangkum penjelasan lengkap mengenai arsitektur, fungsi, dan kegunaan setiap komponen di dalam aplikasi **FinMates** untuk membantu Anda menjelaskan proyek ini kepada dosen pengampu.

---

## 1. Arsitektur & Tech Stack Utama
* **Bahasa:** Kotlin (Idiomatik & Modern).
* **UI Framework:** **Jetpack Compose** (Deklaratif UI, Material 3).
* **Arsitektur:** **MVVM (Model-View-ViewModel)**. Pemisahan tanggung jawab (*separation of concerns*) antara logika bisnis/state (`ViewModel`) dan tampilan visual (`Composable`).
* **Navigasi:** **Jetpack Navigation Compose** (`NavHost` dan `NavController`) untuk berpindah antar halaman secara mulus.
* **Manajemen State:** Menggunakan Compose State (`mutableStateOf`, `mutableIntStateOf`) dan *State Hoisting*.

---

## 2. Penjelasan Per File & Komponen

### A. `MainActivity.kt` (Entry Point & Navigasi)
* **Fungsi:** Sebagai titik masuk utama aplikasi (*Activity* tunggal) yang mendeklarasikan tema (`UtsMobileAplikasionTheme`) dan mengatur seluruh rute navigasi antar layar.
* **Poin Penting untuk Dosen:**
  - Menerapkan arsitektur *Single-Activity* modern.
  - Menggunakan `NavHost` dengan `startDestination = Screen.Splash.route`.
  - Mengirimkan `navController` dan `viewModel` ke setiap layar agar navigasi dan data tetap sinkron.

### B. `FinMatesViewModel.kt` (Pusat Logika Bisnis & State)
* **Fungsi:** Menyimpan seluruh data aplikasi (*Transactions*, *Budgets*, *Mates*, dan *Settings*) agar aman dari perubahan konfigurasi (seperti rotasi layar).
* **Poin Penting untuk Dosen:**
  - Menggunakan *ViewModel* dari Android Jetpack.
  - Memiliki fungsi `addTransaction()` yang otomatis memotong/menambahkan budget per kategori serta memicu notifikasi peringatan jika budget mencapai ambang batas (`budgetAlertThreshold`).
  - Memiliki fungsi `addSplitBill()` untuk menghitung pembagian tagihan secara otomatis ke teman-teman.

### C. `FinMatesData.kt` (Model Data)
* **Fungsi:** Mendefinisikan struktur data (*Data Classes*) yang digunakan di seluruh aplikasi.
* **Poin Penting untuk Dosen:**
  - `TransactionItem`: Menyimpan data transaksi (id, judul, nominal, kategori, tanggal, tipe *INCOME/EXPENSE*).
  - `BudgetItem`: Menyimpan data budget per kategori (limit, terpakai, status lunas/warning).
  - `MateItem`: Menyimpan data utang/piutang dengan teman (*split bill*).

---

### D. Kumpulan Layar UI (*UI Screens*)

#### 1. `SplashOnboardingScreen.kt` (Layar Awal & Onboarding)
* **Fungsi:** Menampilkan logo FinMates, kartu saldo & *split bill* yang dimiringkan dan diberi **animasi melayang (*floating animation*)** menggunakan `rememberInfiniteTransition`, serta tombol "Mulai".
* **Poin Penting untuk Dosen:** Menunjukkan kemampuan animasi kustom di Jetpack Compose.

#### 2. `BerandaScreen.kt` (Dashboard Utama)
* **Fungsi:** Menampilkan sapaan pengguna, kartu "Sisa uang bulan ini", indikator "Aman per hari" (dihitung otomatis dari sisa hari dalam bulan), tombol aksi cepat, ringkasan tagihan masuk/keluar, dan daftar transaksi terbaru.

#### 3. `TambahTransaksiScreen.kt` (Form Input Transaksi)
* **Fungsi:** Form untuk mencatat pengeluaran atau pemasukan baru.
* **Poin Penting untuk Dosen:** Dilengkapi *Segmented Control* (Pengeluaran/Pemasukan), *Quick Chips* (`10rb`, `25rb`, `50rb`, `100rb`) untuk pengisian cepat, dan grid kategori berikon.

#### 4. `RiwayatTransaksiScreen.kt` (Riwayat & Pencarian)
* **Fungsi:** Menampilkan seluruh riwayat transaksi yang dikelompokkan berdasarkan tanggal ("Hari ini", "Kemarin"), dilengkapi **kolom pencarian real-time** dan tombol filter (*Semua, Pengeluaran, Pemasukan*).

#### 5. `BudgetScreen.kt` (Manajemen Anggaran Bulanan)
* **Fungsi:** Memantau pengeluaran per kategori (Makan, Transport, Hiburan, Kos) menggunakan *LinearProgressIndicator*, persentase total, dan badge peringatan otomatis (*"Hampir habis"*).

#### 6. `MatesScreen.kt` & `BuatSplitBillScreen.kt` (Mates & Split Bill)
* **Fungsi:** Mengelola utang-piutang antar teman dan kalkulator pembagian tagihan rata (*Bagi rata* / *Custom*) dengan daftar partisipan ber-avatar warna-warni.

#### 7. `ProfilScreen.kt` (Pengaturan Akun & Keamanan)
* **Fungsi:** Pengaturan aplikasi seperti login biometrik, notifikasi, ambang batas peringatan budget (*threshold*), dan tombol keluar (*logout*).

#### 8. `BottomNavBar.kt` (Navigasi Bawah)
* **Fungsi:** Menu navigasi utama di bagian bawah dengan **tombol tambah (`+`) hijau melingkar di tengah** yang menonjol untuk akses cepat pencatatan transaksi.

---

## 3. Tips Saat Ditanya Dosen
1. **Kenapa pakai Jetpack Compose?** *"Karena deklaratif, lebih sedikit baris kode dibanding XML, UI otomatis merender ulang saat state berubah, dan merupakan standar modern dari Google."*
2. **Bagaimana data dikelola?** *"Menggunakan MVVM. UI hanya bertugas merender state dari ViewModel, sedangkan logika bisnis seperti penambahan transaksi dan perhitungan budget di-handle di ViewModel."*
3. **Bagaimana navigasi bekerja?** *"Menggunakan Navigation Compose dengan NavHost dan NavController untuk mengatur perpindahan antar layar secara aman (*type-safe*)."*
