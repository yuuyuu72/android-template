package com.example.yuuyuu.recycleviewsample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(),RecyclerViewHolder.ItemClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val arrayWord = resources.getStringArray(R.array.value_items).toMutableList()

        recycler_view.adapter = RecyclerAdapter(this, this, arrayWord)
        recycler_view.layoutManager = GridLayoutManager(this,3,LinearLayoutManager.VERTICAL,false)

        //リストのコンテンツの大きさがデータによって変わらないならtrue
        //recycler_view.setHasFixedSize(true)
    }

    override fun onItemClick(view: View, position: Int) {
        Toast.makeText(applicationContext,"position $position was tapped",Toast.LENGTH_SHORT).show()
    }
}
