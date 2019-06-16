package raminduweeraman.com.androidpaging.lib.sample.di


import dagger.Module
import dagger.Provides
import raminduweeraman.com.androidpaging.lib.sample.BASE_URL
import raminduweeraman.com.androidpaging.lib.sample.api.CarService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetModule() {
    @Provides
    @Singleton
    internal fun providesApiService(): CarService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return retrofit.create(CarService::class.java)

    }

}