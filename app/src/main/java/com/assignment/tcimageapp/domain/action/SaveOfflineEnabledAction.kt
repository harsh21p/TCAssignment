package com.assignment.tcimageapp.domain.action

import com.assignment.tcimageapp.data.local.OfflineSettingsDataSource
import jakarta.inject.Inject

class SaveOfflineEnabledAction @Inject constructor(
    private val offlineSettingsDataSource: OfflineSettingsDataSource
) {
    suspend operator fun invoke(enabled: Boolean) {
        offlineSettingsDataSource.setOfflineEnabled(enabled)
    }
}