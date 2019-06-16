package raminduweeraman.com.androidpaging.lib.sample.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.reactivex.disposables.CompositeDisposable
import raminduweeraman.com.androidpaging.lib.sample.PAGE_SIZE
import raminduweeraman.com.androidpaging.lib.sample.api.NetworkState
import raminduweeraman.com.androidpaging.lib.sample.datasource.ItemDataSourceFactory
import raminduweeraman.com.androidpaging.lib.sample.datasource.ItemsDataSource
import raminduweeraman.com.androidpaging.lib.sample.model.ListItem
import javax.inject.Inject

class DataItemViewModel @Inject constructor(usersDataSource: ItemsDataSource, private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    var dataList: LiveData<PagedList<ListItem>>
    private val sourceFactory = ItemDataSourceFactory(usersDataSource)

    init {
        val config = PagedList.Config.Builder()
                .setPageSize(PAGE_SIZE)
                .setInitialLoadSizeHint(PAGE_SIZE)
                .setEnablePlaceholders(false)
                .build()
        this.dataList = LivePagedListBuilder<Long, ListItem>(sourceFactory, config).build()
    }

    fun getNetworkState(): LiveData<NetworkState> = Transformations.switchMap<ItemsDataSource, NetworkState>(
            sourceFactory.usersDataSourceLiveData, { it.networkState })

    fun getInitialLoadState(): LiveData<NetworkState> = Transformations.switchMap<ItemsDataSource, NetworkState>(
            sourceFactory.usersDataSourceLiveData, { it.initialLoad })

    public override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }



}