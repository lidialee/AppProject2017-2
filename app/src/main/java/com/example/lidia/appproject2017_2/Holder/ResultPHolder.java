package com.example.lidia.appproject2017_2.Holder;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.lidia.appproject2017_2.Class.Pension;
import com.example.lidia.appproject2017_2.Listener.OnRecyclerViewClickListener;
import com.example.lidia.appproject2017_2.R;


public class ResultPHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView name;
    private TextView address;
    private TextView things;
    private TextView count;
    private TextView environment;
    private OnRecyclerViewClickListener listener;

    public void setListener(OnRecyclerViewClickListener listener){ this.listener = listener; }

    public ResultPHolder(View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.name);
        address = itemView.findViewById(R.id.address_section);
        things = itemView.findViewById(R.id.things);
        count = itemView.findViewById(R.id.count);
        environment = itemView.findViewById(R.id.environment);
        itemView.setOnClickListener(this);
    }

    public void bindData(Pension pension, Context context){
        name.setText(pension.getName());
        name.setPaintFlags(name.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        address.setText(pension.getSectionArea());
        things.setText(pension.getThings());
        count.setText(pension.getLove()+"");
        environment.setText(pension.getEnvironment());
    }
    @Override
    public void onClick(View view) {
        listener.onItemClick(view,getAdapterPosition());
    }
}
