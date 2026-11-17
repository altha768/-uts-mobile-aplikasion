# Panduan Penjelasan Topik Proyek FinMates (Progress Report & Discussion)

Dokumen ini dirancang khusus untuk membantu Anda dan anggota kelompok memilih serta mempresentasikan 2 topik pilihan untuk sesi *Group Project Progress Report and Discussion* (Minggu 11 & 12).

Setiap topik di bawah ini sudah disesuaikan secara langsung dengan kode yang ada di proyek **FinMates**.

---

## 1. Compose Layout
* **Penjelasan:**
  Aplikasi FinMates dibangun menggunakan berbagai fungsi *Composable* dari Jetpack Compose untuk menyusun tata letak UI:
  - **`Column` & `Row`**: Digunakan untuk menyusun elemen secara vertikal (misal: header dan daftar transaksi) atau horizontal (misal: tombol aksi cepat, baris item riwayat).
  - **`LazyColumn`**: Digunakan pada `BerandaScreen`, `BudgetScreen`, `MatesScreen`, dan `RiwayatTransaksiScreen` untuk merender daftar transaksi/budget yang panjang secara efisien (*recycling items*).
  - **`Card` & `Surface`**: Berfungsi sebagai wadah kartu (*container*) dengan sudut melengkung (*rounded corners*) dan bayangan (*elevation*), seperti pada kartu sisa uang bulanan dan rincian split bill.
  - **Modifier `graphicsLayer(rotationZ = ...)`**: Digunakan pada layar *SplashOnboarding* untuk memiringkan kartu secara estetik.

## 2. UI Theming
* **Penjelasan:**
  Theming diimplementasikan secara terpusat mengikuti standar Material 3:
  - **Color (`Color.kt`)**: Mendefinisikan palet warna kustom FinMates, seperti hijau tua emerald (`FinGreenPrimary = Color(0xFF1B4D3E)`), hijau muda lime (`#C6F454`), serta warna pendukung untuk status sukses/error.
  - **Theme (`Theme.kt`)**: Fungsi `UtsMobileAplikasionTheme` membungkus seluruh aplikasi dan menerapkan `lightColorScheme` / `darkColorScheme` yang terhubung ke `MaterialTheme`.
  - **Typography (`Type.kt`)**: Mengatur gaya teks mulai dari tajuk utama (*Header*) hingga teks kecil (*caption*) dengan konsisten di seluruh layar.

## 3. State
* **Penjelasan:**
  State digunakan untuk mereaktifkan UI agar berubah secara instan saat pengguna berinteraksi:
  - **ViewModel State**: `FinMatesViewModel` menyimpan state global seperti `transactions`, `budgets`, dan `mates` menggunakan `mutableStateOf(...)`. Ketika data bertambah atau dihapus, UI yang mengobservasi state ini akan otomatis memperbarui tampilannya.
  - **Local UI State**: Menggunakan `remember { mutableStateOf(...) }` atau `remember { mutableIntStateOf(...) }` di dalam layar (seperti `TambahTransaksiScreen` dan `BuatSplitBillScreen`) untuk menampung input form sementara (nominal, kategori yang dipilih, judul tagihan) sebelum disimpan.

## 4. Navigation
* **Penjelasan:**
  Navigasi diimplementasikan menggunakan **Jetpack Navigation Compose**:
  - **`NavController` & `NavHost`**: Dideklarasikan di `MainActivity.kt` untuk mengontrol perpindahan antar layar.
  - **`Screen` Sealed Class**: Mendefinisikan seluruh rute secara aman (*type-safe*), seperti `Screen.Splash.route`, `Screen.Beranda.route`, `Screen.TambahTransaksi.route`, hingga rute dinamis berparameter seperti `Screen.DetailTransaksi.createRoute(id)`.
  - **Bottom Navigation (`FinMatesBottomNav`)**: Menyediakan navigasi tab bawah dengan tombol tambah (`+`) hijau di tengah untuk akses cepat ke form transaksi.

## 5. MVVM (Model-View-ViewModel)
* **Penjelasan:**
  FinMates menerapkan arsitektur MVVM secara ketat:
  - **Model**: Berisi struktur data (`TransactionItem`, `BudgetItem`, `MateItem`) dan data dummy awal (`DummyData.kt`).
  - **View**: Komponen *Composable* di package `ui.screens/` yang murni bertugas merender UI dan mendengarkan *event* dari pengguna.
  - **ViewModel**: `FinMatesViewModel` mewarisi `ViewModel()`. Menyimpan state dan logika bisnis (misal: fungsi `addTransaction()` yang tidak hanya menambah list transaksi, tetapi juga otomatis mengupdate budget dan memicu notifikasi).
  - **Rationale**: Pemisahan ini membuat kode lebih bersih, mudah diuji (*testable*), dan state aman dari kehancuran saat terjadi perubahan konfigurasi seperti rotasi layar.

## 6. Ktor, Kotlinx Serialization, Coil
* **Penjelasan:**
  Dalam cakupan arsitektur *client-side prototype* saat ini, FinMates berfokus pada manajemen state lokal dan *in-memory data flow* yang dikelola langsung melalui ViewModel dan data class Kotlin, sehingga library jaringan eksternal belum diaktifkan dalam repositori ini.

## 7. Native Feature
* **Penjelasan:**
  Fitur native Android yang diintegrasikan meliput:
  - **Snackbar Notifications**: Menggunakan `SnackbarHost` dan `SnackbarHostState` di `MainActivity.kt` untuk memunculkan *push notification* pop-up secara asinkron saat budget hampir habis atau saat tagihan *split bill* berhasil dikirim.
  - **Edge-to-Edge**: Mengaktifkan tampilan layar penuh (*edge-to-edge display*) melalui `enableEdgeToEdge()` di `MainActivity.kt`.
  - **Biometric Security Setting**: Opsi pengaturan keamanan biometrik di `ProfilScreen.kt`.

## 8. Database
* **Penjelasan:**
  FinMates menggunakan pendekatan penyimpanan state reaktif berbasis *in-memory mutable lists* yang diinisialisasi melalui `FinMatesViewModel` dan `DummyData.kt`. Pendekatan ini memastikan operasi CRUD (Create, Read, Delete transaksi maupun *split bill*) berjalan sangat cepat dan responsif secara *real-time* tanpa overhead konfigurasi database persisten yang kompleks untuk skala prototipe UTS/UAS.

## 9. Auth
* **Penjelasan:**
  Mekanisme autentikasi diimplementasikan melalui halaman `LoginScreen.kt` dan `RegisterScreen.kt`. Pengguna dapat masuk menggunakan email dan sandi, serta opsi autentikasi biometrik. Setelah berhasil, aplikasi menavigasikan pengguna langsung ke halaman `BerandaScreen` dan menyimpan sesi profil aktif di ViewModel (`currentUserEmail`, `currentUserName`).

## 10. Background Process
* **Penjelasan:**
  Proses latar belakang (*background-like reactivity*) ditangani secara reaktif menggunakan Kotlin Coroutines dan `LaunchedEffect` di `MainActivity.kt`. `LaunchedEffect` mengobservasi perubahan pada `lastNotificationMessage` di ViewModel dan secara asinkron memicu *Snackbar* pemberitahuan begitu batas budget tercapai.
