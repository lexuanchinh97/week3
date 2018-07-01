package sg.howard.twitterclient.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import sg.howard.twitterclient.R;
import sg.howard.twitterclient.timeline.TimelineContract;
import sg.howard.twitterclient.timeline.TimelinePresenter;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Fragment_Noti extends Fragment implements TimelineContract.View {
    TimelineContract.Presenter presenter;
    public Fragment_Noti() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.noti,container,false);
        presenter = new TimelinePresenter(this, TwitterCore.getInstance().getSessionManager().getActiveSession());
        return v;
    }

    @Override
    public void onGetStatusesSuccess(List<Tweet> data) {
        Log.d(TAG, "Loaded " + data.size());

    }

    @Override
    public void setPresenter(TimelineContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading(boolean isShow) {
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
