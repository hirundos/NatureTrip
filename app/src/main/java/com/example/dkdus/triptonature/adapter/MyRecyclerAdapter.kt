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
    fun clear(){
        this.mDatalist.clear()
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

}