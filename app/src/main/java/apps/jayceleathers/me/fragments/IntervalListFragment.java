package apps.jayceleathers.me.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import apps.jayceleathers.me.adapters.IntervalListAdapter;
import apps.jayceleathers.me.data.Interval;
import apps.jayceleathers.me.minutes.R;
import apps.jayceleathers.me.views.FloatingActionButton;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class IntervalListFragment extends android.support.v4.app.ListFragment {

    private OnFragmentInteractionListener mListener;
    private ArrayList<Interval> intervals;

    // TODO: Rename and change types of parameters
    public static IntervalListFragment newInstance() {
        IntervalListFragment fragment = new IntervalListFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public IntervalListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FloatingActionButton mFab = new FloatingActionButton.Builder(getActivity())
                .withColor(getResources().getColor(R.color.logo_color))
                .withDrawable(getResources().getDrawable(R.drawable.plus))
                .withSize(72)
                .withMargins(0, 0, 16, 16)
                .create();
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        intervals = new ArrayList<>(10);
        Interval example = new Interval("Situps", 10000L, 50000L);
        intervals.add(example);
        setListAdapter(new IntervalListAdapter(getActivity(), intervals));
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(intervals.get(position));
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Interval clickedInterval);
    }

}
