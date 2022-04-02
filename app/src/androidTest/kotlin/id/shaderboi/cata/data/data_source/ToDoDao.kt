package id.shaderboi.cata.data.data_source

import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import id.shaderboi.cata.di.AppModule

@HiltAndroidTest
@UninstallModules(AppModule::class)
class ToDoDaoTest