package com.example.lidia.appproject2017_2.Holder;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.lidia.appproject2017_2.Class.Cafe;
import com.example.lidia.appproject2017_2.Class.Etc;
import com.example.lidia.appproject2017_2.Listener.OnRecyclerViewClickListener;
import com.example.lidia.appproject2017_2.R;


public class EtcHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView name;
    private TextView address;
    private TextView things;
    private TextView count;
    private OnRecyclerViewClickListener listener;

    public void setListener(OnRecyclerViewClickListener listener){ this.listener = listener; }

    public EtcHolder(View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.name_pension);
        address = itemView.findViewById(R.id.address_pension);
        things = itemView.findViewById(R.id.things_pension);
        count = itemView.findViewById(R.id.count_pension);
        itemView.setOnClickListener(this);
    }

    public void bindData(Etc etc, Context context){
        name.setText(etc.getName());
        name.setPaintFlags(name.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        address.setText(etc.getWholeAddress());
        things.setText(etc.getThings());
        count.setText(etc.getLove()+"");
    }
    @Override
    public void onClick(View view) {
        listener.onItemClick(view,getAdapterPosition());
    }
}
