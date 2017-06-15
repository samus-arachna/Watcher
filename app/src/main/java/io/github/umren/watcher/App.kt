package io.github.umren.watcher

import android.app.Application
import com.facebook.stetho.Stetho
import com.yandex.metrica.YandexMetrica

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        // init stetho
        Stetho.initializeWithDefaults(this);

        // init yandex appmetrik
        YandexMetrica.activate(getApplicationContext(), "b69d165c-2159-45d3-a838-3f06da95f41a")
        YandexMetrica.enableActivityAutoTracking(this)
    }
}