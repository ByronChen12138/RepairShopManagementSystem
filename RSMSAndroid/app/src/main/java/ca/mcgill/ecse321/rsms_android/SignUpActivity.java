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

public class SignUpActivity extends AppCompatActivity {
    /**
     * this page is used for customer to sign up in the system,
     * They need to provide necessary information such as username, email, addreess, phoneNo and password in required form.
     */
    private String username;
    private String password;
    private String name;
    private String email;
    private String homeAddress;
    private String phoneNo;
    private String error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sign_up);

        Button loginButton = (Button) findViewById(R.id.buttonLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent loginPage = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(loginPage);
            }
        });

        /**
         * Click listener for sign up button, it will read the information customers fill in the blank and call the backend to add a customer to the system
         * if the action is not successed, reason will be shown on the top of the page
         */
        Button signUpButton = (Button) findViewById(R.id.buttonSignUp);
        signUpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final TextView username = (TextView) findViewById(R.id.createTextAddress2);
                SignUpActivity.this.username = username.getText().toString();
                final TextView accountPassword = (TextView) findViewById(R.id.createTextPassword2);
                password = accountPassword.getText().toString();
                final TextView accountName = (TextView) findViewById(R.id.editTextTextFirstName);

                final TextView accountPLastName = (TextView) findViewById(R.id.editTextTextLastName);
                name = accountName.getText().toString() + " " + accountPLastName.getText().toString();
                final TextView accountphoneNo = (TextView) findViewById(R.id.editTextPhone);
                phoneNo = accountphoneNo.getText().toString();
                final TextView accountEmail = (TextView) findViewById(R.id.editTextTextEmailAddress);
                email = accountEmail.getText().toString();
                final TextView accountAddress = (TextView) findViewById(R.id.editTextTextAddress);
                homeAddress = accountAddress.getText().toString();
                RequestParams params = new RequestParams();
                params.add("username", SignUpActivity.this.username);
                params.add("password", password);
                params.add("name", name);
                params.add("phoneNo", phoneNo);
                params.add("homeAddress", homeAddress);
                params.add("email", email);
                TextView notification = (TextView) findViewById(R.id.pleaseSignUp);

                HttpUtils.post("/users/customers/create_to_most_recent_system_android", params, new JsonHttpResponseHandler() {

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

