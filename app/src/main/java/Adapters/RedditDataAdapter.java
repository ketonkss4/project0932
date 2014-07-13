package Adapters;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.redditviewr.app.MainActivity;
import com.google.android.gms.redditviewr.app.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ListData.ListData;
import ImageLoader.RedditIconTask;

/**
 * Created by Administrator on 7/6/2014.
 */
public class RedditDataAdapter extends BaseAdapter  {
    private static final String DEBUG_TAG = "LOGTAG!!!";
    private MainActivity activity;
    private RedditIconTask imgGet;
    private LayoutInflater layoutInflater;
    private ArrayList<ListData> topics;


    public RedditDataAdapter(MainActivity a, RedditIconTask i, LayoutInflater l, ArrayList<ListData
            > data ) {
        this.activity = a;
        this.imgGet = i;
        this.layoutInflater = l;
        this.topics = data;
    }


    @Override
    public int getCount() {
        return this.topics.size();
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MainActivity.MyViewHolder holder;
        if (convertView == null){
            // sets view layout resources

            convertView = layoutInflater.inflate(R.layout.row, parent,false);
            holder = new MainActivity.MyViewHolder();
            holder.listName = (TextView)convertView.findViewById(R.id.title);
            holder.authorName = (TextView)convertView.findViewById(R.id.author);
            holder.thumbnail = (ImageView)convertView.findViewById(R.id.thumbnail);
            holder.goButton = (Button)convertView.findViewById(R.id.go_button);
            holder.postTime = (TextView)convertView.findViewById(R.id.post_date);
            holder.redditScore = (TextView)convertView.findViewById(R.id.score);

            //Allows view to be recycled
            convertView.setTag(holder);


        }else {
            holder = (MainActivity.MyViewHolder) convertView.getTag();
        }

        ListData data = topics.get(position);
        try {
            //converts timestamp into a date format
            long lg = Long.valueOf(data.getPostTime())*1000;
            Date date = new  Date(lg);
            String postTime = new SimpleDateFormat("MM dd, yyyy hh:mma").format(date);

            //sets data resources in the holder
            holder.data = data;
            holder.listName.setText(data.getTitle());
            holder.authorName.setText(data.getAuthor());
            holder.postTime.setText(postTime);
            holder.redditScore.setText(data.getrScore());

        }catch (Exception e){
            e.printStackTrace();
            Log.v(DEBUG_TAG,"Cell Not Created Due to: ",e);
        }
        //sets drawable in adapter view
        if(data.getImageUrl()!=null){
            try {
                holder.thumbnail.setTag(data.getImageUrl());


                    Drawable drawable = imgGet.loadImage(this, holder.thumbnail);


                    if (drawable != null) {
                        holder.thumbnail.setImageDrawable(drawable);


                    } else {
                        holder.thumbnail.setImageResource(R.drawable.filler_icon);
                    }

            }catch (Exception e){
                e.printStackTrace();
                Log.v(DEBUG_TAG,"no image: ",e);
                holder.thumbnail.setImageResource(R.drawable.filler_icon);

            }

            return convertView;
        }


        return null;
    }



}
