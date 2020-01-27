package tj.m.kotlinprojects.utils

import tj.m.kotlinprojects.data.firebase.model.Reminder

interface OnReminderItemDeleteListener {
    fun onDeleteItem(item: Reminder)
}