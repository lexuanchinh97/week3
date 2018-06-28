package sg.howard.twitterclient.adapter;


import android.content.Context;
import android.graphics.Color;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.twitter.sdk.android.core.models.Tweet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sg.howard.twitterclient.R;
import sg.howard.twitterclient.model.PatternEditableBuilder;

public class TimelineItemAdapter extends RecyclerView.Adapter<TimelineItemAdapter.ViewHolder> {
    List<Tweet> data;
    Context context;

    private int lastPosition = -1;
    public TimelineItemAdapter(Context ctx){
        data=new ArrayList<>();
        this.context=ctx;
    }

    public void setData(List<Tweet> data) {
        this.data = data;
        notifyDataSetChanged();
    }
    public void clearData() {
        data.clear();
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.timelineitem,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tweet tweet=data.get(position);
        if(tweet!=null){

            holder.txtDate.setText(getRelativeTimeAgo(tweet.createdAt));
            holder.txtRetweetCount.setText(String.valueOf(tweet.retweetCount));
            holder.txtFavorite.setText(String.valueOf(tweet.favoriteCount));
            holder.txtName.setText(tweet.user.name);
            holder.txtTest.setText(tweet.text);
            new PatternEditableBuilder().
                    addPattern(Pattern.compile("\\@(\\w+)"),Color.BLUE).
                    into(holder.txtTest);
            new PatternEditableBuilder().
                    addPattern(Pattern.compile("\\#(\\w+)"),Color.BLUE).
                    into(holder.txtTest);
            holder.txtIdName.setText("@"+tweet.user.screenName);
            Glide.with(context).load(tweet.user.profileImageUrl).
                    apply(RequestOptions.circleCropTransform()).into(holder.imgProfile);
            if(tweet.entities.media.size()>0){
                Glide.with(context).load(tweet.entities.media.get(0).mediaUrl).into(holder.img);
            }
            else Glide.with(context).load("https://pbs.twimg.com/profile_images/1011471649030299650/pwbTpkeu_400x400.jpg")
              .into(holder.img);
            holder.imgHeart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                        holder.txtRetweetCount.setText(String.valueOf(tweet.retweetCount+1));
                }
            });
            holder.imgShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        }
        setAnimation(holder.itemView, position);
    }
    @Override
    public int getItemCount() {

      return  data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTest,txtName,txtIdName,txtFavorite,txtRetweetCount,txtDate;
        ImageView imgProfile,img,imgHeart,imgShare;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgShare=itemView.findViewById(R.id.imgShare);
            imgHeart=itemView.findViewById(R.id.imgHeart);
            txtDate=itemView.findViewById(R.id.txtDate);
            txtRetweetCount=itemView.findViewById(R.id.txtRetweetCount);
            txtFavorite=itemView.findViewById(R.id.txtFavorite);
            txtIdName=itemView.findViewById(R.id.txtIdName);
            txtTest=itemView.findViewById(R.id.txtTest);
            txtName=itemView.findViewById(R.id.txtName);
            imgProfile=itemView.findViewById(R.id.imgProfile);
            img=itemView.findViewById(R.id.img);
        }

    }
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

}
