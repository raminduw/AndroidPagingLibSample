package raminduweeraman.com.androidpaging.lib.sample.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import raminduweeraman.com.androidpaging.lib.sample.API_KEY
import raminduweeraman.com.androidpaging.lib.sample.SEARCH_BUILD_YEAR
import raminduweeraman.com.androidpaging.lib.sample.SEARCH_MAIN_TYPES
import raminduweeraman.com.androidpaging.lib.sample.SEARCH_MANUFACTURE
import raminduweeraman.com.androidpaging.lib.sample.api.CarService
import raminduweeraman.com.androidpaging.lib.sample.api.NetworkState
import raminduweeraman.com.androidpaging.lib.sample.api.SearchParams
import raminduweeraman.com.androidpaging.lib.sample.model.ApiResponse
import raminduweeraman.com.androidpaging.lib.sample.model.ListItem

class ItemsDataSource(
    private val carService: CarService, private val compositeDisposable: CompositeDisposable
) :
    PageKeyedDataSource<Long, ListItem>() {

    val networkState = MutableLiveData<NetworkState>()
    val initialLoad = MutableLiveData<NetworkState>()
    lateinit var searchParams: SearchParams
    var searchType: Int? = null
    fun setParams(networkParams: SearchParams, searchType: Int) {
        this.searchParams = networkParams
        this.searchType = searchType
    }

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, ListItem>) {
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)

        compositeDisposable.add(getApiResponse(0, params.requestedLoadSize).subscribe({ data ->
            callback.onResult(createItemList(data), null, 1)
            networkState.postValue(NetworkState.LOADED)
            initialLoad.postValue(NetworkState.LOADED)
        }, { throwable ->
            val error = NetworkState.error(throwable.message)
            networkState.postValue(error)
            initialLoad.postValue(error)
        }))
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, ListItem>) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(getApiResponse(params.key, params.requestedLoadSize).subscribe({ data ->
            if (data.totalPageCount > params.key) {
                callback.onResult(createItemList(data), params.key + 1)
                networkState.postValue(NetworkState.LOADED)
            } else {
                networkState.postValue(NetworkState.LOADED)
            }
        }, { throwable ->
            networkState.postValue(NetworkState.error(throwable.message))
        }))
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, ListItem>) {
        // ignored, since we only ever append to our initial load
    }

    private fun getApiResponse(page: Long, pageSize: Int): Single<ApiResponse> {
        when (searchType) {
            SEARCH_MANUFACTURE -> return carService.getManufactures(page, pageSize, API_KEY)
            SEARCH_MAIN_TYPES -> return carService.getMainTypes(searchParams.manufacture.key, page, pageSize, API_KEY)
            SEARCH_BUILD_YEAR -> return carService.getBuildDates(
                searchParams.manufacture.key,
                searchParams.mainType.key,
                API_KEY
            )
        }
        return carService.getManufactures(page, pageSize, API_KEY)
    }

    private fun createItemList(dataItem: ApiResponse): MutableList<ListItem> {
        val mutableList = mutableListOf<ListItem>()
        for ((k, v) in dataItem.wkdaMap) {
            mutableList.add(ListItem(k, v))
        }
        return mutableList
    }

}