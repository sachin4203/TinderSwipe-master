package com.sachin.tinderswipe;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sachin.tinderswipe.tindercard.FlingCardListener;
import com.sachin.tinderswipe.tindercard.SwipeFlingAdapterView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends AppCompatActivity implements FlingCardListener.ActionDownInterface {

    public static MyAppAdapter myAppAdapter;
    public static ViewHolder viewHolder;
    private ArrayList<Data> al;
    private SwipeFlingAdapterView flingContainer;
    String[] items;
    int counter;

    public static final String ROOT_URL = "http://www.json-generator.com/";

    private List<PicturesData> pics;
    private ArrayList<PicItem> mPicData;
    public static void removeBackground() {


        viewHolder.background.setVisibility(View.GONE);
        myAppAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);

        getBooks();
        al = new ArrayList<>();
        al.add(new Data("http://i.ytimg.com/vi/PnxsTxV8y3g/maxresdefault.jpg", "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness."));
        al.add(new Data("http://switchboard.nrdc.org/blogs/dlashof/mission_impossible_4-1.jpg", "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness."));
        al.add(new Data("http://i.ytimg.com/vi/PnxsTxV8y3g/maxresdefault.jpg", "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness."));
        al.add(new Data("http://switchboard.nrdc.org/blogs/dlashof/mission_impossible_4-1.jpg", "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness."));
        al.add(new Data("http://i.ytimg.com/vi/PnxsTxV8y3g/maxresdefault.jpg", "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness."));
        myAppAdapter = new MyAppAdapter(al, MainActivity.this);
        flingContainer.setAdapter(myAppAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {

            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                al.remove(0);
                myAppAdapter.notifyDataSetChanged();
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                counter++;
            }


            @Override
            public void onRightCardExit(Object dataObject) {

                al.remove(0);
                myAppAdapter.notifyDataSetChanged();
                counter++;
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

            }

            @Override
            public void onScroll(float scrollProgressPercent) {

                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.background).setAlpha(0);
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });



        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {

                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.background).setAlpha(0);
                Toast.makeText(getApplicationContext(), "This Item was clicked"+ itemPosition, Toast.LENGTH_LONG).show();
                String upics=mPicData.get(counter).getLink1() + "#" + mPicData.get(counter).getLink2() + "#"+
                mPicData.get(counter).getLink3() + "#" + mPicData.get(counter).getLink4();
                Intent intent = new Intent(MainActivity.this,PhotoDetails.class);
                intent.putExtra("timeslots",upics);

                startActivity(intent);

                myAppAdapter.notifyDataSetChanged();
            }
        });

    }

    private void prefectch(){
       /* String uppics=" ";
        for(int i=0;i<2;i++)
        {
        uppics=uppics+ mPicData.get(i).getLink1() + "#" + mPicData.get(i).getLink2() + "#"+
                    mPicData.get(i).getLink3() + "#" + mPicData.get(i).getLink4();

        }
        String[] timeslots=uppics.split("#");*/
        for(int i=0;i<items.length;i++)
        {
            Picasso.with(this)
                    .load(items[i])
                    .resizeDimen(R.dimen.activity_vertical_width, R.dimen.activity_vertical_height)
                    .fetch();

Toast.makeText(getApplicationContext(),items[i],Toast.LENGTH_LONG).show();
        }
    }


    private void getBooks(){
        //While the app fetched data we are displaying a progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Fetching Data","Please wait...",false,false);

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .build();

        //Creating an object of our api interface
        PicturesApi api = adapter.create(PicturesApi.class);

        //Defining the method
        api.getpics(new Callback<List<PicturesData>>() {
            @Override
            public void success(List<PicturesData> list, Response response) {
                //Dismissing the loading progressbar
                loading.dismiss();

                //Storing the data in our list
               pics = list;

                //Calling a method to show the list
                showList();
            }

            @Override
            public void failure(RetrofitError error) {
                //you can handle the errors here
            }
        });
    }

    private void showList() {
        //String array to store all the book names
         items = new String[pics.size()];
        PicItem item;
        al = new ArrayList<>();
        mPicData=new ArrayList<>();
        //Traversing through the whole list to get all the names
        for (int i = 0; i < pics.size(); i++) {
            //Storing names to string array
            al.add(new Data(pics.get(i).getLink4(), "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness."));
            item= new PicItem();
            String link1 = pics.get(i).getLink1();
            item.setLink1(link1);
           item.setLink2(pics.get(i).getLink2());
            item.setLink3(pics.get(i).getLink3());
            item.setLink4(pics.get(i).getLink4());
            items[i] = pics.get(i).getLink4();
            mPicData.add(item);
            //Toast.makeText(getApplicationContext(),items[i],Toast.LENGTH_LONG).show();

        }
        myAppAdapter = new MyAppAdapter(al, MainActivity.this);
        flingContainer.setAdapter(myAppAdapter);
        prefectch();
    }



    @Override
    public void onActionDownPerform() {
        Log.e("action", "bingo");
    }

    public static class ViewHolder {
        public static FrameLayout background;
        public TextView DataText;
        public ImageView cardImage;


    }

    public class MyAppAdapter extends BaseAdapter {


        public List<Data> parkingList;
        public Context context;

        private MyAppAdapter(List<Data> apps, Context context) {
            this.parkingList = apps;
            this.context = context;
        }

        @Override
        public int getCount() {
            return parkingList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View rowView = convertView;


            if (rowView == null) {

                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.item, parent, false);
                // configure view holder
                viewHolder = new ViewHolder();
                viewHolder.DataText = (TextView) rowView.findViewById(R.id.bookText);
                viewHolder.background = (FrameLayout) rowView.findViewById(R.id.background);
                viewHolder.cardImage = (ImageView) rowView.findViewById(R.id.cardImage);
                rowView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.DataText.setText(parkingList.get(position).getDescription() + "");

            Picasso.with(MainActivity.this).load(parkingList.get(position).getImagePath()).into(viewHolder.cardImage);

            return rowView;
        }
    }
}
