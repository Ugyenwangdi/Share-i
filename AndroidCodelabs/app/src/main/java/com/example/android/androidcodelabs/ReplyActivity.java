package com.example.android.androidcodelabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ReplyActivity extends AppCompatActivity {

    TextView textView;
    EditText replyEditTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        Intent intent = getIntent();
        String message = intent.getStringExtra("msg");

        textView = (TextView) findViewById(R.id.receivedTV);
        replyEditTxt = (EditText) findViewById(R.id.reply);

        textView.setText(message);

    }

    public void sendReply(View view) {

        String replyMsg = replyEditTxt.getText().toString();
        Intent i = new Intent();
        i.putExtra("rply", replyMsg);
        setResult(RESULT_OK, i);
        finish();


    }
}