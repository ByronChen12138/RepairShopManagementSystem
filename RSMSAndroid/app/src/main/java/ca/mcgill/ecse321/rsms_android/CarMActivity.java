package ca.mcgill.ecse321.rsms_android;

import android.content.Intent;
import android.os.Bundle;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class CarMActivity extends AppCompatActivity {
    /**
     * this page is used for customer to add or update a car in the system,
     * Customer can only access this page after that have logged in.
     * They need to provide necessary information such as plate number,
     * model and year in required form.
     */
    private String plateNo;
    private String model;
    private String year;
    private String manufacturer;
    private String error;
    private Button addCarButton;
    private Button updateCarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_management);
        Intent carPage = getIntent();
        String name = carPage.getStringExtra("currentUsername");
        final TextView CarPlateNo = (TextView) findViewById(R.id.CarPlateNo);
        final TextView CarYear = (TextView) findViewById(R.id.CarYear);
        final TextView CarModel = (TextView) findViewById(R.id.CarModel);
        final TextView CarManufacturer = (TextView) findViewById(R.id.CarManufacturer);
        final TextView notification = (TextView) findViewById(R.id.Title2);
        addCarButton = (Button) findViewById(R.id.AddCarButton);
        /*
         * Click listener for add button, it will read the information customers fill in the blank and call the backend to add a car to the system
         * if the action is not successful, reason will be shown on the top of the page
         */
        addCarButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                plateNo = CarPlateNo.getText().toString();
                model = CarModel.getText().toString();
                year = CarYear.getText().toString();
                manufacturer = CarManufacturer.getText().toString();
                RequestParams params = new RequestParams();
                params.add("username", name);
                params.add("plateNo", plateNo);
                params.add("model", model);
                params.add("year", year);
                params.add("manufacturer", manufacturer);
                HttpUtils.post("/users/cars/create_android", params, new JsonHttpResponseHandler() {

                    /**
                     *
                     * @param statusCode
                     * @param headers
                     * @param response
                     * this method call the backend addCar with the given parameter, if the call is successful, it will check if the response contains the error message that we
                     * defined in backend, if that is the case, it will display the error(normally it is form problem)
                     */
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            String errorResponseText = response.toString();
                            if (errorResponseText.contains("true")) {
                                String[] errorMessage = errorResponseText.split(":");
                                error = errorMessage[2];
                            } else {
                                error = "Successful";
                            }
                            notification.setText(error);
                        } catch (Exception e) {
                            error = e.getMessage();
                            notification.setText(error);
                        }
                    }


                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        try {
                            error = errorResponse.get("message").toString();
                        } catch (Exception e) {
                            error = e.getMessage();
                            notification.setText(error);
                        }
                    }
                });
            }
        });


        updateCarButton = (Button) findViewById(R.id.UpdateCarButton);
        /*
         * Click listener for update button, it will read the information customers fill in the blank and call the backend to add a car to the system
         * if the action is not successful, reason will be shown on the top of the page
         */
        updateCarButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                plateNo = CarPlateNo.getText().toString();
                model = CarModel.getText().toString();
                year = CarYear.getText().toString();
                manufacturer = CarManufacturer.getText().toString();
                RequestParams params = new RequestParams();
                params.add("newUsername", name);
                params.add("plateNo", plateNo);
                params.add("newModel", model);
                params.add("newYear", year);
                params.add("newManufacturer", manufacturer);
                HttpUtils.put("/users/cars/update_android", params, new JsonHttpResponseHandler() {

                    /**
                     *
                     * @param statusCode
                     * @param headers
                     * @param response
                     * this method call the backend addCar with the given parameter, if the call is successed, it will check if the reponse contains the error message that we
                     * defined in backend, if that is the case, it will display the error(normally it is form problem)
                     */
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            String err = response.toString();
                            if (err.contains("true")) {
                                String[] errorMessage = err.split(":");
                                error = errorMessage[2];
                            } else {
                                error = "Successful";
                            }
                            notification.setText(error);
                        } catch (Exception e) {
                            error = e.getMessage();
                            notification.setText(error);
                        }
                    }


                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        try {
                            error = errorResponse.get("message").toString();
                        } catch (Exception e) {
                            error = e.getMessage();
                            notification.setText(error);
                            notification.setText(error);
                        }
                    }
                });
            }
        });
    }
}
