package tj.m.kotlinprojects.ui.create

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.AndroidViewModel
import tj.m.kotlinprojects.data.firebase.model.Reminder
import tj.m.kotlinprojects.data.repository.AppRepository
import java.util.*

class DateViewModel(application: Application) : AndroidViewModel(application) {

    private var mRepository: AppRepository =
        AppRepository.getInstance(application.applicationContext)


    fun saveReminder(body: String, date: String) {
        mRepository.insertReminder(Reminder(body, date))
    }
}