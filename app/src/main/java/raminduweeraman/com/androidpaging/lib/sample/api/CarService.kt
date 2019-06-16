package raminduweeraman.com.androidpaging.lib.sample.api

import io.reactivex.Single
import raminduweeraman.com.androidpaging.lib.sample.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CarService {
   @GET("v1/car-types/manufacturer")
    fun getManufactures(@Query("page") page: Long, @Query("pageSize") perPage: Int,@Query("wa_key") waKey: String):
            Single<ApiResponse>

    @GET("v1/car-types/main-types")
    fun getMainTypes(@Query("manufacturer") manufacturer: String,@Query("page") page: Long, @Query("pageSize") perPage: Int,@Query("wa_key") waKey: String):
            Single<ApiResponse>

    @GET("v1/car-types/built-dates")
    fun getBuildDates(@Query("manufacturer") manufacturer: String,@Query("main-type") mainType: String,@Query("wa_key") waKey: String):
            Single<ApiResponse>
}