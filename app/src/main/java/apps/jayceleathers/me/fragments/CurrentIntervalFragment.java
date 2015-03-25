package apps.jayceleathers.me.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import apps.jayceleathers.me.data.Interval;
import apps.jayceleathers.me.minutes.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentIntervalFragment extends android.support.v4.app.Fragment {
    //views
    private Button btnPlayPause;
    private Button btnStop;
    private TextView tvCurrentTime;
    private TextView tvCurrentLabel;
    private TextView tvNextLabel;
    private TextView tvNextTime;
    private TextView tvRepCount;
    //handler for the uiupdate runnable
    private Handler mHandler = new Handler();
    //current interval being displayed
    private Interval currentInterval;
    //current interval key to put the current interval in the bundle
    public static String INTERVAL_KEY = "Current Interval";
    //values to maintain the timer
    private long startTime = 0L;
    private long timeInMilliseconds = 0L;
    private long timeSwapBuff = 0L;
    private long updatedTime = 0L;
    private int repCount;
    //booleans to represent the state of the timer
    private boolean paused = true;
    private boolean firstStart = true;


    public static CurrentIntervalFragment newInstance(Interval currentInterval) {
        CurrentIntervalFragment fragment = new CurrentIntervalFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(INTERVAL_KEY, currentInterval);
        fragment.setArguments(bundle);
        return fragment;
    }

    public CurrentIntervalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View newView = inflater.inflate(R.layout.fragment_current_interval, container, false);
        //set the current interval
        this.currentInterval = (Interval) getArguments().getSerializable(INTERVAL_KEY);
        repCount = currentInterval.getReps();

        //fetch the views for the ui elements
        btnPlayPause = (Button) newView.findViewById(R.id.btnPlayPause);
        btnStop = (Button)  newView.findViewById(R.id.btnStop);
        tvCurrentLabel = (TextView) newView.findViewById(R.id.tvCurrentLabel);
        tvCurrentTime = (TextView) newView.findViewById(R.id.tvCurrentTime);
        tvNextLabel = (TextView) newView.findViewById(R.id.tvNextLabel);
        tvNextTime = (TextView) newView.findViewById(R.id.tvNextTime);
        tvRepCount = (TextView) newView.findViewById(R.id.tvRepCounter);
        //set starting ui
        resetDisplay();

        //pause/play/stop button listeners  all actions implemented in play(), pause(), and stop methods below
        btnPlayPause.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        if (paused)
                            btnPlayPause.setBackgroundColor(getResources().getColor(R.color.logo_dark));
                        else
                            btnPlayPause.setBackgroundColor(getResources().getColor(R.color.pause_dark));
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        if (paused){
                            btnPlayPause.setBackgroundColor(getResources().getColor(R.color.pause_color));
                            btnPlayPause.setText("PAUSE");
                            play();
                        }
                        else {
                            btnPlayPause.setBackgroundColor(getResources().getColor(R.color.logo_color));
                            btnPlayPause.setText("START");
                            pause();
                        }
                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });

        btnStop.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        btnStop.setBackgroundColor(getResources().getColor(R.color.stop_dark));
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        btnStop.setBackgroundColor(getResources().getColor(R.color.stop_color));
                        stop();
                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });
        return newView;
    }

    //runnable to update the ui - posts it self if repcount is not yet reached
    Runnable uiUpdate = new Runnable() {
        public void run() {

                //time elapsed since start
                timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
                //logic is symmetric for work and rest intervals only difference is the ui changes
                //and repcount is only decremented on a work interval zero
                if (currentInterval.isWork()) {
                    //computes new time to display
                    updatedTime = currentInterval.getWorkTime() - timeInMilliseconds - timeSwapBuff;
                    //if the time flips the interval
                    if (updatedTime <= 0) {
                        //reset start time and buffer and set the start time of next rest interval
                        startTime = SystemClock.uptimeMillis();
                        timeSwapBuff = 0L;
                        updatedTime = currentInterval.getRestTime();
                        //set interval to rest and decrement the rep counter one rep has been completed
                        currentInterval.flipInterval();
                        repCount--;
                        //all reps have been completed reset timer to start state
                        if (repCount == 0) {
                            paused = true;

                            currentInterval.setWork();
                            tvCurrentTime.setText("");
                            resetDisplay();
                            timeSwapBuff = 0L;
                            updatedTime = 0L;
                            timeInMilliseconds = 0L;

                        } else {
                            //more reps to go swap to the rest state
                            tvRepCount.setText(repCount + "/" + currentInterval.getReps());
                            tvCurrentLabel.setText(getString(R.string.REST));
                            tvNextTime.setText(Interval.formatIntervalTimer(currentInterval.getWorkTime()));
                            tvNextLabel.setText(getString(R.string.WORK));
                            tvCurrentTime.setTextColor(getResources().getColor(R.color.stop_color));

                            tvRepCount.setText("" + repCount +"/" + currentInterval.getReps());
                        }

                    }
                }
                //comparable logic for rest state
                else {
                    updatedTime = currentInterval.getRestTime() - timeInMilliseconds - timeSwapBuff;
                    if (updatedTime <= 0) {
                        startTime = SystemClock.uptimeMillis();
                        timeSwapBuff = 0L;
                        updatedTime = currentInterval.getWorkTime();
                        currentInterval.flipInterval();
                        tvCurrentLabel.setText(getString(R.string.WORK));
                        tvNextTime.setText(Interval.formatIntervalTimer(currentInterval.getRestTime()));
                        tvNextLabel.setText(getString(R.string.REST));
                        tvCurrentTime.setTextColor(getResources().getColor(R.color.logo_color));
                    }
                }
            //post next runnable if more reps to go
            if (repCount > 0) {

                tvCurrentTime.setText(Interval.formatIntervalTimer(updatedTime));
                mHandler.postDelayed(this,0);
            }
            //else reset the reps must check after checking the rep otherwise runnable would always be posted
            //because repcount would always be greater than zero
            else
                repCount = currentInterval.getReps();
        }

    };

    //replaces the current interval with a new one used in interval list when selecting an interval
    public void setNewInterval(Interval newInterval){
        currentInterval = newInterval;
        repCount = currentInterval.getReps();
        mHandler.removeCallbacks(uiUpdate);
        resetDisplay();
    }

    //sets display to the start state
    private void resetDisplay(){
        tvRepCount.setText(currentInterval.getReps() + "/" + currentInterval.getReps());
        tvCurrentTime.setText(Interval.formatIntervalTimer(currentInterval.getWorkTime()));
        tvCurrentLabel.setText(getString(R.string.WORK));
        tvNextTime.setText(Interval.formatIntervalTimer((currentInterval.getRestTime())));
        tvNextLabel.setText(getString(R.string.REST));
        tvCurrentTime.setTextColor(getResources().getColor(R.color.logo_color));
        btnPlayPause.setBackgroundColor(getResources().getColor(R.color.logo_color));
        btnPlayPause.setText("START");
    }

    //starts the timer and posts the first ui update runnable
    private void play(){
        paused = false;
        startTime = SystemClock.uptimeMillis();
        mHandler.postDelayed(uiUpdate,0);
    }

    //pauses timer and removes callbacks to the ui update runnable
    private void pause(){
        paused = true;
        mHandler.removeCallbacks(uiUpdate);
        timeSwapBuff = timeInMilliseconds;
    }

    //resets everrrything
    private void stop(){
        paused = true;
        resetDisplay();
        repCount = currentInterval.getReps();
        currentInterval.setWork();
        timeSwapBuff = 0L;
        updatedTime = 0L;
        timeInMilliseconds = 0L;
        mHandler.removeCallbacks(uiUpdate);
    }





}
