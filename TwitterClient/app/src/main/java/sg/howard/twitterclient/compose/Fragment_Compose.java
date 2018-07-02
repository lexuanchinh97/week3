package sg.howard.twitterclient.compose;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.models.Tweet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import sg.howard.twitterclient.R;
import sg.howard.twitterclient.compose.ComposeContract;
import sg.howard.twitterclient.compose.ComposeTweetPresenter;

public class Fragment_Compose extends Fragment implements ComposeContract.View {
    Button btnSend;
    EditText edtCompose;
    ProgressBar loader;
    ComposeContract.Presenter presenter;
    public Fragment_Compose() {
    }

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_compose,container,false);
        btnSend = v.findViewById(R.id.btnSend);
        edtCompose = v.findViewById(R.id.edtCompose);
        loader = v.findViewById(R.id.loader);
        presenter = new ComposeTweetPresenter(this, TwitterCore.getInstance().getSessionManager().getActiveSession());
        btnSend.setOnClickListener( view -> presenter.sendTweet(edtCompose.getText().toString())
        );
        return v;
    }

    @Override
    public void sendTweetSuccess(Result<Tweet> result) {
        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
        edtCompose.setText("");
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
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
