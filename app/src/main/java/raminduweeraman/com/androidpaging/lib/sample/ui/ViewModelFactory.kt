package raminduweeraman.com.androidpaging.lib.sample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.disposables.CompositeDisposable
import raminduweeraman.com.androidpaging.lib.sample.api.SearchParams
import raminduweeraman.com.androidpaging.lib.sample.datasource.ItemsDataSource

class ViewModelFactory(private val itemsDataSource: ItemsDataSource, private val compositeDisposable: CompositeDisposable,
                       private val searchParams: SearchParams,private val searchType:Int
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DataItemViewModel(itemsDataSource, compositeDisposable,searchParams,searchType) as T
    }
}