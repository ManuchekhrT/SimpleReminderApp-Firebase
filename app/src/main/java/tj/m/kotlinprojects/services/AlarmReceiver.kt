package tj.m.kotlinprojects.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import tj.unam.simpletodoapp.services.NotificationService

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val service = Intent(context, NotificationService::class.java)
        service.putExtra("reason", intent!!.getStringExtra("reason"))
        service.putExtra("timestamp", intent.getLongExtra("timestamp", 0))
        service.putExtra("reminderBody", intent.getStringExtra("reminderBody"))
        context!!.startService(service)
    }

}