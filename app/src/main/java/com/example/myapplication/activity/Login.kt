package com.example.myapplication.activity

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.example.myapplication.api.ServerApi
import com.example.myapplication.model.User
import com.example.myapplication.model.UserResponse
import com.google.android.material.textfield.TextInputLayout.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.login_form.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Login : AppCompatActivity() {

    lateinit var uData: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        backGroundColor()
        setContentView(R.layout.login_form)

        val content1 = getString(R.string.signupText)
        val spannableString1 = SpannableString(content1)
        spannableString1.setSpan(UnderlineSpan(), 0, content1.length, 0)
        signupText.text = spannableString1

        val loginButton = findViewById<Button>(R.id.login_button)

        loginButton.setOnClickListener {

            val email = userName.text.toString()
            val password = passWord.text.toString()

            login(email, password)
        }

        signupText.setOnClickListener {
            val intent = Intent(this, RegistrerUser::class.java)
            startActivity(intent)
        }

        if (image != null) {
            ValueAnimator.ofFloat(0f, -40f, 50f).apply {
                addUpdateListener { animation ->
                    image.arcHeight = (animation.animatedValue as Float)
                }
                duration = 8000
                repeatCount = ValueAnimator.INFINITE
                repeatMode = ValueAnimator.REVERSE
            }.start()
        }

        passwordLayout.endIconMode = END_ICON_PASSWORD_TOGGLE
    }

    private fun login(email: String, password: String) {

        Log.i("EditTexxt Values", "$email $password")
        val retrofit =
            Retrofit.Builder().baseUrl(getString(R.string.serverURL)).addConverterFactory(
                GsonConverterFactory.create()
            ).build()

        val api = retrofit.create(ServerApi::class.java)

        val call = api.loginUser(email, password)

        val gson = Gson()

        call.enqueue(object : Callback<UserResponse> {

            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                Log.i("ResponseString", gson.toJson(response.body()))

                if (response.isSuccessful) {
                    if (response.body()?.status!!) {
                    uData = response.body()?.user!!

                    val intent = Intent(this@Login, ListFruits::class.java)
                    val gson = Gson()
                    intent.putExtra("userData", gson.toJson(uData))
                    Toast.makeText(this@Login, "Login successfully.", Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                     }else {
                        if (userName.text.isNullOrEmpty() || passWord.text.isNullOrEmpty()) {
                            if (userName.text.isNullOrEmpty())
                                userName.error = getString(R.string.emailRequired)

                            if (passWord.text.isNullOrEmpty())
                                passWord.error = getString(R.string.passwordRequired)

//                            passwordLayout.endIconMode = END_ICON_NONE
//                            passwordLayout.endIconMode = END_ICON_CUSTOM
//                            passwordLayout.endIconDrawable = getDrawable(R.drawable.ic_report_black_24dp)

                        }else {
//                            val resId = R.anim.bounce
//                            val animation = AnimationUtils.loadAnimation(applicationContext, resId)
//                            login_error.startAnimation(animation)
                            login_error.visibility = View.VISIBLE
                        }
                    }

//                    response.body()?.matunda
                } else {
//                    login_error.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.i("ResponseFailure1", t.message)
            }

        })

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun backGroundColor() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.navigationBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        //window.setBackgroundDrawableResource(R.drawable.gradient)
    }
}