package com.example.tvodpicker

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat

class PickerActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val selected = AppRepository.getSelected(this)
        val apps = AppRepository.loadInstalled(this).filter { it.packageName in selected }

        // Belum ada pilihan: langsung ke pengaturan
        if (apps.isEmpty()) {
            Toast.makeText(this, R.string.empty_hint, Toast.LENGTH_LONG).show()
            startActivity(Intent(this, SettingsActivity::class.java))
            finish()
            return
        }

        // Item terakhir: pintu masuk ke pengaturan
        val settingsItem = AppInfo(
            label = getString(R.string.open_settings),
            packageName = packageName,
            icon = ContextCompat.getDrawable(this, android.R.drawable.ic_menu_preferences)
                ?: ContextCompat.getDrawable(this, R.drawable.ic_launcher)!!,
            launchIntent = Intent(this, SettingsActivity::class.java)
        )
        val items = apps + settingsItem

        val dialog = AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Dialog_Alert)
            .setTitle(R.string.picker_title)
            .setAdapter(AppAdapter(items, showCheckbox = false)) { _, which ->
                try {
                    startActivity(items[which].launchIntent)
                } catch (e: Exception) {
                    Toast.makeText(this, R.string.launch_failed, Toast.LENGTH_SHORT).show()
                }
            }
            .create()
        dialog.setOnDismissListener { finish() }
        dialog.show()
    }
}
