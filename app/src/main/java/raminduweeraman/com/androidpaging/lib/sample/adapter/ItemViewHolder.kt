package raminduweeraman.com.androidpaging.lib.sample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_view.view.*
import raminduweeraman.com.androidpaging.lib.sample.R
import raminduweeraman.com.androidpaging.lib.sample.model.ListItem

class ItemViewHolder( private val view: View, private val clickCallback: (listItem: ListItem) -> Unit) : RecyclerView.ViewHolder(view){

    init {
    }

    fun bindTo(listItem: ListItem?){
        itemView.itemName.text = listItem!!.value
        view.setOnClickListener { clickCallback(listItem) }
    }

    companion object {
        fun create(parent: ViewGroup ,clickCallback: (listItem: ListItem) -> Unit): ItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_view, parent, false)
            return ItemViewHolder(view,clickCallback)
        }
    }


}