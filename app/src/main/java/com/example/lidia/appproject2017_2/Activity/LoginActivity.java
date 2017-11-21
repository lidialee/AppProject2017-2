package com.example.lidia.appproject2017_2.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lidia.appproject2017_2.Listener.UserInfoEventListener;
import com.example.lidia.appproject2017_2.R;
import com.example.lidia.appproject2017_2.Class.User;
import com.example.lidia.appproject2017_2.Model.UserModel;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.login_email_edit)
    EditText email;

    @BindView(R.id.login_pw_edit)
    EditText password;

    @BindView(R.id.login_usual_btn)
    Button ususlLogin;

    @BindView(R.id.login_google_btn)
    SignInButton googleLogin;

    @BindView(R.id.login_signin_text)
    TextView siginText;

    @BindView(R.id.login_loading_img)
    ImageView loadingImage;

    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient mGoogleApiClient;
    private UserModel userModel = new UserModel();
    private GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener;
    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, onConnectionFailedListener)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent siginIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(siginIntent, RC_SIGN_IN);
            }
        });

        ususlLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userEmail = email.getText().toString();
                final String userPass = password.getText().toString();

                if (!userEmail.equals("") && !userPass.equals("")) {
                    showBall();         //  loading image 보이기

                    Task<AuthResult> authResultTask
                            = mAuth.signInWithEmailAndPassword(userEmail, userPass);

                    authResultTask.addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(final AuthResult authResult) {
                            setUidIntoSharePreference(authResult.getUser().getUid());

                            Intent intent = new Intent(getApplicationContext(), MainFindActivity.class);
                            startActivity(intent);
                            finish();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            hideBall();
                            Snackbar.make(getWindow().getDecorView().getRootView(), "로그인 실패", Snackbar.LENGTH_LONG).show();
                        }
                    });
                } else
                    Snackbar.make(getWindow().getDecorView().getRootView(), "빈칸이 존재합니다", Snackbar.LENGTH_LONG).show();
            }
        });

        siginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SigninActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }else{
                Log.e("FAIL", "onActivityResult");
            }

        }
    }


    /** onComplete에서 성공으로 받아온 userBox 정보를 활용합니다 */
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            userModel.getUserInfo(mAuth.getCurrentUser().getUid());

                            userModel.setUserInfoEventListener(new UserInfoEventListener() {
                                @Override
                                public void getUser(User user) {
                                    if(user==null){
                                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                        String name = firebaseUser.getDisplayName();
                                        String email = firebaseUser.getEmail();
                                        String image = String.valueOf(firebaseUser.getPhotoUrl());
                                        String uid = firebaseUser.getUid();
                                        userModel.writeGoogleUserToDatabase(name, email,image,uid);
                                    }

                                    setUidIntoSharePreference(mAuth.getCurrentUser().getUid());
                                    Intent intent = new Intent(LoginActivity.this,MainFindActivity.class);
                                    startActivity(intent);
                                }
                            });

                        } else {
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void showBall() {
        loadingImage.setVisibility(View.VISIBLE);
        ususlLogin.setEnabled(false);
        siginText.setVisibility(View.INVISIBLE);

        Animation loadingAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.loading);
        loadingImage.startAnimation(loadingAnimation);
    }

    private void hideBall() {
        loadingImage.setVisibility(View.GONE);
        ususlLogin.setEnabled(true);
        siginText.setVisibility(View.VISIBLE);
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

    private void setUidIntoSharePreference(String uid){
        sharedPreferences = getSharedPreferences("uidfile",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("uid",uid);
        editor.apply();

    }
}
