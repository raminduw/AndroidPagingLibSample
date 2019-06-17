package raminduweeraman.com.androidpaging.lib.sample.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.item_loading_state.*
import raminduweeraman.com.androidpaging.lib.sample.SEARCH_MAIN_TYPES
import raminduweeraman.com.androidpaging.lib.sample.adapter.ItemAdapter
import raminduweeraman.com.androidpaging.lib.sample.api.NetworkState
import raminduweeraman.com.androidpaging.lib.sample.api.SearchParams
import raminduweeraman.com.androidpaging.lib.sample.api.Status
import raminduweeraman.com.androidpaging.lib.sample.datasource.ItemsDataSource
import raminduweeraman.com.androidpaging.lib.sample.model.ListItem


class MainTypeSearchActivity : BaseActivity() {

    private lateinit var itemAdapter: ItemAdapter
    private lateinit var manufactureKey: String
    private lateinit var manufactureValue: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        manufactureKey =  intent.getStringExtra("manufacture_key")
        manufactureValue =  intent.getStringExtra("manufacture_value")

        setContentView(raminduweeraman.com.androidpaging.lib.sample.R.layout.activity_search)
        val searchParams = SearchParams(ListItem(manufactureKey, manufactureValue), ListItem("", ""), ListItem("", ""));
        val dataItemViewModel = ViewModelProviders.of(
            this, ViewModelFactory(
                ItemsDataSource(carService, compositeDisposable),
                compositeDisposable, searchParams, SEARCH_MAIN_TYPES
            )
        )
        .get(DataItemViewModel::class.java)
        initAdapter(dataItemViewModel)
    }


    private fun initAdapter( dataItemViewModel: DataItemViewModel) {
        itemAdapter = ItemAdapter {
            startNextSearch(it)
        }

        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        dataRecyclerView.layoutManager = linearLayoutManager
        dataRecyclerView.adapter = itemAdapter
        dataRecyclerView.addItemDecoration(
            DividerItemDecoration(
                dataRecyclerView.context,
                linearLayoutManager.orientation
            )
        );
        dataItemViewModel.dataList.observe(this, Observer<PagedList<ListItem>> {
            itemAdapter.submitList(it)
        })
        dataItemViewModel.getNetworkState().observe(this, Observer<NetworkState> { itemAdapter.setNetworkState(it) })

        dataItemViewModel.getInitialLoadState().observe(this, Observer<NetworkState> { setInitialLoadingState(it) })
    }

    private fun setInitialLoadingState(networkState: NetworkState?) {
        errorMessageTextView.visibility = if (networkState?.message != null) View.VISIBLE else View.GONE
        if (networkState?.message != null) {
            errorMessageTextView.text = networkState.message
        }
        loadingProgressBar.visibility = if (networkState?.status == Status.LOADING) View.VISIBLE else View.GONE
    }

    private fun startNextSearch(itemList:ListItem){
        val intent = Intent(this, BuiltYearSearchActivity::class.java)
        intent.putExtra("manufacture_key", manufactureKey)
        intent.putExtra("manufacture_value", manufactureValue)
        intent.putExtra("main_type_key", itemList.key)
        intent.putExtra("main_type_value", itemList.value)
        startActivity(intent)
    }
}
