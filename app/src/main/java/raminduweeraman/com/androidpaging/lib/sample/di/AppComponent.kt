package raminduweeraman.com.androidpaging.lib.sample.di

import dagger.Component
import raminduweeraman.com.androidpaging.lib.sample.ui.BaseFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [NetModule::class, ViewModelModule::class, RepositoryModule::class, DisposableModule::class])
interface AppComponent {
    fun inject(searchFragment: BaseFragment)
}

