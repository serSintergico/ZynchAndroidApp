import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response

interface ApiService {

    @GET("encontrar-usuario")
    suspend fun getUser(
        @Query("fullName") fullName: String?,
        @Query("phone") phone: String?,
        @Query("email") email: String
    ):Response <DataResponse>
}