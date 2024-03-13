package gr.novidea.museumapp

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color

object PreferenceManager {
    private const val PREF_NAME = "MyPrefs"

    fun saveColor(context: Context, hallNumber: Int, color: Int) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putInt("hall_$hallNumber _color", color).apply()
    }

    fun getColor(context: Context, hallNumber: Int): Int {
        val prefs: SharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getInt("hall_$hallNumber _color", Color.WHITE)
    }

    fun saveLightState(context: Context, hallNumber: Int, lightOff: Boolean) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean("hall_$hallNumber _lightOff", lightOff).apply()
    }

    fun getLightState(context: Context, hallNumber: Int): Boolean {
        val prefs: SharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean("hall_$hallNumber _lightOff", false)
    }

    fun saveDoorState(context: Context, hallNumber: Int, doorsOpen: Boolean) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean("hall_$hallNumber _doorsOpen", doorsOpen).apply()
    }

    fun getDoorState(context: Context, hallNumber: Int): Boolean {
        val prefs: SharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean("hall_$hallNumber _doorsOpen", false)
    }

    fun saveUnavailableSeats(context: Context, seats: List<Int>) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val seatsString = seats.joinToString(",")
        prefs.edit().putString("_unavailableSeats", seatsString).apply()
    }

    fun getUnavailableSeats(context: Context): List<Int> {
        val prefs: SharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val seatsString = prefs.getString("_unavailableSeats", "") ?: ""
        return if (seatsString.isNotEmpty()) {
            seatsString.split(",").mapNotNull { it.toIntOrNull() }
        } else {
            emptyList()
        }
    }
}