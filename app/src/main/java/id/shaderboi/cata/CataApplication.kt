package id.shaderboi.cata

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import id.shaderboi.cata.util.log.ReleaseTree
import timber.log.Timber

@HiltAndroidApp
class CataApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
    }
}