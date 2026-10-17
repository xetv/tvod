package com.example.tvodpicker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView

class AppAdapter(
    private val apps: List<AppInfo>,
    private val showCheckbox: Boolean,
    val checked: MutableSet<String> = mutableSetOf()
) : BaseAdapter() {

    override fun getCount() = apps.size
    override fun getItem(position: Int) = apps[position]
    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(parent.context)
            .inflate(R.layout.item_app, parent, false)
        val app = apps[position]
        view.findViewById<ImageView>(R.id.icon).setImageDrawable(app.icon)
        view.findViewById<TextView>(R.id.label).text = app.label
        val box = view.findViewById<CheckBox>(R.id.check)
        box.visibility = if (showCheckbox) View.VISIBLE else View.GONE
        box.isChecked = checked.contains(app.packageName)
        return view
    }

    fun toggle(position: Int): Boolean {
        val pkg = apps[position].packageName
        val nowChecked = if (checked.contains(pkg)) { checked.remove(pkg); false } else { checked.add(pkg); true }
        notifyDataSetChanged()
        return nowChecked
    }
}
