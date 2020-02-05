package com.example.myapplication.activity

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.*
import com.example.myapplication.adapter.ListFruitsAdapter
import com.example.myapplication.api.ServerApi
import com.example.myapplication.Model.MatundaResponse
import com.example.myapplication.Model.Tunda
import com.google.gson.Gson
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

        val resId = R.anim.slide_down
        val animation = AnimationUtils.loadAnimation(this, resId)
        listFruitscyclerView.startAnimation(animation)

        loadFruits()
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

                    lfAdapter =
                        ListFruitsAdapter(
                            mData,
                            applicationContext,
                            this@ListFruits
                        )

                    listFruitscyclerView.adapter = lfAdapter

//                    response.body()?.matunda
                }else{
                    //to catch
                }
            }

            override fun onFailure(call: Call<MatundaResponse>, t: Throwable) {
                Log.i("ResponseFailure1",t.message)
            }

        })

    }

}