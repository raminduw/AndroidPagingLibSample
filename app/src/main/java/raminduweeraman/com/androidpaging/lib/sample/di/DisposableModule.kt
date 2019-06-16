package raminduweeraman.com.androidpaging.lib.sample.di

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class DisposableModule() {
    private lateinit var compositeDisposable: CompositeDisposable

    @Provides
    fun provideDisposable(): CompositeDisposable{
         compositeDisposable = CompositeDisposable()
        return compositeDisposable
    }

}