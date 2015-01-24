package apps.jayceleathers.me.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import apps.jayceleathers.me.minutes.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewIntervalDialogFragment extends android.support.v4.app.DialogFragment {


    public NewIntervalDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_interval_dialog, container, false);
    }


}
