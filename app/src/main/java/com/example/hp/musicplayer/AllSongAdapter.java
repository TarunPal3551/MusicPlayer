package com.example.hp.musicplayer;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AllSongAdapter extends ArrayAdapter<AllSong_Info>{
    private ArrayList<AllSong_Info> allSongInfo;
    Context mContext;

    public AllSongAdapter(ArrayList<AllSong_Info> allSongInfo, Context context) {
        super(context,R.layout.allsongs_listview_item,allSongInfo);
        this.allSongInfo = allSongInfo;
        this.mContext = context;
    }
    private static class ViewHolder{
        TextView songName,albumName,artistName,songLenght;
        ImageView songCover;



    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        AllSong_Info allSong_info=getItem(position);
        ViewHolder viewHolder;
        if (convertView==null){


            viewHolder=new ViewHolder();
            LayoutInflater inflater=LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.allsongs_listview_item,parent,false);
            viewHolder.songName=(TextView)convertView.findViewById(R.id.songName);
            viewHolder.artistName=(TextView)convertView.findViewById(R.id.artistname);
            viewHolder.albumName=(TextView)convertView.findViewById(R.id.allsongsalbumname);
            viewHolder.songLenght=(TextView)convertView.findViewById(R.id.songduration);
            viewHolder.songCover=(ImageView)convertView.findViewById(R.id.songcover);
            convertView.setTag(viewHolder);

        }
        else {


            viewHolder=(ViewHolder)convertView.getTag();

        }
        viewHolder.songName.setText(allSong_info.getSongname());
        viewHolder.artistName.setText(allSong_info.getArtistname());
        viewHolder.albumName.setText(allSong_info.getSongAlbum());
        viewHolder.songLenght.setText(allSong_info.getSongLenght());
//       Drawable img = Drawable.createFromPath(allSong_info.getAlbumCover());
//        Picasso picasso=Picasso.with(mContext);
//        picasso.load(String.valueOf(img)).resize(86,87).into(viewHolder.songCover);
        //viewHolder.songCover.setImageDrawable(img);
        Bitmap bm=BitmapFactory.decodeFile(allSong_info.getAlbumCover());
        if (bm!=null){
            viewHolder.songCover.setBackgroundDrawable(new BitmapDrawable(getContext().getResources(),bm));
        }
        else {
            viewHolder.songCover.setBackgroundResource(R.drawable.ic_action_music);
        }






        return convertView;

    }




}
