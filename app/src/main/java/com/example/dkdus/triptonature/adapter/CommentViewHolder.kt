package com.example.dkdus.triptonature.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.dkdus.triptonature.model.CommentDTO

import kotlinx.android.synthetic.main.item_comment.view.*

class CommentViewHolder(itemView: View, val itemClick: (CommentDTO)->Unit) : RecyclerView.ViewHolder(itemView){
    val id = itemView.id_text
    val time = itemView.time_text
    val comment = itemView.comment_text
    val delete = itemView.delete_tv


}