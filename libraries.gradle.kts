ext {
    coilVersion = "2.5.0"
    pagingVersion = "3.2.1"
    okHttpBomVersion = "4.11.0"
    retrofitVersion = "2.9.0"
    moshiAdapterVersion = "1.14.0"
    hiltVersion = "2.42"
}

dependencies {
    coil = "io.coil-kt:coil-compose:$coilVersion"
    paging = "androidx.paging:paging-compose:$pagingVersion"
    okHttpBom = "com.squareup.okhttp3:okhttp-bom:$okHttpBomVersion"
    okHttp = "com.squareup.okhttp3:okhttp:$okHttpBomVersion"
    logging = "com.squareup.okhttp3:logging-interceptor:$okHttpBomVersion"
    retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
    moshi = "com.squareup.retrofit2:converter-moshi:$retrofitVersion"
    moshiAdapter = "com.squareup.moshi:moshi-adapters:$moshiAdapterVersion"
    moshiKotlin = "com.squareup.moshi:moshi-kotlin:$moshiAdapterVersion"
    moshiKsp = "com.squareup.moshi:moshi-kotlin-codegen:$moshiAdapterVersion"
    hilt = "com.google.dagger:hilt-android:$hiltVersion"
    hiltKsp = "com.google.dagger:hilt-android-compiler:$hiltVersion"
}