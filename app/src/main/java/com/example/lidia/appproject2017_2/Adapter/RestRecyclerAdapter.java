package com.example.lidia.appproject2017_2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lidia.appproject2017_2.Activity.DetailActivity.PensionDetailActivity;
import com.example.lidia.appproject2017_2.Activity.DetailActivity.RestDetailActivity;
import com.example.lidia.appproject2017_2.Class.Rest;
import com.example.lidia.appproject2017_2.Holder.RestHolder;
import com.example.lidia.appproject2017_2.Listener.OnRecyclerViewClickListener;
import com.example.lidia.appproject2017_2.R;

import java.util.List;


public class RestRecyclerAdapter extends RecyclerView.Adapter<RestHolder> {
    private List<Rest> restList;
    private Context context;

    public RestRecyclerAdapter(List<Rest> restList, Context context) {
        this.restList = restList;
        this.context = context;
    }


    public void setRestList(List<Rest> list){ this.restList = list; }

    @Override
    public RestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_rank_item_pension,parent,false);
        return new RestHolder(view);
    }

    @Override
    public void onBindViewHolder(RestHolder holder, int position) {
        final Rest rest = restList.get(position);
        holder.bindData(rest,context);

        holder.setListener(new OnRecyclerViewClickListener() {
            @Override
            public void onItemClick(View v, int adapterPosition) {
                Intent intent = new Intent(context, RestDetailActivity.class);
                intent.putExtra("rest",rest);
                intent.putExtra("type",3);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return restList.size();
    }
}
