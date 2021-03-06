package sg.howard.twitterclient.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import sg.howard.twitterclient.fragment.FragmentTimeline;
import sg.howard.twitterclient.compose.Fragment_Compose;
import sg.howard.twitterclient.fragment.Fragment_Frofile;
import sg.howard.twitterclient.fragment.Fragment_User;

public class PagerAdapter  extends FragmentStatePagerAdapter {
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
            switch (position){
                case 0:
                    fragment=new FragmentTimeline();
                    break;
                case 1:
                    fragment=new Fragment_User();
                    break;
                case 2:
                    fragment=new Fragment_Compose();
                break;
                case 3:

                    fragment=new Fragment_Frofile()
                    ;
                break;

            }
        return fragment;
    }


    @Override
    public int getCount() {
        return 4;
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Home";
                break;
            case 1:
                title = "User";
                break;
            case 2:
                title = "Compose";
                break;
            case 3:
                title="Profile";
                break;
        }
        return title;
    }


}
