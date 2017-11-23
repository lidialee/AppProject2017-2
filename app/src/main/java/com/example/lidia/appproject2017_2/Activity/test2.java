package com.example.lidia.appproject2017_2.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.lidia.appproject2017_2.R;

public class test2 extends AppCompatActivity {

    ImageView next,back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pension_common);

        next = findViewById(R.id.pension_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(test2.this, PensionKeyword2Activity.class);
                startActivity(intent);
            }
        });



    }
}
