package com.aura.presentation

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.aura.App
import com.aura.R
import com.aura.databinding.ActivityMainBinding
import com.aura.domain.models.BootEvent
import kotlinx.coroutines.launch
import java.util.Date
import java.util.Locale

private const val DATE_PATTERN = "dd/MM/yyyy"

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        viewModel = ViewModelProvider(
            owner = this,
            factory = MainViewModelFactory(application as App)
        )[MainViewModel::class.java]

        viewModel.load()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lifecycleScope.launch {
            viewModel.getBootEventsStateFlow.collect {
                displayBootEvents(it)
            }
        }
    }

    private fun displayBootEvents(events: List<BootEvent>) {
        val text = if (events.isEmpty()) {
            "No boots detected"
        } else {
            events.groupBy { formatDate(it.timestamp, DATE_PATTERN) }
                .map { (date, events) -> "$date - ${events.size}" }
                .joinToString("\n")
        }
        binding.bootEventsTextView.text = text
    }

    private fun formatDate(timestamp: Long, pattern: String): String {
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        return sdf.format(Date(timestamp))
    }
}