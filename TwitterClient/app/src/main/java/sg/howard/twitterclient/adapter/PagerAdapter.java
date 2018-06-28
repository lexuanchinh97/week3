package sg.howard.twitterclient.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import sg.howard.twitterclient.R;
import sg.howard.twitterclient.fragment.FragmentTimeline;
import sg.howard.twitterclient.fragment.Fragment_Messages;
import sg.howard.twitterclient.fragment.Fragment_Noti;
import sg.howard.twitterclient.fragment.Fragment_Profile;

public class PagerAdapter  extends FragmentStatePagerAdapter {
    Context context;
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
                    fragment=new Fragment_Noti();
                    break;
                case 2:
                    fragment=new Fragment_Profile();
                break;
                case 3:
                    fragment=new Fragment_Messages();
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
//        String title = "";
//        switch (position){
//            case 0:
//                title = "Home";
//                break;
//            case 1:
//                title = "Profile";
//                break;
//            case 2:
//                title = "Notifications";
//                break;
//            case 3:
//                title="Messages";
//                break;
//        }
        return null;
//        Drawable image = context.getResources().getDrawable(imageResId[position]);
//        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
//        SpannableString sb = new SpannableString(" ");
//        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
//        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        return sb;
    }


}
