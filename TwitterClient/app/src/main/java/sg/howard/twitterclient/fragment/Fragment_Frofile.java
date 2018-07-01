package sg.howard.twitterclient.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import sg.howard.twitterclient.R;
import sg.howard.twitterclient.timeline.TimelineContract;
import sg.howard.twitterclient.timeline.TimelinePresenter;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Fragment_Frofile extends Fragment implements TimelineContract.View {
    TimelineContract.Presenter presenter;
    ImageView imgBackground,imgProfile;
    TextView txtName,txtFollowing,txtFollowers;
    List<Tweet>list;
    public Fragment_Frofile() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_frofile,container,false);
        list=new ArrayList<>();
        imgBackground=v.findViewById(R.id.img_background);
        imgProfile=v.findViewById(R.id.img_profile);
        txtName=v.findViewById(R.id.txtName);
        txtFollowers=v.findViewById(R.id.txtFlowers);
        txtFollowing=v.findViewById(R.id.txtFollowing);
        presenter = new TimelinePresenter(this, TwitterCore.getInstance().getSessionManager().getActiveSession());
        presenter.startUser();

        return v;
    }

    @Override
    public void onGetStatusesSuccess(List<Tweet> data) {
        Log.d(TAG, "Loaded " + data.size());
        if(data!=null){
            list=data;
        }
        display();
    }

    private void display() {
        txtFollowing.setText("Following "+list.get(0).user.friendsCount);
        txtName.setText(list.get(0).user.name);
        txtFollowers.setText("Followers "+list.get(0).user.followersCount);
        Glide.with(getActivity()).load(list.get(0).user.profileBannerUrl).into(imgBackground);
        Glide.with(getActivity()).load(list.get(0).user.profileImageUrl).apply(RequestOptions.circleCropTransform()).into(imgProfile);
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
