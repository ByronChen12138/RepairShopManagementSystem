package ca.mcgill.ecse321.rsms_android;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import cz.msebera.android.httpclient.Header;

public class AppointmentViewAndPayActivity extends AppCompatActivity {

    private String name = "";
    private ListView myListView;
    private List<String> types = new ArrayList<String>();
    private List<String> plateNos = new ArrayList<String>();
    private List<String> times = new ArrayList<String>();
    private List<String> prices = new ArrayList<String>();
    private List<String> statuses = new ArrayList<String>();
    private String error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_view_and_pay);

        myListView = (ListView) findViewById(R.id.appointmentListView);

        RequestParams params = new RequestParams();
        Intent intent = getIntent();

        /*
         * In this page, we will find the appointment of current users and display in on the screen
         *
         */
        name = intent.getStringExtra("currentUsername");
        params.add("username", name);
        HttpUtils.post("/appointment/find_appointments_of_customer", params, new JsonHttpResponseHandler() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                try {
                    if (response != null) {
                        List<JSONObject> services = getValuesForGivenKeyJSON(response, "service");
                        for (int i = 0; i < services.size(); i++) {
                            types.add(services.get(i).optString("serviceType"));
                        }
                        plateNos.addAll(getValuesForGivenKey(response, "plateNo"));

                        List<JSONObject> shifts = getValuesForGivenKeyJSON(response, "shift");
                        String date = "";
                        String startTime = "";
                        String endTime = "";

                        for (int i = 0; i < services.size(); i++) {
                            date = shifts.get(i).optString("date");
                            startTime = shifts.get(i).optString("startTime");
                            endTime = shifts.get(i).optString("endTime");
                            times.add(date + "/" + startTime + "--" + endTime);
                        }

                        prices.addAll(getValuesForGivenKey(response, "price"));
                        statuses.addAll(getValuesForGivenKey(response, "status"));

                        AppointmentAdapter appointmentAdapter = new AppointmentAdapter(getBaseContext(), types,
                                plateNos, times, prices, statuses);
                        myListView.setAdapter(appointmentAdapter);

                        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent showPaymentActivity = new Intent(getApplicationContext(), AppointmentPaymentActivity.class);
                                showPaymentActivity.putExtra("ca.mcgill.ecse321.rsms_android.TYPE", types.get(position));
                                showPaymentActivity.putExtra("ca.mcgill.ecse321.rsms_android.STATUS", statuses.get(position));
                                startActivity(showPaymentActivity);
                            }
                        });
                    } else {
                        error = "No appointment!";
                    }

                } catch (Exception e) {
                    error = e.getMessage();
                    System.out.println(error);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error = errorResponse.get("message").toString();
                } catch (Exception e) {
                    error = e.getMessage();
                }
                Intent pageBack = new Intent(getApplicationContext(), MakeAppointmentSelectDate.class);
                startActivity(pageBack);
            }
        });
    }

    /**
     * return the string by the provided key
     *
     * @param jsonArray
     * @param key
     * @return
     * @throws JSONException
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<String> getValuesForGivenKey(JSONArray jsonArray, String key) throws JSONException {
        return IntStream.range(0, jsonArray.length())
                .mapToObj(index -> {
                    try {
                        return ((JSONObject) jsonArray.get(index)).optString(key);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }

    /**
     * return a list of JSONObject by the given key
     *
     * @param jsonArray
     * @param key
     * @return
     * @throws JSONException
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<JSONObject> getValuesForGivenKeyJSON(JSONArray jsonArray, String key) throws JSONException {
        return IntStream.range(0, jsonArray.length())
                .mapToObj(index -> {
                    try {
                        return ((JSONObject) jsonArray.get(index)).optJSONObject(key);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }

    /**
     * return a list of JSONArray by the given key
     *
     * @param jsonArray
     * @param key
     * @return
     * @throws JSONException
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<JSONArray> getValuesForGivenKeyJSONArray(JSONArray jsonArray, String key) throws JSONException {
        return IntStream.range(0, jsonArray.length())
                .mapToObj(index -> {
                    try {
                        return ((JSONObject) jsonArray.get(index)).optJSONArray(key);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }

}