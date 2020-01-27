package tj.m.kotlinprojects.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.database.*
import tj.m.kotlinprojects.data.firebase.model.Reminder
import tj.m.kotlinprojects.data.firebase.FirebaseQueryLiveData


@SuppressLint("StaticFieldLeak")
class AppRepository(var context: Context) {

    private var mDatabase: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("reminders")
    private var liveData: FirebaseQueryLiveData
    var mReminders: LiveData<DataSnapshot>? = null

    init {
        liveData = FirebaseQueryLiveData(mDatabase)
        mReminders = getAllReminders()
    }

    private fun getAllReminders(): LiveData<DataSnapshot>? {
        return liveData
    }

    fun insertReminder(item: Reminder) {
        val uuid = mDatabase.push().key
        if (uuid.isNullOrEmpty())
            return
        mDatabase.child(uuid).setValue(item)
    }

    fun deleteReminder(reminderId: String) {
        mDatabase.child(reminderId).removeValue()
    }


    companion object {
        private var instance: AppRepository? = null
        fun getInstance(context: Context): AppRepository {
            if (instance == null)
                instance =
                    AppRepository(context)

            return instance as AppRepository
        }
    }


}