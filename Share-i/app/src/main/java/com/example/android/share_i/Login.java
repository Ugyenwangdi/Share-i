package com.example.android.share_i;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private EditText loginUsername, loginPass;
    private Button loginBtn;
    private TextView loginRegBtn;
    private DbHelper myDB;
    ImageView loginProfile;

    int profI;
    String nm;

    private String sharedPrefFile =
            "com.example.android.share_i";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginUsername = findViewById(R.id.loginname);
        loginPass = findViewById(R.id.loginpass);
        loginBtn = findViewById(R.id.loginbtn);
        loginRegBtn = findViewById(R.id.loginReg);
        loginProfile = findViewById(R.id.loginProfile);

        myDB = new DbHelper(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            //profI = bundle.getInt("ID");
            nm = bundle.getString("name");
            loginUsername.setText(nm);
        }

        if (nm != null){
            profI = myDB.getImgID(nm);
        } else {
            profI = R.drawable.profile;
        }
        loginProfile.setImageResource(profI);

//        SharedPreferences prefs = getSharedPreferences("name", MODE_PRIVATE);
//        boolean isLoggedIn= prefs.getBoolean("isLoggedIn", false);
//
//        if(isLoggedIn){
//            startActivity(new Intent(getApplicationContext(),WiFiDirectActivity.class));
//            finish();
//            return;
//        }
//        setContentView(R.layout.main);

        loginUser();

        loginRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, CreateUser.class);
                startActivity(intent);
            }
        });

    }

    private void loginUser() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = loginUsername.getText().toString();
                String password = loginPass.getText().toString();

                if (name.equals("")||password.equals(""))
                    Toast.makeText(Login.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                else {
                    boolean checkuserpass = myDB.checkusernamepassword(name, password);
                    if (checkuserpass==true) {
                        SharedPreferences.Editor editor = getSharedPreferences(sharedPrefFile, MODE_PRIVATE).edit();
                        editor.putString("currentusername", name);
                        editor.putBoolean("isLoggedIn", true);
                        editor.apply();
                        Toast.makeText(Login.this, "SignIn successful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), WiFiDirectActivity.class);
                        intent.putExtra("NAME", name);
                        intent.putExtra("ID", profI);
                        Log.d("signin", "signedin");
                        startActivity(intent);
                    } else {
                            Toast.makeText(Login.this, "Invalid credentials!", Toast.LENGTH_SHORT).show();
                    }

                }

            }

//                if (var) {
//                    Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(Login.this, WiFiDirectActivity.class));
//                    finish();
//                } else {
//                    Toast.makeText(Login.this, "Login failed", Toast.LENGTH_SHORT).show();
//                }
        });

    }
}