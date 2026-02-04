data class DeezerResponse(val data: List<Song>)

data class Song(
    val title: String,
    val artist: Artist,
    val album: Album,
    val preview: String
)

data class Artist(val name: String)

data class Album(val cover_medium: String)