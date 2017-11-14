package com.example.lidia.appproject2017_2;

import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.TextView;

public class MainFindActivity extends AppCompatActivity {

    // android.app.Fragment
    private TextView mTextMessage;
    private UserModel userModel = new UserModel();
    private SharedPreferences sharedPreferences;
    private android.support.v4.app.FragmentTransaction fragmentTransaction;
    private String userUid;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener(){
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_find:
                    FindFragment findF = new FindFragment();
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content,findF,"fragmentName").commit();
                    return true;
                case R.id.navigation_ranking:
                    RankingFragment rankF = new RankingFragment();
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content,rankF,"fragmentName").commit();
                    return true;
                case R.id.navigation_myprofile:
                    ProfileFragment prodileF = new ProfileFragment();
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content,prodileF,"fragmentName").commit();
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bottom);

        sharedPreferences = getSharedPreferences("uidfile",MODE_PRIVATE);
        userUid = sharedPreferences.getString("uid","");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_find);

    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            //하드웨어 뒤로가기 버튼에 따른 이벤트 설정
            case KeyEvent.KEYCODE_BACK:
                new AlertDialog.Builder(this)
                        .setMessage("프로그램을 종료 하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton("아니오", null)
                        .show();
                break;
            default:
                break;
        }

        return super.onKeyDown(keyCode, event);
    }

    public String getUid(){
        return userUid;
    }

}



