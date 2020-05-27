package com.example.ds_p3;

import androidx.appcompat.app.AppCompatActivity;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void addOne(View view) {

        Intent i = new Intent(this,RedesActivity.class);


        EditText twitterUser = (EditText) findViewById(R.id.twitterUsername);
        String message1 = twitterUser.getText().toString();
        i.putExtra("twitterUser", message1);

        EditText instagramUser = (EditText) findViewById(R.id.instagramUsername);
        String message2 = instagramUser.getText().toString();
        i.putExtra("instagramUser", message2);

        startActivity(i);

    }
}
