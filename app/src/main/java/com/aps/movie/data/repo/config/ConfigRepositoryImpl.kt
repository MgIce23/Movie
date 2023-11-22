package com.aps.movie.data.repo.config

import com.aps.movie.data.modal.DeviceLanguage
import com.aps.movie.data.paging.ConfigDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class ConfigRepositoryImpl @Inject constructor(
    private val configDataSource: ConfigDataSource
) : ConfigRepository {
    override fun getDeviceLanguage(): Flow<DeviceLanguage> {
        return emptyFlow()
    }
}