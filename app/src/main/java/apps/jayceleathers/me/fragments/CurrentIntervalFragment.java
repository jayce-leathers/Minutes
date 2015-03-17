package apps.jayceleathers.me.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
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

    private Button btnStart;
    private Button btnPause;
    private Button btnStop;
    private TextView tvCurrentTime;
    private TextView tvCurrentLabel;
    private TextView tvNextLabel;
    private TextView tvNextTime;
    private long startTime = 0L;
    private Handler customHandler = new Handler();
    private Interval currentInterval;
    public static String Interval_Key = "Current Interval";
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;


    public static CurrentIntervalFragment newInstance(Interval currentInterval) {
        CurrentIntervalFragment fragment = new CurrentIntervalFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Interval_Key, currentInterval);
        fragment.setArguments(bundle);
        return fragment;
    }

    public CurrentIntervalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        FloatingActionButton mFab = new FloatingActionButton.Builder(getActivity())
//                .withColor(getResources().getColor(R.color.logo_color))
//                .withDrawable(getResources().getDrawable(R.drawable.plus))
//                .withSize(72)
//                .withMargins(0, 0, 16, 16)
//                .create();
//        mFab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DialogFragment newFragment = NewIntervalDialogFragment.newInstance();
//                newFragment.show(getFragmentManager(), "dialog");
//            }
//        });
        // Inflate the layout for this fragment
        View newView = inflater.inflate(R.layout.fragment_current_interval, container, false);
        this.currentInterval = (Interval) getArguments().getSerializable(Interval_Key);
        btnStart = (Button) newView.findViewById(R.id.btnStart);
        btnPause = (Button) newView.findViewById(R.id.btnPause);
        btnStop = (Button)  newView.findViewById(R.id.btnStop);
        tvCurrentLabel = (TextView) newView.findViewById(R.id.tvCurrentLabel);
        tvCurrentTime = (TextView) newView.findViewById(R.id.tvCurrentTime);
        tvNextLabel = (TextView) newView.findViewById(R.id.tvNextLabel);
        tvNextTime = (TextView) newView.findViewById(R.id.tvNextTime);
        setDisplay();

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = SystemClock.uptimeMillis();
                customHandler.postDelayed(updateTimerThread, 0);
            }
        });
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSwapBuff = timeInMilliseconds;
                customHandler.removeCallbacks(updateTimerThread);
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDisplay();
                customHandler.removeCallbacks(updateTimerThread);
            }
        });
        return newView;
    }


    private Runnable updateTimerThread = new Runnable() {
        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            if(currentInterval.isWork()) {
                updatedTime = currentInterval.getWorkTime() - timeInMilliseconds - timeSwapBuff;
                if(updatedTime<=0){
                    startTime = SystemClock.uptimeMillis();
                    timeSwapBuff = 0L;
                    updatedTime = currentInterval.getRestTime();
                    currentInterval.flipInterval();
                    tvCurrentLabel.setText(getString(R.string.REST));
                    tvNextTime.setText(formatMillis(currentInterval.getWorkTime()));
                    tvNextLabel.setText(getString(R.string.WORK));
                    tvCurrentTime.setTextColor(getResources().getColor(R.color.stop_color));
                }
            }
            else {
                updatedTime = currentInterval.getRestTime() - timeInMilliseconds- timeSwapBuff;
                if(updatedTime<=0){
                    startTime = SystemClock.uptimeMillis();
                    timeSwapBuff = 0L;
                    updatedTime = currentInterval.getWorkTime();
                    currentInterval.flipInterval();
                    tvCurrentLabel.setText(getString(R.string.WORK));
                    tvNextTime.setText(formatMillis(currentInterval.getRestTime()));
                    tvNextLabel.setText(getString(R.string.REST));
                    tvCurrentTime.setTextColor(getResources().getColor(R.color.logo_color));
                }
            }

            tvCurrentTime.setText(formatMillis(updatedTime));
            customHandler.postDelayed(this, 0);
        }

    };

    public void setNewInterval(Interval newInterval){
        currentInterval = newInterval;
        customHandler.removeCallbacks(updateTimerThread);
        setDisplay();
    }

    private void setDisplay(){
        if(currentInterval.isWork()) {
            tvCurrentTime.setText(formatMillis(currentInterval.getWorkTime()));
            tvCurrentLabel.setText(getString(R.string.WORK));
            tvNextTime.setText(formatMillis(currentInterval.getRestTime()));
            tvNextLabel.setText(getString(R.string.REST));
            tvCurrentTime.setTextColor(getResources().getColor(R.color.logo_color));
        }
        else {
            tvCurrentTime.setText(formatMillis(currentInterval.getRestTime()));
            tvCurrentLabel.setText(getString(R.string.REST));
            tvNextTime.setText(formatMillis(currentInterval.getWorkTime()));
            tvNextLabel.setText(getString(R.string.WORK));
            tvCurrentTime.setTextColor(getResources().getColor(R.color.stop_color));
        }
    }


    private String formatMillis(long millis){
        int secs = (int) (millis / 1000);
        int mins = secs / 60;
        secs = secs % 60;
        return "0" + mins + ":"
                + String.format("%02d", secs);
    }

}
