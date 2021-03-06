package no.usn.mob3000_disky.ui

import android.text.format.DateUtils
import android.util.Log
import org.ocpsoft.prettytime.PrettyTime
import java.sql.Timestamp
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Utils {
    companion object{

        fun getTimeSince(timestamp: String): String {
            //2021-12-01T23:57:09.000+00:00
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
            sdf.timeZone = TimeZone.getTimeZone("GMT")
            var time = 0L
            try {
                time = sdf.parse(timestamp).time
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            val prettyTime = PrettyTime(Locale.getDefault())
            return prettyTime.format(Date(time))
        }

        //2021-11-17T17:03:07.000+00:00
        fun getDate(timestamp: String): Date{
            var dateAndTime = timestamp.split("T")
            var datePart = dateAndTime[0].split("-")
            var time = dateAndTime[1].split(".")[0].split(":")
            var year = datePart[0].toInt()
            var month = datePart[1].toInt()
            var day = datePart[2].toInt()
            var hours = time[0].toInt()
            var min = time[1].toInt()
            var sec = time[2].toInt()
            var date = Date(datePart[0].toInt(),datePart[1].toInt(),datePart[2].toInt(),time[0].toInt() + 2,time[1].toInt(),time[2].toInt())
            return date
        }
        @Deprecated("Use getTimeSince(String timeStamp) instead. Will return correct time since value based on locale")
        fun Date.getTimeAgo(): String {
            val calendar = Calendar.getInstance()
            calendar.time = this

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val currentCalendar = Calendar.getInstance()

            val currentYear = currentCalendar.get(Calendar.YEAR)
            val currentMonth = currentCalendar.get(Calendar.MONTH)
            val currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH)
            val currentHour = currentCalendar.get(Calendar.HOUR_OF_DAY)
            val currentMinute = currentCalendar.get(Calendar.MINUTE)

            return if (year < currentYear ) {
                val interval = currentYear - year
                if (interval == 1) "$interval ??r siden" else "$interval ??r siden"
            } else if (month < currentMonth) {
                val interval = currentMonth - month
                if (interval == 1) "$interval m??ned siden" else "$interval m??neder siden"
            } else  if (day < currentDay) {
                val interval = currentDay - day
                if (interval == 1) "$interval dag siden" else "$interval dager siden"
            } else if (hour < currentHour) {
                val interval = currentHour - hour
                if (interval == 1) "$interval time siden" else "$interval time siden"
            } else if (minute < currentMinute) {
                val interval = currentMinute - minute
                if (interval == 1) "$interval minutt siden" else "$interval minutter siden"
            } else {
                "akkurat n??"
            }
        }
    }
}