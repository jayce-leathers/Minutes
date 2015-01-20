package apps.jayceleathers.me;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import apps.jayceleathers.me.minutes.R;


public class IntervalListFragment extends android.support.v4.app.ListFragment {

        public IntervalListFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_interval_list, container, false);
            return rootView;
        }

}



