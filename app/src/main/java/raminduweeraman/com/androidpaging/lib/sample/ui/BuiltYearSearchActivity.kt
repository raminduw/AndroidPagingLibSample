package raminduweeraman.com.androidpaging.lib.sample.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.item_loading_state.*
import raminduweeraman.com.androidpaging.lib.sample.SEARCH_BUILD_YEAR
import raminduweeraman.com.androidpaging.lib.sample.adapter.ItemAdapter
import raminduweeraman.com.androidpaging.lib.sample.api.NetworkState
import raminduweeraman.com.androidpaging.lib.sample.api.SearchParams
import raminduweeraman.com.androidpaging.lib.sample.api.Status
import raminduweeraman.com.androidpaging.lib.sample.datasource.ItemsDataSource
import raminduweeraman.com.androidpaging.lib.sample.model.ListItem


class BuiltYearSearchActivity : BaseActivity() {
    private lateinit var manufactureKey: String
    private lateinit var manufactureValue: String
    private lateinit var mainTypeKey: String
    private lateinit var mainTypeValue: String

    private lateinit var itemAdapter: ItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        manufactureKey =  intent.getStringExtra("manufacture_key")
        manufactureValue =  intent.getStringExtra("manufacture_value")
        mainTypeKey =  intent.getStringExtra("main_type_key")
        mainTypeValue =  intent.getStringExtra("main_type_value")

        setContentView(raminduweeraman.com.androidpaging.lib.sample.R.layout.activity_search)
        val searchParams = SearchParams(ListItem(manufactureKey, manufactureValue), ListItem(mainTypeKey, mainTypeValue), ListItem("", ""));
        val dataItemViewModel = ViewModelProviders.of(
            this, ViewModelFactory(
                ItemsDataSource(carService, compositeDisposable),
                compositeDisposable, searchParams, SEARCH_BUILD_YEAR
            )
        )
            .get(DataItemViewModel::class.java)
        initAdapter(dataItemViewModel)
    }


    private fun initAdapter(dataItemViewModel: DataItemViewModel) {
        itemAdapter = ItemAdapter {
            showSearchCarAlert(dataItemViewModel,it)
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
        loadingProgressBar.visibility = if (networkState?.status == Status.RUNNING) View.VISIBLE else View.GONE
    }

    private fun showSearchCarAlert(dataItemViewModel: DataItemViewModel,item: ListItem) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Search Your Car")
        builder.setMessage(
            "Manufacture : " + dataItemViewModel.paramList.value!!.manufacture.value + "\n" + "Main Type : "
                    + dataItemViewModel.paramList.value!!.mainType.value + "\n" + "Built Year : " +
                    item.value
        )

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            Toast.makeText(
                applicationContext,
                "Start your car search", Toast.LENGTH_SHORT
            ).show()
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->

        }

        builder.setNeutralButton("Search Again") { dialog, which ->
            val intent = Intent(this, ManufactureSearchActivity::class.java)
            startActivity(intent)
        }
        builder.show()
    }
}
