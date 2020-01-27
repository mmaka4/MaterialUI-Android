package com.example.myapplication

import com.google.gson.annotations.SerializedName

class Tunda{
    @SerializedName("id")
    var id:String? = null
    @SerializedName("name")
    var name:String? = null
    @SerializedName("price")
    var price:String? = null
    @SerializedName("image")
    var image:String? = null
}


class MatundaResponse {
   @SerializedName("matunda")
    var matunda: ArrayList<Tunda>? = null
}
