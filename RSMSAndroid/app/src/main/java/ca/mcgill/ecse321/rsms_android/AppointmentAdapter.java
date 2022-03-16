package ca.mcgill.ecse321.rsms_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AppointmentAdapter extends BaseAdapter {
    /**
     * this class is used to display the appointment of customers on the page
     */
    LayoutInflater mInflater;
    List<String> appointments;
    List<String> plateNos;
    List<String> times;
    List<String> prices;
    List<String> statuses;

    public AppointmentAdapter(Context c, List<String> appointments, List<String> plateNos,
                              List<String> times, List<String> prices, List<String> statuses) {
        this.appointments = appointments;
        this.plateNos = plateNos;
        this.times = times;
        this.prices = prices;
        this.statuses = statuses;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return appointments.size();
    }

    @Override
    public Object getItem(int i) {
        return appointments.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.appointment_view_and_pay_detail, null);
        TextView appointmentTextView = (TextView) view.findViewById(R.id.appointmentTextView);
        TextView plateNoTextView = (TextView) view.findViewById(R.id.plateNoTextView);
        TextView timeTextView = (TextView) view.findViewById(R.id.timeTextView);
        TextView priceTextView = (TextView) view.findViewById(R.id.priceTextView);
        TextView statusTextView = (TextView) view.findViewById(R.id.statusTextView);

        String appointment = appointments.get(i);
        String plateNo = plateNos.get(i);
        String time = times.get(i);
        String price = prices.get(i);
        String status = statuses.get(i);

        appointmentTextView.setText(appointment);
        plateNoTextView.setText(plateNo);
        timeTextView.setText(time);
        priceTextView.setText(price);
        statusTextView.setText(status);

        return view;
    }
}
