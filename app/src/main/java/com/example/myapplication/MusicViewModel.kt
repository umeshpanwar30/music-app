import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MusicViewModel : ViewModel() {

    private val _songs = MutableStateFlow<List<Song>>(emptyList())
    val songs: StateFlow<List<Song>> = _songs

    fun loadSongs(query: String = "arijit") {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.searchSongs(query)

                // ⭐ NEVER allow null
                _songs.value = response.data ?: emptyList()

            } catch (e: Exception) {
                e.printStackTrace()

                // ⭐ Safe fallback
                _songs.value = emptyList()
            }
        }
    }
}