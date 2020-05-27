package com.example.ds_p3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class RedesActivity extends AppCompatActivity {

    RequestQueue queue;
    String numSeguidoresT="";
    String numSeguidoresI="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redes);

        queue = Volley.newRequestQueue(this);

        Intent i = getIntent();
        String usernameT = i.getStringExtra("twitterUser");
        String usernameI = i.getStringExtra("instagramUser");

        TextView usernameTwitter = (TextView) findViewById(R.id.usernameTwitter);
        usernameTwitter.setText("@" +usernameT);

        TextView usernameInstagram = (TextView) findViewById(R.id.usernameInstagram);
        usernameInstagram.setText("@" +usernameI);

        askForServer("instagramBiography", usernameI, R.id.descripcionInstagram);

        askForServer("twitterFollowers", usernameT, R.id.seguidoresTwitter);
        askForServer("twitterFollowed", usernameT, R.id.seguidosTwitter);
        askForServer("twitterPrivacy", usernameT, R.id.privacidadTwitter);
        askForServer("twitterVerified", usernameT, R.id.verificadoTwitter);
        askForServer("twitterBiography", usernameT, R.id.descripcionTwitter);

        askForServer("instagramFollowers", usernameI, R.id.seguidoresInstagram);
        askForServer("instagramFollowed", usernameI, R.id.seguidosInstagram);
        askForServer("instagramPrivacy", usernameI, R.id.privacidadInstagram);
        askForServer("instagramVerified", usernameI, R.id.verificadoInstagram);


        TextView descripcionTwitter = (TextView) findViewById(R.id.descripcionTwitter);
        descripcionTwitter.setMovementMethod(new ScrollingMovementMethod());
        TextView descripcionInstagram = (TextView) findViewById(R.id.descripcionInstagram);
        descripcionInstagram.setMovementMethod(new ScrollingMovementMethod());
    }

    public String askForServer(String path, final String username, final int id){
        String aDevolver = "";
        //contador++;

        //RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.1.137:8080/TomCat_DS/demo/hello/" + path;

        System.out.println(url);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        TextView tView = (TextView) findViewById(id);
                        String aInsertar = response.toString();
                        if (aInsertar.equals("Verificado")){
                            aInsertar = "Si";
                        } else if (aInsertar.equals("No verificado")){
                            aInsertar = "No";
                        }
                        tView.setText(aInsertar);
                        if (id == R.id.seguidoresTwitter){
                            numSeguidoresT = response.toString();
                        } else if (id == R.id.seguidoresInstagram){
                            numSeguidoresI = response.toString();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
                ) {

            @Override
            public String getBodyContentType() {
                return "text/plain; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return username.getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                    // can get more details such as response.headers
                }
                return super.parseNetworkResponse(response);
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(stringRequest);


        return aDevolver;
    }

    public void showStats(View view){
        TextView sTwitter = (TextView) findViewById(R.id.seguidoresTwitter);
        TextView sInstagram = (TextView) findViewById(R.id.seguidoresInstagram);

        int sTotales = Integer.parseInt(sTwitter.getText().toString()) + Integer.parseInt(sInstagram.getText().toString());
        double sMedia = (sTotales * 1.0) / 2.0;

        TextView seguidoresTotales = (TextView) findViewById(R.id.seguidoresTotales);
        seguidoresTotales.setText(Integer.toString(sTotales));

        TextView seguidoresMedia = (TextView) findViewById(R.id.seguidoresMedia);
        seguidoresMedia.setText(Double.toString(sMedia));
    }
}
