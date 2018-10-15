package com.example.hp.musicplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Thread.sleep;

public class NowPlayingActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener{
    GestureDetector gestureScanner;
    float x1, x2, y1, y2;
    TextView songName, songAlbum, currentTime, totaltime;
    ArrayList<? extends AllSong_Info> currentSong = new ArrayList<>();
    int position;
    static MediaPlayer mediaPlayer;
    All_Song_Fragment all_song_fragment;
    Utilities utilities;
    SeekBar seekBar;
    Handler mHandler;
    Thread updateSeekBar, updateSong;
    int songIndex;
    AllSong_Info allSong_info;
    ImageButton play_pause, nextSong, previousSong,shuffelbutton,repeatbutton;
    ImageView songCover;
    RelativeLayout nowplayingLayout;
    Context mContext;
    private static final String TAG = "NowPlayingActivity";
    int isShuffel=0;
    int isRepeat=0;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nowplayinglayout);
        songName = (TextView) findViewById(R.id.nowplaying_layout_songname);
        songAlbum = (TextView) findViewById(R.id.nowplayingAlbum);
        currentTime = (TextView) findViewById(R.id.nowplayingCurrenttime);
        totaltime = (TextView) findViewById(R.id.nowPlayingTotaltime);
        seekBar = (SeekBar) findViewById(R.id.nowplayingSeekbar);
        play_pause = (ImageButton) findViewById(R.id.nowplayingpause);
        nextSong = (ImageButton) findViewById(R.id.nowplayingNextSong);
        previousSong = (ImageButton) findViewById(R.id.nowplayingPrevious);
        songCover = (ImageView) findViewById(R.id.nowplayingSongcover);
        nowplayingLayout = (RelativeLayout) findViewById(R.id.nowplaying);
        shuffelbutton=(ImageButton)findViewById(R.id.nowplayingshuffel);
        repeatbutton=(ImageButton)findViewById(R.id.nowplayingrepeat);
        mContext = NowPlayingActivity.this;

        currentSong = new ArrayList<AllSong_Info>();
        utilities = new Utilities();
        mHandler = new Handler();
        Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();
        songIndex = (int) bundle.get("songIndex");
        currentSong = bundle.getParcelableArrayList("songs");
        playSongFromSongList(songIndex);

        play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    play_pause.setImageResource(R.drawable.playbutton);

                } else {
                    mediaPlayer.start();
                    play_pause.setImageResource(R.drawable.pause);
                }


            }

        });
        nextSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (songIndex <(currentSong.size()-1)) {

                    playSongFromSongList(songIndex + 1);
                    songIndex = songIndex + 1;

                } else {
                    playSongFromSongList(0);
                    songIndex = 0;

                }


            }
        });
        previousSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (songIndex>0){
                    playSongFromSongList(songIndex-1);
                    songIndex=songIndex-1;

               }
               else {
                   playSongFromSongList(currentSong.size()-1);
                   songIndex=currentSong.size()-1;

               }
            }
        });

        repeatbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRepeat=1;

            }
        });

        shuffelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random=new Random();
                int index=random.nextInt(currentSong.size()-1+1)+1;
                playSongFromSongList(index);
                songIndex=index;
                isShuffel=1;
            }
        });


    }

    public void playSongFromSongList(final int songIndex) {
        mediaPlayer = new MediaPlayer();
        Log.d(TAG, "selectSongFromSongList: select song from");
        ///set Songname and song album from current song list which is inside bundel
        songName.setText(currentSong.get(songIndex).getSongname());
        songAlbum.setText(currentSong.get(songIndex).getSongAlbum());
        ////get album cover by using getAlbumCover and make it blur by using tak code from stack over flow blurBuild method
        Bitmap bm = BitmapFactory.decodeFile(currentSong.get(songIndex).getAlbumCover());
        if (bm != null) {
            Bitmap bitmap = BlurBuilder.blur(this, bm);
            nowplayingLayout.setBackgroundDrawable(new BitmapDrawable(mContext.getResources(), bitmap));

        } else{
            //nowplayingLayout.setBackgroundResource(R.drawable.ic_action_music);

        }


        if (bm != null) {
            songCover.setImageBitmap(bm);
        } else {
            songCover.setImageResource(R.drawable.ic_action_music);
        }


        try {
//            mediaPlayer.reset();
//            mediaPlayer.stop();
//            mediaPlayer.setDataSource(currentSong.get(songIndex).getSongPath());
//            mediaPlayer.prepare();
//            mediaPlayer.start();
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.setDataSource(currentSong.get((songIndex)).getSongPath());
                mediaPlayer.prepare();
                mediaPlayer.start();
                play_pause.setImageResource(R.drawable.pause);

            } else {
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.setDataSource(currentSong.get(songIndex).getSongPath());
                mediaPlayer.prepare();
                mediaPlayer.start();
                play_pause.setImageResource(R.drawable.pause);


            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        totaltime.setText(utilities.millisecondsToTimer(mediaPlayer.getDuration()));
        seekBar.setMax(mediaPlayer.getDuration());
        updateCurrentTime();


        updateSeekBar = new Thread() {
            @Override
            public void run() {
                int totalDuration = mediaPlayer.getDuration();
                int currentPosition = 0;
                while (currentPosition <totalDuration) {
                    try {
                        sleep(250);
                        currentPosition = mediaPlayer.getCurrentPosition();
                        seekBar.setProgress(currentPosition);
                    } catch (InterruptedException e) {
                        currentPosition = mediaPlayer.getCurrentPosition();
                        seekBar.setProgress(currentPosition);
                        e.printStackTrace();
                    }
                }



            }
        };

        updateSeekBar.start();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {



            }


        });

        Log.d(TAG, "selectSongFromSongList: music stop");


    }



    /**
     * Background Runnable thread
     */
    public void updateCurrentTime() {
        mHandler.postDelayed(mUpdateTimeTask, 250);
    }

    /**
     * Background Runnable thread
     * */
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = mediaPlayer.getDuration();
            long currentDuration = mediaPlayer.getCurrentPosition();

            // Displaying Total Duration time
            totaltime.setText(""+utilities.millisecondsToTimer(totalDuration));
            // Displaying time completed playing
            currentTime.setText(""+utilities.millisecondsToTimer(currentDuration));

            // Updating progress bar
//            int progress = (int)(utilities.getProgressPercentage(currentDuration, totalDuration));
            //Log.d("Progress", ""+progress);

            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 100);
        }
    };


    /**
     *
     * */


    ////////////////////////////---------------------Gesture Up or down bottom bar to nowplaying and now playing to bottom bar-----------------
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                x1=event.getX();
//                y1=event.getY();
//                break;
//
//            case MotionEvent.ACTION_UP:
//                x2=event.getX();
//                y2=event.getY();
//                if(y1<y2){
//                    Intent intent=new Intent(NowPlayingActivity.this,Home_Activity.class);
//                    startActivity(intent);
//                    overridePendingTransition(R.anim.goup,R.anim.godown);
//
//
//
//                }
//                break;
//        }
//
//
//
//
//
//        return false;
//    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
            Intent intent=new Intent(NowPlayingActivity.this,SongwithNowplaying.class);
            intent.putExtra("nowplayingsongindex",songIndex);
            intent.putExtra("all_song_list",currentSong);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.goup, R.anim.godown);


    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if(songIndex < (currentSong.size() - 1)){
            playSongFromSongList(songIndex+ 1);
            songIndex = songIndex + 1;
        }
        else if (isRepeat==1){
            playSongFromSongList(songIndex);
            isRepeat=1;

        }
        else if(isShuffel==1){
            Random random=new Random();
            int index=random.nextInt(currentSong.size()-1+1)+1;
            playSongFromSongList(index);
            songIndex=index;
            isShuffel=1;
        }
        else{
            // play first song
            playSongFromSongList(songIndex+1);
            songIndex = songIndex+1;
        }

    }


//    @Override
//    public boolean onDown(MotionEvent e) {
//        return false;
//    }
//
//    @Override
//    public void onShowPress(MotionEvent e) {
//
//    }
//
//    @Override
//    public boolean onSingleTapUp(MotionEvent e) {
//        return false;
//    }
//
//    @Override
//    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//        return false;
//    }
//
//    @Override
//    public void onLongPress(MotionEvent e) {
//
//    }
//
//    @Override
//    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//        return false;
//    }


    public static class BlurBuilder {
        private static final float BITMAP_SCALE = 0.5f;
        private static final float BLUR_RADIUS = 20f;

        public static Bitmap blur(View v) {
            return blur(v.getContext(), getScreenshot(v));
        }

        @SuppressLint("NewApi")
        public static Bitmap blur(Context ctx, Bitmap image) {
            int width = 0, height = 0;
            try {
                width = Math.round(image.getWidth() * BITMAP_SCALE);
                height = Math.round(image.getHeight() * BITMAP_SCALE);
            } catch (NullPointerException e) {


            }
            Bitmap inputBitmap;
            if (width == 0) {
                inputBitmap = Bitmap.createScaledBitmap(image, 400, height, false);


            } else {
                inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
            }


            Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

            RenderScript rs = RenderScript.create(ctx);
            ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
            Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                theIntrinsic.setRadius(BLUR_RADIUS);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                theIntrinsic.setInput(tmpIn);
            }
            theIntrinsic.forEach(tmpOut);
            tmpOut.copyTo(outputBitmap);

            return outputBitmap;
        }

        private static Bitmap getScreenshot(View v) {
            Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(b);
            v.draw(c);
            return b;
        }
    }

}
