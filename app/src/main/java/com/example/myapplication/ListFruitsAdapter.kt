package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_food.view.*
import kotlinx.android.synthetic.main.list_fruits_item.view.*

class ListFruitsAdapter (val foodList:ArrayList<Tunda>, val context: Context): RecyclerView.Adapter<ListFruitsAdapter.FoodViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view:View = layoutInflater.inflate(R.layout.list_fruits_item,parent,false)
        return FoodViewHolder(view,viewType)
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.name.text = foodList[position].name
        holder.price.text = foodList[position].price
        Picasso.get().load(context.resources.getString(R.string.imageFruitsURL)+foodList[position].image).into(holder.image)

        holder.editIconLayout.setOnClickListener(View.OnClickListener {
            val gson = Gson()
            val intent  = Intent(context,UpdateActivity::class.java)
            intent.putExtra("tundaData",gson.toJson(foodList[position]))
            context.startActivity(intent)
        })
    }

    inner class FoodViewHolder(itemView: View, viewType: Int): RecyclerView.ViewHolder(itemView){
        val name = itemView.fruitName
        val price = itemView.fruitPrice
        val image = itemView.fruitImage
        val editIconLayout = itemView.editIconLayout
    }
}