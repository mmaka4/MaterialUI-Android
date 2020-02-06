package com.example.myapplication.activity

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Model.MatundaResponse
import com.example.myapplication.Model.Tunda
import com.example.myapplication.R
import com.example.myapplication.adapter.ListFruitsAdapter
import com.example.myapplication.api.ServerApi
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.list_fruits_layout.*
import kotlinx.android.synthetic.main.navigation_drawer_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ListFruits : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var lfAdapter: ListFruitsAdapter
    lateinit var mData: ArrayList<Tunda>
    var refreshTimes = 0

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_fruits_layout)

        listFruitscyclerView.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        listFruitscyclerView.addOnItemTouchListener(
            object: RecyclerView.OnItemTouchListener {
                override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
                override fun onInterceptTouchEvent(rv: RecyclerView, e:
                MotionEvent): Boolean {
                    if (e.action == MotionEvent.ACTION_DOWN &&
                        rv.scrollState == RecyclerView.SCROLL_STATE_SETTLING) {
                        rv.stopScroll()
                    }
                    return false
                }
                override fun onRequestDisallowInterceptTouchEvent(
                    disallowIntercept: Boolean) {}
            })

        val resId = R.anim.slide_down
        val animation = AnimationUtils.loadAnimation(this, resId)
        listFruitscyclerView.startAnimation(animation)

        loadFruits()

        //** Set the colors of the Pull To Refresh View
        itemsswipetorefresh.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this, R.color.colorPrimary))
        itemsswipetorefresh.setColorSchemeColors(Color.WHITE)

        itemsswipetorefresh.setOnRefreshListener {
            mData.clear()
            loadFruits()
            itemsswipetorefresh.isRefreshing = false
        }

//        t = ActionBarDrawerToggle(this, nav_drawer, R.string.open, R.string.close)
//        t!!.isDrawerIndicatorEnabled = true
//        nav_drawer.addDrawerListener(t!!)
//        t!!.syncState()
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setHomeButtonEnabled(true)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        drawerLayout = findViewById(R.id.nav_drawer)
        navView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.account -> {
                Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.settings -> {
                Toast.makeText(this, "Messages clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.edit_profile -> {
                Toast.makeText(this, "Friends clicked", Toast.LENGTH_SHORT).show()
            }

        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
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

                    lfAdapter.notifyDataSetChanged()

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