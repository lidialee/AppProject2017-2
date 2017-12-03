package com.example.lidia.appproject2017_2.Holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.lidia.appproject2017_2.Class.Pension;
import com.example.lidia.appproject2017_2.Listener.OnRecyclerViewClickListener;
import com.example.lidia.appproject2017_2.R;


public class PensionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView name;
    private TextView address;
    private TextView things;
    private TextView count;
    private OnRecyclerViewClickListener listener;

    public void setListener(OnRecyclerViewClickListener listener){ this.listener = listener; }

    public PensionHolder(View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.name_pension);
        address = itemView.findViewById(R.id.address_pension);
        things = itemView.findViewById(R.id.things_pension);
        count = itemView.findViewById(R.id.count_pension);
        itemView.setOnClickListener(this);
    }

    public void bindData(Pension pension, Context context){
        name.setText(pension.getName());
        address.setText(pension.getWholeAddress());
        things.setText(pension.getThings());
        count.setText(pension.getLove()+"");
    }
    @Override
    public void onClick(View view) {
        listener.onItemClick(view,getAdapterPosition());
    }
}
