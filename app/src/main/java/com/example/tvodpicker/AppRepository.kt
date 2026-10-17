package com.example.tvodpicker

import android.content.Context
import android.content.Intent

object AppRepository {
    private const val PREFS = "tvod_picker_prefs"
    private const val KEY_SELECTED = "selected_packages"

    /** Semua aplikasi yang bisa dibuka (Leanback diprioritaskan), kecuali aplikasi ini. */
    fun loadInstalled(context: Context): List<AppInfo> {
        val pm = context.packageManager
        val result = LinkedHashMap<String, AppInfo>()
        val categories = listOf(Intent.CATEGORY_LEANBACK_LAUNCHER, Intent.CATEGORY_LAUNCHER)
        for (category in categories) {
            val query = Intent(Intent.ACTION_MAIN).addCategory(category)
            for (ri in pm.queryIntentActivities(query, 0)) {
                val pkg = ri.activityInfo.packageName
                if (pkg == context.packageName || result.containsKey(pkg)) continue
                val launch = pm.getLeanbackLaunchIntentForPackage(pkg)
                    ?: pm.getLaunchIntentForPackage(pkg)
                    ?: continue
                launch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                result[pkg] = AppInfo(ri.loadLabel(pm).toString(), pkg, ri.loadIcon(pm), launch)
            }
        }
        return result.values.sortedBy { it.label.lowercase() }
    }

    fun getSelected(context: Context): Set<String> =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getStringSet(KEY_SELECTED, emptySet())?.toSet() ?: emptySet()

    fun setSelected(context: Context, packages: Set<String>) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit().putStringSet(KEY_SELECTED, packages.toSet()).apply()
    }
}
