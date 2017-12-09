package com.example.lidia.appproject2017_2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lidia.appproject2017_2.Activity.DetailActivity.EtcDetailActivity;
import com.example.lidia.appproject2017_2.Class.Etc;
import com.example.lidia.appproject2017_2.Holder.ResultEHolder;
import com.example.lidia.appproject2017_2.Listener.OnRecyclerViewClickListener;
import com.example.lidia.appproject2017_2.R;

import java.util.List;


public class ResultERecyclerAdapter extends RecyclerView.Adapter<ResultEHolder> {
    private List<Etc> EtcList;
    private Context context;

    public ResultERecyclerAdapter(List<Etc> EtcList, Context context) {
        this.EtcList = EtcList;
        this.context = context;
    }

    public void setEtcList(List<Etc> list){ this.EtcList = list; }

    @Override
    public ResultEHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_result_e_item,parent,false);
        return new ResultEHolder(view);
    }

    @Override
    public void onBindViewHolder(ResultEHolder holder, int position) {
        final Etc etc = EtcList.get(position);
        holder.bindData(etc,context);

        holder.setListener(new OnRecyclerViewClickListener() {
            @Override
            public void onItemClick(View v, int adapterPosition) {
                Intent intent = new Intent(context, EtcDetailActivity.class);
                intent.putExtra("etc",etc);
                intent.putExtra("type",4);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return EtcList.size();
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
