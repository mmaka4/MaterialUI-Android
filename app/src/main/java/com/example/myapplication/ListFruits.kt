package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.shimmer.Shimmer
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.list_fruits_item.*
import kotlinx.android.synthetic.main.list_fruits_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListFruits : AppCompatActivity() {

    lateinit var lfAdapter: ListFruitsAdapter
    lateinit var mData: ArrayList<Tunda>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_fruits_layout)

        listFruitscyclerView.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        var resId = R.anim.item_animation_from_bottom_scale
        val animation = AnimationUtils.loadAnimation(this, resId)
        listFruitscyclerView.startAnimation(animation)

        loadFruits()

//        edit.setOnClickListener {
//            //Toast.makeText(this@Login, "You clicked me.", Toast.LENGTH_SHORT).show()
//            val intent = Intent(this, Login::class.java)
//            startActivity(intent)
//        }
    }

    private fun loadFruits(){

        val retrofit = Retrofit.Builder().baseUrl(getString(R.string.serverURL)).addConverterFactory(
            GsonConverterFactory.create()).build()

        val api = retrofit.create(ServerApi::class.java)

        val call = api.getMatunda()

        val gson = Gson()

        call.enqueue(object : Callback<MatundaResponse> {

            override fun onResponse(
                call: Call<MatundaResponse>,
                response: Response<MatundaResponse>
            ) {
                if(response.isSuccessful){
                    Log.i("ResponseString",gson.toJson(response.body()))

                    shimmer_frame2.stopShimmer()
                    shimmer_frame2.visibility = View.GONE
                    listFruitscyclerView.visibility = View.VISIBLE


                    mData = ArrayList()

                    mData = response.body()?.matunda!!

                    lfAdapter = ListFruitsAdapter(mData,applicationContext)

                    listFruitscyclerView.adapter = lfAdapter

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