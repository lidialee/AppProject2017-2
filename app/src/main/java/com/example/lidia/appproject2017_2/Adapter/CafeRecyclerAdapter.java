package com.example.lidia.appproject2017_2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lidia.appproject2017_2.Activity.StoreDetailActivity;
import com.example.lidia.appproject2017_2.Class.Cafe;
import com.example.lidia.appproject2017_2.Class.Pension;
import com.example.lidia.appproject2017_2.Holder.CafeHolder;
import com.example.lidia.appproject2017_2.Holder.PensionHolder;
import com.example.lidia.appproject2017_2.Listener.OnRecyclerViewClickListener;
import com.example.lidia.appproject2017_2.R;

import java.util.List;


public class CafeRecyclerAdapter extends RecyclerView.Adapter<CafeHolder> {
    private List<Cafe> cafeList;
    private Context context;

    public CafeRecyclerAdapter(List<Cafe> cafeList, Context context) {
        this.cafeList = cafeList;
        this.context = context;
    }


    public void setCafeList(List<Cafe> list){ this.cafeList = list; }

    @Override
    public CafeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_rank_item_pension,parent,false);
        return new CafeHolder(view);
    }

    @Override
    public void onBindViewHolder(CafeHolder holder, int position) {
        final Cafe cafe = cafeList.get(position);
        holder.bindData(cafe,context);

        holder.setListener(new OnRecyclerViewClickListener() {
            @Override
            public void onItemClick(View v, int adapterPosition) {
                Intent intent = new Intent(context, StoreDetailActivity.class);
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
}
