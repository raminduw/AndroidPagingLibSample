package raminduweeraman.com.androidpaging.lib.sample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_loading_state.view.*
import raminduweeraman.com.androidpaging.lib.sample.R
import raminduweeraman.com.androidpaging.lib.sample.api.NetworkState
import raminduweeraman.com.androidpaging.lib.sample.api.Status

class LoadingStateViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    init {
    }

    fun bindTo(networkState: NetworkState?) {
        itemView.errorMessageTextView.visibility = if (networkState?.message != null) View.VISIBLE else View.GONE
        if (networkState?.message != null) {
            itemView.errorMessageTextView.text = networkState.message
        }
        itemView.loadingProgressBar.visibility = if (networkState?.status == Status.RUNNING) View.VISIBLE else View.GONE
    }

    companion object {
        fun create(parent: ViewGroup): LoadingStateViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_loading_state, parent, false)
            return LoadingStateViewHolder(view)
        }
    }

}