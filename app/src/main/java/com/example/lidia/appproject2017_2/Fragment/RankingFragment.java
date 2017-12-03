package com.example.lidia.appproject2017_2.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListPopupWindow;
import android.widget.Spinner;

import com.example.lidia.appproject2017_2.Fragment.RankingShop.RankCafeFragment;
import com.example.lidia.appproject2017_2.Fragment.RankingShop.RankEtcFragment;
import com.example.lidia.appproject2017_2.Fragment.RankingShop.RankFoodFragment;
import com.example.lidia.appproject2017_2.Fragment.RankingShop.RankPenFragment;
import com.example.lidia.appproject2017_2.R;

import java.lang.reflect.Field;


/**
 * A simple {@link Fragment} subclass.
 */
public class RankingFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public RankingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_ranking, container, false);
        tabLayout = rootView.findViewById(R.id.tablayout_ranking);
        viewPager = rootView.findViewById(R.id.viewpager_ranking);

        // 팬션 , 카페, 음식, 기타 프래그먼트
        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            private final Fragment[] fragments = new Fragment[]{
                    new RankPenFragment(),
                    new RankCafeFragment(),
                    new RankFoodFragment(),
                    new RankEtcFragment(),
            };

            private final String[] titles = new String[]{"팬션/호텔", "카페", "음식점","기타"};

            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        };
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
