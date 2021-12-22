package com.example.android.androidcodelabs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final int TEXT_REQUEST = 1;

    String msg;
    EditText editText;
    TextView msgHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.message);
        msgHeader = (TextView) findViewById(R.id.replyTV);

        if (savedInstanceState != null) {

            Boolean visible = savedInstanceState.getBoolean("visible");

            if (visible) {
                String s = savedInstanceState.getString("reply");
                msgHeader.setText(s);
                msgHeader.setVisibility(View.VISIBLE);

            }

        }

    }

    public void sendMessage(View view) {

        msg = editText.getText().toString();

        Intent intent = new Intent(this, ReplyActivity.class);
        intent.putExtra("msg", msg);
        startActivityForResult(intent, TEXT_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TEXT_REQUEST) {

            if (resultCode == RESULT_OK) {

                String reply = data.getStringExtra("rply");
                msgHeader.setVisibility(View.VISIBLE);

                msgHeader.setText(reply);

            }

        }

    }


    // Save instance states
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if (msgHeader.getVisibility() == View.VISIBLE) {
            outState.putBoolean("visible", true);
            outState.putString("reply", msgHeader.getText().toString());
        }
    }

    // Shared Preferences


    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences s = getSharedPreferences("SharedPref", MODE_PRIVATE);

        Boolean visible = s.getBoolean("visible", false);

        if (visible) {
            String s1 = s.getString("reply", "");
            msgHeader.setText(s1);
            msgHeader.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        if (msgHeader.getVisibility() == View.VISIBLE) {
            SharedPreferences sh = getSharedPreferences("SharedPref", MODE_PRIVATE);
            SharedPreferences.Editor shEdit = sh.edit();
            shEdit.putBoolean("visible", true);
            shEdit.putString("reply", msgHeader.getText().toString());
            shEdit.commit();

        }


    }
}