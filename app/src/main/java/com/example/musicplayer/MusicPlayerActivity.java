package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MusicPlayerActivity extends AppCompatActivity {

    TextView titleTv, currentTimeTv, totalTimeTv;
    SeekBar seekBar;
    ImageView pausePlay, nextBtn, previousBtn, musicIcon, hengio, repeat;
    ArrayList<AudioModel> songsList;
    AudioModel currentSong;
    MediaPlayer mediaPlayer = MyMediaPlayer.getInstance();
    int x = 0;
    private CountDownTimer countDownTimer;
    private long timeLeft = 60000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        titleTv = findViewById(R.id.song_title);
        currentTimeTv = findViewById(R.id.current_time);
        totalTimeTv = findViewById(R.id.total_time);
        seekBar = findViewById(R.id.seek_bar);
        pausePlay = findViewById(R.id.pause_play);
        nextBtn = findViewById(R.id.next);
        previousBtn = findViewById(R.id.previous);
        musicIcon = findViewById(R.id.music_icon_big);
        hengio = findViewById(R.id.hengio);
        repeat = findViewById(R.id.repeat);

        titleTv.setSelected(true);

        songsList = (ArrayList<AudioModel>) getIntent().getSerializableExtra("LIST");

        setResourcesWithMusic();

        MusicPlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    currentTimeTv.setText(convertToMMSS(mediaPlayer.getCurrentPosition() + ""));

                    if (mediaPlayer.isPlaying()) {
                        pausePlay.setImageResource(R.drawable.baseline_pause_circle_outline_24);
                        musicIcon.setRotation(x++);
                    } else {
                        pausePlay.setImageResource(R.drawable.baseline_play_circle_outline_24);
                        musicIcon.setRotation(0);
                    }

                }
                new Handler().postDelayed(this, 100);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

//Nút hẹn giờ đi ngủ
        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                // Cập nhật hiển thị thời gian còn lại
            }

            @Override
            public void onFinish() {
                // Thay đổi hình ảnh khi hết thời gian
                hengio.setImageResource(R.drawable.baseline_alarm_24);
            }
        };
        hengio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog();
            }

            private void showTimePickerDialog() {
                TimePickerDialog timePickerDialog = new TimePickerDialog(

                        getApplicationContext(),new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        long millis = (hourOfDay * 60 + minute) * 60 * 1000; // chuyển đổi giờ và phút sang milliseconds
                        startTimer(millis);
                    }

                    private void startTimer(long time) {
                        timeLeft = time;
                        countDownTimer.cancel(); // Huỷ bỏ bất kỳ hẹn giờ nào đang chạy
                        countDownTimer.start(); // Bắt đầu hẹn giờ mới
                    }
                },
                        0, // Giờ mặc định
                        0, // Phút mặc định
                        true // Sử dụng định dạng 24 giờ
                );
                timePickerDialog.show();
            }
        });
    }
    //Lặp lại bài hát
    boolean isRepeatOn = false;

    public void toggleRepeat(View view) {
        if (isRepeatOn) {
            repeat.setImageResource(R.drawable.baseline_repeat_one_24);
            mediaPlayer.setLooping(false);
            isRepeatOn = false;


        } else {
            repeat.setImageResource(R.drawable.baseline_repeat_one_on_24);
            mediaPlayer.setLooping(true);
            isRepeatOn = true;
            mediaPlayer.start();
        }

    }

    void setResourcesWithMusic() {
        currentSong = songsList.get(MyMediaPlayer.currentIndex);

        titleTv.setText(currentSong.getTitle());

        totalTimeTv.setText(convertToMMSS(currentSong.getDuration()));

        pausePlay.setOnClickListener(v -> pausePlay());
        nextBtn.setOnClickListener(v -> playNextSong());
        previousBtn.setOnClickListener(v -> playPreviousSong());

        playMusic();
    }


    private void playMusic() {

        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(currentSong.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            seekBar.setProgress(0);
            seekBar.setMax(mediaPlayer.getDuration());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void playNextSong() {

        if (MyMediaPlayer.currentIndex == songsList.size() - 1)
            return;
        MyMediaPlayer.currentIndex += 1;
        mediaPlayer.reset();
        setResourcesWithMusic();

    }

    private void playPreviousSong() {
        if (MyMediaPlayer.currentIndex == 0)
            return;
        MyMediaPlayer.currentIndex -= 1;
        mediaPlayer.reset();
        setResourcesWithMusic();
    }

    private void pausePlay() {
        if (mediaPlayer.isPlaying())
            mediaPlayer.pause();
        else
            mediaPlayer.start();
    }


    public static String convertToMMSS(String duration) {
        Long millis = Long.parseLong(duration);
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
    }
}