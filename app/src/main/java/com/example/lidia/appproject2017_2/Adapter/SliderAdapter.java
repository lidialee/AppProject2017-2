package com.example.lidia.appproject2017_2.Adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.lidia.appproject2017_2.R;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends PagerAdapter{
    Context context;
    LayoutInflater inflater;
    List<String> imagelist = new ArrayList<>();

    public SliderAdapter(Context context, List<String> imagelist) {
        this.context = context;
        this.imagelist = imagelist;
    }

    @Override
    public int getCount() {
        return imagelist.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.slider,container,false);
        ImageView image = rootView.findViewById(R.id.slider_image);
        // 이미지뷰에 붙이는 작업이 있어야 하지 않겠니?

        System.out.println("들어오고 있는 것인가 "+ imagelist.get(position));
        Glide.with(context).load(imagelist.get(position)).into(image);
        container.addView(rootView);
        return rootView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
