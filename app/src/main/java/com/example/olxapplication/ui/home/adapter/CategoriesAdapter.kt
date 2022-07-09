package com.example.olxapplication.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.olxapplication.R
import com.example.olxapplication.model.CategoriesModel
import de.hdodenhof.circleimageview.CircleImageView

class CategoriesAdapter(
    var categoriesList: MutableList<CategoriesModel>,
   var itemClickListener: ItemClickListener
) :
    RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    private lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
           context = parent.context
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.adapter_categories,parent,false)
        return ViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.textViewTitle.setText(categoriesList.get(position).key)
        Glide.with(context).load(categoriesList.get(position).image)
            .placeholder(R.drawable.ic_placeholder).into(holder.imageView)

        holder.itemView.setOnClickListener(View.OnClickListener {
            itemClickListener.onItemClick(position)
        })
    }

    override fun getItemCount(): Int {
   return categoriesList.size
    }

    fun updateList(temp: MutableList<CategoriesModel>) {
           categoriesList=temp
          notifyDataSetChanged()
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

           val textViewTitle = itemView.findViewById<TextView>(R.id.tvTitle)
           val imageView = itemView.findViewById<CircleImageView>(R.id.ivIcon)
    }
    interface ItemClickListener{
        fun onItemClick(position: Int){

        }
    }
}

