package raminduweeraman.com.androidpaging.lib.sample.di

import dagger.Component
import io.reactivex.disposables.CompositeDisposable
import raminduweeraman.com.androidpaging.lib.sample.api.CarService
import javax.inject.Singleton

@Singleton
@Component(modules = [NetModule::class, DisposableModule::class])
interface AppComponent {
    fun getDisposable(): CompositeDisposable
    fun getService(): CarService
}
