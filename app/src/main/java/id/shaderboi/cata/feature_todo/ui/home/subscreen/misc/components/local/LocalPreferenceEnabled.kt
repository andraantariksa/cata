package id.shaderboi.cata.feature_todo.ui.home.subscreen.misc.components.local

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.structuralEqualityPolicy

val LocalPreferenceEnabledStatus: ProvidableCompositionLocal<Boolean> =
    compositionLocalOf(structuralEqualityPolicy()) { true }