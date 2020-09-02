package com.example.dkdus.triptonature.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dkdus.triptonature.model.place_material.Item
import kotlinx.android.synthetic.main.item_main.view.*

public class ItemViewHolder(itemView: View, val itemClick: (Item)->Unit)
    : RecyclerView.ViewHolder(itemView) {
    val title: TextView = itemView.title_text
    val contents:TextView = itemView.contents_text
    val image : ImageView = itemView.image_place

}
