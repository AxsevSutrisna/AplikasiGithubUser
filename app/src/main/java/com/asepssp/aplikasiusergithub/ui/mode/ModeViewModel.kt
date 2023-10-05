package com.asepssp.aplikasiusergithub.ui.mode

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ModeViewModel(private val preference: SettingPreferences) : ViewModel() {

    fun getModeSettings(): LiveData<Boolean> {
        return preference.getThemeSetting().asLiveData()
    }

    fun saveModeSettings(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            preference.saveThemeSetting(isDarkModeActive)
        }
    }
}