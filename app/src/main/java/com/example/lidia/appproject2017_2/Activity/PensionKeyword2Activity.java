package com.example.lidia.appproject2017_2.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lidia.appproject2017_2.R;

public class PensionKeyword2Activity extends BasicActivity {

    ImageView back, next;
    RelativeLayout dog,cat;
    TextView guideText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pension_keyword);
        back  = findViewById(R.id.pension2_back);
        next =  findViewById(R.id.pension2_next);
        dog = findViewById(R.id.dog_layout);
        cat = findViewById(R.id.cat_layout);
        guideText = findViewById(R.id.guide_text);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dog.setVisibility(View.VISIBLE);
                cat.setVisibility(View.GONE);
                guideText.setVisibility(View.GONE);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cat.setVisibility(View.VISIBLE);
                dog.setVisibility(View.GONE);
                guideText.setVisibility(View.GONE);
            }
        });
    }
}
