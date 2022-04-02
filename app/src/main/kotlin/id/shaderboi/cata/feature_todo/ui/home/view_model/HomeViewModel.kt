package id.shaderboi.cata.feature_todo.ui.home.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private var _isCreateToDoModalOpened by mutableStateOf(false)
    val isCreateToDoModalOpened get() = _isCreateToDoModalOpened

    fun toggleToDoModal() {
        _isCreateToDoModalOpened = !isCreateToDoModalOpened
    }
}