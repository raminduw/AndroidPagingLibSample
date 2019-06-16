package raminduweeraman.com.androidpaging.lib.sample.di


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import raminduweeraman.com.androidpaging.lib.sample.ui.DataItemViewModel
import raminduweeraman.com.androidpaging.lib.sample.ui.ViewModelFactory


@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(DataItemViewModel::class)
    internal abstract fun postMainViewModel(viewModel: DataItemViewModel): ViewModel
}