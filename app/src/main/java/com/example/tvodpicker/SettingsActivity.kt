package com.example.tvodpicker

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ListView

class SettingsActivity : Activity() {

    private lateinit var list: ListView
    private lateinit var adapter: AppAdapter
    private val checked = mutableSetOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        checked.addAll(AppRepository.getSelected(this))
        list = findViewById(R.id.list)
        loadAdapter()

        // OK / klik: centang
        list.setOnItemClickListener { _, _, position, _ ->
            adapter.toggle(position)
            AppRepository.setSelected(this, checked)
        }
        // Tahan OK / klik lama: ganti nama
        list.setOnItemLongClickListener { _, _, position, _ ->
            showRenameDialog(adapter.getItem(position))
            true
        }
        list.requestFocus()
    }

    private fun loadAdapter() {
        adapter = AppAdapter(AppRepository.loadInstalled(this), showCheckbox = true, checked = checked)
        list.adapter = adapter
    }

    private fun showRenameDialog(app: AppInfo) {
        val input = EditText(this).apply {
            setText(app.label)
            hint = app.originalLabel
            setSingleLine()
            setSelectAllOnFocus(true)
        }
        val pad = (24 * resources.displayMetrics.density).toInt()
        val box = FrameLayout(this).apply {
            setPadding(pad, pad / 2, pad, 0)
            addView(input)
        }
        AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Dialog_Alert)
            .setTitle(getString(R.string.rename_title, app.originalLabel))
            .setView(box)
            .setPositiveButton(R.string.save) { _, _ ->
                AppRepository.setCustomLabel(this, app.packageName, input.text.toString())
                loadAdapter()
            }
            .setNeutralButton(R.string.reset) { _, _ ->
                AppRepository.setCustomLabel(this, app.packageName, null)
                loadAdapter()
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }
}
