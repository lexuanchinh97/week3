package sg.howard.twitterclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Image_Activity extends AppCompatActivity {
    ImageView image,imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_);
        String url=getIntent().getStringExtra("url");
        image=findViewById(R.id.image);
        imgBack=findViewById(R.id.imgBack);
        Glide.with(this).load(url).into(image);

        imgBack.setOnClickListener(view -> {
            finish();
        });
    }
}
