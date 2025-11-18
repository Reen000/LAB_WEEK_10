package com.example.lab_week_10

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView
import android.widget.Button
import viewmodels.TotalViewModel // Assuming TotalViewModel is in the viewmodels package
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    // 1. Initialize TotalViewModel using ViewModelProvider
    private val viewModel by lazy {
        ViewModelProvider(this)[TotalViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // You might want to remove the enableEdgeToEdge and ViewCompat code if it's not strictly needed
        // enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
        //     val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        //     v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
        //     insets
        // }

        prepareViewModel()
    }

    // Function to update the TextView
    private fun updateText(total: Int) {
        // Find TextView and set its text using a string resource
        findViewById<TextView>(R.id.text_total).text =
            getString(R.string.text_total, total)
    }

    // Function to set up LiveData observation and button click listener
    private fun prepareViewModel(){
        // 2. Observe the 'total' LiveData from the ViewModel
        viewModel.total.observe(this) { total ->
            updateText(total)
        }

        // 3. Set click listener for the increment button
        findViewById<Button>(R.id.button_increment).setOnClickListener {
            // Call the ViewModel method to update the total
            viewModel.incrementTotal()
        }
    }
}