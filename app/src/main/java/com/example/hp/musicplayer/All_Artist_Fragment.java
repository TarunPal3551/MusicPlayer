package com.example.hp.musicplayer;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;


public class All_Artist_Fragment extends Fragment {
    GridView allArtist_gridview;
    ArrayList<AllArtist_info>artist=new ArrayList<>();
    All_Artist_GridAdapter all_artist_gridAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_all__artist_, container, false);
        allArtist_gridview=(GridView)view.findViewById(R.id.artistGridview);
           artist=new ArrayList<>();
           loadAllArtist();



        return  view;
    }
    public void loadAllArtist(){
       Cursor cursor=getContext().getContentResolver().query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
               null,null,null,null);
       if (cursor!=null){
           if (cursor.moveToFirst()){

               int idCol = cursor.getColumnIndex(BaseColumns._ID);
               int nameCol = cursor.getColumnIndex(MediaStore.Audio.ArtistColumns.ARTIST);
               int albumsNbCol = cursor.getColumnIndex(MediaStore.Audio.ArtistColumns.NUMBER_OF_ALBUMS);
               int tracksNbCol = cursor.getColumnIndex(MediaStore.Audio.ArtistColumns.NUMBER_OF_TRACKS);
               int  artistCover=cursor.getColumnIndex(MediaStore.Audio.AlbumColumns.ALBUM_ART);
               do {
                   long id = cursor.getLong(idCol);
                   String artistName = cursor.getString(nameCol);
                   int albumCount = cursor.getInt(albumsNbCol);
                   int trackCount = cursor.getInt(tracksNbCol);
                   artist.add(new AllArtist_info(artistName,id,albumCount,trackCount));
               } while (cursor.moveToNext());

           }
           cursor.close();
           }

           all_artist_gridAdapter=new All_Artist_GridAdapter(getContext(),artist);
           allArtist_gridview.setAdapter(all_artist_gridAdapter);

       }



}
