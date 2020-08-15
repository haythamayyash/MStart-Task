package com.haythamayyash.mstarttask.common.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class TimeManager {
    fun getUtcTime(): Long {
        val calendar: Calendar = Calendar.getInstance()
        val utcOffset: Int = calendar.get(Calendar.ZONE_OFFSET) + calendar.get(Calendar.DST_OFFSET)
        return calendar.timeInMillis + utcOffset
    }

    fun formatDate(timeInMillis: Long, newFormat: String): String? {
        val dateFormat: DateFormat = SimpleDateFormat(newFormat, Locale.getDefault())
        return dateFormat.format(timeInMillis)
    }
}