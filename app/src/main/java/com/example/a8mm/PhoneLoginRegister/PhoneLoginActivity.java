package com.example.a8mm.PhoneLoginRegister;

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
import com.example.a8mm.EmailLoginRegister.EmailLoginActivity;
import com.example.a8mm.MainActivity;
import com.example.a8mm.R;
import com.example.a8mm.Retrofit.ApiClient;
import com.example.a8mm.Retrofit.ApiInterface;
import com.google.gson.Gson;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneLoginActivity extends AppCompatActivity {
    private EditText phone;
    private Button btn_signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);

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
        phone = (EditText)findViewById(R.id.phone);
        btn_signIn = (Button)findViewById(R.id.btn_signIn);
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneLogin();
            }
        });

    }

    private void PhoneLogin() {
        String phoneNumber = phone.getText().toString();



        if (TextUtils.isEmpty(phoneNumber))
        {
            phone.setError("phoneNumber is required!");
        }else
        {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Signing in...");
            progressDialog.setMessage("Please wait while we are checking your credentials");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);

            ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call call = apiInterface.performPhoneLogin(phoneNumber);
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {

                    try{
                        JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));

                        if (!jsonObject.getBoolean("error")){
                            Toast.makeText(PhoneLoginActivity.this,"Login In Successfully",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                        else {
                            Toast.makeText(PhoneLoginActivity.this,jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }



                    }
                    catch (Exception e){
                        Toast.makeText(PhoneLoginActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                }

                @Override
                public void onFailure(Call call, Throwable t) {
                }
            });
        }
    }

    public void GoToRegister(View view) {
        Intent intent = new Intent(PhoneLoginActivity.this, PhoneRegisterActivity.class);
        startActivity(intent);
        Animatoo.animateSwipeRight(PhoneLoginActivity.this);
    }

    public void GoToHome(View view) {
        Intent intent = new Intent(PhoneLoginActivity.this, MainActivity.class);
        startActivity(intent);
        Animatoo.animateSlideDown(PhoneLoginActivity.this);
    }
}
