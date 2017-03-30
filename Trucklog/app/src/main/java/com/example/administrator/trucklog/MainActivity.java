package com.example.administrator.trucklog;

        import android.content.DialogInterface;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import org.json.JSONException;
        import org.json.JSONObject;
        import java.io.IOException;
        import java.util.concurrent.TimeUnit;
        import okhttp3.OkHttpClient;
        import okhttp3.Request;
        import okhttp3.RequestBody;
        import okhttp3.Response;
        import static com.example.administrator.trucklog.Constants.JSON;



        public class MainActivity extends AppCompatActivity {

            EditText txt_email, txt_password;
            Button bt_login;
            public static final String SERVER_URL = "https://tesla.urbantrucking.com/pitch";
            UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_email = (EditText)findViewById(R.id.edit_text_username);
        txt_password = (EditText)findViewById(R.id.edit_text_password);
        bt_login = (Button)findViewById(R.id.login);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendLoginRequest();
            }
        });
    }
    public void sendLoginRequest(){
        if(VerificationUtils.isEmptyText(txt_email) || VerificationUtils.isEmptyText(txt_password))
            return;
        if(VerificationUtils.isValidEmail(txt_email.getText().toString()) == false){
            txt_email.setError("invalid email address");
            return;
        }
        try {
            ShowProgressDialog.showProgressDialog(MainActivity.this, "Log in");
            LoginService service = new LoginService(createJsonObject(txt_email.getText().toString(), txt_password.getText().toString()), new AsyncTaskResponse() {
                @Override
                public void response(Object o) {

                    ShowProgressDialog.hideProgressDialog();
                    try {
                        JSONObject result_object = new JSONObject((String) o);
                        if(result_object.getBoolean("success")){
                            String data = result_object.getString("data");
                           System.out.println("token"+data);
                            JSONObject result_object1 = new JSONObject(data);
                            String token = result_object1.getString("token");
                            System.out.println("token"+token);
                           TokenManage.setToken(MainActivity.this.getApplicationContext(), token);
                         userModel = new UserModel(result_object1.getJSONObject("user"));
                            gotoNextScreen();
                        }
                        else{
                            new AlertDialog.Builder(MainActivity.this).setMessage(result_object.getString("message")).setTitle("Error")
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
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void gotoNextScreen(){
        Intent i  = new Intent(MainActivity.this, Location_show.class);
        i.putExtra("user",userModel);
        startActivity(i);
        finish();
    }

    private JSONObject createJsonObject(String email, String password) throws JSONException{
        JSONObject request_content = new JSONObject();
        JSONObject request_data = new JSONObject();
        request_content.put("email", email);
        request_content.put("password", password);
        request_data.put("pitch", "login");
        request_data.put("data", request_content);
        return request_data;
    }

    public class LoginService extends AsyncTask{
        AsyncTaskResponse response;
        JSONObject request_object;
        OkHttpClient okhttp;
        public LoginService(JSONObject request_object, AsyncTaskResponse response){
            this.response = response;
            this.request_object = request_object;
            okhttp = new OkHttpClient.Builder().readTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS).connectTimeout(10, TimeUnit.SECONDS).build();
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            RequestBody body = RequestBody.create(JSON, request_object.toString());
            Request request = new Request.Builder()
                    .addHeader("Content-Type","application/json")
                    .addHeader("Accept","application/json")
                    .url(SERVER_URL)
                    .post(body)
                    .build();
            try {
                Response response = okhttp.newCall(request).execute();
                return
                        response.body().string();
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            response.response(o);
        }
    }
}

