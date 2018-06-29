package sg.howard.twitterclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import sg.howard.twitterclient.R;
import sg.howard.twitterclient.adapter.TimelineItemAdapter;
import sg.howard.twitterclient.model.EndlessRecyclerViewScrollListener;
import sg.howard.twitterclient.timeline.TimelineActivity;
import sg.howard.twitterclient.timeline.TimelineContract;
import sg.howard.twitterclient.timeline.TimelinePresenter;

public class FragmentTimeline extends Fragment implements TimelineContract.View {
    private static String TAG = TimelineActivity.class.getSimpleName();
    RecyclerView rvTimeline;
    ProgressBar loader;
    FloatingActionButton fab;
    TimelineContract.Presenter presenter;
    TimelineItemAdapter adapter;
    EndlessRecyclerViewScrollListener scrollListener;
    SwipeRefreshLayout swipeContainer;
    LinearLayoutManager layoutManager;
    @Override
    public void onResume() {
        super.onResume();
       presenter.start();
    }

    public FragmentTimeline() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.activity_timeline,container,false);

        rvTimeline = v.findViewById(R.id.rvTimeline);
        loader = v.findViewById(R.id.loader);
        fab = v.findViewById(R.id.fab);
        swipeContainer=v.findViewById(R.id.swipeContainer);
        presenter = new TimelinePresenter(this, TwitterCore.getInstance().getSessionManager().getActiveSession());
        fab.setOnClickListener(view -> {
          // startActivity(new Intent(getActivity(), ComposeTweetActivity.class));
        });
        setUplistview();
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(false);
                presenter.start();
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        return v;
    }

    private void setUplistview() {
        adapter=new TimelineItemAdapter(getActivity());
        layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvTimeline.setLayoutManager(layoutManager);
        rvTimeline.setAdapter(adapter);
        scrollListener=new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                presenter.loadMore();
            }
        };
        rvTimeline.addOnScrollListener(scrollListener);
    }

    @Override
    public void onGetStatusesSuccess(List<Tweet> data) {
        Log.d(TAG, "Loaded " + data.size());
        adapter.setData(data);
    }

    @Override
    public void setPresenter(TimelineContract.Presenter presenter) {
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
