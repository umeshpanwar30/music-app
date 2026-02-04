import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search")
    suspend fun searchSongs(@Query("q") query: String): DeezerResponse
}