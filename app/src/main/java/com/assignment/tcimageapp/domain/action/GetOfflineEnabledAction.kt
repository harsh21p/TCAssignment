package com.assignment.tcimageapp.domain.action

import com.assignment.tcimageapp.data.local.OfflineSettingsDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOfflineEnabledAction @Inject constructor(
    private val offlineSettingsDataSource: OfflineSettingsDataSource
) {
    operator fun invoke(): Flow<Boolean> = offlineSettingsDataSource.offlineEnabledFlow
}