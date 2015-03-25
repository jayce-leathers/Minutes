package apps.jayceleathers.me.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import net.simonvt.numberpicker.NumberPicker;

import apps.jayceleathers.me.data.Interval;
import apps.jayceleathers.me.minutes.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewIntervalDialogFragment extends android.support.v4.app.DialogFragment {
    //the ui views
    private Button btnSave;
    private Button btnCancel;
    private EditText etLabel;
    private EditText etReps;
    //what interval to display seconds in
    private static final int SEC_INTERVAL = 15;
    //the maximum allowable min value
    private static final int MAX_MIN = 10;
    private NewDialogListener listener;

    public NewIntervalDialogFragment() {
        // Required empty public constructor
    }

    public static NewIntervalDialogFragment newInstance() {
        return new NewIntervalDialogFragment();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View newView = inflater.inflate(R.layout.fragment_new_interval_dialog, container, false);
        etLabel = (EditText) newView.findViewById(R.id.etLabel);

        //hides the keyboard on enter of data
        etLabel.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(newView.getWindowToken(), 0);
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });


        etReps = (EditText) newView.findViewById(R.id.etReps);
        //with reps edittext
        etReps.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(newView.getWindowToken(), 0);
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
        //fetch number picker views
        final NumberPicker npMinutesWork = (NumberPicker) newView.findViewById(R.id.npMinutesWork);
        final NumberPicker npSecondsWork = (NumberPicker) newView.findViewById(R.id.npSecondsWork);
        final NumberPicker npMinutesRest = (NumberPicker) newView.findViewById(R.id.npMinutesRest);
        final NumberPicker npSecondsRest = (NumberPicker) newView.findViewById(R.id.npSecondsRest);

        //create arrays for the values the number pickers can display
        String[] mins = new String[MAX_MIN+1];
        for(int i=0; i<mins.length; i++)
            mins[i] = Integer.toString(i);
        String[] secs = new String[60/SEC_INTERVAL];
        for(int i=0, n=0; i<secs.length; i++,n+=SEC_INTERVAL) {
            if(n==0)
                secs[i]="00";
            else
                secs[i] = Integer.toString(n);
        }

        npMinutesWork.setMinValue(0);
        npMinutesWork.setMaxValue(mins.length-1);
        npMinutesWork.setWrapSelectorWheel(false);
        npMinutesWork.setDisplayedValues(mins);
        npMinutesWork.setValue(1);

        npMinutesRest.setMinValue(0);
        npMinutesRest.setMaxValue(mins.length-1);
        npMinutesRest.setWrapSelectorWheel(false);
        npMinutesRest.setDisplayedValues(mins);
        npMinutesRest.setValue(0);

        npSecondsWork.setMinValue(0);
        npSecondsWork.setMaxValue(secs.length-1);
        npSecondsWork.setWrapSelectorWheel(false);
        npSecondsWork.setDisplayedValues(secs);

        npSecondsRest.setMinValue(0);
        npSecondsRest.setMaxValue(secs.length-1);
        npSecondsRest.setWrapSelectorWheel(false);
        npSecondsRest.setDisplayedValues(secs);
        npSecondsRest.setValue(secs.length/2);

        btnSave = (Button) newView.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create
                long workMins = npMinutesWork.getValue() * 60000;
                long restMins = npMinutesRest.getValue() * 60000;
                long workSecs = npSecondsWork.getValue()*SEC_INTERVAL * 1000;
                long restSecs = npSecondsRest.getValue()*SEC_INTERVAL * 1000;
                String label = etLabel.getText().toString().toUpperCase();
                Interval newInterval = new Interval(label, workMins + workSecs, restMins + restSecs, Integer.parseInt(etReps.getText().toString()));
                newInterval.save();
                listener.onFinishNewDialog();
                dismiss();
            }
        });
        btnCancel = (Button) newView.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
        return newView;
    }

    //attaches listener to acitvity
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
        listener = (NewDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NewDialogListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    //listener subscribed to by activity to handle the addition of a new interval
    public interface NewDialogListener {
        void onFinishNewDialog();
    }

}
