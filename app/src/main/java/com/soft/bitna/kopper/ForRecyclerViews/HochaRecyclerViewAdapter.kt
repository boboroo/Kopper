package com.soft.bitna.kopper.ForRecyclerViews

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.soft.bitna.kopper.R

import java.util.ArrayList

class HochaRecyclerViewAdapter(internal var context: Context, internal var list: ArrayList<String>?) : RecyclerView.Adapter<HochaRecyclerViewAdapter.HochaViewHolder>() {

    override fun onBindViewHolder(holder: HochaRecyclerViewAdapter.HochaViewHolder, position: Int) {
        holder.hochaTv.text = list!![position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HochaRecyclerViewAdapter.HochaViewHolder {
        return HochaRecyclerViewAdapter.HochaViewHolder(LayoutInflater.from(context).inflate(R.layout.hocha_textview, parent, false))
    }

    override fun getItemCount(): Int {
        return if (list != null) {
            list!!.size
        } else {
            0
        }
    }

    inner class HochaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var hochaTv: TextView

        init {

            hochaTv = itemView.findViewById(R.id.hochaTv)
        }
    }

}
