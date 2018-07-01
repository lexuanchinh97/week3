package sg.howard.twitterclient.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import com.bumptech.glide.Glide;
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
import sg.howard.twitterclient.Image_Activity;
import sg.howard.twitterclient.R;
import sg.howard.twitterclient.model.PatternEditableBuilder;

public class TimelineItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType){
            case 1:
                View viewImg = inflater.inflate(R.layout.timelineitem_image,parent,false);
                holder = new ViewHolderImg(viewImg);
                break;
            case 2:
                View viewNormal = inflater.inflate(R.layout.timelineitem,parent,false);
                holder = new ViewHolderNormal(viewNormal);
                break;
            default:
                View v = inflater.inflate(R.layout.timelineitem, parent, false);
                holder = new ViewHolderNormal(v);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case 1:
                ViewHolderImg imgView = (ViewHolderImg) holder;
                configureImg(imgView,position);
                break;
            case 2:
                ViewHolderNormal normalView = (ViewHolderNormal) holder;
                configureNormal(normalView,position);
                break;
            default:
                ViewHolderImg v = (ViewHolderImg) holder;
                configureImg(v,position);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        int numMedia = data.get(position).entities.media.size();
        switch (numMedia){
            case 0:
                return 2;
            case 1:
                return 1;
            default:
                return 3;
        }
    }

    private void configureNormal(ViewHolderNormal holder, int position) {
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
            //image profile
            Glide.with(context).load(tweet.user.profileImageUrl).
                    apply(RequestOptions.circleCropTransform()).into(holder.imgProfile);
            holder.imgProfile.setOnClickListener(view -> {
                Intent intent=new Intent(context,Image_Activity.class);
                intent.putExtra("url",tweet.user.profileImageUrl);
                context.startActivity(intent);
            });
            holder.imgHeart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                        holder.txtRetweetCount.setText(String.valueOf(tweet.retweetCount+1));
                }
            });
            holder.imgShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Text");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
                    context.startActivity(Intent.createChooser(sharingIntent, "Share using"));
                }
            });
            setAnimation(holder.itemView, position);
        }
    }

    private void configureImg(ViewHolderImg holder, int position) {
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
            holder.imgProfile.setOnClickListener(view -> {
                Intent intent=new Intent(context,Image_Activity.class);
                intent.putExtra("url",tweet.user.profileImageUrl);
                context.startActivity(intent);
            });
            if(tweet.entities.media.size()>0){
                Glide.with(context)
                        .load(tweet.entities.media.get(0).mediaUrlHttps).into(holder.img);
            }
            if(tweet.entities.media.size()>0){
                holder.img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(context,Image_Activity.class);
                        intent.putExtra("url",tweet.entities.media.get(0).mediaUrlHttps);
                        context.startActivity(intent);
                    }
                });
            }
            holder.imgHeart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                        holder.txtRetweetCount.setText(String.valueOf(tweet.retweetCount+1));
                }
            });
            holder.imgShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Text");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
                    context.startActivity(Intent.createChooser(sharingIntent, "Share using"));
                }
            });

            setAnimation(holder.itemView, position);

        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private class ViewHolderImg extends RecyclerView.ViewHolder {
        TextView txtTest,txtName,txtIdName,txtFavorite,txtRetweetCount,txtDate;
        ImageView imgProfile,img,imgHeart,imgShare;
        public ViewHolderImg(@NonNull View itemView) {
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

    private class ViewHolderNormal extends RecyclerView.ViewHolder {
        TextView txtTest,txtName,txtIdName,txtFavorite,txtRetweetCount,txtDate;
        ImageView imgProfile,imgHeart,imgShare;
        public ViewHolderNormal(@NonNull View itemView) {
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
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            animation.setDuration(1000);
            lastPosition = position;
        }
    }

}
