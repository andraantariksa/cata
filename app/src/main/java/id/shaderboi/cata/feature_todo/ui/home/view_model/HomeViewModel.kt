package id.shaderboi.cata.feature_todo.ui.home.view_model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _isCreateToDoModalOpened = mutableStateOf(false)
    val isCreateToDoModalOpened: State<Boolean> = _isCreateToDoModalOpened

    fun toggleToDoModal() {
        _isCreateToDoModalOpened.value = !_isCreateToDoModalOpened.value
    }
}