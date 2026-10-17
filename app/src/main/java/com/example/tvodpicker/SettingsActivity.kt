package com.example.tvodpicker

import android.app.Activity
import android.os.Bundle
import android.widget.ListView

class SettingsActivity : Activity() {

    private lateinit var adapter: AppAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val apps = AppRepository.loadInstalled(this)
        adapter = AppAdapter(apps, showCheckbox = true,
            checked = AppRepository.getSelected(this).toMutableSet())

        val list = findViewById<ListView>(R.id.list)
        list.adapter = adapter
        list.setOnItemClickListener { _, _, position, _ ->
            adapter.toggle(position)
            AppRepository.setSelected(this, adapter.checked)
        }
        list.requestFocus()
    }
}
