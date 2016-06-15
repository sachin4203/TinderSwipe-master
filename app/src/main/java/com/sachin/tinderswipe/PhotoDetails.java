package com.sachin.tinderswipe;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class PhotoDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_details);

        Bundle extras=getIntent().getExtras();
        String[] timeslots=extras.getString("timeslots").split("#");
        Toast.makeText(getApplicationContext(), timeslots[0], Toast.LENGTH_LONG).show();


        for(int i=0;i<timeslots.length;i++)
        {
            Picasso.with(this)
                    .load(timeslots[i])
                    .resizeDimen(R.dimen.activity_vertical_widthh, R.dimen.activity_vertical_heightt)
                    .fetch();

           // Toast.makeText(getApplicationContext(),items[i],Toast.LENGTH_LONG).show();
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        ImageAdapter adapter = new ImageAdapter(timeslots,this);
        viewPager.setAdapter(adapter);
    }
}
