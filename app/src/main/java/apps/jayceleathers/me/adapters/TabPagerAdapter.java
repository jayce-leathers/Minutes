package apps.jayceleathers.me.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.Locale;

import apps.jayceleathers.me.data.Interval;
import apps.jayceleathers.me.fragments.CurrentIntervalFragment;
import apps.jayceleathers.me.fragments.IntervalListFragment;
import apps.jayceleathers.me.minutes.R;

/**
 * A {@link android.support.v4.app.FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class TabPagerAdapter extends SmartFragmentStatePagerAdapter {
    Context mContext;

    public TabPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        Interval testInterval = new Interval("Situps", 60000L, 30000L);
        switch (position) {
            case 0:
                return new IntervalListFragment();
            case 1:
                return CurrentIntervalFragment.newInstance(testInterval);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return mContext.getString(R.string.title_section1).toUpperCase(l);
            case 1:
                return mContext.getString(R.string.title_section2).toUpperCase(l);
        }
        return null;
    }
}
