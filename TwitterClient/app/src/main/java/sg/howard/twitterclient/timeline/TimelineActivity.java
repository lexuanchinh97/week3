package sg.howard.twitterclient.timeline;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.List;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import sg.howard.twitterclient.R;
import sg.howard.twitterclient.adapter.PagerAdapter;
import sg.howard.twitterclient.adapter.TimelineItemAdapter;
import sg.howard.twitterclient.compose.ComposeTweetActivity;
import sg.howard.twitterclient.model.EndlessRecyclerViewScrollListener;

public class TimelineActivity extends AppCompatActivity  {
    ViewPager viewPager;
    TabLayout tabLayout;
    private static String TAG = TimelineActivity.class.getSimpleName();
    RecyclerView rvTimeline;
    ProgressBar loader;
    FloatingActionButton fab;
    TimelineContract.Presenter presenter;
    TimelineItemAdapter adapter;
    EndlessRecyclerViewScrollListener scrollListener;
    SwipeRefreshLayout swipeContainer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablayout);
        addControl();
        setUpIcon();
//        rvTimeline = findViewById(R.id.rvTimeline);
//        loader = findViewById(R.id.loader);
//        fab = findViewById(R.id.fab);
//        swipeContainer=findViewById(R.id.swipeContainer);
//        presenter = new TimelinePresenter(this, TwitterCore.getInstance().getSessionManager().getActiveSession());
//        fab.setOnClickListener(view -> {
//           startActivity(new Intent(this, ComposeTweetActivity.class));
//        });
//        setUplistview();
//        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swipeContainer.setRefreshing(false);
//                presenter.start();
//            }
//        });
//        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);

    }

    private void setUpIcon() {
        int[] imageResId = {
                R.drawable.ic_home,
                R.drawable.ic_notifications_black_24dp,
                R.drawable.ic_profile,
                R.drawable.ic_message
        };
        for (int i = 0; i < imageResId.length; i++) {
            tabLayout.getTabAt(i).setIcon(imageResId[i]);
        }

    }

    //    @Override
//    protected void onResume() {
//        super.onResume();
//        presenter.start();
//    }
//
//    @Override
//    public void setPresenter(TimelineContract.Presenter presenter) {
//        this.presenter = presenter;
//    }
//
//    @Override
//    public void showLoading(boolean isShow) {
//        loader.setVisibility(isShow ? View.VISIBLE : View.GONE);
//    }
//
//    @Override
//    public void onGetStatusesSuccess(List<Tweet> data) {
//        Log.d(TAG, "Loaded " + data.size());
//        adapter.setData(data);
//    }
//
//    @Override
//    public void showError(String message) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//    }
//    private void setUplistview() {
//        adapter=new TimelineItemAdapter(this);
//        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        this.rvTimeline.setLayoutManager(layoutManager);
//        this.rvTimeline.setAdapter(adapter);
//        scrollListener=new EndlessRecyclerViewScrollListener(layoutManager) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                presenter.loadMore();
//            }
//        };
//        rvTimeline.addOnScrollListener(scrollListener);
//    }
private void addControl() {
    viewPager = (ViewPager) findViewById(R.id.viewPager);
    tabLayout = (TabLayout) findViewById(R.id.tablayout);
    FragmentManager manager = getSupportFragmentManager();
    PagerAdapter adapter = new PagerAdapter(manager);
    viewPager.setAdapter(adapter);
    tabLayout.setupWithViewPager(viewPager);
    viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    tabLayout.setTabsFromPagerAdapter(adapter);
}
}
