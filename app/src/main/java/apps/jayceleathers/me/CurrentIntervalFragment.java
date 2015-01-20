package apps.jayceleathers.me;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import apps.jayceleathers.me.minutes.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentIntervalFragment extends android.support.v4.app.Fragment {


    public CurrentIntervalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_interval, container, false);
    }


}
