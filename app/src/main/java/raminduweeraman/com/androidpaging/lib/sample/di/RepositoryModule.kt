package raminduweeraman.com.androidpaging.lib.sample.di

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import raminduweeraman.com.androidpaging.lib.sample.api.CarService
import raminduweeraman.com.androidpaging.lib.sample.api.SearchParams
import raminduweeraman.com.androidpaging.lib.sample.datasource.ItemsDataSource
import javax.inject.Singleton

@Module
class RepositoryModule constructor(private val networkParams: SearchParams) {

    @Provides
    @Singleton
    fun provideUserRepository(githubService: CarService,
                              compositeDisposable: CompositeDisposable) = ItemsDataSource(githubService,compositeDisposable,networkParams)

}