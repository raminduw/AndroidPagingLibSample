package raminduweeraman.com.androidpaging.lib.sample.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.item_loading_state.*
import raminduweeraman.com.androidpaging.lib.sample.BASE_URL
import raminduweeraman.com.androidpaging.lib.sample.R
import raminduweeraman.com.androidpaging.lib.sample.adapter.ItemAdapter
import raminduweeraman.com.androidpaging.lib.sample.api.NetworkState
import raminduweeraman.com.androidpaging.lib.sample.api.SearchParams
import raminduweeraman.com.androidpaging.lib.sample.api.Status
import raminduweeraman.com.androidpaging.lib.sample.di.*
import raminduweeraman.com.androidpaging.lib.sample.model.ListItem

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [BlankFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class BuiltDateSearchFragment : BaseFragment(){

    private var listener: OnFragmentInteractionListener? = null

    lateinit var appComponent: AppComponent

    lateinit var searchParams: SearchParams

    private lateinit var dataItemViewModel: DataItemViewModel
    private lateinit var itemAdapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        arguments?.let {

        }
        this.appComponent = initDagger()
        this.configureDagger()
        this.dataItemViewModel = ViewModelProviders.of(this, viewModelFactory)[DataItemViewModel::class.java]

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    protected open fun initDagger(): AppComponent
            = DaggerAppComponent.builder().netModule(NetModule(BASE_URL)).repositoryModule(
            RepositoryModule(searchParams)
        ).disposableModule(DisposableModule()).build()

    private fun configureDagger() = appComponent.inject(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }



    companion object {
        @JvmStatic
        fun newInstance() =
            BuiltDateSearchFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private fun initAdapter() {
        val linearLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        itemAdapter = ItemAdapter{
            listener?.onFragmentInteraction(it.key,it.value,searchParams.searchType)
           }

        dataRecyclerView.layoutManager = linearLayoutManager
        dataRecyclerView.adapter = itemAdapter
        dataRecyclerView.addItemDecoration(DividerItemDecoration(dataRecyclerView.context,linearLayoutManager.orientation));
        dataItemViewModel.dataList.observe(this, Observer<PagedList<ListItem>> {
            itemAdapter.submitList(it)
        })
        dataItemViewModel.getNetworkState().observe(this, Observer<NetworkState> { itemAdapter.setNetworkState(it) })

        dataItemViewModel.getInitialLoadState().observe(this, Observer<NetworkState> { setInitialLoadingState(it) })
    }

    private fun setInitialLoadingState(networkState: NetworkState?) {
        //error message
        errorMessageTextView.visibility = if (networkState?.message != null) View.VISIBLE else View.GONE
        if (networkState?.message != null) {
            errorMessageTextView.text = networkState.message
        }
        loadingProgressBar.visibility = if (networkState?.status == Status.RUNNING) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        dataItemViewModel.onCleared()
    }

}
