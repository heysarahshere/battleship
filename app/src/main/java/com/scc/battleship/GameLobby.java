package com.scc.battleship;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GameLobby extends BaseActivity {
  TextView usernameText;
  ImageView avatarImageView;
  ListView allPlayersListView;
  ArrayAdapter playersArrayAdapter;
  UserPreferences user;
  UsersListAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_game_lobby);
    usernameText = findViewById(R.id.usernameTextView);
    allPlayersListView = findViewById(R.id.allPlayersListView);
    avatarImageView = findViewById(R.id.avatarImageView);

    usernameText.setText(userPrefs.getAvatarName());
    Picasso.with( getApplicationContext() ).load( "http://www.battlegameserver.com/" + userPrefs.getAvatarImage() ).into( avatarImageView );
    fillPlayersArray();
  }


  public void fillPlayersArray(){
    String url = BATTLE_SERVER + "api/v1/all_users.json";
    StringRequest request = new StringRequest(
        Request.Method.GET, url,
        // call backs
        new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {
            // do something with returned data
            Log.d("INTERENT", response);
            allUsers = gson.fromJson( response, UserPreferences[].class );
            // start next intent

            ArrayList<UserPreferences> arrayList = new ArrayList<UserPreferences>(Arrays.asList(allUsers));
            adapter = new UsersListAdapter(GameLobby.this, 0, arrayList);
            allPlayersListView.setAdapter(adapter);

            allPlayersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("CLICKED", "Clicked something");

                Intent intent = new Intent( view.getContext(), Show.class);
                intent.putExtra("avatar", allUsers[position].getAvatarImage());
                intent.putExtra("username",  allUsers[position].getAvatarName());
                intent.putExtra("firstName",  allUsers[position].getFirstName());
                intent.putExtra("lastName",  allUsers[position].getLastName());
                intent.putExtra("email",  allUsers[position].getEmail());
                intent.putExtra("level",  allUsers[position].getLevel());
                intent.putExtra("won",  allUsers[position].getBattlesWon().toString());
                intent.putExtra("lost",  allUsers[position].getBattlesLost().toString());
                intent.putExtra("tied",  allUsers[position].getBattlesTied().toString());
                intent.putExtra("coins",  allUsers[position].getCoins().toString());
                intent.putExtra("exp",  allUsers[position].getExperiencePoints().toString());
                startActivity(intent);
              }
            });
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

  public void profileOnClick(View v){
    Intent intent = new Intent(GameLobby.this, UserProfile.class);
    startActivity(intent);

  }

  public void gameOnClick(View v){

    String url = BATTLE_SERVER + "api/v1/challenge_computer.json";
    JsonObjectRequest request = new JsonObjectRequest( url, null,
//        Request.Method.GET, url,
        // call backs
        new Response.Listener<JSONObject>() {
          @Override
          public void onResponse(JSONObject response) {
            // do something with returned data
            Log.d("INTERENT", response.toString());
            // do something to get game id
            try {
              gameId = response.getInt("game_id");
            } catch (JSONException e) {
              e.printStackTrace();
            }
            Intent intent = new Intent(GameLobby.this, Game.class);
            startActivity(intent);
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


  public void logoutOnClick(View v){
    String url = BATTLE_SERVER + "api/v1/logout.json";
    StringRequest request = new StringRequest(
        Request.Method.GET, url,
        // call backs
        new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {
            // do something with returned data
            Log.d("INTERENT", response);


            toastIt("Success: " + response);

            Intent intent = new Intent(GameLobby.this, MainActivity.class);
            startActivity(intent);
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

}
