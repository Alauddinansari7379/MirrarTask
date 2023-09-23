package com.example.mirrartask.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mirrartask.R
import com.example.mirrartask.roomDatabase.Table

class AdapterDownloadedImg: RecyclerView.Adapter<AdapterDownloadedImg.MyViewHolder>(){

    private var data = emptyList<Table>()

   // inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.firstName.text = data[position].title
        holder.lastName.text = data[position].date
        holder.dailyImageNew.load(data[position].image)
    }

    fun setData(person: List<Table>){
        this.data = person
        notifyDataSetChanged()
    }
    open class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dailyImageNew: ImageView = itemView.findViewById(R.id.imageView)
        val firstName: TextView = itemView.findViewById(R.id.firstName_txt)
        val lastName: TextView = itemView.findViewById(R.id.lastName_txt)


    }
}