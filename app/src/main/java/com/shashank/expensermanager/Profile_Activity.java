package com.shashank.expensermanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Profile_Activity extends AppCompatActivity {

    ImageView imageView, imageView1;
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_);

        imageButton = findViewById(R.id.add_imge_btn);
        imageView = findViewById(R.id.user_imageview);
        imageView1 = findViewById(R.id.back_press);

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();


            }
        });

    }
}