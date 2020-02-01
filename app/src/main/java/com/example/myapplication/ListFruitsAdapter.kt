package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.provider.Settings.Global.getString
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_food.view.*
import kotlinx.android.synthetic.main.list_fruits_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

        holder.editIconLayout.setOnClickListener {
            val gson = Gson()
            val intent  = Intent(context,UpdateActivity::class.java)
            intent.putExtra("tundaData",gson.toJson(foodList[position]))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
            Log.i("EditLayoutClicked: ", "Triggered")
        }

        holder.deleteIconLayout.setOnClickListener {
            val id: String = foodList[position].id.toString()
            update(id)
        }
    }

    inner class FoodViewHolder(itemView: View, viewType: Int): RecyclerView.ViewHolder(itemView){
        val name = itemView.fruitName
        val price = itemView.fruitPrice
        val image = itemView.fruitImage
        val editIconLayout = itemView.editIconLayout
        val deleteIconLayout = itemView.deleteIconLayout
    }

    private fun update(id:String){

        Log.i("Delete Id of ",id)
        val retrofit = Retrofit.Builder().baseUrl(context.resources.getString(R.string.serverURL)).addConverterFactory(
            GsonConverterFactory.create()).build()

        val api = retrofit.create(ServerApi::class.java)

        val call = api.deleteTunda(id)

        val gson = Gson()

        call.enqueue(object : Callback<MatundaResponse> {

            override fun onResponse(
                call: Call<MatundaResponse>,
                response: Response<MatundaResponse>
            ) {
                Log.i("ResponseString",gson.toJson(response.body()))

                if(response.isSuccessful){
                    if (response.body()?.status!!){
//                        val intent = Intent(this@ListFruitsAdapter, ListFruits::class.java)
//                        startActivity(intent)
                    }

//                    response.body()?.matunda
                }else{

                }
            }

            override fun onFailure(call: Call<MatundaResponse>, t: Throwable) {
                Log.i("ResponseFailure1",t.message)
            }

        })

    }
}