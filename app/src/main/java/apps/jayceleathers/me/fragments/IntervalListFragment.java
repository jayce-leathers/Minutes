package apps.jayceleathers.me.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
 * Activities containing this fragment MUST implement the {@link apps.jayceleathers.me.fragments.IntervalListFragment.OnListFragmentInteractionListener}
 * interface.
 */
public class IntervalListFragment extends android.support.v4.app.ListFragment {

    private static final int CONTEXT_ACTION_DELETE = 101;
    private OnListFragmentInteractionListener mListener;
    public int DIALOG_REQUEST_CODE = 101;
    private ArrayList<Interval> intervals;
    private IntervalListAdapter adapter;
    static  FloatingActionButton mFab;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public IntervalListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mFab = new FloatingActionButton.Builder(getActivity())
                .withColor(getResources().getColor(R.color.logo_color))
                .withDrawable(getResources().getDrawable(R.drawable.plus))
                .withSize(72)
                .withMargins(0, 0, 16, 16)
                .create();

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        try{
        intervals = (ArrayList<Interval>) Interval.listAll(Interval.class);}
        catch (android.database.sqlite.SQLiteException e){
            intervals = new ArrayList<>(10);
        }
        //Interval example = new Interval("Situps", 10000L, 50000L);
        //intervals.add(example);
        adapter = new IntervalListAdapter(getActivity(), intervals);
        setListAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //menu.setHeaderTitle("Menu");
        menu.add(0, CONTEXT_ACTION_DELETE, 0, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CONTEXT_ACTION_DELETE) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            ((IntervalListAdapter) getListAdapter()).remove(info.position);
            ((IntervalListAdapter) getListAdapter()).notifyDataSetChanged();
        } else {
            return false;
        }
        return true;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registerForContextMenu(getListView());

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnListFragmentInteractionListener) activity;
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
            mListener.onClick(v, intervals.get(position));
        }
    }


    public void refresh(){
        //Log.d("REFRESH", "REFRESH CALLED");
        intervals = (ArrayList<Interval>) Interval.listAll(Interval.class);

        IntervalListAdapter newAdapter = new IntervalListAdapter(getActivity(),intervals);
        setListAdapter(newAdapter);

    }
    void showDialog(){
        DialogFragment newFragment = NewIntervalDialogFragment.newInstance();
        newFragment.setTargetFragment(this, 1);
        newFragment.show(getFragmentManager(), "dialog");
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnListFragmentInteractionListener {
        public void onClick(View v, Interval clickedInterval);
    }

}
