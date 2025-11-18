package viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData

class TotalViewModel: ViewModel() {
    private val _total = MutableLiveData(0)
    val total: LiveData<Int> = _total

    fun incrementTotal() {
        _total.value = (_total.value ?: 0) + 1
    }
}