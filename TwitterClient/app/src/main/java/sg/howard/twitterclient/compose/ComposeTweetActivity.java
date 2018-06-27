package sg.howard.twitterclient.compose;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.models.Tweet;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import sg.howard.twitterclient.R;
import sg.howard.twitterclient.timeline.TimelineActivity;

public class ComposeTweetActivity extends AppCompatActivity implements ComposeContract.View{
    Button btnSend;
    EditText edtCompose;
    ProgressBar loader;
    ComposeContract.Presenter presenter;
    ImageView imgExit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        btnSend = findViewById(R.id.btnSend);
        edtCompose = findViewById(R.id.edtCompose);
        loader = findViewById(R.id.loader);
        imgExit=findViewById(R.id.imgExit);
        presenter = new ComposeTweetPresenter(this, TwitterCore.getInstance().getSessionManager().getActiveSession());
        imgExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ComposeTweetActivity.this,TimelineActivity.class);
                startActivity(intent);
            }
        });
        btnSend.setOnClickListener( view -> presenter.sendTweet(edtCompose.getText().toString()));
    }

    @Override
    public void setPresenter(ComposeContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading(boolean isShow) {
        loader.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sendTweetSuccess(Result<Tweet> result) {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        finish();
    }
}
