package com.example.lidia.appproject2017_2.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.lidia.appproject2017_2.R;

public class PensionCommon1Activity extends BasicActivity {

    private ImageView next;
    Uri i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pension_common);

        next = findViewById(R.id.pension_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PensionCommon1Activity.this, PensionKeyword2Activity.class);
                startActivity(intent);
            }
        });

    }
}
