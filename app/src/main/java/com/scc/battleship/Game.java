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

public class Game extends BaseActivity {
  Spinner spinnerShips;
  Spinner spinnerDirections;
  Spinner spinnerRows;
  Spinner spinnerCols;
  String ship, direction, row, col, shipPegs;
  int shipPegsNum, dir, length, rowNum;

  static TreeMap<String, Integer> shipsMap = new TreeMap<String, Integer>();
  static TreeMap<String, Integer> directionsMap = new TreeMap<String, Integer>();

  String[] shipsArray;
  String[] directionsArray;

  ArrayAdapter shipSpinnerArrayAdapter;
  ArrayAdapter directionsSpinnerArrayAdapter;

  ImageView imageDefensiveGrid;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_game);

    spinnerShips = findViewById(R.id.spinnerShips);
    spinnerDirections = findViewById(R.id.spinnerDirections);
    spinnerRows = findViewById(R.id.spinnerRows);
    spinnerCols = findViewById(R.id.spinnerCols);
    imageDefensiveGrid = findViewById(R.id.imageDefensiveGrid);

    imageDefensiveGrid.setOnTouchListener(new View.OnTouchListener(){

      @Override
      public boolean onTouch( View v, MotionEvent event ){

        if(event.getAction() == MotionEvent.ACTION_UP){
          Log.i("SHIP", "onTouch: x(" + event.getX() + ") y(" + event.getY() + ")");
          Log.i("SHIP", "onTouch: cellx(" + (event.getX()/BoardView.cellWidth) + ") celly(" + (event.getY()/BoardView.cellWidth) + ")");
        }

        return true;
      }
    });


    getAvailableShips();
    getAvailableDirections();
    initGamegrid();

//    int shipPegs = shipsMap.get("carrier");
//    if(shipsMap.get("carrier")){
//      dir = "0";
//    }
//    if(direction.equals("east")){
//      dir = "2";
//    }
//    if(direction.equals("south")){
//      dir = "4";
//    }
//    if(direction.equals("west")){
//      dir = "6";
//    }
  }

  public void initGamegrid(){
    for(int y=0; y < 11; y++){
      for(int x=0; x < 11; x++){
        playerBoard[x][y] = new GameCell();
      }
    }
  }

  public void addShipOnClick(View v){
    ship = spinnerShips.getSelectedItem().toString();
    direction = spinnerDirections.getSelectedItem().toString();
    row = spinnerRows.getSelectedItem().toString();
    col = spinnerCols.getSelectedItem().toString();

    if(direction.equals("north")){
      dir = 0;
    }
    if(direction.equals("east")){
      dir = 2;
    }
    if(direction.equals("south")){
      dir = 4;
    }
    if(direction.equals("west")){
      dir = 6;
    }

    if(ship.equals("carrier")){
      length = 5;
    }
    if(ship.equals("battleship")){
      length = 4;
    }
    if(ship.equals("cruiser")){
      length = 3;
    }
    if(ship.equals("submarine")){
      length = 3;
    }
    if(ship.equals("destroyer")){
      length = 2;
    }

    if(row.equals("A")){
      rowNum = 1;
    }
    if(row.equals("B")){
      rowNum = 2;
    }
    if(row.equals("C")){
      rowNum = 3;
    }
    if(row.equals("D")){
      rowNum = 4;
    }
    if(row.equals("E")){
      rowNum = 5;
    }
    if(row.equals("F")){
      rowNum = 6;
    }
    if(row.equals("G")){
      rowNum = 7;
    }
    if(row.equals("H")){
      rowNum = 8;
    }
    if(row.equals("I")){
      rowNum = 9;
    }
    if(row.equals("J")){
      rowNum = 10;
    }


    //call add ship api
    // send the 4 values in url
    String url = BATTLE_SERVER + "api/v1/game/"+ gameId +"/add_ship/" + ship + "/" + row + "/" + col + "/" + dir + ".json";
    Log.d("INTERENT", url);

    StringRequest request = new StringRequest(
        Request.Method.GET, url,
        // call backs
        new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {
            // do something with returned data
            Log.d("INTERENT", response);
            // do something with response

            // take selected ship out of spinner and refresh page
            // draw ship on grid
            drawShip(Integer.parseInt(col), rowNum, dir, length);

//            playerBoard[8][2].setHasShip(true);
            // invalidate grid to redraw on screen
            imageDefensiveGrid.invalidate();

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

  public void startOnClick(View v){
    Intent intent = new Intent(Game.this, Defend.class);
    startActivity(intent);
  }

  public void getAvailableDirections(){
    String url = BATTLE_SERVER + "api/v1/available_directions.json";
    JsonObjectRequest request = new JsonObjectRequest( url, null,
//        Request.Method.GET, url,
        // call backs
        new Response.Listener<JSONObject>() {
          @Override
          public void onResponse(JSONObject response) {
            // do something with returned data
            Log.d("INTERENT", response.toString());

            Iterator iterator = response.keys();
            while(iterator.hasNext()){
              String key = (String)iterator.next();
              try {
                Integer value = (Integer) response.get(key);
                directionsMap.put(key, value);

              } catch(JSONException e) {
                e.printStackTrace();
              }
            }
            // convert to array for spinner
            int size = directionsMap.keySet().size();
            directionsArray = new String[size];
            directionsArray = directionsMap.keySet().toArray( new String[]{});

            directionsSpinnerArrayAdapter = new ArrayAdapter<String>( getApplicationContext(), android.R.layout.simple_spinner_item, directionsArray);
            spinnerDirections.setAdapter(directionsSpinnerArrayAdapter);
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

    requestQueue.add( request );
  }

  public void getAvailableShips(){
    String url = BATTLE_SERVER + "api/v1/available_ships.json";
    JsonObjectRequest request = new JsonObjectRequest( url, null,
//        Request.Method.GET, url,
        // call backs
        new Response.Listener<JSONObject>() {
          @Override
          public void onResponse(JSONObject response) {
            // do something with returned data
            Log.d("INTERENT", response.toString());

            Iterator iterator = response.keys();
            while(iterator.hasNext()){
              String key = (String)iterator.next();
              try {
                Integer value = (Integer) response.get(key);
                shipsMap.put(key, value);

              } catch(JSONException e) {
                e.printStackTrace();
              }
            }
            // convert to array for spinner
            int size = shipsMap.keySet().size();
            shipsArray = new String[size];
            shipsArray = shipsMap.keySet().toArray( new String[]{});

            shipSpinnerArrayAdapter = new ArrayAdapter<String>( getApplicationContext(), android.R.layout.simple_spinner_item, shipsArray);
            spinnerShips.setAdapter(shipSpinnerArrayAdapter);
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

    requestQueue.add( request );
  }

  public void drawShip(int startX, int startY, int direction, int length){

    //north
    if(direction == 0){
      // for each direction
      for(int y = startY; y < length + startY; y--) {
        playerBoard[startX][y].setHasShip(true);
      }
    }
    //east
    if(direction == 2){
      // for each direction
      for(int x = startX; x < length + startX; x++) {
        playerBoard[x][startY].setHasShip(true);
      }
    }
    //south
    if(direction == 4){
      // for each direction
      for(int y = startY; y < length + startY; y++){
        playerBoard[startX][y].setHasShip(true);
      }
    }
    //west
    if(direction == 6){
      // for each direction
      for(int x = startX; x < length + startX; x--) {
        playerBoard[x][startY].setHasShip(true);
      }

    }
  }
}
