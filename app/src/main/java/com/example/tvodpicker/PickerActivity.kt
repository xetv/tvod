package com.example.tvodpicker

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast

class PickerActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val selected = AppRepository.getSelected(this)
        val apps = AppRepository.loadInstalled(this).filter { it.packageName in selected }

        if (apps.isEmpty()) {
            Toast.makeText(this, R.string.empty_hint, Toast.LENGTH_LONG).show()
            startActivity(Intent(this, SettingsActivity::class.java))
            finish()
            return
        }

        val dialog = AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Dialog_Alert)
            .setTitle(R.string.picker_title)
            .setAdapter(AppAdapter(apps, showCheckbox = false)) { _, which ->
                try {
                    startActivity(apps[which].launchIntent)
                } catch (e: Exception) {
                    Toast.makeText(this, R.string.launch_failed, Toast.LENGTH_SHORT).show()
                }
            }
            .create()
        dialog.setOnDismissListener { finish() }
        dialog.show()
    }
}
