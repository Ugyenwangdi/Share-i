package com.example.android.share_i;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateUser extends AppCompatActivity
{

   ImageView profileView, aV1, aV2, aV3, loginProfile;
    EditText username, pass;
    TextView regLogin;
    Button signup, update, delete, view;
    private DbHelper myDB;

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        setTitle("Create Profile");
        username = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        profileView = findViewById(R.id.profileimg);
        aV1 = findViewById(R.id.avatar1);
        aV2 = findViewById(R.id.avatar2);
        aV3 = findViewById(R.id.avatar3);
        loginProfile = findViewById(R.id.loginProfile);

        signup = findViewById(R.id.Enterbtn);
        regLogin = findViewById(R.id.createLogin);

        //update = findViewById(R.id.Updatebtn);
        //delete = findViewById(R.id.Deletebtn);
        //view = findViewById(R.id.Viewbtn);

        myDB = new DbHelper(this);
        createUser();

        regLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateUser.this, Login.class);
                startActivity(intent);
            }
        });

    }


    public void createUser()
    {

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = username.getText().toString();
                String password = pass.getText().toString();

                if (name.equals("")||password.equals(""))
                    Toast.makeText(CreateUser.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                else {
                    boolean var = myDB.checkusernamepassword(name);
                    if (var==false) {
                        Boolean insert = myDB.registerUser(name, password, id);
                        if (insert == true) {
                            Toast.makeText(CreateUser.this, "Registered user successfully !", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Login.class);
                            intent.putExtra("ID", id);
                            startActivity(intent);
                        } else {
                            Toast.makeText(CreateUser.this, "Registration failed!", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else {
                        Toast.makeText(CreateUser.this, "User already exists !", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    public void profileImgUpdate(View view)
    {
        switch (view.getId()) {
            case R.id.avatar1:
                id = R.drawable.avatar4;
                profileView.setImageResource(R.drawable.avatar4);
                //loginProfile.setImageResource(R.drawable.avatar1);
                break;
            case R.id.avatar2:
                id = R.drawable.avatar2;
                profileView.setImageResource(R.drawable.avatar2);
                break;
            case R.id.avatar3:
                id = R.drawable.avatar6;
                profileView.setImageResource(R.drawable.avatar6);
                break;
            default:
                break;
        }

    }

}