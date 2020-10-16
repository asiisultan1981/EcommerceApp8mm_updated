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
import com.example.a8mm.EmailLoginRegister.EmailRegisterActivity;
import com.example.a8mm.MainActivity;
import com.example.a8mm.R;
import com.example.a8mm.Retrofit.ApiClient;
import com.example.a8mm.Retrofit.ApiInterface;
import com.google.gson.Gson;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneRegisterActivity extends AppCompatActivity {
    private EditText phone;
    private Button btn_signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_register);

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
        btn_signUp = (Button)findViewById(R.id.btn_signUp);
        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneRegistration();
            }
        });

    }

    private void PhoneRegistration() {
        String phoneNumber = phone.getText().toString();



        if (TextUtils.isEmpty(phoneNumber))
        {
            phone.setError("phoneNumber is required!");
        }else
        {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Registering...");
            progressDialog.setMessage("Please wait while we are adding your credentials");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);

            ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call call = apiInterface.performPhoneRegistration(phoneNumber);
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {

                    try{
                        JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));

                        if (!jsonObject.getBoolean("error")){
                            Toast.makeText(PhoneRegisterActivity.this,"Account Created Successfully",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                        else {
                            Toast.makeText(PhoneRegisterActivity.this,jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }



                    }
                    catch (Exception e){
                        Toast.makeText(PhoneRegisterActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                }

                @Override
                public void onFailure(Call call, Throwable t) {
                }
            });
        }


    }





    public void GoToLogin(View view) {
        Intent intent = new Intent(PhoneRegisterActivity.this, PhoneLoginActivity.class);
        startActivity(intent);
        Animatoo.animateSwipeRight(PhoneRegisterActivity.this);
    }

    public void GoToHome(View view) {
        Intent intent = new Intent(PhoneRegisterActivity.this, MainActivity.class);
        startActivity(intent);
        Animatoo.animateSlideDown(PhoneRegisterActivity.this);
    }
}