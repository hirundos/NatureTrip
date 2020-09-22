package com.example.dkdus.triptonature.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dkdus.triptonature.R
import com.example.dkdus.triptonature.model.place_material.Item

class MyRecyclerAdapter(
    frag: Fragment,
    Datalist: MutableList<Item>,
    private val itemClick: (Item) -> Unit
)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    public var mDatalist: MutableList<Item?> = Datalist.toMutableList()
    var mfragment : Fragment = frag

    fun addPost(dataList: MutableList<Item>){
        mDatalist = dataList.toMutableList()
        notifyDataSetChanged()
    }
    fun clear(){
        mDatalist.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
     val view: View = LayoutInflater.from(parent.context)
      .inflate(R.layout.item_main, parent, false)
        return ItemViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
     val itemHolder = holder as ItemViewHolder
     mDatalist.get(position).let { item ->
         itemHolder.title.text = item?.title
         itemHolder.contents.text = item?.addr

         if(item?.mainimage == null){
            itemHolder.image.setImageResource(R.drawable.ic_launcher_foreground)
          }else {
             Glide.with(mfragment).load(item?.mainimage).into(itemHolder.image)
          }
         holder.itemView.setOnClickListener {
             holder.itemClick(mDatalist[position]!!) }
        }
    }

    override fun getItemCount(): Int {
        return mDatalist.size
    }

    override fun getFilter(): Filter {
        var filter = object :Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var filterResult = FilterResults()
                var placelist: MutableList<Item> = mutableListOf()

                for (item in mDatalist) {
                    if (item?.sigungucode.toString().contains(constraint.toString())) {
                        placelist.add(item!!)
                    }
                    filterResult.count = placelist.size
                    filterResult.values = placelist
                }
                return filterResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
               if(results?.count!! > 0){
                   mDatalist = results.values as MutableList<Item?>
                   notifyDataSetChanged()
               }else{
                    clear()
               }
            }

        }
        return filter
    }
}