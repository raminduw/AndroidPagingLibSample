package raminduweeraman.com.androidpaging.lib.sample.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import raminduweeraman.com.androidpaging.lib.sample.model.ListItem

class ItemDataSourceFactory(private val itemsDataSource: ItemsDataSource): DataSource.Factory<Long, ListItem>() {
    val usersDataSourceLiveData = MutableLiveData<ItemsDataSource>()
    override fun create(): DataSource<Long, ListItem> {
        usersDataSourceLiveData.postValue(itemsDataSource)
        return itemsDataSource
    }

}
