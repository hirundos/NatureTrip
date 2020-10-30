package com.example.dkdus.triptonature.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.dkdus.triptonature.R
import com.example.dkdus.triptonature.model.CommentDTO

class CommentAdapter(var context : Context, var dataList: MutableList<CommentDTO>, private val itemClick : (CommentDTO)->Unit
        )  : RecyclerView.Adapter<CommentViewHolder>(){

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        var view : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_comment,parent,false)
        return CommentViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.id.text = dataList[position].id
        holder.time.text = dataList[position].time
        holder.comment.text = dataList[position].comment

        if(dataList[position].isEmail)
            holder.delete.visibility = View.VISIBLE
        else
            holder.delete.visibility = View.GONE

        holder.delete.setOnClickListener {
            holder.itemClick(dataList[position])
        }


    }

}