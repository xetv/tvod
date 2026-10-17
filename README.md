# TVOD Picker

Aplikasi Android TV (Kotlin) untuk memilih aplikasi lewat dialog pop-up saat tombol TVOD ditekan.

## Cara pakai
1. Upload project ke GitHub, buka tab **Actions**, tunggu build selesai, unduh APK dari **Artifacts**.
2. Install APK ke TV (`adb install app-debug.apk`).
3. Buka **TVOD Picker** dari menu TV, centang aplikasi yang diinginkan.
4. Di Button Mapper: pilih tombol TVOD, aksi Aplikasi/Activity/Shortcut, lalu pilih
   **TVOD Picker > PickerActivity** (atau intent action `com.example.tvodpicker.SHOW`).
5. Tekan tombol TVOD: dialog muncul.

## Struktur
- `SettingsActivity`: daftar centang (muncul di menu TV).
- `PickerActivity`: dialog translucent (target Button Mapper).
