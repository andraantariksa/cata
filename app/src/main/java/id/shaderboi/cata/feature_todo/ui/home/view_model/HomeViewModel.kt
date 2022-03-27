package id.shaderboi.cata.feature_todo.ui.home.view_model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel() {
    private val _isCreateToDoModalOpened = mutableStateOf(false)
    val isCreateToDoModalOpened: State<Boolean> get() = _isCreateToDoModalOpened

    fun toggleToDoModal() {
        viewModelScope.launch {
            _isCreateToDoModalOpened.value = !_isCreateToDoModalOpened.value
        }
    }
}