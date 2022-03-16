package ca.mcgill.ecse321.rsms_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SelectServiceAndCar extends AppCompatActivity {

    private Button button_wash;
    private Button button_maintenance;
    private Button button_road_assistance;
    private Button button_tire_change;
    private Button button_towing;
    private Button button_car_inspection;
    private Button button_confirm;

    private String date;
    private String UName;
    private String Password;
    private String serviceType = "";
    private String error;
    private String startTime;
    private String endTime;
    private String weight;
    private String plateNo;

    @Override
    /*
     * this page will ask the customer to pick a desired service and
     * enter the plate number of the target car and make an appointment,
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_service_and_car);

        button_confirm = findViewById(R.id.button);
        button_confirm.setOnClickListener(v -> {
            final TextView plateNoView = (TextView) findViewById(R.id.plateNo);
            final TextView weightView = (TextView) findViewById(R.id.weight);
            TextView errorView = (TextView) findViewById(R.id.error);
            plateNo = plateNoView.getText().toString();
            weight = weightView.getText().toString();
            UName = getIntent().getStringExtra("username");
            date = getIntent().getStringExtra("date");
            startTime = getIntent().getStringExtra("timeStart");
            endTime = getIntent().getStringExtra("timeEnd");
            RequestParams params = new RequestParams();
            params.add("serviceType", serviceType);
            params.add("username", UName);
            params.add("plateNo", plateNo);
            params.add("date", date);
            params.add("startTime", startTime.substring(0, 5));
            params.add("endTime", endTime.substring(0, 5));
            params.add("weight", weight);

            HttpUtils.post("/appointment/make_appointment", params, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    String errowr = response.toString();
                    if (errowr.contains("true")) {
                        String[] errorMessage = errowr.split(":");
                        error = errorMessage[2];
                        ((TextView) findViewById(R.id.error)).setText(error);

                    } else {
                        error = "Successful";
                        ((TextView) findViewById(R.id.error)).setText(response.toString());
                        back_to_homepage();
                    }
                }
            });
        });

        button_wash = findViewById(R.id.Button);
        button_wash.setOnClickListener(v -> wash());

        button_maintenance = findViewById(R.id.Button2);
        button_maintenance.setOnClickListener(v -> maintenance());

        button_road_assistance = findViewById(R.id.Button3);
        button_road_assistance.setOnClickListener(v -> road_assistance());

        button_tire_change = findViewById(R.id.Button4);
        button_tire_change.setOnClickListener(v -> tire_change());

        button_towing = findViewById(R.id.Button5);
        button_towing.setOnClickListener(v -> towing());

        button_car_inspection = findViewById(R.id.Button6);
        button_car_inspection.setOnClickListener(v -> car_inspection());

    }


    private void wash() {
        serviceType = "car wash";
    }

    private void maintenance() {
        serviceType = "maintenance";
    }

    private void road_assistance() {
        serviceType = "road assistance";
    }

    private void tire_change() {
        serviceType = "tire change";
    }

    private void towing() {
        serviceType = "towing";
    }

    private void car_inspection() {
        serviceType = "car inspection";
    }

    private void refreshErrorMessage() {
        // set the error message
        TextView tvError = (TextView) findViewById(R.id.error);
        tvError.setText(error);

        if (error == null || error.length() == 0) {
            tvError.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }
    }

    private void back_to_homepage() {
        Intent homepage = new Intent(getApplicationContext(), HomePageActivity.class);
        homepage.putExtra("ca.mcgill.ecse321.rsms.android.CURRUNAME", UName);
        homepage.putExtra("ca.mcgill.ecse321.rsms_android.CURRPASSWORD", Password);

        startActivity(homepage);
    }

}