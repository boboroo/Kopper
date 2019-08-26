package com.soft.bitna.kopper.ForRecyclerViews;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soft.bitna.kopper.R;

import java.util.ArrayList;

public class SeatRecyclerViewAdapter extends RecyclerView.Adapter<SeatRecyclerViewAdapter.SeatViewHolder> {

    Context context;
    ArrayList<SeatGreedItem> list;

    public SeatRecyclerViewAdapter(Context context, ArrayList<SeatGreedItem> list) {
        super();
        this.context = context;
        this.list = list;
    }

    @Override
    public void onBindViewHolder(SeatViewHolder holder, int position) {
        if(list!=null){
            //경로 아이템 문자형식을 경로A-경로B 로 두면,
            //글자가 애매하게 다음 줄로 넘어가 출력되어, 사용자의 화면으로는 의미의 전달이 단 번에 될 수 없다고 생각했습니다.
            //그래서 한 눈에 경로를 단 번에 명료히 볼 수 있도록
            //        경로A
            //          -
            //        경로B
            //로 변경하였습니다.
            String[] routeArr = list.get(position).getRoute$app_debug().split("-");
            StringBuffer route = new StringBuffer().append(routeArr[0]).append("\n-\n").append(routeArr[1]);
            holder.routeTv.setText(route);

            holder.seatIdTv.setText(list.get(position).getSeat$app_debug());


            //경로 아이템들의 배경색을 짝수와 홀수로 나누어 지정하여, 경로 목록의 아이템들을 사용자들이 더 편하고 명료하게 인지할 수 있도록 하였습니다.
            if (position % 2 == 0) {
                holder.routeTv.setBackgroundColor(Color.rgb(232, 225, 202));
            } else {
                holder.routeTv.setBackgroundColor(Color.rgb(179, 158, 145));
            }
        }
    }

    @Override
    public SeatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SeatViewHolder(LayoutInflater.from(context).inflate(R.layout.seat_greed_item, parent, false));
    }

    @Override
    public int getItemCount() {
        if(list!=null) {
            return list.size();
        }
        else{
            return 0;
        }
    }

    public class SeatViewHolder extends RecyclerView.ViewHolder{
        TextView routeTv;
        TextView seatIdTv;

        public SeatViewHolder(View itemView) {
            super(itemView);

            routeTv = itemView.findViewById(R.id.routeTv);
            seatIdTv = itemView.findViewById(R.id.seatIdTv);
        }
    }

}