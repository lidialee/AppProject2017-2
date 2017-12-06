package com.example.lidia.appproject2017_2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lidia.appproject2017_2.Activity.DetailActivity.PensionDetailActivity;
import com.example.lidia.appproject2017_2.Class.Pension;
import com.example.lidia.appproject2017_2.Holder.PensionHolder;
import com.example.lidia.appproject2017_2.Listener.OnRecyclerViewClickListener;
import com.example.lidia.appproject2017_2.R;

import java.util.List;


public class PensionRecyclerAdapter extends RecyclerView.Adapter<PensionHolder> {
    private List<Pension> pensionList;
    private Context context;

    public PensionRecyclerAdapter(List<Pension> pensionList, Context context) {
        this.pensionList = pensionList;
        this.context = context;
    }


    public void setPensionList(List<Pension> list){ this.pensionList = list; }

    @Override
    public PensionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_rank_item_pension,parent,false);
        return new PensionHolder(view);
    }

    @Override
    public void onBindViewHolder(PensionHolder holder, int position) {
        final Pension pension = pensionList.get(position);
        holder.bindData(pension,context);

        holder.setListener(new OnRecyclerViewClickListener() {
            @Override
            public void onItemClick(View v, int adapterPosition) {
                Intent intent = new Intent(context, PensionDetailActivity.class);
                intent.putExtra("pension",pension);
                intent.putExtra("type",1);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return pensionList.size();
    }
}
