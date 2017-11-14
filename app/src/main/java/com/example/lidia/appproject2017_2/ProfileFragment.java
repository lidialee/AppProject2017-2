package com.example.lidia.appproject2017_2;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private String userUid;
    private CircleImageView profileImage;
    private TextView name;
    private TextView email;
    private TextView type;
    private ImageView setting;
    private MainFindActivity activity;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private UserModel userModel = new UserModel();


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        profileImage = rootView.findViewById(R.id.profile_image_mypage);
        name = rootView.findViewById(R.id.name_text_mypage);
        email = rootView.findViewById(R.id.email_text_mypage);
        type = rootView.findViewById(R.id.userType_text_mypage);
        setting = rootView.findViewById(R.id.edit_info_image);

        tabLayout = rootView.findViewById(R.id.tablayout_profile_frag);
        viewPager = rootView.findViewById(R.id.ViewPagercontainerProfile);

        activity = (MainFindActivity) getActivity();
        this.userUid = activity.getUid();


        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(activity.getSupportFragmentManager()) {
            private final Fragment[] fragments = new Fragment[]{
                    new WishFragment(),
                    new GoodFragment(),
                    new ReviewFragment(),
            };

            private final String[] titles = new String[]{"위시 리스트","좋아요 가게","작성한 리뷰"};

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
        System.out.println("불려져");
        tabLayout.setupWithViewPager(viewPager);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        userModel.getUserInfo(userUid);
        userModel.setUserInfoEventListener(new UserInfoEventListener() {
            @Override
            public void getUser(User user) {
                if (user != null) {
                    Glide.with(ProfileFragment.this).load(user.getUserImage()).into(profileImage);
                    name.setText(user.getName()+"");
                    email.setText(user.getEmail()+"");

                    if(user.getUserType()==0)
                        type.setText("일반사용자");
                    else
                        type.setText("가게주");
                } else
                    System.out.println("유저가 안옴 문제 있음");
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity,EditProfileActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        activity = (MainFindActivity) getActivity();
        this.userUid = activity.getUid();
        userModel.getUserInfo(userUid);
    }
}

