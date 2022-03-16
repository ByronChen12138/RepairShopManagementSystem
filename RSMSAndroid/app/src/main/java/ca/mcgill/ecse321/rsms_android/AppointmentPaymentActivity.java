package ca.mcgill.ecse321.rsms_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class AppointmentPaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**
         * this page is used to let customer pay for an appointment.
         * Since the price is predefined, we put the price pf the appointment
         * directly on the screen if the appointment is not paid yet.
         *
         */
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String type = intent.getStringExtra("ca.mcgill.ecse321.rsms_android.TYPE");
        String status  = intent.getStringExtra("ca.mcgill.ecse321.rsms_android.STATUS");
        String price = "10";

        if (status != "paid"){
            setContentView(R.layout.appointment_payment);
            TextView priceForPayTextView = (TextView) findViewById(R.id.priceForPayTextView);
            if (type.equals("car wash")){
                price = "$30";
            }else if(type.equals("maintenance")) {
                price = "$80";
            }else if(type.equals("repair")) {
                price = "$150";
            }else if(type.equals("changeTire")) {
                price = "$30";
            }
            priceForPayTextView.setText(price);
        }else{
            setContentView(R.layout.appointment_payment_paid);
        }
    }
}