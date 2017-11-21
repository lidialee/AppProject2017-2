package com.example.lidia.appproject2017_2.Activity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.example.lidia.appproject2017_2.Model.UserModel;
import com.example.lidia.appproject2017_2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class SigninActivity extends AppCompatActivity {

    @BindView(R.id.signin_circle_image)
    CircleImageView circleImageView;

    @BindView(R.id.signin_camera_image)
    ImageView cameraImage;

    @BindView(R.id.signin_done)
    ImageView done;

    @BindView(R.id.signin_email_edit)
    EditText emailEdit;

    @BindView(R.id.signin_pass_edit)
    EditText passEdit;

    @BindView(R.id.signin_name_edit)
    EditText nameEdit;

    @BindView(R.id.signin_userGroup)
    RadioGroup userGroup;

    @BindView(R.id.signin_userinfo_check1)
    RadioButton userBox;

    @BindView(R.id.signin_userinfo_check2)
    RadioButton ownerBox;

    @BindView(R.id.signin_petGroup)
    RadioGroup petGroup;

    @BindView(R.id.signin_petcount_check1)
    RadioButton dogBox;

    @BindView(R.id.signin_petcount_check2)
    RadioButton catBox;

    @BindView(R.id.signin_petcount_check3)
    RadioButton etcBox;

    private SharedPreferences sharedPreferences;
    private final int PICK_FROM_ALBUM = 100;
    private FirebaseAuth firebaseAuth;
    private StorageReference storage;
    private UserModel userModel;
    private ProgressDialog mProgressDialog;
    private Uri userProfileUri;
    private int inputUserType;
    private int inputPetType;

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.signin_done:
                    signUp();
                    break;
                case R.id.signin_camera_image:
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, PICK_FROM_ALBUM);
                    break;
                case R.id.signin_userinfo_check1:
                    inputUserType = 0;
                    break;
                case R.id.signin_userinfo_check2:
                    inputUserType = 1;
                    break;
                case R.id.signin_petcount_check1:
                    inputPetType = 0;
                    break;
                case R.id.signin_petcount_check2:
                    inputPetType = 1;
                    break;
                case R.id.signin_petcount_check3:
                    inputPetType = 2;
                    break;
            }
        }
    };


    // main
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        ButterKnife.bind(this);

        // 생성자 만들기
        setup();

        // 리스너
        cameraImage.setOnClickListener(listener);
        done.setOnClickListener(listener);
        userBox.setOnClickListener(listener);
        ownerBox.setOnClickListener(listener);
        dogBox.setOnClickListener(listener);
        catBox.setOnClickListener(listener);
        etcBox.setOnClickListener(listener);

    }


    private void setup() {
        storage = FirebaseStorage.getInstance()
                .getReferenceFromUrl("gs://pettogether-11ca5.appspot.com")
                .child("userProfileUri");
        firebaseAuth = FirebaseAuth.getInstance();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;

        if (requestCode == PICK_FROM_ALBUM) {
            userProfileUri = data.getData();

            if (userProfileUri != null) {
                Glide.with(SigninActivity.this)
                        .load(userProfileUri)
                        .into(circleImageView);
            }
        }
    }


    // 회원가입 완료 버튼을 클릭시 모든 입력 사항이 올바른지 확인하는 함수
    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(emailEdit.getText().toString())) {
            emailEdit.setError("이메일을 입력해주세요");
            result = false;
        } else
            emailEdit.setError(null);

        if (TextUtils.isEmpty(nameEdit.getText().toString())) {
            nameEdit.setError("이름을 입력해주세요");
            result = false;
        } else
            nameEdit.setError(null);

        if (TextUtils.isEmpty(passEdit.getText().toString())) {
            passEdit.setError("패스워드를 입력해주세요");
            result = false;
        } else
            passEdit.setError(null);

        if (userProfileUri == null) {
            Snackbar.make(getWindow().getDecorView().getRootView(), "사진을 등록해주세요", Snackbar.LENGTH_SHORT).show();
            result = false;
        } else
            passEdit.setError(null);

        return result;
    }

    private void signUp(){
        if(!validateForm())
            return;

        String userEmail = emailEdit.getText().toString();
        String userPass = passEdit.getText().toString();

        showProgressDialog();

        firebaseAuth.createUserWithEmailAndPassword(userEmail,userPass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            onAuthSuccess(task.getResult().getUser());
                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Snackbar.make(getWindow().getDecorView().getRootView(),"실패",Snackbar.LENGTH_SHORT).show();
                hideProgressDialog();

            }
        });
    }

    private void onAuthSuccess(final FirebaseUser user) {
        final String userName = nameEdit.getText().toString();
        final int userType= inputUserType;
        final int petType = inputPetType;

        try {
            ContentResolver resolver = getContentResolver();
            InputStream input = resolver.openInputStream(userProfileUri);

            assert input != null;

            UploadTask uploadTask = storage.child(user.getUid()).putStream(input);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    hideProgressDialog();

                    userModel= new UserModel();
                    userModel.writeUserToDatabase(userName,user.getEmail(),taskSnapshot.getDownloadUrl().toString(),user.getUid(),userType,petType);
                    setUidIntoSharePreference(user.getUid());

                    Intent intent = new Intent(SigninActivity.this, MainFindActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        } catch (Exception e) {
            Log.e("에러확인", e.getLocalizedMessage());
        }
    }




    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                new AlertDialog.Builder(this)
                        .setMessage("회원가입 페이지에서 나가시겠습니까?")
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

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage(" 회원 등록 중입니다. \n 잠시만 기다려주세요");
        }
        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private void setUidIntoSharePreference(String uid){
        sharedPreferences = getSharedPreferences("uidfile",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("uid",uid);
        editor.apply();

    }


}
