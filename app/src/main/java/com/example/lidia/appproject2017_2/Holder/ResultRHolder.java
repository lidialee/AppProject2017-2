package com.example.lidia.appproject2017_2.Holder;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.lidia.appproject2017_2.Class.Cafe;
import com.example.lidia.appproject2017_2.Class.Rest;
import com.example.lidia.appproject2017_2.Listener.OnRecyclerViewClickListener;
import com.example.lidia.appproject2017_2.R;


public class ResultRHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView name;
    private TextView address;
    private TextView things;
    private TextView count;
    private TextView food;
    private OnRecyclerViewClickListener listener;

    public void setListener(OnRecyclerViewClickListener listener){ this.listener = listener; }

    public ResultRHolder(View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.name);
        address = itemView.findViewById(R.id.address_section);
        things = itemView.findViewById(R.id.things);
        count = itemView.findViewById(R.id.count);
        food = itemView.findViewById(R.id.food);
        itemView.setOnClickListener(this);
    }

    public void bindData(Rest rest, Context context){
        name.setText(rest.getName());
        name.setPaintFlags(name.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        address.setText(rest.getSectionArea());
        things.setText(rest.getThings());
        count.setText(rest.getLove()+"");

        int foodInfo = rest.getIsFood();
        if(foodInfo == 1)
            food.setText("반려동물 음식 판매 O");
        if(foodInfo == 2)
            food.setText("반려동물 음식 판매 X");
    }
    @Override
    public void onClick(View view) {
        listener.onItemClick(view,getAdapterPosition());
    }
}
