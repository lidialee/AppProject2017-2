package com.example.lidia.appproject2017_2.Activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.lidia.appproject2017_2.R;

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


    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.comment_back:
                    finish();
                    break;
                case R.id.comment_submit:
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
    }
}
