package com.example.lidia.appproject2017_2.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.lidia.appproject2017_2.Adapter.CommentRecyclerAdapter;
import com.example.lidia.appproject2017_2.Class.Comment;
import com.example.lidia.appproject2017_2.Class.User;
import com.example.lidia.appproject2017_2.Listener.OnCommentChangeListener;
import com.example.lidia.appproject2017_2.Listener.UserInfoEventListener;
import com.example.lidia.appproject2017_2.Model.CommentModel;
import com.example.lidia.appproject2017_2.Model.UserModel;
import com.example.lidia.appproject2017_2.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentActivity extends BasicActivity {

    @BindView(R.id.comment_back)
    ImageView back;

    @BindView(R.id.comment_recycler)
    RecyclerView commentRecycler;

    @BindView(R.id.comment_edit)
    EditText commentEdit;

    @BindView(R.id.comment_submit)
    Button submitBtn;

    private CommentRecyclerAdapter commentRecyclerAdapter;
    private CommentModel commentModel = new CommentModel();
    private String storeUid;

    OnCommentChangeListener commentChangeListener = new OnCommentChangeListener() {
        @Override
        public void getComment(List<Comment> list) {
            commentRecycler.getAdapter().notifyDataSetChanged();
            commentRecyclerAdapter.setCommentList(list);
        }
    };

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.comment_back:
                    finish();
                    break;
                case R.id.comment_submit:
                    submitComment();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);

        back.setOnClickListener(listener);
        submitBtn.setOnClickListener(listener);
        // 가게 디테일 액티비에서 가게 uid 가져오기
        storeUid =  getIntent().getStringExtra("storeUid");

        // 리사이클러뷰
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        commentRecycler.setLayoutManager(linearLayoutManager);

        // 해당 포스트별 댓글 가져오기
        commentModel.getStoreComments(storeUid);
        commentRecyclerAdapter = new CommentRecyclerAdapter(commentModel.getCommentList(), this);
        commentModel.setCommentChangeListener(commentChangeListener);
        commentRecycler.setAdapter(commentRecyclerAdapter);

    }

    private void submitComment(){
        String comment = commentEdit.getText().toString();
        commentModel.writeComment(comment,storeUid);
        commentEdit.setText("");
    }
}
