package apps.jayceleathers.me.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import apps.jayceleathers.me.data.Interval;
import apps.jayceleathers.me.minutes.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewIntervalDialogFragment extends android.support.v4.app.DialogFragment {
    private Button btnSave;
    private EditText etLabel;
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
        View newView = inflater.inflate(R.layout.fragment_new_interval_dialog, container, false);
        etLabel = (EditText) newView.findViewById(R.id.etLabel);
        final NumberPicker npMinutesWork = (NumberPicker) newView.findViewById(R.id.npMinutesWork);
        final NumberPicker npSecondsWork = (NumberPicker) newView.findViewById(R.id.npSecondsWork);
        final NumberPicker npMinutesRest = (NumberPicker) newView.findViewById(R.id.npMinutesRest);
        final NumberPicker npSecondsRest = (NumberPicker) newView.findViewById(R.id.npSecondsRest);
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

        btnSave = (Button) newView.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewDialogListener activity = (NewDialogListener) getActivity();
//                if(activity==null){
//                    Log.d("NULL" ,"NULL ACTIVITY");
//                }
                long workMins = npMinutesWork.getValue() * 60000;
                long restMins = npMinutesRest.getValue() * 60000;
                long workSecs = npSecondsWork.getValue() * 1000;
                long restSecs = npSecondsRest.getValue() * 1000;
                String label = etLabel.getText().toString();
                Interval newInterval = new Interval(label, workMins + workSecs, restMins + restSecs);
                newInterval.save();
                activity.onFinishNewDialog();
                dismiss();
            }
        });
        return newView;
    }

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

    public interface NewDialogListener {
        void onFinishNewDialog();
    }

}
