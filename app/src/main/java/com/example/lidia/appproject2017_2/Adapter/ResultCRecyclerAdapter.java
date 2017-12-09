package com.example.lidia.appproject2017_2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lidia.appproject2017_2.Activity.DetailActivity.CafeDetailActivity;
import com.example.lidia.appproject2017_2.Class.Cafe;
import com.example.lidia.appproject2017_2.Holder.ResultCHolder;
import com.example.lidia.appproject2017_2.Listener.OnRecyclerViewClickListener;
import com.example.lidia.appproject2017_2.R;

import java.util.List;


public class ResultCRecyclerAdapter extends RecyclerView.Adapter<ResultCHolder> {
    private List<Cafe> cafeList;
    private Context context;

    public ResultCRecyclerAdapter(List<Cafe> cafeList, Context context) {
        this.cafeList = cafeList;
        this.context = context;
    }

    public void setCafeList(List<Cafe> list){ this.cafeList = list; }

    @Override
    public ResultCHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_result_cr_item,parent,false);
        return new ResultCHolder(view);
    }

    @Override
    public void onBindViewHolder(ResultCHolder holder, int position) {
        final Cafe cafe = cafeList.get(position);
        holder.bindData(cafe,context);

        holder.setListener(new OnRecyclerViewClickListener() {
            @Override
            public void onItemClick(View v, int adapterPosition) {
                Intent intent = new Intent(context, CafeDetailActivity.class);
                intent.putExtra("cafe",cafe);
                intent.putExtra("type",2);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cafeList.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
