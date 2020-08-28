package com.example.dkdus.triptonature.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dkdus.triptonature.R
import com.example.dkdus.triptonature.model.place_material.Item

class MyRecyclerAdapter(frag: Fragment, Datalist: MutableList<Item>, val itemClick: (Item)->Unit)
    : RecyclerView.Adapter<ViewHolder>() {
    var mDatalist: MutableList<Item>
    var mfragment : Fragment
    init {
        mDatalist = Datalist
        mfragment = frag
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_main, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return mDatalist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mDatalist.get(position).let { item ->
            holder.title.text = item.title
            holder.contents.text = item.addr

            if(item.mainimage == null){
                holder.image.setImageResource(R.drawable.ic_launcher_foreground)
            } else {
                Glide.with(mfragment).load(item.mainimage).into(holder.image)
            }


            holder.itemView.setOnClickListener {
                holder.itemClick(mDatalist[position])
            }
        }
    }



    public fun getItem(position: Int) : Item{
        return mDatalist.get(position)
    }
}