package com.soft.bitna.kopper.ForRecyclerViews;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soft.bitna.kopper.R;

import java.util.ArrayList;

public class HochaRecyclerViewAdapter extends RecyclerView.Adapter<HochaRecyclerViewAdapter.HochaViewHolder> {

    Context context;
    ArrayList<String> list;

    public HochaRecyclerViewAdapter(Context context, ArrayList<String> list) {
        super();
        this.context = context;
        this.list = list;
    }

    @Override
    public void onBindViewHolder(HochaRecyclerViewAdapter.HochaViewHolder holder, int position) {
        holder.hochaTv.setText(list.get(position));
    }

    @Override
    public HochaRecyclerViewAdapter.HochaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HochaRecyclerViewAdapter.HochaViewHolder(LayoutInflater.from(context).inflate(R.layout.hocha_textview, parent, false));
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

    public class HochaViewHolder extends RecyclerView.ViewHolder{
        TextView hochaTv;

        public HochaViewHolder(View itemView) {
            super(itemView);

            hochaTv = itemView.findViewById(R.id.hochaTv);
        }
    }

}
