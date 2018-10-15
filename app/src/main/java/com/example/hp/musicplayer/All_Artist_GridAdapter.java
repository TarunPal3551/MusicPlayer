package com.example.hp.musicplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class All_Artist_GridAdapter extends ArrayAdapter<AllArtist_info> {
    private ArrayList<AllArtist_info> allArtist_info;
    Context mContext;


    public All_Artist_GridAdapter( Context context,ArrayList<AllArtist_info> allArtist_info) {
        super(context, R.layout.allartist_listview_item, allArtist_info);
        this.allArtist_info = allArtist_info;
        this.mContext = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        AllArtist_info allArtist_info=getItem(position);
        ViewHolder viewHolder;

        if (convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater=LayoutInflater.from(mContext);
            convertView=inflater.inflate(R.layout.allartist_listview_item,parent,false);
            viewHolder.artist_name=(TextView)convertView.findViewById(R.id.allartistartistname);
            viewHolder.artist_cover=(ImageView)convertView.findViewById(R.id.artistimageview);
            convertView.setTag(viewHolder);


        }
        else {

            viewHolder=(ViewHolder)convertView.getTag();
        }
        viewHolder.artist_name.setText(allArtist_info.getArtist_Name());



        return convertView;
    }
    private  static class ViewHolder{
        TextView artist_name;
        ImageView artist_cover;

    }
}
