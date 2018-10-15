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
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class All_Album_Fragment extends Fragment {
    private ArrayList<AllAlbum_info> albums=new ArrayList<>();
    private static final String TAG = "All_Album_Fragment";
    GridView gridView;
    AlbumGridAdapter albumGridAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_all__album_, container, false);
        gridView=(GridView) view.findViewById(R.id.gridviewallalbums);
        albums=new ArrayList<>();
        loadAll_Album();






        return view;
    }
    public void loadAll_Album(){
        String where=null;
        String sortOrder=null;
        String[] selectioArg=null;

       String [] dataColumn={BaseColumns._ID,
               MediaStore.Audio.AlbumColumns.ALBUM,
               MediaStore.Audio.AlbumColumns.ARTIST,
               MediaStore.Audio.AlbumColumns.NUMBER_OF_SONGS,
               MediaStore.Audio.AlbumColumns.FIRST_YEAR,
               MediaStore.Audio.AlbumColumns.ALBUM_ART

       };
        Cursor songAlbumCursor = getContext().getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,dataColumn,where,selectioArg,sortOrder);

        if (songAlbumCursor != null && songAlbumCursor.moveToFirst()) {
            int titleColumn = songAlbumCursor.getColumnIndex(MediaStore.Audio.AlbumColumns.ALBUM);
            int idColumn = songAlbumCursor.getColumnIndex(BaseColumns._ID);
            int artistColumn = songAlbumCursor.getColumnIndex(MediaStore.Audio.AlbumColumns.ARTIST);
            int numOfSongsColumn = songAlbumCursor.getColumnIndex(MediaStore.Audio.AlbumColumns.NUMBER_OF_SONGS);
            int albumfirstColumn = songAlbumCursor.getColumnIndex(MediaStore.Audio.AlbumColumns.FIRST_YEAR);
            int albumCoverColumn=songAlbumCursor.getColumnIndex(MediaStore.Audio.AlbumColumns.ALBUM_ART);
            do {
                String albumName = songAlbumCursor.getString(titleColumn);
                long albumId = songAlbumCursor.getLong(idColumn);
                String artistName = songAlbumCursor.getString(artistColumn);
                int year = songAlbumCursor.getInt(albumfirstColumn);
                int no = songAlbumCursor.getInt(numOfSongsColumn);
                String albumCover=songAlbumCursor.getString(albumCoverColumn);
                AllAlbum_info allAlbum_info=new AllAlbum_info();
                allAlbum_info.setArtist(artistName);
                allAlbum_info.setAlbum_name(albumName);
                allAlbum_info.setAlbum_id(albumId);
                allAlbum_info.setNo_of_song(no);
                allAlbum_info.setAlumYear(year);
                allAlbum_info.setAlbumCover(albumCover);
                albums.add(allAlbum_info);
                Log.d(TAG, "loadAll_Album: "+albumName);
            } while (songAlbumCursor.moveToNext());
           songAlbumCursor.close();
        }
        albumGridAdapter=new AlbumGridAdapter(getContext(),albums);
        gridView.setAdapter(albumGridAdapter);


       }

}
