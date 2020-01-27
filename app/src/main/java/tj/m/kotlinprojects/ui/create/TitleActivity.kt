package tj.m.kotlinprojects.ui.create

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_title.*
import kotlinx.android.synthetic.main.toolbar.*
import tj.m.kotlinprojects.R

class TitleActivity : AppCompatActivity() {

    private var mViewModel: TitleViewModel? = null

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, TitleActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_title)

        setSupportActionBar(mainToolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar_title.text = resources.getString(R.string.label_new_reminder)

        initViewModel()
    }

    private fun initViewModel() {
        mViewModel = ViewModelProviders.of(this)
            .get(TitleViewModel::class.java)


        navigateToDateViewBtn.setOnClickListener {
            val reminderBody = reminderBodyEdt.text.toString().trim()
            if (reminderBody.isBlank()) {
                reminderBodyEdt.error = resources.getString(R.string.error_msg)
                return@setOnClickListener
            }

            val intent = Intent(this, DateActivity::class.java)
            intent.putExtra(DateActivity.ARG_REMINDER_BODY, reminderBody)
            startActivity(intent)

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
