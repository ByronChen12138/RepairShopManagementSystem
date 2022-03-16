package ca.mcgill.ecse321.rsms_android;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

public class HomePageActivity extends AppCompatActivity {

    private Button viewCarButton;
    private Button changeInfoButton;
    private Button viewAppointmentButton;
    private Button makeAppointmentButton;
    private Button logOutButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home_page);

        Intent ingressMessage = getIntent();

        String username = ingressMessage.getStringExtra("currentName");
        String currentPassword = ingressMessage.getStringExtra("ca.mcgill.ecse321.rsms_android.CURRPASSWORD");
        viewCarButton = (Button) findViewById(R.id.viewCars);
        viewCarButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent carMA = new Intent(getApplicationContext(), CarMActivity.class);

                carMA.putExtra("currentUsername", username);
                startActivity(carMA);
            }
        });


        changeInfoButton = (Button) findViewById(R.id.changeInfoButton);
        changeInfoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent InfoManagement = new Intent(getApplicationContext(), EditInfoActivity.class);
                InfoManagement.putExtra("ca.mcgill.ecse321.rsms.android.NOWUNAME", username);
                InfoManagement.putExtra("ca.mcgill.ecse321.rsms_android.NOWPASSWORD", currentPassword);
                startActivity(InfoManagement);
            }
        });

        viewAppointmentButton = (Button) findViewById(R.id.viewAppointment);
        viewAppointmentButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent viewAppointment = new Intent(getApplicationContext(), AppointmentViewAndPayActivity.class);
                viewAppointment.putExtra("currentUsername", username);
                startActivity(viewAppointment);
            }
        });

        makeAppointmentButton = (Button) findViewById(R.id.makeAppointment);
        makeAppointmentButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent makeAppointment = new Intent(getApplicationContext(), MakeAppointmentSelectDate.class);
                makeAppointment.putExtra("username", username);
                startActivity(makeAppointment);
            }
        });

        logOutButton = (Button) findViewById(R.id.logOutButton);
        logOutButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent logout = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(logout);
            }
        });
    }
}