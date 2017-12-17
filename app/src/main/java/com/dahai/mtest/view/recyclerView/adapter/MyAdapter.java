package com.dahai.mtest.view.recyclerView.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dahai.mtest.R;

/**
 * Created by 张海洋 on 2017-12-17.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    public static final int REFRESH_TYPE = 1;

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView;

        if (viewType == REFRESH_TYPE) {
            itemView = inflater.inflate(R.layout.view_refresh, parent, false);
            /*ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
            layoutParams.height = 0;
            itemView.setLayoutParams(layoutParams);*/
        } else 
        itemView = inflater.inflate(R.layout.recycler_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {
        if (position == 0) {

        } else
        holder.tv_content.setText(String.valueOf(position));
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return REFRESH_TYPE;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return 50;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_content;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_content = itemView.findViewById(R.id.item_content);
        }
    }
}
