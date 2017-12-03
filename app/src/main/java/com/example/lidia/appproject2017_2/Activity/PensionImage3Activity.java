package com.example.lidia.appproject2017_2.Activity;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.lidia.appproject2017_2.Adapter.NewPostImageRecyclerAdapter;
import com.example.lidia.appproject2017_2.Listener.OnImageAddedListener;
import com.example.lidia.appproject2017_2.Model.PensionModel;
import com.example.lidia.appproject2017_2.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PensionImage3Activity extends BasicActivity {
    @BindView(R.id.pension3_back)
    ImageView backStep;

    @BindView(R.id.pension3_done)
    ImageView done;

    @BindView(R.id.pension3_image)
    ImageView recentImage;

    @BindView(R.id.pension3_recycler)
    RecyclerView imageRecycler;

    @BindView(R.id.pension3_camera)
    ImageView camera;

    private List<Uri> mImageUriList = new ArrayList<>();
    private NewPostImageRecyclerAdapter imageAdapter;
    private PensionModel pensionModel = new PensionModel();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();


    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.pension3_back:
                    finish();
                    overridePendingTransition(0, 0);
                    break;
                case R.id.pension3_done:
                    // 이게 둘리면 이제 서버로 저장되야겠지

                    if (mImageUriList.isEmpty()) {
                        Snackbar.make(getWindow().getDecorView().getRootView(), "한장의 사진이라도 추가해주십시오", Snackbar.LENGTH_SHORT).show();
                        return;
                    }

                    /**
                     * 2에서 온 번들에 마지막에 마지막으로 현재 로그인한 사용자의 uid를 외부저장소에서
                     * 불러와서 번들에 저장하고 이 번들을 storeImage()의 파라미터로 던져준다*/

                    Bundle finalBundle = getIntent().getExtras();
                    SharedPreferences sharedPreferences = getSharedPreferences("uidfile", MODE_PRIVATE);
                    finalBundle.putString("userUid", sharedPreferences.getString("uid", ""));

                    // 번들에서 '서울광역시' 같은 지역 단위를 불러와 아래 데이터베이스 계층 이름으로 추가 한다
                    String areaSection = finalBundle.getString("area");
                    if(areaSection!=null){
                        DatabaseReference storeRef = mDatabase.child("PensionORHotel").child(areaSection).push();
                        String storeKey = storeRef.getKey();
                        pensionModel.storeImage(changeToInputStream(mImageUriList), storeRef, storeKey, finalBundle);
                        showAlertDialog("포스트를 등록하였습니다", "등록한 포스트 확인하기",finalBundle);
                    }
                    break;

                case R.id.pension3_camera:
                    Intent intent2 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent2, 100);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pension_image);
        ButterKnife.bind(this);


        // 버튼 리스너 부착
        backStep.setOnClickListener(listener);
        done.setOnClickListener(listener);
        camera.setOnClickListener(listener);

        settingImageRecycler();

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            //하드웨어 뒤로가기 버튼에 따른 이벤트 설정
            case KeyEvent.KEYCODE_BACK:
                finish();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;

        if (requestCode == 100) {
            mImageUriList.add(data.getData());
            imageAdapter.setUriImageList(mImageUriList);

            Glide.with(this)
                    .load(data.getData())
                    .into(recentImage);
        }
    }

    private void settingImageRecycler() {
        LinearLayoutManager imageLayoutManager = new LinearLayoutManager(getBaseContext());
        imageLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        imageRecycler.setLayoutManager(imageLayoutManager);

        imageAdapter = new NewPostImageRecyclerAdapter(getBaseContext(), mImageUriList);

        // 이미지 추가 리스너
        imageAdapter.setAddListener(new OnImageAddedListener() {
            @Override
            public void imageAdd() {
                imageRecycler.getAdapter().notifyDataSetChanged();
                imageRecycler.removeAllViews();
            }
        });

        imageRecycler.setAdapter(imageAdapter);
    }

    private List<InputStream> changeToInputStream(List<Uri> list) {
        List<InputStream> newList = new ArrayList<>();
        InputStream is;
        ContentResolver contentResolver;
        try {
            for (Uri i : list) {
                contentResolver = getContentResolver();
                is = contentResolver.openInputStream(i);
                newList.add(is);
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return newList;
    }

    private void showAlertDialog(String mainText, String buttonText, final Bundle bundle) {
        AlertDialog dialog = new AlertDialog.Builder(PensionImage3Activity.this)
                .setMessage(mainText)
                .setPositiveButton(buttonText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Intent intent = new Intent(PensionImage3Activity.this, StoreDetailActivity.class);
//                        //원래 이거 보내고 데베에서 찾아야 된다 intent.putExtra("storeUid",storeUid);
//                        intent.putExtras(bundle);
//                        startActivity(intent);
//                        overridePendingTransition(0, 0);
                        finish();
                    }
                }).create();
        dialog.show();
    }

}
