package tj.m.kotlinprojects.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import tj.m.kotlinprojects.data.repository.AppRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    var mRemindersList: LiveData<DataSnapshot>
    private var mRepository: AppRepository = AppRepository.getInstance(application.applicationContext)

    init {
        mRemindersList = getRemindersList()
    }

    private fun getRemindersList(): LiveData<DataSnapshot> {
        return mRepository.mReminders!!
    }

    fun deleteReminder(uuid: String) {
        mRepository.deleteReminder(uuid)
    }

}