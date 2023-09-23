package com.example.mirrartask.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
 import androidx.recyclerview.widget.RecyclerView
 import com.example.mirrartask.R
import com.example.mirrartask.model.ModelDailyImage
import com.squareup.picasso.Picasso


class AdapterDailyImage(
    val context: Context,
    private val list: ModelDailyImage,
    private val download: Download
) :
    RecyclerView.Adapter<AdapterDailyImage.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.single_row_daily_image, parent, false)
        )
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (list.photos[position].img_src != null) {
             Picasso.get().load("${list.photos[position].img_src}")
                 .placeholder(R.drawable.placeholder_n).error(R.drawable.error_placeholder)
                 .into(holder.dailyImageNew)
              Log.e("img_src", list.photos[position].img_src)
        }
        holder.imgDownload.setOnClickListener {
              download.download(list.photos[position].img_src,list.photos[position].earth_date,list.photos[position].rover.name)

        }

    }


    override fun getItemCount(): Int {
        return list.photos.size

    }

    open class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val dailyImageNew: ImageView = itemView.findViewById(R.id.dailyImage)
         val imgDownload: ImageView = itemView.findViewById(R.id.imgDownload)


    }

    interface Download {
        fun download(imageUrl:String,title:String,date:String)
    }

}