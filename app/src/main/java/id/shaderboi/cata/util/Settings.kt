package id.shaderboi.cata.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import de.schnettler.datastore.manager.PreferenceRequest
import id.shaderboi.cata.ui.theme.Theme

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
object ThemePref : PreferenceRequest<String>(stringPreferencesKey("theme"), Theme.Light.name)

