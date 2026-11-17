# FinMates UI/UX Revision Implementation Plan

This plan outlines the implementation of the UI/UX revision for FinMates based on the provided design reference across all 6 main screens (Awal/Splash, Beranda, Tambah Transaksi, Budget, Mates, and Split Bill).

## User Review Required

> [!IMPORTANT]
> The UI components will be refined to match the exact visual layout, color palette (deep forest greens, light cream cards, crisp typography), and interactive elements shown in the design reference.

## Proposed Changes

### 1. Splash & Onboarding Screen (`SplashOnboardingScreen.kt`)
- **[MODIFY]** Update background colors to deep forest green matching the design.
- **[MODIFY]** Add preview card showing current balance ("Rp 3.183.000", safe per day) and active split bill card ("Listrik kos Rp 20.000/orang" with participant avatar bubbles).
- **[MODIFY]** Update tagline text ("Kelola uang, bareng teman. Catat pengeluaran, pantau budget, dan bagi tagihan kos tanpa drama.") and styling of "Mulai" button and login footer.

### 2. Beranda Screen (`BerandaScreen.kt`)
- **[MODIFY]** Update header to show greeting ("Halo, Altha") and date ("Selasa, 3 Nov").
- **[MODIFY]** Refine "Sisa uang bulan ini" card with safe daily allowance and countdown ("20 hari lagi").
- **[MODIFY]** Ensure 4 quick action buttons (Catat, Split Bill, Budget, Mates) with icons.
- **[MODIFY]** Update summary cards ("Kamu ditagih" & "Kamu menagih") and transaction item rows.

### 3. Tambah Transaksi Screen (`TambahTransaksiScreen.kt`)
- **[MODIFY]** Add segmented control for Pengeluaran / Pemasukan.
- **[MODIFY]** Refine nominal display ("Rp 25.000") and quick amount chips (10rb, 25rb, 50rb, 100rb).
- **[MODIFY]** Update category grid with icons (Makan, Transport, Hiburan, Kos, Belanja, Lainnya).
- **[MODIFY]** Refine date selector, note input field, and "Simpan" button.

### 4. Budget Screen (`BudgetScreen.kt`)
- **[MODIFY]** Update header with month ("November 2026") and "+ Kategori" button.
- **[MODIFY]** Add progress card (70%, "Terpakai Rp 1.055.000 dari Rp 1.500.000").
- **[MODIFY]** Refine budget category cards with warning badges ("Hampir habis, sisa Rp 20.000", "Lunas").

### 5. Mates Screen (`MatesScreen.kt`)
- **[MODIFY]** Add top summary cards for "Kamu ditagih" (with "Bayar" button) and "Kamu menagih" (with "Ingatkan" button).
- **[MODIFY]** Refine "Aktivitas" section with item rows and amounts.
- **[MODIFY]** Add prominent "Bagi Tagihan" button at the bottom.

### 6. Split Bill Screen (`BuatSplitBillScreen.kt`)
- **[MODIFY]** Update header with back button and title "Buat Split Bill".
- **[MODIFY]** Refine "Judul tagihan" ("Listrik kos") and "Total nominal" ("Rp 80.000") input cards.
- **[MODIFY]** Add "Bagi rata" / "Custom" toggle and calculation breakdown ("4 orang · Rp 20.000/orang").
- **[MODIFY]** Refine participant list with avatar circles (A, B, D, R) and individual amounts.
- **[MODIFY]** Add "Kirim Tagihan" button at the bottom.

## Verification Plan

### Automated Tests
- Run existing unit tests: `./gradlew :app:testDebugUnitTest`
- Build app: `./gradlew :app:assembleDebug`

### Manual Verification
- Deploy to emulator / connected device and visually inspect all screens against the reference design.
