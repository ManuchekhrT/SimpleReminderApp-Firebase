package tj.m.kotlinprojects.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_reminder.view.*
import tj.m.kotlinprojects.R
import tj.m.kotlinprojects.data.firebase.model.Reminder


class ReminderAdapter(private val mRemindersList: MutableList<Reminder>, private val listener: OnReminderItemDeleteListener)
    : RecyclerView.Adapter<ReminderAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(reminder: Reminder, listener: OnReminderItemDeleteListener) {
            itemView.reminderDeleteItemIv.setOnClickListener {
                listener.onDeleteItem(reminder)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reminder, parent, false)
        return ViewHolder(itemView)    }

    override fun getItemCount(): Int = mRemindersList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reminder = mRemindersList[position]
        holder.itemView.reminderBodyItemTv.text = reminder.body
        holder.itemView.reminderDateItemTv.text = reminder.date

        holder.bind(reminder, listener)
    }
}