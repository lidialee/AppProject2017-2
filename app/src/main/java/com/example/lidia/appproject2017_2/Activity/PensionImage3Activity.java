package com.example.lidia.appproject2017_2.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.lidia.appproject2017_2.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PensionImage3Activity extends BasicActivity {
    @BindView(R.id.pension3_back)
    ImageView backStep;

    @BindView(R.id.pension3_done)
    ImageView done;


    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.pension3_back:
                    finish();
                    break;
                case R.id.pension3_done:
                    // 이게 둘리면 이제 서버로 저장되야겠지

                    Intent intent = new Intent(PensionImage3Activity.this, MainFindActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pension_image);
        ButterKnife.bind(this);

        backStep.setOnClickListener(listener);
        done.setOnClickListener(listener);
    }
}
