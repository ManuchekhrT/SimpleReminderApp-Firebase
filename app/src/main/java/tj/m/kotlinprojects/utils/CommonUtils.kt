package tj.m.kotlinprojects.utils

import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.util.Log
import androidx.appcompat.app.AlertDialog
import java.text.SimpleDateFormat
import java.util.*


object CommonUtils {

    var monthName = arrayOf(
        "Jan.",
        "Feb.",
        "Mar.",
        "Apr.",
        "May",
        "June",
        "July",
        "Aug.",
        "Sep.",
        "Oct.",
        "Nov.",
        "Dec."
    )

    const val MIN_MINUTE = 0
    const val MAX_MIINUTE = 59
    const val MIN_HOUR = 0
    const val MAX_HOUR = 23

    fun getMonthName(month: String): HashMap<String, String> {
        val map = HashMap<String, String>()
        map["Jan"] = "янв."
        map["Feb"] = "фев."
        map["Mar"] = "март"
        map["Apr"] = "апр."
        map["May"] = "май"
        map["June"] = "июнь"
        map["July"] = "июль"
        map["Aug"] = "авг."
        map["Sep"] = "сен."
        map["Oct"] = "окт."
        map["Nov"] = "нояб."
        map["Dec"] = "дек."
        return map
    }

    fun getDatesBetweenUsingJava7(
        startDate: Date, endDate: Date
    ): List<Date> {
        val datesInRange = ArrayList<Date>()
        val calendar = GregorianCalendar()
        calendar.time = startDate

        val endCalendar = GregorianCalendar()
        endCalendar.time = endDate

        while (calendar.before(endCalendar)) {
            val result = calendar.time
            datesInRange.add(result)
            calendar.add(Calendar.DATE, 1)
        }
        return datesInRange
    }

    fun prepareFormatTime(time: Int): String {
        return if (time in 0..9)
            "0$time"
        else
            "" + time
    }

    fun getDatesFromCalender(): Array<String>? {
        val c1 = Calendar.getInstance()

        val dates = ArrayList<String>()
        val dateFormat = SimpleDateFormat("dd MMM")
        if (Locale.getDefault().language == "ru") dates.add("Сегодня")
        else dates.add("Today")

        for (i in 0..364) {
            c1.add(Calendar.DATE, 1)
            dates.add(dateFormat.format(c1.time))
        }

        Log.d("DATES", "" + dates)
        return dates.toTypedArray()
    }

    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null
    }

    fun showAlertDialog(context: Context, message: String, positive: String) {
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setMessage(message)
            .setPositiveButton(positive) { dialogInterface, _ -> dialogInterface.dismiss() }

        val alert = dialogBuilder.create()
        alert.show()
    }

}