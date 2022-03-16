package ca.mcgill.ecse321.rsms_android;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class EditInfoActivity extends AppCompatActivity {

    private Button updButton, backButton;
    private String newPassword, newName, newPhone, newAddress, newEmail, currUsername;

    /**
     * This method does some preparation for edit info page.
     *
     * @param savedInstanceState Some information coming from user's previous actions.
     */
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Get some basic information including current username.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_info);
        Intent intent = getIntent();
        currUsername = intent.getStringExtra("ca.mcgill.ecse321.rsms.android.NOWUNAME");
        TextView nowU = (TextView) findViewById(R.id.nowUName);
        nowU.setText(currUsername);
        updButton = (Button) findViewById(R.id.updateChangeButton);
        backButton = (Button) findViewById(R.id.goBackButton);
        //Actions for update info button.
        updButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get user entered information.
                final TextView passwordText = (TextView) findViewById(R.id.editPassword);
                newPassword = passwordText.getText().toString();
                final TextView nameText = (TextView) findViewById(R.id.editPersonName);
                newName = nameText.getText().toString();
                final TextView phoneText = (TextView) findViewById(R.id.editPhone);
                newPhone = phoneText.getText().toString();
                final TextView addressText = (TextView) findViewById(R.id.editAddress);
                newAddress = addressText.getText().toString();
                final TextView emailText = (TextView) findViewById(R.id.editEmail);
                newEmail = emailText.getText().toString();
                TextView tv = (TextView) findViewById(R.id.errors);
                RequestParams params = new RequestParams();
                params.add("newUsername", currUsername);
                params.add("newPassword", newPassword);
                params.add("newName", newName);
                params.add("newPhoneNo", newPhone);
                params.add("newAddress", newAddress);
                params.add("newEmail", newEmail);
                params.add("username", currUsername);
                //Send information to backend.
                HttpUtils.put("/users/customers/update_info_android", params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        String error = "";
                        try {
                            error = response.toString();
                            if (error.contains("true")) {
                                String[] errors = error.split(":");
                                error = errors[2];
                            } else error = "Successful";
                            tv.setText(error);
                        } catch (Exception e) {
                            error = e.getMessage();
                            tv.setText(error);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        String error = "An error occurs:";
                        try {
                            error += errorResponse.get("message").toString();
                        } catch (Exception e) {
                            error += e.getMessage();
                            tv.setText(error);
                        }
                    }
                });
            }
        });

        //Actions for back button.
        backButton.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Go back to home page.
                Intent homePageActivity = new Intent(getApplicationContext(), HomePageActivity.class);
                startActivity(homePageActivity);
            }
        });
    }
}