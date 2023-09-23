package com.example.mirrartask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mirrartask.R
import com.example.mirrartask.model.ModelVideoList


class AdapterVideoList(
    val context: Context,
    private val list: ArrayList<ModelVideoList>,
    private val play: Play
) :
    RecyclerView.Adapter<AdapterVideoList.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.row_layout_video, parent, false)
        )
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
          holder.title.text= list[position].title
          holder.description.text= list[position].description
        holder.imgDownload.setImageResource(list[position].image);

        holder.itemView.setOnClickListener {
              play.play(list[position].link)

        }

    }


    override fun getItemCount(): Int {
        return list.size

    }

    open class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val title: TextView = itemView.findViewById(R.id.tvTitle)
         val description: TextView = itemView.findViewById(R.id.tvDescription)
         val imgDownload: ImageView = itemView.findViewById(R.id.imageView)


    }

    interface Play {
        fun play(link:String)
    }

}