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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.example.lidia.appproject2017_2.Listener.UserInfoEventListener;
import com.example.lidia.appproject2017_2.R;
import com.example.lidia.appproject2017_2.Class.User;
import com.example.lidia.appproject2017_2.Model.UserModel;

import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {
    @BindView(R.id.editprofile_circle_image)
    CircleImageView circleImageView;

    @BindView(R.id.edit_camera_image)
    ImageView cameraImage;

    @BindView(R.id.edit_done)
    Button done;

    @BindView(R.id.edit_name_edit)
    EditText nameEdit;

    @BindView(R.id.edit_userGroup)
    RadioGroup userGroup;

    @BindView(R.id.edit_userinfo_check1)
    RadioButton userBox;

    @BindView(R.id.edit_userinfo_check2)
    RadioButton ownerBox;

    @BindView(R.id.edit_petGroup)
    RadioGroup petGroup;

    @BindView(R.id.edit_petcount_check1)
    RadioButton dogBox;

    @BindView(R.id.edit_petcount_check2)
    RadioButton catBox;

    @BindView(R.id.edit_petcount_check3)
    RadioButton etcBox;

    private Uri mImageUri = null;
    private String mImageString = null;
    private int inputUserType = 100 ;
    private int inputPetType = 100 ;
    private SharedPreferences sharedPreferences;
    private UserModel userModel = new UserModel();
    private User tarUser;

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.edit_camera_image:
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 100);
                    break;
                case R.id.edit_userinfo_check1:
                    inputUserType = 0;
                    break;
                case R.id.edit_userinfo_check2:
                    inputUserType = 1;
                    break;
                case R.id.edit_petcount_check1:
                    inputPetType = 0;
                    break;
                case R.id.edit_petcount_check2:
                    inputPetType = 1;
                    break;
                case R.id.edit_petcount_check3:
                    inputPetType = 2;
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);

        sharedPreferences = getSharedPreferences("uidfile",MODE_PRIVATE);
        String userUid = sharedPreferences.getString("uid","");


        cameraImage.setOnClickListener(listener);
        done.setOnClickListener(listener);
        userBox.setOnClickListener(listener);
        ownerBox.setOnClickListener(listener);
        dogBox.setOnClickListener(listener);
        catBox.setOnClickListener(listener);
        etcBox.setOnClickListener(listener);

        userModel.getUserInfo(userUid);
        userModel.setUserInfoEventListener(new UserInfoEventListener() {
            @Override
            public void getUser(User user) {
                if (user != null)
                    tarUser = user;
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 1. 그림을 변경하는 경우
                // 2. 그림을 변경하지 않는 경우, 이름만 바꿀경우  mImageUri== null;
                final String newName = nameEdit.getText().toString();
                final int userNum = inputUserType;
                final int petNum = inputPetType;

                AlertDialog dialog = new AlertDialog.Builder(EditProfileActivity.this)
                        .setMessage("변경하시겠습니까?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(tarUser!=null){
                                    if (mImageUri != null && !newName.equals("") && inputPetType!=100 && inputUserType!=100) {
                                        changeUriToInputStream(mImageUri);
                                        userModel.updateProfileWithImage(mImageUri,tarUser.getUserUid(),tarUser.getEmail(),newName,userNum,petNum);
                                        finish();
                                    } else
                                        Snackbar.make(getWindow().getDecorView().getRootView(), "입력사항이 잘못되었습니다 ", Snackbar.LENGTH_LONG).show();

                                }else{
                                    System.out.println("사용자 정보가 오지 않았어");
                                }
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {}
                        }).create();
                dialog.show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;

        if (requestCode == 100) {
            mImageUri = data.getData();
            mImageString = mImageUri.toString();
            Glide.with(this).load(mImageString).into(circleImageView);
        }
    }


    private InputStream changeUriToInputStream(Uri imageUri) {
        InputStream is = null;
        try {
            ContentResolver resolver = getContentResolver();
            is = resolver.openInputStream(imageUri);
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return is;
    }
}
