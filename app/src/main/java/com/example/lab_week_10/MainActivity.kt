package com.example.lab_week_10

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.lab_week_10.R
import com.example.lab_week_10.database.Total
import com.example.lab_week_10.database.TotalDatabase
import com.example.lab_week_10.viewmodels.TotalViewModel

// You would typically define this in a separate file (TotalViewModel.kt)
// but it is included here for completeness of the logic.

class MainActivity : AppCompatActivity() {
    // Pindah deklarasi db dan viewModel ke sini
    private val db by lazy { prepareDatabase() }
    private val viewModel by lazy {
        ViewModelProvider(this)[TotalViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi nilai dari DB
        initializeValueFromDatabase()

        // Siapkan ViewModel dan observer
        prepareViewModel()
    }

    private fun updateText(total: Int) {
        findViewById<TextView>(R.id.text_total).text =
            getString(R.string.text_total, total)
    }

    private fun prepareViewModel() {
        viewModel.total.observe(this) { totalValue ->
            updateText(totalValue ?: 0)
        }

        findViewById<Button>(R.id.button_increment).setOnClickListener {
            viewModel.incrementTotal()
        }
    }

    private fun prepareDatabase(): TotalDatabase {
        return Room.databaseBuilder(
            applicationContext,
            TotalDatabase::class.java, "total-database"
        ).allowMainThreadQueries().build()
    }

    private fun initializeValueFromDatabase() {
        val totalList = db.totalDao().getTotal(ID)
        if (totalList.isEmpty()) {
            db.totalDao().insert(Total(id = ID, total = 0))
        } else {
            viewModel.setTotal(totalList.first().total)
        }
    }

    override fun onPause() {
        super.onPause()
        val currentTotal = viewModel.total.value ?: 0
        db.totalDao().update(Total(ID, currentTotal))
    }

    companion object {
        const val ID: Long = 1
    }
}