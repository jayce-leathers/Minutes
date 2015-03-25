package apps.jayceleathers.me.activities;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import apps.jayceleathers.me.adapters.TabPagerAdapter;
import apps.jayceleathers.me.data.Interval;
import apps.jayceleathers.me.fragments.CurrentIntervalFragment;
import apps.jayceleathers.me.fragments.IntervalListFragment;
import apps.jayceleathers.me.fragments.NewIntervalDialogFragment;
import apps.jayceleathers.me.minutes.R;
import apps.jayceleathers.me.views.FloatingActionButton;

public class MainActivity extends ActionBarActivity implements ActionBar.TabListener, IntervalListFragment.OnListFragmentInteractionListener, NewIntervalDialogFragment.NewDialogListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    TabPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton mFab = new FloatingActionButton.Builder(this)
                .withColor(getResources().getColor(R.color.logo_color))
                .withDrawable(getResources().getDrawable(R.drawable.plus))
                .withSize(72)
                .withMargins(0, 0, 6,6)
                .create();

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayShowTitleEnabled(false);
        getSupportActionBar().setIcon(R.drawable.logo1);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), this);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onClick(View v, Interval clickedInterval) {
        CurrentIntervalFragment refreshedFragment = (CurrentIntervalFragment) mSectionsPagerAdapter.getRegisteredFragment(1);
        refreshedFragment.setNewInterval(clickedInterval);
        mViewPager.setCurrentItem(1);
    }

    //sets current list fragment and refreshes it
    @Override
    public void onFinishNewDialog() {

            IntervalListFragment fragment = (IntervalListFragment) mSectionsPagerAdapter.getRegisteredFragment(0);
            fragment.refresh();

    }

    //creates and shows a dialog fragment of NewIntervalDialogFragment
    void showDialog(){
        DialogFragment newFragment = NewIntervalDialogFragment.newInstance();
        newFragment.show(getSupportFragmentManager(), "dialog");
    }




    }



