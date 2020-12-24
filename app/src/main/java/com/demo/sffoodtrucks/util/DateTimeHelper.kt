package com.demo.sffoodtrucks.util

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId

class DateTimeHelper {

    private val ZONE_ID = "America/Los_Angeles"

    private val curHour: Int
    private val curDay: String

    init {
         curDay =  LocalDate.now(ZoneId.of(ZONE_ID)).getDayOfWeek().toString()
         curHour=  LocalDateTime.now(ZoneId.of(ZONE_ID)).hour
    }

    fun getCurHour(): Int {
        return  curHour
    }

    fun getCurDay(): String {
        return  curDay
    }

}
