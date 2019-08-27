package com.soft.bitna.kopper.ForRecyclerViews

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.soft.bitna.kopper.R

import java.util.ArrayList

class SeatRecyclerViewAdapter(internal var context: Context, internal var list: ArrayList<SeatGreedItem>?) : RecyclerView.Adapter<SeatRecyclerViewAdapter.SeatViewHolder>() {

    override fun onBindViewHolder(holder: SeatViewHolder, position: Int) {
        Log.d("kopper", "onBindViewHolder() 호출")

        if (list != null) {
            //경로 아이템 문자형식을 경로A-경로B 로 두면,
            //글자가 애매하게 다음 줄로 넘어가 출력되어, 사용자의 화면으로는 의미의 전달이 단 번에 될 수 없다고 생각했습니다.
            //그래서 한 눈에 경로를 단 번에 명료히 볼 수 있도록
            //        경로A
            //          -
            //        경로B
            //로 변경하였습니다.
            holder.routeTv.text = StringBuffer().also {
                val routeArr = list!![position].route.split("-").toTypedArray()
                it.append(routeArr[0]).append("\n-\n").append(routeArr[1])
            }
            /*위와 동일한 코드. 이게 더 빠름.
            val routeArr = list!![position].route.split("-").toTypedArray()
            holder.routeTv.text = "${routeArr[0]}\n-\n${routeArr[1]}"*/

            holder.seatIdTv.text = list!![position].seat


            //경로 아이템들의 배경색을 짝수와 홀수로 나누어 지정하여, 경로 목록의 아이템들을 사용자들이 더 편하고 명료하게 인지할 수 있도록 하였습니다.
            if (position % 2 == 0) {
                holder.routeTv.setBackgroundColor(Color.rgb(232, 225, 202))
            } else {
                holder.routeTv.setBackgroundColor(Color.rgb(179, 158, 145))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeatViewHolder {
        return SeatViewHolder(LayoutInflater.from(context).inflate(R.layout.seat_greed_item, parent, false))
    }

    override fun getItemCount(): Int {
        return (list?.size)?:0
    }

    inner class SeatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val routeTv: TextView = itemView.findViewById(R.id.routeTv)
        val seatIdTv: TextView = itemView.findViewById(R.id.seatIdTv)
    }

}