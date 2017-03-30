package com.example.administrator.trucklog;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.administrator.trucklog.Constants.JSON;

public class Location_show extends AppCompatActivity {
    GPSTracker gps;
    double latitude;
    double longitude;
    String token;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_show);
        token=TokenManage.getToken(getBaseContext());
                gps = new GPSTracker(Location_show.this);
                if(gps.canGetLocation()){


                    latitude = gps.getLatitude();
                   longitude = gps.getLongitude();
                           Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                }else{

                    gps.showSettingsAlert();
                }


    try {
      //  ShowProgressDialog.showProgressDialog(Location_show.this, "Location Send ");
        Location_show.LoginService service = new Location_show.LoginService(createJsonObject(latitude,longitude), new AsyncTaskResponse() {
            @Override
            public void response(Object o) {

                ShowProgressDialog.hideProgressDialog();
                try {
                    JSONObject result_object = new JSONObject((String) o);
                    if(result_object.getBoolean("success")){
                        Toast.makeText(getApplicationContext(),"location send",Toast.LENGTH_LONG).show();
                    }
                    else{
                        new AlertDialog.Builder(Location_show.this).setMessage(result_object.getString("message")).setTitle("Error")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
        service.execute();
    }catch (JSONException e) {
        e.printStackTrace();
    }}
private JSONObject createJsonObject(double longitude, double latitude) throws JSONException{
        JSONObject request_content = new JSONObject();
        JSONObject request_data = new JSONObject();
        request_content.put("longitude", longitude);
        request_content.put("latitude", latitude);
        request_data.put("pitch", "updateLocation");
        request_data.put("data", request_content);

        return request_data;
        }

public class LoginService extends AsyncTask {
    AsyncTaskResponse response;
    JSONObject request_object;
    OkHttpClient okhttp;

    public LoginService(JSONObject request_object, AsyncTaskResponse response) {
        this.response = response;
        this.request_object = request_object;
        okhttp = new OkHttpClient.Builder().readTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS).connectTimeout(10, TimeUnit.SECONDS).build();
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        RequestBody body = RequestBody.create(JSON, request_object.toString());
        Request request = new Request.Builder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
               .addHeader("Authorization",token)
                .url(MainActivity.SERVER_URL)
                .post(body)
                .build();
        try {
            Response response = okhttp.newCall(request).execute();
            return
                    response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        response.response(o);
    }

}}