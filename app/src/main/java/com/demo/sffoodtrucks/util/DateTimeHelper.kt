package com.demo.sffoodtrucks.util

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId

/* Written by Nathan N 12/27/2020
Class to help with getting current time and date, covers from Android Lolipop and beyond.
Uses Jake Wharton library 'org.threeten.bp'
*/

class DateTimeHelper {



    private val curHour: Int
    private val curDay: String

    init {
        curDay = LocalDate.now(ZoneId.of(Constants.ZONE_ID)).getDayOfWeek().toString()
        curHour = LocalDateTime.now(ZoneId.of(Constants.ZONE_ID)).hour
    }

    fun getCurHour(): Int {
        return curHour
    }

    fun getCurDay(): String {
        return curDay
    }

}
