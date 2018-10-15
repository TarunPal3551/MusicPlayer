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
import java.util.List;

public class AlbumGridAdapter extends ArrayAdapter<AllAlbum_info> {
    Context mContext;
    private ArrayList<AllAlbum_info> allAlbum_info;

    public AlbumGridAdapter(@NonNull Context context, ArrayList<AllAlbum_info> allAlbum_info) {
        super(context, R.layout.allalbum_listview_item,allAlbum_info);
        this.mContext = context;
        this.allAlbum_info = allAlbum_info;
    }
    private static class ViewHolder{
        TextView albumNmae,no_of_songs,album_artist_name;
        ImageView albumCover;



    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        AllAlbum_info allAlbum_info=getItem(position);
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater=LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.allalbum_listview_item,parent,false);
            viewHolder.albumNmae=(TextView)convertView.findViewById(R.id.allalbumname);
            viewHolder.album_artist_name=(TextView)convertView.findViewById(R.id.all_album_artist_name);
            viewHolder.albumCover=(ImageView)convertView.findViewById(R.id.all_album_cover);
            convertView.setTag(viewHolder);
            //viewHolder.no_of_songs=(TextView)convertView.findViewById(R.id.)

        }
        else {
            viewHolder=(ViewHolder)convertView.getTag();

        }
        viewHolder.albumNmae.setText(allAlbum_info.getAlbum_name());
        viewHolder.album_artist_name.setText(allAlbum_info.getArtist());
        Bitmap bm= BitmapFactory.decodeFile(allAlbum_info.getAlbumCover());
        if (bm!=null){
            viewHolder.albumCover.setBackgroundDrawable(new BitmapDrawable(getContext().getResources(),bm));
        }
        else {
            viewHolder.albumCover.setBackgroundResource(R.drawable.ic_action_music);
        }


        return convertView;
    }

}
