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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablayout);
        addControl();
        setUpIcon();
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
