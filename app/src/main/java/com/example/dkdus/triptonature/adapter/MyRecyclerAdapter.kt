package com.example.dkdus.triptonature.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dkdus.triptonature.R
import com.example.dkdus.triptonature.model.place_material.Item

class MyRecyclerAdapter(frag: Fragment, Datalist: MutableList<Item>, private val itemClick: (Item)->Unit)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        private const val TYPE_ITEM = 0
        private const val TYPE_LOADING = 1
    }

    private var mDatalist: MutableList<Item?>
    var mfragment : Fragment

    init {
        mDatalist = Datalist.toMutableList()
        mfragment = frag
    }

    fun addPost(Datalist: MutableList<Item>){
        this.mDatalist.addAll(Datalist)
        notifyDataSetChanged()
    }

    fun setLoadingView(b : Boolean){
        if(b){
            this.mDatalist.add(null)
            notifyDataSetChanged()
        }else{
            if(this.mDatalist[this.mDatalist.size-1] == null ){
                this.mDatalist.removeAt(this.mDatalist.size -1)
                notifyItemRemoved(mDatalist.size)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when(mDatalist[position]){
            null -> TYPE_LOADING
            else -> TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType){
            TYPE_ITEM ->{
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_main, parent, false)
                return ItemViewHolder(view, itemClick)
            }
            else->{
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_loading, parent, false)
                return LoadingViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType){
            TYPE_ITEM -> {
                val itemHolder = holder as ItemViewHolder
                mDatalist.get(position).let { item ->
                    itemHolder.title.text = item?.title
                    itemHolder.contents.text = item?.addr

                    if(item?.mainimage == null){
                        itemHolder.image.setImageResource(R.drawable.ic_launcher_foreground)
                    } else {
                        Glide.with(mfragment).load(item?.mainimage).into(itemHolder.image)
                    }
                    holder.itemView.setOnClickListener {
                        holder.itemClick(mDatalist[position]!!)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return mDatalist.size
    }

}