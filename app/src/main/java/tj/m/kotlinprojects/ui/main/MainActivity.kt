package tj.m.kotlinprojects.ui.main

import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.provider.ContactsContract
import android.text.method.TextKeyListener.clear
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import kotlinx.android.synthetic.main.activity_main.*
import tj.m.kotlinprojects.data.firebase.model.Reminder
import tj.m.kotlinprojects.ui.create.TitleActivity
import tj.m.kotlinprojects.utils.CommonUtils
import tj.m.kotlinprojects.utils.OnReminderItemDeleteListener
import tj.m.kotlinprojects.utils.ReminderAdapter
import java.util.*


class MainActivity : AppCompatActivity(), OnReminderItemDeleteListener {

    private lateinit var mViewModel: MainViewModel
    private var mAdapter: ReminderAdapter? = null
    private var remindersList: MutableList<Reminder> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(tj.m.kotlinprojects.R.layout.activity_main)

        initRecyclerView()
        initViewModel()

        createReminderFab.setOnClickListener {
            startActivity(TitleActivity.newIntent(this))
        }
    }

    private fun initRecyclerView() {
        reminderListRv.hasFixedSize()
        val linearLayoutManager = LinearLayoutManager(this)
        reminderListRv.layoutManager = linearLayoutManager

        val dividerItemDecoration =
            DividerItemDecoration(reminderListRv.context, linearLayoutManager.orientation)
        reminderListRv.addItemDecoration(dividerItemDecoration)
    }

    private fun initViewModel() {
        loadingPb.visibility = View.VISIBLE
        val remindersObserver: Observer<DataSnapshot> = Observer { dataSnapshot ->
            remindersList.clear()
            if (dataSnapshot != null) {

                for (it in dataSnapshot.children) {
                    val reminder = it.getValue(Reminder::class.java)
                    if (reminder != null) {
                        reminder.helperUID = it.key!!
                        remindersList.add(reminder)
                    }
                }

                if (remindersList.isNullOrEmpty()) {
                    emptyRemindersListTv.visibility = View.VISIBLE
                    reminderListRv.visibility = View.GONE
                } else {
                    emptyRemindersListTv.visibility = View.GONE
                    reminderListRv.visibility = View.VISIBLE
                }

                if (mAdapter == null) {
                    mAdapter = ReminderAdapter(remindersList, this)
                    reminderListRv.adapter = mAdapter
                } else
                    mAdapter!!.notifyDataSetChanged()

                loadingPb.visibility = View.GONE

            }
        }

        mViewModel = ViewModelProviders.of(this)
            .get(MainViewModel::class.java)

        mViewModel.mRemindersList.observe(this, remindersObserver)
    }

    override fun onDeleteItem(item: Reminder) {
        mViewModel.deleteReminder(item.helperUID)
    }

}
