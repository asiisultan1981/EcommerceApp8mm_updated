package com.example.a8mm.EmailLoginRegister;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.a8mm.MainActivity;
import com.example.a8mm.Models.User;
import com.example.a8mm.R;
import com.example.a8mm.Retrofit.ApiClient;
import com.example.a8mm.Retrofit.ApiInterface;
import com.google.gson.Gson;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailRegisterActivity extends AppCompatActivity {
    private static final String TAG = "emailRegister";
    private EditText name, email, password;
    private Button btn_signUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_register);


        ///////////////// No Status bar start ///////////////
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        ///////////////// No Status bar end ///////////////
        
        init();
    }

    private void init() {
        name = (EditText)findViewById(R.id.name);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        btn_signUp = (Button)findViewById(R.id.btn_signUp);

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               registration();


            }
        });
    }

    private void registration() {
        String user_name = name.getText().toString();
        String user_email = email.getText().toString();
        String user_password = password.getText().toString();

        if (TextUtils.isEmpty(user_name))
        {
            name.setError("Name is required!");
        }else if(TextUtils.isEmpty(user_email))
        {
            email.setError("Email is required!");
        }else if (TextUtils.isEmpty(user_password))
        {
            password.setError("Password is required!");
        }else{
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Registering...");
            progressDialog.setMessage("Please wait while we are adding your credentials");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);
            ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call call = apiInterface.performEmailRegistration(user_name, user_email, user_password);
            Log.d(TAG, "registration: ");
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {

                    try{
                    JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));

                    if (!jsonObject.getBoolean("error")){
                        Toast.makeText(EmailRegisterActivity.this,"Account Created Successfully",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                    else {
                        Toast.makeText(EmailRegisterActivity.this,jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }



                    }
                    catch (Exception e){
                        Toast.makeText(EmailRegisterActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    Log.d(TAG, "onFailure: "+t);
                }
            });
        }

    }


    public void GoToHome(View view) {
        Intent intent = new Intent(EmailRegisterActivity.this, MainActivity.class);
        startActivity(intent);
        Animatoo.animateSlideDown(EmailRegisterActivity.this);
    }


    public void GoToLogin(View view) {
       startActivity(new Intent(EmailRegisterActivity.this, EmailLoginActivity.class));
        Animatoo.animateSwipeRight(EmailRegisterActivity.this);
    }
}