package com.example.hp.musicplayer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;

public class SongwithNowplaying extends AppCompatActivity {
    private ArrayList<? extends AllSong_Info> aLLsongs = new ArrayList<AllSong_Info>();
    int nowplayin_SongIndex;
    NowPlayingActivity nowPlayingActivity;
    TextView songName,songAlbum;
    RelativeLayout nowPlayingRelativeLayout;
    ImageView nowplaying_layout_song_cover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songwith_nowplaying);
        songName=(TextView)findViewById(R.id.nowplaying_layout_songname);
        songAlbum=(TextView)findViewById(R.id.nowplaying_layout_albumname);
        nowPlayingRelativeLayout=(RelativeLayout)findViewById(R.id.nowplayingrelativelayout);
        nowplaying_layout_song_cover=(ImageView)findViewById(R.id.nowplaying_layout_albumcover);

        setUptoptabbar();
        nowPlayingActivity=new NowPlayingActivity();
        updateTopNowplayingContent();


    }
    public void setUptoptabbar(){


        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)

                .add("All Songs", All_Song_Fragment.class)
                .add("Albums",All_Album_Fragment.class)
                .add("Artists",All_Artist_Fragment.class)
                .add("Folder",All_Song_Fragment.class)
                .add("Playlists",All_Song_Fragment.class)
                .create());
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);
    }
    public void updateTopNowplayingContent(){
        Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();
        nowplayin_SongIndex = (int) bundle.get("nowplayingsongindex");
       aLLsongs = bundle.getParcelableArrayList("all_song_list");
       songName.setText(aLLsongs.get(nowplayin_SongIndex).getSongname());
       songAlbum.setText(aLLsongs.get(nowplayin_SongIndex).getSongAlbum());
        Bitmap bm= BitmapFactory.decodeFile(aLLsongs.get(nowplayin_SongIndex).getAlbumCover());
        if (bm!=null){
            Bitmap bitmap= NowPlayingActivity.BlurBuilder.blur(SongwithNowplaying.this,bm);
            //nowPlayingRelativeLayout.setBackgroundDrawable(new BitmapDrawable(SongwithNowplaying.this.getResources(),bitmap));
            nowplaying_layout_song_cover.setBackgroundDrawable(new BitmapDrawable(SongwithNowplaying.this.getResources(),bm));


        }
        else
            {
            //nowPlayingRelativeLayout.setBackgroundResource(R.drawable.ic_action_music);
                nowplaying_layout_song_cover.setBackgroundResource(R.drawable.ic_action_music);

        }

       //nowPlayingActivity.playSongFromSongList(nowplayin_SongIndex);


    }
}
