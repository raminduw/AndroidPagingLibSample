package raminduweeraman.com.androidpaging.lib.sample.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.reactivex.disposables.CompositeDisposable
import raminduweeraman.com.androidpaging.lib.sample.PAGE_SIZE
import raminduweeraman.com.androidpaging.lib.sample.SEARCH_BUILD_YEAR
import raminduweeraman.com.androidpaging.lib.sample.SEARCH_MAIN_TYPES
import raminduweeraman.com.androidpaging.lib.sample.SEARCH_MANUFACTURE
import raminduweeraman.com.androidpaging.lib.sample.api.NetworkState
import raminduweeraman.com.androidpaging.lib.sample.api.SearchParams
import raminduweeraman.com.androidpaging.lib.sample.datasource.ItemDataSourceFactory
import raminduweeraman.com.androidpaging.lib.sample.datasource.ItemsDataSource
import raminduweeraman.com.androidpaging.lib.sample.model.ListItem


class DataItemViewModel(
    itemsDataSource: ItemsDataSource, private val compositeDisposable: CompositeDisposable,
    private val searchParams: SearchParams, searchType: Int) : ViewModel() {
    private val sourceFactory = ItemDataSourceFactory(itemsDataSource)
    var dataList: LiveData<PagedList<ListItem>> = MutableLiveData<PagedList<ListItem>>()
    var paramList: MutableLiveData<SearchParams> = MutableLiveData<SearchParams>()

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setEnablePlaceholders(false)
            .build()
        itemsDataSource.setParams(searchParams, searchType)
        dataList = LivePagedListBuilder<Long, ListItem>(sourceFactory, config).build()
        saveSearchParams(searchType)
    }

    private fun saveSearchParams(searchType:Int){
        when (searchType) {
            SEARCH_MANUFACTURE -> setManufacture(searchParams.manufacture)
            SEARCH_MAIN_TYPES ->  {
                setMainType(searchParams.manufacture,searchParams.mainType)
            }
            SEARCH_BUILD_YEAR -> {
                setBuiltYear(searchParams.manufacture,searchParams.mainType,searchParams.builtDate)
            }
        }
    }

    fun getNetworkState(): LiveData<NetworkState> = Transformations.switchMap<ItemsDataSource, NetworkState>(
        sourceFactory.usersDataSourceLiveData, { it.networkState })

    fun getInitialLoadState(): LiveData<NetworkState> = Transformations.switchMap<ItemsDataSource, NetworkState>(
        sourceFactory.usersDataSourceLiveData, { it.initialLoad })

    public override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    private fun setManufacture(manufacture: ListItem) {
        val searchParams = SearchParams(manufacture, ListItem("", ""), ListItem("", ""));
        paramList.value = searchParams;
    }

    private fun setMainType(manufacture: ListItem,mainType: ListItem) {
        val searchParams = SearchParams(manufacture, mainType, ListItem("", ""));
        paramList.value = searchParams;
    }

    private fun setBuiltYear(manufacture: ListItem,mainType: ListItem,builtDate:ListItem) {
        val searchParams = SearchParams(manufacture, mainType, builtDate);
        paramList.value = searchParams;
    }

}