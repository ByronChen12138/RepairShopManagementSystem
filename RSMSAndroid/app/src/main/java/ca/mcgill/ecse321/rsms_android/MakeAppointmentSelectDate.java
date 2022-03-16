package ca.mcgill.ecse321.rsms_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

public class MakeAppointmentSelectDate extends AppCompatActivity {

    private CalendarView simpleCalendarView;
    private Button button;
    private String date;

    /**
     * this page will asked the customer to pick a selected date and display all the available shifts
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date_for_appointment);
        // get the reference of CalendarView
        simpleCalendarView = findViewById(R.id.calendarView);
        // perform setOnDateChangeListener event on CalendarView
        simpleCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // display the selected date by using a toast
                String month_string = (month + 1) + "";
                String day_string = dayOfMonth + "";
                Toast.makeText(getApplicationContext(), dayOfMonth + "/" + (month + 1) + "/" + year, Toast.LENGTH_LONG).show();
                if (month < 10) {
                    month_string = "0" + (month + 1);
                }
                if (dayOfMonth < 10) {
                    day_string = "0" + dayOfMonth;
                }
                date = year + "-" + month_string + "-" + day_string;
            }
        });

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShowShifts.class);
                intent.putExtra("date", date);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
            }
        });
    }
}