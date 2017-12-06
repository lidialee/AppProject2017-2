package com.example.lidia.appproject2017_2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lidia.appproject2017_2.Activity.DetailActivity.EtcDetailActivity;
import com.example.lidia.appproject2017_2.Activity.DetailActivity.PensionDetailActivity;
import com.example.lidia.appproject2017_2.Class.Etc;
import com.example.lidia.appproject2017_2.Holder.EtcHolder;
import com.example.lidia.appproject2017_2.Listener.OnRecyclerViewClickListener;
import com.example.lidia.appproject2017_2.R;

import java.util.List;


public class EtcRecyclerAdapter extends RecyclerView.Adapter<EtcHolder> {
    private List<Etc> etcList;
    private Context context;

    public EtcRecyclerAdapter(List<Etc> etcList, Context context) {
        this.etcList = etcList;
        this.context = context;
    }


    public void setEtcList(List<Etc> list){ this.etcList = list; }

    @Override
    public EtcHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_rank_item_pension,parent,false);
        return new EtcHolder(view);
    }

    @Override
    public void onBindViewHolder(EtcHolder holder, int position) {
        final Etc etc = etcList.get(position);
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
        return etcList.size();
    }
}
