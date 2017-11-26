package com.example.lidia.appproject2017_2.Holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.lidia.appproject2017_2.Listener.OnImageClickListener;
import com.example.lidia.appproject2017_2.R;


public class NewImageHolder extends RecyclerView.ViewHolder {
    private Context mContext;
    private ImageView mImageItem;
    private OnImageClickListener mListener;

    public void setListener(OnImageClickListener mListener) {
        this.mListener = mListener;
    }

    public NewImageHolder(View itemView,Context context) {
        super(itemView);

        this.mContext = context;
        mImageItem = (ImageView) itemView.findViewById(R.id.postimageItem);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onImageCliecked(v,getAdapterPosition());
            }
        });
    }
    public void bindImage(String imageString) {
        Glide.with(mContext).load(imageString).into(mImageItem);
    }
}
