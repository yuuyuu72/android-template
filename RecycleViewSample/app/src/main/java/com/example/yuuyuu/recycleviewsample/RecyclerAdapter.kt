package com.example.yuuyuu.recycleviewsample

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created by yuuyuu on 2018/09/18.
 */
class RecyclerAdapter(private val context: Context, private val itemClickListener :
        RecyclerViewHolder.ItemClickListener, private val itemList:List<String>) :
        RecyclerView.Adapter<RecyclerViewHolder>(){

    private var mRecycleView : RecyclerView? =null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecycleView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView?) {
        super.onDetachedFromRecyclerView(recyclerView)
        mRecycleView = null
    }
    override fun onBindViewHolder(holder: RecyclerViewHolder?, position: Int) {
        holder?.let{
            it.itemTextView.text = itemList.get(position)
            it.itemImageView.setImageResource(R.mipmap.ic_launcher)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val mView = layoutInflater.inflate(R.layout.photo_item, parent, false)
        mView.setOnClickListener { view ->
            mRecycleView?.let {
                itemClickListener.onItemClick(view, it.getChildAdapterPosition(view))
            }
        }
        return RecyclerViewHolder(mView)
    }

    override fun getItemCount(): Int {
        return  itemList.size
    }
}


