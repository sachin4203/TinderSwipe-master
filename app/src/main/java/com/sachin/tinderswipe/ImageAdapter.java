package com.sachin.tinderswipe;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by kapil pc on 6/15/2016.
 */
public class ImageAdapter extends PagerAdapter {
    Context context;
    private String[] myimages;

    ImageAdapter(String[] myimages, Context context){
        this.myimages=myimages;
        this.context=context;
    }
    @Override
    public int getCount() {
        return myimages.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        /*int padding = context.getResources().getDimensionPixelSize(R.dimen.padding_medium);
        imageView.setPadding(padding, padding, padding, padding);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);*/
        //imageView.setImageResource(GalImages[position]);
        Picasso.with(context).load(myimages[position]).into(imageView);
        ((ViewPager) container).addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }
}