package com.example.myapplication

import MusicViewModel
import SearchBar
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import com.example.musicapp.ui.SongListScreen
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem

class MainActivity : ComponentActivity() {

    private lateinit var player: ExoPlayer
    private val viewModel: MusicViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ExoPlayer
        player = ExoPlayer.Builder(this).build()

        setContent {

            val songs by viewModel.songs.collectAsState()
            var query by remember { mutableStateOf("") }

            Column {
                SearchBar(
                    query = query,
                    onQueryChange = {
                        query = it
                        viewModel.loadSongs(it)   // ⭐ Auto search
                    }
                   // onSearch = { } // Ab iski zaroorat nahi
                )

                SongListScreen(
                    songs = songs,
                    onSongClick = { song ->
                        playSong(song.preview)
                    }
                )
            }
        }

        // Load songs from API
        viewModel.loadSongs()
    }

    private fun playSong(url: String) {
        val mediaItem = MediaItem.fromUri(url)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}