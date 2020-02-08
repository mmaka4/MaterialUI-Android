package com.example.myapplication.activity

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.myapplication.*
import com.example.myapplication.adapter.FoodAdapter
import com.example.myapplication.adapter.FoodyAdapter
import com.example.myapplication.api.ServerApi
import com.example.myapplication.model.*
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_order.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.collections.ArrayList

class orderAcivity : AppCompatActivity() {

    lateinit var mAdapter: FoodAdapter
    lateinit var mData: ArrayList<Tunda>

    lateinit var fAdapter: FoodyAdapter
    lateinit var fData: ArrayList<Foody>

    val gson = Gson()
    private lateinit var userString: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        backGroundColor()
        setContentView(R.layout.activity_order)

        val resId = R.anim.slide_down
        val animation = AnimationUtils.loadAnimation(this, resId)
        name.startAnimation(animation)
        profilepic.startAnimation(animation)

        val resId3 = R.anim.slide_up
        val animationSU = AnimationUtils.loadAnimation(this, resId3)
        userstatus.startAnimation(animationSU)

        userString = intent.getStringExtra("userData")

        val userInfo = gson.fromJson<User>(
            userString,
            User::class.java
        )

        Log.i("User Data [OrederActy]", userInfo.id + " " + userInfo.email + " " + userInfo.image)

        if (userInfo.email!!.isNotEmpty()) {
            name.text = userInfo.email
        }

        val imgUrl = getString(R.string.userImageURL) + userInfo.image
        Log.i("imageURL", imgUrl)
        if (imgUrl.isEmpty()) { //url.isEmpty()
            Picasso.get()
                .load(R.drawable.profile_pic2)
                .placeholder(R.drawable.profile_pic2)
                .error(R.drawable.profile_pic2)
                .into(profilepic)

        } else {
            Picasso.get()
                .load(resources.getString(R.string.userImageURL) + userInfo.image)
                .placeholder(R.drawable.profile_pic2)
                .error(R.drawable.profile_pic2)
                .into(profilepic) //this is your ImageView
        }

        backLayout.setOnClickListener {
            finish()
        }

//        loadFruits()
//        loadFood()
    }

    private fun loadFruits() {

        val retrofit = Retrofit.Builder().baseUrl(getString(R.string.serverURL))
            .addConverterFactory(GsonConverterFactory.create()).build()

        val api = retrofit.create(ServerApi::class.java)

        val call = api.getMatunda()

        val gson = Gson()

        call.enqueue(object : Callback<MatundaResponse> {

            override fun onResponse(
                call: Call<MatundaResponse>,
                response: Response<MatundaResponse>
            ) {
                if (response.isSuccessful) {
                    Log.i("ResponseString", gson.toJson(response.body()))

                    // stop animating Shimmer and hide the layout
//                    shimmer_frame.stopShimmer()
//                    shimmer_frame.visibility = View.GONE
//                    foodRecyclerView.visibility = View.VISIBLE


                    mData = ArrayList()

                    mData = response.body()?.matunda!!

                    mAdapter = FoodAdapter(
                        mData,
                        applicationContext
                    )

//                    foodRecyclerView.adapter = mAdapter

//                    response.body()?.matunda
                } else {

                }
            }

            override fun onFailure(call: Call<MatundaResponse>, t: Throwable) {
                Log.i("ResponseFailure1", t.message)
            }

        })

    }

    private fun loadFood() {

        val retrofit = Retrofit.Builder().baseUrl(getString(R.string.serverURL))
            .addConverterFactory(GsonConverterFactory.create()).build()

        val api = retrofit.create(ServerApi::class.java)

        val call = api.getFood()

        val gson = Gson()

        call.enqueue(object : Callback<FoodResponse> {

            override fun onResponse(
                call: Call<FoodResponse>,
                response: Response<FoodResponse>
            ) {
                if (response.isSuccessful) {
                    Log.i("ResponseString", gson.toJson(response.body()))

                    // stop animating Shimmer and hide the layout
//                    shimmer_frame.stopShimmer()
//                    shimmer_frame.visibility = View.GONE
//                    foodRecyclerView.visibility = View.VISIBLE

                    fData = ArrayList()

                    fData = response.body()?.food!!

                    fAdapter = FoodyAdapter(
                        fData,
                        applicationContext
                    )

                    //foodRecyclerView.adapter = mAdapter

                    //val list = listOf(fData)

                    //itemAdapter.setItems(fData)
//                    item_list.adapter = fAdapter

//                    response.body()?.matunda
                } else {

                }
            }

            override fun onFailure(call: Call<FoodResponse>, t: Throwable) {
                Log.i("ResponseFailure2", t.message)
            }

        })

    }

//    private fun getLargeListOfItems(): ArrayList<Foody> {
//        val items = mutableListOf<Foody>()
//        (0..40).map { items.add(possibleItems.random()) }
//        return items
//    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun backGroundColor() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.navigationBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.setBackgroundDrawableResource(R.drawable.gradient)
    }

    override fun onResume() {
        super.onResume()
//        shimmer_frame.startShimmer()
    }

    override fun onPause() {
        super.onPause()
//        shimmer_frame.stopShimmer()
    }
}


