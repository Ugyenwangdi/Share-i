package com.example.android.share_i;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class About extends AppCompatActivity {

    EditText name, feedback;
    Button btnfeedback;
    private DbHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        name = findViewById(R.id.user);
        feedback = findViewById(R.id.feedback);
        //imageView = findViewById(R.id.profileimg);

        btnfeedback = findViewById(R.id.feedbtn);
        //update = findViewById(R.id.Updatebtn);
        //delete = findViewById(R.id.Deletebtn);
        //view = findViewById(R.id.Viewbtn);

        myDB = new DbHelper(this);
        sendFeedback();
    }

    public void sendFeedback() {

        btnfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nme = name.getText().toString();
                String feed = feedback.getText().toString();

                if (nme.equals("") || feed.equals(""))
                    Toast.makeText(About.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                else {
                    boolean checkuser = myDB.checkusernamepassword(nme);
                    if (checkuser==true) {
                        Boolean insert = myDB.feedback(nme, feed);
                        if (insert == true) {
                            Toast.makeText(About.this, "Feedback sent !", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(About.this, "Feedback not sent!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(About.this, "No such user!", Toast.LENGTH_SHORT).show();
                    }




                }

            }
        });
    }
}