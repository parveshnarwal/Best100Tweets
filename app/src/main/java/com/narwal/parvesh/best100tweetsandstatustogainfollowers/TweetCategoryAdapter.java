package com.narwal.parvesh.best100tweetsandstatustogainfollowers;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Parvesh on 24-Dec-16.
 */

public class TweetCategoryAdapter extends BaseAdapter {

    ArrayList<SingleCategory> list;

    Context context;

    public TweetCategoryAdapter(Context context) {

        this.context = context;

        list = new ArrayList<SingleCategory>();
        Resources res = context.getResources();
        String[] tempEmotionName = res.getStringArray(R.array.tweet_emotions);
        int[] tempEmotionImg = {R.drawable.newtweets, R.drawable.funny, R.drawable.cute, R.drawable.happy,
                R.drawable.romantic, R.drawable.flirty, R.drawable.confused};

        for (int i = 0; i < tempEmotionImg.length; i++) {
            SingleCategory singleCategory = new SingleCategory(tempEmotionImg[i], tempEmotionName[i]);
            list.add(singleCategory);
        }

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder{
        ImageView emojImg;
        TextView emoName;

        ViewHolder(View v){
            emojImg = (ImageView) v.findViewById(R.id.ivEmotionImg);
            emoName = (TextView) v.findViewById(R.id.tvEmotionName);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View grid = convertView;
        ViewHolder holder = null;

        if (grid == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            grid = layoutInflater.inflate(R.layout.single_tweet_category, parent, false);

            holder = new ViewHolder(grid);

            grid.setTag(holder);

        } else {

            holder = (ViewHolder) grid.getTag();

        }

        SingleCategory singleCategory = list.get(position);

        holder.emojImg.setImageResource(singleCategory.getImageId());
        holder.emoName.setText(singleCategory.getCategoryName());
        //holder.emoName.setTypeface(Typeface.createFromAsset(context.getApplicationContext().getAssets(), "fonts/TravelingTypewriter.ttf" ));


        return grid;
    }
}
