//package com.example.myapplication
//
//
//import android.os.Bundle
//import android.widget.Toast
//import androidx.annotation.DrawableRes
//import androidx.appcompat.app.AppCompatActivity
//import kotlinx.android.synthetic.main.activity_main.*
//
//class MainActivity : AppCompatActivity() {
//
//    private val itemAdapter by lazy {
//        ItemAdapter { position: Int, item: Item ->
//            Toast.makeText(this@MainActivity, "Pos ${position}", Toast.LENGTH_LONG).show()
//            item_list.smoothScrollToPosition(position)
//        } }
//    private val possibleItems = listOf(
//        Item("Kilemba", R.drawable.banana),
//        Item("Gauni", R.drawable.watermelon),
//        Item("Marimba", R.drawable.mango),
//        Item("Gauni", R.drawable.food5),
//        Item("Kishungi", R.drawable.food3)
//    )
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        item_list.initialize(itemAdapter)
//        item_list.setViewsToChangeColor(listOf(R.id.list_item_background, R.id.list_item_text))
//        itemAdapter.setItems(getLargeListOfItems())
//    }
//
//    private fun getLargeListOfItems(): List<Item> {
//        val items = mutableListOf<Item>()
//        (0..40).map { items.add(possibleItems.random()) }
//        return items
//    }
//}
//
//data class Item(
//    val title: String,
//    @DrawableRes val icon: Int
//)
