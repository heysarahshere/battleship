package com.scc.battleship;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class Attack extends BaseActivity {

  ImageView imageOffensiveGrid;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_attack);
  }

  public void onClickMyShips(View v ) {
    Intent intent = new Intent(Attack.this, Defend.class);
    startActivity(intent);

    initGamegrid();
    imageOffensiveGrid = findViewById(R.id.imageOffensiveGrid);

    imageOffensiveGrid.setOnTouchListener(new View.OnTouchListener(){

      @Override
      public boolean onTouch( View v, MotionEvent event ){

        if(event.getAction() == MotionEvent.ACTION_UP){
          String url = BATTLE_SERVER + "api/v1/game/"+ gameId +"/attack/" + event.getX() + "/" + event.getY() + "/" + ".json";
          Log.d("INTERENT", url);

          StringRequest request = new StringRequest(
              Request.Method.GET, url,
              // call backs
              new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                  // do something with returned data
                  Log.d("INTERENT", response);


                  // need to draw enemy board and cycle through cells???
                  imageOffensiveGrid.invalidate();

                  // if shipsMap has nothing left in it, start game or show button to start game
                  // start defense intent (def first?)
                }
              }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              // if error
              Log.d("INTERENT", error.toString());
              toastIt("Internet failure: " + error.toString());
            }
          }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
              Map<String, String> headers = new HashMap<String, String>();
              String credentials = username + ":" + password;
              Log.d("AUTH", "Login info: " + credentials);
              String auth = "Basic " + Base64.encodeToString( credentials.getBytes(), Base64.NO_WRAP);
              headers.put( "Authorization", auth );
              return headers;
            }
          };
          request.setRetryPolicy(new DefaultRetryPolicy(
              20000,
              DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
              DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
          ));
          requestQueue.add( request );

        }

        return true;
      }
    });
  }

  public void initGamegrid(){
    for(int y=0; y < 11; y++){
      for(int x=0; x < 11; x++){
        computerBoard[x][y] = new GameCell();
      }
    }
  }
}
