package raminduweeraman.com.androidpaging.lib.sample

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PageKeyedDataSource
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import raminduweeraman.com.androidpaging.lib.sample.api.CarService
import raminduweeraman.com.androidpaging.lib.sample.api.NetworkState
import raminduweeraman.com.androidpaging.lib.sample.api.SearchParams
import raminduweeraman.com.androidpaging.lib.sample.datasource.ItemsDataSource
import raminduweeraman.com.androidpaging.lib.sample.model.ApiResponse
import raminduweeraman.com.androidpaging.lib.sample.model.ListItem

class ItemDataSourceTest {

    @Rule
    @JvmField
    val taskExecutorRule = InstantTaskExecutorRule()
    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()
    @Mock
    lateinit var carService: CarService
    @Mock
    lateinit var compositeDisposable: CompositeDisposable
    lateinit var itemsDataSource: ItemsDataSource
    @Mock
    lateinit var initialParams: PageKeyedDataSource.LoadInitialParams<Long>
    @Mock
    lateinit var callbackInitial: PageKeyedDataSource.LoadInitialCallback<Long, ListItem>
    @Mock
    lateinit var loadCallback: PageKeyedDataSource.LoadCallback<Long, ListItem>
    @Mock
    lateinit var apiResponse: ApiResponse
    @Mock
    lateinit var searchParams: SearchParams

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        itemsDataSource = ItemsDataSource(carService,compositeDisposable)
    }

    @Test
    fun fetchDataInitially_Manufacture_Success() {
        itemsDataSource.searchType = SEARCH_MANUFACTURE

        Mockito.`when`(this.carService.getManufactures(ArgumentMatchers.anyLong(),ArgumentMatchers.anyInt(),
            ArgumentMatchers.anyString())).
            thenAnswer {
            return@thenAnswer Single.just(apiResponse)
        }

        itemsDataSource.loadInitialsData(initialParams, callbackInitial)
        assertEquals(NetworkState.LOADED, itemsDataSource.networkState.value)
        assertEquals(NetworkState.LOADED, itemsDataSource.initialLoad.value)
    }

    @Test
    fun fetchDataInitially_Manufacture_Error() {
        itemsDataSource.searchType = SEARCH_MANUFACTURE
        var exception = Exception("This is error")

        Mockito.`when`(this.carService.getManufactures(ArgumentMatchers.anyLong(),ArgumentMatchers.anyInt(),
            ArgumentMatchers.anyString())).
            thenAnswer {
                return@thenAnswer Single.error<ApiResponse>(exception)
            }
        itemsDataSource.loadInitialsData(initialParams, callbackInitial)
        assertEquals(NetworkState.error("This is error"), itemsDataSource.networkState.value)
    }

    @Test
    fun fetchDataAfter_Manufacture_Success() {
        itemsDataSource.searchType = SEARCH_MANUFACTURE

        Mockito.`when`(this.carService.getManufactures(ArgumentMatchers.anyLong(),ArgumentMatchers.anyInt(),
            ArgumentMatchers.anyString())).
            thenAnswer {
                return@thenAnswer Single.just(apiResponse)
            }

        Mockito.`when`(apiResponse.totalPageCount).thenReturn(2)
        itemsDataSource.loadAfterData(1,PAGE_SIZE, loadCallback)
        assertEquals(NetworkState.LOADED, itemsDataSource.networkState.value)
    }


    @Test
    fun fetchDataInitially_MainType_Success() {
        val manufacture = ListItem("Key","value")
        Mockito.`when`(searchParams.manufacture).thenReturn(manufacture)

        itemsDataSource.setParams(searchParams,SEARCH_MAIN_TYPES)

        Mockito.`when`(this.carService.getMainTypes(ArgumentMatchers.anyString(),ArgumentMatchers.anyLong(),ArgumentMatchers.anyInt(),
            ArgumentMatchers.anyString())).
            thenAnswer {
                return@thenAnswer Single.just(apiResponse)
            }

        itemsDataSource.loadInitialsData(initialParams, callbackInitial)
        assertEquals(NetworkState.LOADED, itemsDataSource.networkState.value)
        assertEquals(NetworkState.LOADED, itemsDataSource.initialLoad.value)
    }

    @Test
    fun fetchDataInitially_MainType_Error() {
        val manufacture = ListItem("Key","value")
        Mockito.`when`(searchParams.manufacture).thenReturn(manufacture)

        itemsDataSource.setParams(searchParams,SEARCH_MAIN_TYPES)

        var exception = Exception("This is error")

        Mockito.`when`(this.carService.getMainTypes(ArgumentMatchers.anyString(),ArgumentMatchers.anyLong(),ArgumentMatchers.anyInt(),
            ArgumentMatchers.anyString())).
            thenAnswer {
                return@thenAnswer Single.error<ApiResponse>(exception)
            }

        itemsDataSource.loadInitialsData(initialParams, callbackInitial)
        assertEquals(NetworkState.error("This is error"), itemsDataSource.networkState.value)
    }


    @Test
    fun fetchDataAfter_MainType_Success() {
        val manufacture = ListItem("Key","value")
        Mockito.`when`(searchParams.manufacture).thenReturn(manufacture)

        itemsDataSource.setParams(searchParams,SEARCH_MAIN_TYPES)

        Mockito.`when`(this.carService.getMainTypes(ArgumentMatchers.anyString(),ArgumentMatchers.anyLong(),ArgumentMatchers.anyInt(),
            ArgumentMatchers.anyString())).
            thenAnswer {
                return@thenAnswer Single.just(apiResponse)
            }

        Mockito.`when`(apiResponse.totalPageCount).thenReturn(2)
        itemsDataSource.loadAfterData(1,PAGE_SIZE, loadCallback)
        assertEquals(NetworkState.LOADED, itemsDataSource.networkState.value)
    }

    @Test
    fun fetchDataInitially_BuiltDate_Success() {
        val manufacture = ListItem("Key","value")
        val mainType = ListItem("Key","value")
        Mockito.`when`(searchParams.manufacture).thenReturn(manufacture)
        Mockito.`when`(searchParams.mainType).thenReturn(mainType)

        itemsDataSource.setParams(searchParams,SEARCH_BUILD_YEAR)

        Mockito.`when`(this.carService.getBuildDates(ArgumentMatchers.anyString(),ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString())).
            thenAnswer {
                return@thenAnswer Single.just(apiResponse)
            }

        itemsDataSource.loadInitialsData(initialParams, callbackInitial)
        assertEquals(NetworkState.LOADED, itemsDataSource.networkState.value)
        assertEquals(NetworkState.LOADED, itemsDataSource.initialLoad.value)
    }

    @Test
    fun fetchDataInitially_BuiltDate_Fail() {
        val manufacture = ListItem("Key","value")
        val mainType = ListItem("Key","value")
        Mockito.`when`(searchParams.manufacture).thenReturn(manufacture)
        Mockito.`when`(searchParams.mainType).thenReturn(mainType)
        var exception = Exception("This is error")
        itemsDataSource.setParams(searchParams,SEARCH_BUILD_YEAR)

        Mockito.`when`(this.carService.getBuildDates(ArgumentMatchers.anyString(),ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString())).
            thenAnswer {
                return@thenAnswer Single.error<ApiResponse>(exception)
            }

        itemsDataSource.loadInitialsData(initialParams, callbackInitial)
        assertEquals(NetworkState.error("This is error"), itemsDataSource.networkState.value)
    }

    @Test
    fun fetchDataAfter_BuiltDate_Success() {
        val manufacture = ListItem("Key","value")
        val mainType = ListItem("Key","value")
        Mockito.`when`(searchParams.manufacture).thenReturn(manufacture)
        Mockito.`when`(searchParams.mainType).thenReturn(mainType)

        itemsDataSource.setParams(searchParams, SEARCH_BUILD_YEAR)

        Mockito.`when`(this.carService.getBuildDates(ArgumentMatchers.anyString(),ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString())).
            thenAnswer {
                return@thenAnswer Single.just(apiResponse)
            }

        Mockito.`when`(apiResponse.totalPageCount).thenReturn(2)
        itemsDataSource.loadAfterData(1,PAGE_SIZE, loadCallback)
        assertEquals(NetworkState.LOADED, itemsDataSource.networkState.value)
    }

}