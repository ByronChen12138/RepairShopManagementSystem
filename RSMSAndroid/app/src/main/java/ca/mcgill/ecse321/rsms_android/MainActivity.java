package ca.mcgill.ecse321.rsms_android;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.content.res.Resources;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    private String username;
    private String password;
    private String error = "";

    @SuppressLint("ResourceType")
    @Override

    /**
     * This page is used for login in the system, or you can register an account.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginButton = (Button) findViewById(R.id.loginButton);
        Resources res = getResources();
        username = res.getString(R.id.accountUName);
        password = res.getString(R.id.accountPassword);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent homepage = new Intent(getApplicationContext(), HomePageActivity.class);
                final TextView accountName = (TextView) findViewById(R.id.accountUName);
                final TextView accountPassword = (TextView) findViewById(R.id.accountPassword);
                final TextView errorMe = (TextView) findViewById(R.id.errorMessage);
                String username = accountName.getText().toString();
                String accPassword = accountPassword.getText().toString();
                RequestParams params = new RequestParams();
                params.add("username", username);


                /*
                 * This method call the getcustomer method in the backend by the provided username
                 * after that, we will compare the password provided and the actual password,
                 * if it does not match, it will display incorrect password on the bottom of the page
                 */
                HttpUtils.get("/users/customers/get_by_username", params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            String password = response.getString("password");
                            if (accPassword.equals(password)) {
                                homepage.putExtra("currentName", username);
                                startActivity(homepage);
                                homepage.putExtra("ca.mcgill.ecse321.rsms.android.CURRUNAME", MainActivity.this.username);
                                homepage.putExtra("ca.mcgill.ecse321.rsms_android.CURRPASSWORD", MainActivity.this.password);
                            } else {
                                error = "incorrect password";
                                errorMe.setText(error);
                            }
                        } catch (Exception e) {
                            error = e.getMessage();
                            errorMe.setText(error);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        try {
                            error = errorResponse.get("message").toString();
                        } catch (Exception e) {
                            error = e.getMessage();
                            errorMe.setText(error);
                        }

                    }
                });
            }

        });
        /*
         * if user clicked login, it will jump in to the sign up page
         */
        Button signUpButton = (Button) findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent signUpPage = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(signUpPage);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}