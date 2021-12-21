package com.turtlecode.photosharing_androidkotlinfirebasetutorial

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.reycler_row.view.*

class PhotoRecyclerAdapter (val postList : ArrayList<Post>) : RecyclerView.Adapter<PhotoRecyclerAdapter.PostHolder>() {
    class PostHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.reycler_row,parent,false)
        return PostHolder(view)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.itemView.recycler_row_comment_text.text = postList[position].userComment
        Picasso.get().load(postList[position].imageUrl).into(holder.itemView.recycler_row_imageview)
    }

    override fun getItemCount(): Int {
        return postList.size
    }


}


