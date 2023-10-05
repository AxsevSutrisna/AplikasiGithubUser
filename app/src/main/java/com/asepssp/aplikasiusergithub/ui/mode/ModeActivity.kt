package com.asepssp.aplikasiusergithub.ui.mode


import android.os.Bundle
import android.view.MenuItem
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.asepssp.aplikasiusergithub.databinding.ActivityModeBinding

class ModeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityModeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Setting Theme Mode"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        switchMode()
        setActionBar()

    }

    private fun setActionBar() {
        supportActionBar?.elevation = 0f
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun switchMode() {
        val switchTheme = binding.switchMode
        val preferences = SettingPreferences.getInstance(application.dataStore)
        val modeViewModel =
            ViewModelProvider(this, ViewModelFactory(preferences))[ModeViewModel::class.java]

        modeViewModel.getModeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            modeViewModel.saveModeSettings(isChecked)
        }
    }
}