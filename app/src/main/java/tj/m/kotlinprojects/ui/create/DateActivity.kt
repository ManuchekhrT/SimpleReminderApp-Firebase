package tj.m.kotlinprojects.ui.create

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_date.*
import kotlinx.android.synthetic.main.toolbar.*
import tj.m.kotlinprojects.ui.main.MainActivity
import tj.m.kotlinprojects.utils.CommonUtils.getDatesFromCalender
import tj.m.kotlinprojects.utils.CommonUtils.prepareFormatTime
import tj.m.kotlinprojects.utils.NotificationUtils
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.*


class DateActivity : AppCompatActivity() {

    private var dates: Array<String>? = null
    private var hourVal: String? = null
    private var minuteVal: String? = null
    private var dateVal: String? = null
    private var suf: String? = ""
    private var remindText: String = ""
    private lateinit var mViewModel: DateViewModel

    companion object {
        const val ARG_REMINDER_BODY = "arg_reminder_body"
    }


    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(tj.m.kotlinprojects.R.layout.activity_date)

        setSupportActionBar(mainToolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar_title.text = resources.getString(tj.m.kotlinprojects.R.string.label_new_reminder)

        val reminderBody = intent.getStringExtra(ARG_REMINDER_BODY)

        mViewModel = ViewModelProviders.of(this)
            .get(DateViewModel::class.java)

        dates = getDatesFromCalender()


        val rightNow = getInstance()

        hourNumberPicker.minValue = 0
        hourNumberPicker.maxValue = 23
        hourNumberPicker.setFormatter { i -> String.format("%02d", i) }
        hourNumberPicker.value = rightNow.get(HOUR_OF_DAY)
        hourNumberPicker.setOnValueChangedListener { numberPicker, oldVal, newVal ->
            hourVal = prepareFormatTime(newVal)

            if (minuteVal.isNullOrEmpty()) {
                val minuteRightNow = rightNow.get(MINUTE)
                minuteVal = prepareFormatTime(minuteRightNow)
            }

            updateSubmitBtnText()
        }

        minuteNumberPicker.wrapSelectorWheel
        minuteNumberPicker.minValue = 0
        minuteNumberPicker.maxValue = 59
        minuteNumberPicker.setFormatter { i -> String.format("%02d", i) }
        minuteNumberPicker.value = rightNow.get(MINUTE)
        minuteNumberPicker.setOnValueChangedListener { numberPicker, oldVal, newVal ->
            minuteVal = prepareFormatTime(newVal)

            if (hourVal.isNullOrEmpty()) {
                val hourRightNow = rightNow.get(HOUR)
                hourVal = prepareFormatTime(hourRightNow)
            }

            updateSubmitBtnText()
        }

        dateNumberPicker.minValue = 0
        dateNumberPicker.wrapSelectorWheel = false
        dateNumberPicker.maxValue = dates?.size?.minus(1)!!
        dateNumberPicker.setFormatter { value -> dates!![value] }
        dateNumberPicker.displayedValues = dates
        dateNumberPicker.setOnValueChangedListener { numberPicker, oldVal, newVal ->
            dateVal = numberPicker.displayedValues[newVal]

            updateSubmitBtnText()
        }

        val hour = rightNow.get(HOUR_OF_DAY)
        hourVal = prepareFormatTime(hour)

        val minute = rightNow.get(MINUTE)
        minuteVal = prepareFormatTime(minute)

        if (Locale.getDefault().language == "ru") {
            dateVal = "сегодня"
            remindText = "Напомнить"
            suf = "в"
        } else {
            dateVal = "today"
            remindText = "Remind"
            suf = "in"
        }

        val defSubmitBtnTxt = "$remindText $dateVal в ${hourVal}:${minuteVal}"
        submitDateBtn.text = defSubmitBtnTxt

        submitDateBtn.setOnClickListener {

            val overallDate = "${hourVal}:${minuteVal}, $dateVal"
            if (reminderBody.isNullOrEmpty() && overallDate.isEmpty())
                return@setOnClickListener

            val datetimeToAlarm = getInstance(Locale.getDefault())
            datetimeToAlarm.timeInMillis = System.currentTimeMillis()
            datetimeToAlarm.set(HOUR_OF_DAY, Integer.parseInt(hourVal!!))
            datetimeToAlarm.set(MINUTE, Integer.parseInt(minuteVal!!))
            datetimeToAlarm.set(SECOND, 0)
            datetimeToAlarm.set(MILLISECOND, 0)

            Log.d("TODAY", "" + dateVal)

            if (dateVal == "сегодня" || dateVal == "today") {
                datetimeToAlarm.set(DAY_OF_MONTH, rightNow.get(DAY_OF_MONTH))
                datetimeToAlarm.set(MONTH, rightNow.get(MONTH))
            } else {
                val df = SimpleDateFormat("dd MMM")
                val readDate = df.parse(dateVal)
                val cal = getInstance()
                cal.timeInMillis = readDate.time
                datetimeToAlarm.set(DAY_OF_MONTH, cal.get(DAY_OF_MONTH))
                datetimeToAlarm.set(MONTH, cal.get(MONTH))
            }
            datetimeToAlarm.set(YEAR, rightNow.get(YEAR))

            NotificationUtils().setNotification(datetimeToAlarm.timeInMillis, reminderBody, this)

            mViewModel.saveReminder(reminderBody, overallDate)
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }


    }

    @SuppressLint("SetTextI18n", "DefaultLocale")
    private fun updateSubmitBtnText() {
        submitDateBtn.text = "$remindText ${dateVal!!.toLowerCase()} $suf $hourVal:$minuteVal"
    }

}


