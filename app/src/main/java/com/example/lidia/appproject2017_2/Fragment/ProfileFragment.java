package com.example.lidia.appproject2017_2.Fragment;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lidia.appproject2017_2.Activity.EditProfileActivity;
import com.example.lidia.appproject2017_2.Activity.MainFindActivity;
import com.example.lidia.appproject2017_2.Activity.CafeCommon1Activity;
import com.example.lidia.appproject2017_2.Activity.EtcCommon1Activity;
import com.example.lidia.appproject2017_2.Activity.PensionCommon1Activity;
import com.example.lidia.appproject2017_2.Activity.RestCommon1Activity;
import com.example.lidia.appproject2017_2.Fragment.Love.LovePFragment;
import com.example.lidia.appproject2017_2.Fragment.Love.LoveCFragment;
import com.example.lidia.appproject2017_2.Listener.UserInfoEventListener;
import com.example.lidia.appproject2017_2.R;
import com.example.lidia.appproject2017_2.Class.User;
import com.example.lidia.appproject2017_2.Model.UserModel;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private String userUid;
    private CircleImageView profileImage;
    private TextView name, email, type;
    private ImageView setting, editStore;
    private MainFindActivity activity;
    private Dialog registerStoreDialog;
   // private TabLayout tabLayout;
   // private ViewPager viewPager;
    private UserModel userModel = new UserModel();


    View.OnClickListener layoutClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = null;
            switch (view.getId()) {
                case R.id.register_dialog_pension:
                    registerStoreDialog.dismiss();
                    intent = new Intent(getContext(), PensionCommon1Activity.class);
                    break;
                case R.id.register_dialog_cafe:
                    registerStoreDialog.dismiss();
                    intent= new Intent(getContext(), CafeCommon1Activity.class);
                    break;
                case R.id.register_dialog_rest:
                    registerStoreDialog.dismiss();
                    intent = new Intent(getContext(), RestCommon1Activity.class);
                    break;
                case R.id.register_dialog_etc:
                    registerStoreDialog.dismiss();
                    intent = new Intent(getContext(), EtcCommon1Activity.class);
                    break;
            }
            startActivity(intent);
            getActivity().overridePendingTransition(0, 0);
        }
    };


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
        editStore = rootView.findViewById(R.id.register_store);

       // tabLayout = rootView.findViewById(R.id.tablayout_profile_frag);
       // viewPager = rootView.findViewById(R.id.ViewPagercontainerProfile);

        activity = (MainFindActivity) getActivity();
        this.userUid = activity.getUid();


//        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(activity.getSupportFragmentManager()) {
//            private final Fragment[] fragments = new Fragment[]{
//                    new LoveCFragment(),
//                    new LovePFragment(),
//                    // 여기를 그냥 전부 love누른 숙박 fragment, 카페fragment, 이렇게 4개로 나눔
//            };
//
//            private final String[] titles = new String[]{"위시 리스트", "좋아요 가게", "작성한 리뷰"};
//
//            @Override
//            public Fragment getItem(int position) {
//                return fragments[position];
//            }
//
//            @Override
//            public int getCount() {
//                return fragments.length;
//            }
//
//            @Override
//            public CharSequence getPageTitle(int position) {
//                return titles[position];
//            }
//        };
//
//        viewPager.setAdapter(pagerAdapter);
//        tabLayout.setupWithViewPager(viewPager);

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
                    name.setText(user.getName());
                    email.setText(user.getEmail());

                    if (user.getUserType() == 0) {
                        type.setText("일반사용자");
                        editStore.setVisibility(View.INVISIBLE);
                    } else {
                        type.setText("가게주");
                        editStore.setVisibility(View.VISIBLE);
                        editStore.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                registerStoreDialog.show();
                            }
                        });
                    }
                } else
                    System.out.println("유저가 안옴 문제 있음");
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, EditProfileActivity.class);
                startActivity(intent);
            }
        });

        initiateDialog();

    }

    @Override
    public void onStart() {
        super.onStart();
        activity = (MainFindActivity) getActivity();
        this.userUid = activity.getUid();
        userModel.getUserInfo(userUid);
    }

    private void initiateDialog() {
        registerStoreDialog = new Dialog(getContext());
        registerStoreDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        registerStoreDialog.setContentView(R.layout.register_store_dialog);
        //registerStoreDialog.setCanceledOnTouchOutside(false);

        ImageView pension = registerStoreDialog.findViewById(R.id.register_dialog_pension);
        ImageView cafe = registerStoreDialog.findViewById(R.id.register_dialog_cafe);
        ImageView rest = registerStoreDialog.findViewById(R.id.register_dialog_rest);
        ImageView etc = registerStoreDialog.findViewById(R.id.register_dialog_etc);

        Glide.with(this).load(R.drawable.pension2).into(pension);
        Glide.with(this).load(R.drawable.cafe2).into(cafe);
        Glide.with(this).load(R.drawable.rest2).into(rest);
        Glide.with(this).load(R.drawable.etc2).into(etc);

        pension.setOnClickListener(layoutClickListener);
        cafe.setOnClickListener(layoutClickListener);
        rest.setOnClickListener(layoutClickListener);
        etc.setOnClickListener(layoutClickListener);

    }
}

