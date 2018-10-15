package com.example.hp.musicplayer;

import android.Manifest;

import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;


public class All_Song_Fragment extends Fragment {
    ListView allsonglistView;
    private ArrayList<AllSong_Info>songs = new ArrayList<AllSong_Info>();
    AllSongAdapter songAdapter;
    private static final String TAG = "All_Song_Fragment";
    Utilities utilities;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all__song_, container, false);
        allsonglistView = (ListView) view.findViewById(R.id.allsonglistview);
        utilities=new Utilities();
        checkUserPermission();
        songAdapter = new AllSongAdapter(songs, getContext());
        allsonglistView.setAdapter(songAdapter);
        allsonglistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "" + position, Toast.LENGTH_LONG).show();

                loadSongs();
                Intent intent = new Intent(getActivity(), NowPlayingActivity.class);
                intent.putExtra("songIndex", position);
                intent.putExtra("songs", songs);
                intent.putExtra("SongName", songs.get(position).getSongname());
                intent.putExtra("SongAlbum", songs.get(position).getSongAlbum());
                startActivity(intent);
                Bitmap bm= BitmapFactory.decodeFile(songs.get(position).getAlbumCover());
                if (bm!=null){
                    Bitmap bitmap= NowPlayingActivity.BlurBuilder.blur(getContext(),bm);
                    allsonglistView.setBackgroundDrawable(new BitmapDrawable(getContext().getResources(),bitmap));
                    songAdapter.notifyDataSetChanged();


                }
                else{
                    //allsonglistView.setBackgroundResource(R.drawable.ic_action_music);
                    }

                    }
        });



        return view;


    }

    private void checkUserPermission(){
        if(Build.VERSION.SDK_INT>=123){
            if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},123);
                return;
            }
            else {
                Toast.makeText(getActivity(),"Check Permission",Toast.LENGTH_LONG);

            }

        }
        loadSongs();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    loadSongs();
                }else{
                    Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    checkUserPermission();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        }

    }
    public void loadSongs(){
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC+"!=0";
        Cursor songCursor = getContext().getContentResolver().query(uri,null,selection,null,null);
        if(songCursor != null){
            if(songCursor.moveToFirst()){
                do{
                    String name = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    String artist = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    String album = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                    long songLenght= songCursor.getLong(songCursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                    long albumid=songCursor.getLong(songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                    String songPath=songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    //String songcover=songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
                    Cursor cursor = getActivity().getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                            new String[] {MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                            MediaStore.Audio.Albums._ID+ "=?",
                            new String[] {String.valueOf(albumid)},
                            null);
                    if (cursor.moveToFirst()) {
                       final String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
                        // do whatever you need to do
                        String songDuration=(utilities.millisecondsToTimer(songLenght));
                        AllSong_Info info= new AllSong_Info(name,artist,album,songDuration,path,albumid,songPath);
                        songs.add(info);
                    }
                    }while (songCursor.moveToNext());
            }
            songCursor.close();


//            songAdapter = new AllSongAdapter(songs, getContext(), new AllSongAdapter.CustomItemClickListener() {
//                @Override
//                public void onItemClickClick(View view, int position) {
//                    Toast.makeText(getContext(),""+position,Toast.LENGTH_LONG).show();
//                    AllSong_Info allSong_info=new AllSong_Info();
//                    Intent intent=new Intent(getContext(),NowPlayingActivity.class);
//                    intent.putExtra("SongName",allSong_info.getSongname());
//                    startActivity(intent);
//                }
//            });


        }
    }



}
