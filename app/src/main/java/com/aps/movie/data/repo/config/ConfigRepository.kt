package com.aps.movie.data.repo.config

import com.aps.movie.data.modal.DeviceLanguage
import kotlinx.coroutines.flow.Flow

interface ConfigRepository {
    fun getDeviceLanguage(): Flow<DeviceLanguage>

}