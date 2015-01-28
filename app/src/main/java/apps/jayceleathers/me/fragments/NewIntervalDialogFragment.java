package apps.jayceleathers.me.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import apps.jayceleathers.me.minutes.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewIntervalDialogFragment extends android.support.v4.app.DialogFragment {


    public NewIntervalDialogFragment() {
        // Required empty public constructor
    }

    static NewIntervalDialogFragment newInstance() {
        return new NewIntervalDialogFragment();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View newView = inflater.inflate(R.layout.fragment_new_interval_dialog, container, false);

        NumberPicker npMinutesWork = (NumberPicker) newView.findViewById(R.id.npMinutesWork);
        NumberPicker npSecondsWork = (NumberPicker) newView.findViewById(R.id.npSecondsWork);
        NumberPicker npMinutesRest = (NumberPicker) newView.findViewById(R.id.npMinutesRest);
        NumberPicker npSecondsRest = (NumberPicker) newView.findViewById(R.id.npSecondsRest);
        String[] mins = new String[11];
        for(int i=0; i<mins.length; i++)
            mins[i] = Integer.toString(i);
        String[] secs = new String[60];
        for(int i=0; i<secs.length; i++)
            secs[i] = Integer.toString(i);

        npMinutesWork.setMinValue(0);
        npMinutesWork.setMaxValue(10);
        npMinutesWork.setWrapSelectorWheel(false);
        npMinutesWork.setDisplayedValues(mins);
        npMinutesWork.setValue(1);

        npMinutesRest.setMinValue(0);
        npMinutesRest.setMaxValue(10);
        npMinutesRest.setWrapSelectorWheel(false);
        npMinutesRest.setDisplayedValues(mins);
        npMinutesRest.setValue(0);

        npSecondsWork.setMinValue(0);
        npSecondsWork.setMaxValue(59);
        npSecondsWork.setWrapSelectorWheel(false);
        npSecondsWork.setValue(30);
        npSecondsWork.setDisplayedValues(secs);

        npSecondsRest.setMinValue(0);
        npSecondsRest.setMaxValue(59);
        npSecondsRest.setWrapSelectorWheel(false);
        npSecondsRest.setValue(30);
        npSecondsRest.setDisplayedValues(secs);
        return newView;
    }


}
