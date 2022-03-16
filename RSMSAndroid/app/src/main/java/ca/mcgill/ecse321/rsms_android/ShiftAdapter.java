package ca.mcgill.ecse321.rsms_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class ShiftAdapter extends BaseAdapter {

    LayoutInflater mInflater;
    String dateRequired;
    ListView myListView;
    List<String> shiftId;
    List<String> timeStart;
    List<String> timeEnd;

    public ShiftAdapter(Context c, List<String> shiftId, List<String> timeStart, List<String> timeEnd, String dateRequired) {
        this.shiftId = shiftId;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.dateRequired = dateRequired;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return shiftId.size();
    }

    @Override
    public Object getItem(int i) {
        return shiftId.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.single_shift, null);
        TextView shiftIdTextView = (TextView) view.findViewById(R.id.shiftid);
        TextView timeTextView = (TextView) view.findViewById(R.id.time);
        TextView dateTextView = (TextView) view.findViewById(R.id.date);

        String shiftId_show = shiftId.get(i);
        String timeStart_show = timeStart.get(i);
        String timeEnd_show = timeEnd.get(i);
        String date_show = dateRequired;

        shiftIdTextView.setText(shiftId_show);
        timeTextView.setText(timeStart_show + "--" + timeEnd_show);
        dateTextView.setText(date_show);

        return view;
    }
}
