package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekBar;
    TextView counterTextView;
    CountDownTimer countDownTimer;
    MediaPlayer mediaPlayer;
    Button goButton;
    boolean isCounterActive = false;

    public void updateTimer(int secsLeft){

        int mins = secsLeft / 60;
        int secs = secsLeft % 60;

        String minString = "";
        String secString = "";

        if(mins < 10){
            minString = "0";
        }

        if(secs < 10){
            secString = "0";
        }

        counterTextView.setText(minString + Integer.toString(mins) + ":" + secString + Integer.toString(secs));

    }

    public void resetTimer(){

        counterTextView.setText("00:10");
        timerSeekBar.setProgress(10);
        timerSeekBar.setEnabled(true);
        countDownTimer.cancel();
        goButton.setText("GO!");
        isCounterActive = false;
    }

    public void countdown(View v){

        if(isCounterActive){

            resetTimer();

        }else {

            isCounterActive = true;
            goButton.setText("STOP!");
            timerSeekBar.setEnabled(false);

            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long l) {

                    updateTimer((int) (l / 1000));

                }

                @Override
                public void onFinish() {

                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.schoolbell);
                    mediaPlayer.start();
                    resetTimer();

                }
            };

            countDownTimer.start();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = (SeekBar) findViewById(R.id.seekBar);
        counterTextView = (TextView) findViewById(R.id.textView);
        goButton = (Button) findViewById(R.id.button);

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(10);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                updateTimer(i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}