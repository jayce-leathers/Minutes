package apps.jayceleathers.me;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import apps.jayceleathers.me.data.Interval;

import apps.jayceleathers.me.minutes.R;

/**
 * Created by Jayce on 1/23/15.
 */
public class IntervalListAdapter extends ArrayAdapter {

    private Context context;

    public IntervalListAdapter(Context context, List intervals) {
        super(context, android.R.layout.simple_list_item_1, intervals);
        this.context = context;
    }

    /*
    * Holder for the list items.
    */
    private class ViewHolder {
        TextView tvLabel;
        TextView tvWorkTime;
        TextView tvRestTime;
    }

    private String formatIntervalTimer(long milliseconds) {
        int secs = (int) (milliseconds / 1000);
        int mins = secs / 60;
        secs = secs % 60;
        return mins + ":" + secs;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Interval interval = (Interval) getItem(position);
        View viewToUse = null;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            viewToUse = mInflater.inflate(R.layout.interval_list_row, null);


            holder = new ViewHolder();
            holder.tvLabel = (TextView) viewToUse.findViewById(R.id.tvLabel);
            holder.tvWorkTime = (TextView) viewToUse.findViewById(R.id.tvWorkTime);
            holder.tvRestTime = (TextView) viewToUse.findViewById(R.id.tvRestTime);
            viewToUse.setTag(holder);
        } else {
            viewToUse = convertView;
            holder = (ViewHolder) viewToUse.getTag();
        }
        holder.tvLabel.setText(interval.getLabel());
        holder.tvWorkTime.setText(formatIntervalTimer(interval.getWorkTime()));
        holder.tvRestTime.setText(formatIntervalTimer(interval.getRestTime()));
        return viewToUse;
    }

}
