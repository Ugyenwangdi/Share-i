package com.example.android.share_i;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Profile extends AppCompatActivity {

    ImageView profileI;
    EditText nameEdt;
    int profI;
    String myName;
    private DbHelper myDB;
    Button updateBtn;
    int avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        myDB = new DbHelper(this);

        profileI = findViewById(R.id.profile_img);
        nameEdt = findViewById(R.id.username);
        updateBtn = findViewById(R.id.updateBtn);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            myName = bundle.getString("name");
        }

        if (myName != null){
            avatar = myDB.getImgID(myName);
        } else {
            avatar = R.drawable.profile;
        }
        profileI.setImageResource(avatar);
        nameEdt.setText(myName);


        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newname = nameEdt.getText().toString();
                if (newname.equals(""))
                    Toast.makeText(Profile.this, "Please fill fields", Toast.LENGTH_SHORT).show();
                else {
                    boolean var = myDB.checkusernamepassword(newname);
                    if (var==true) {
                        Boolean update = myDB.updateuserdata(newname,  avatar);
                        if (update == true) {
                            Toast.makeText(Profile.this, "Updated successfully !", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Profile.this, "Update failed!", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else {
                        Toast.makeText(Profile.this, "User does not exists !", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void profileUpdate(View view)
    {
        switch (view.getId()) {
            case R.id.avatar5:
                avatar = R.drawable.avatar2;
                profileI.setImageResource(R.drawable.avatar2);
                //loginProfile.setImageResource(R.drawable.avatar1);
                break;
            case R.id.avatar4:
                avatar = R.drawable.avatar4;
                profileI.setImageResource(R.drawable.avatar4);
                break;
            case R.id.avatar6:
                avatar = R.drawable.avatar6;
                profileI.setImageResource(R.drawable.avatar6);
                break;
            default:
                break;
        }

    }
}