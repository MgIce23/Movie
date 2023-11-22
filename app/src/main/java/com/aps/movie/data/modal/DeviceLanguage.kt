package com.aps.movie.data.modal

data class DeviceLanguage(
    val region: String,
    val languageCode : String
){

    companion object{
        val default: DeviceLanguage = DeviceLanguage(
            region = "US",
            languageCode = "en-US"
        )
    }
}
