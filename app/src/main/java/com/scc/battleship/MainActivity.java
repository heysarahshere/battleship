package com.scc.battleship;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.AlphabeticIndex;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity {

  Button loginButton;
  EditText usernameEditText, passwordEditText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    loginButton = findViewById(R.id.loginButton);
    usernameEditText = findViewById(R.id.usernameEditText);
    passwordEditText = findViewById(R.id.passwordEditText);
  }

  public void LoginOnClick(View v){
    username = usernameEditText.getText().toString();
    password = passwordEditText.getText().toString();

    String url = "http://www.battlegameserver.com/api/v1/login.json";
    StringRequest request = new StringRequest(
        Request.Method.GET, url,
        // call backs
        new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {
            // do something with returned data
            Log.d("INTERENT", response);

            if(response.isEmpty()){
              toastIt("Error: no reponse. " + response);

              Intent intent = new Intent(MainActivity.this, MainActivity.class);
              startActivity(intent);
//             recreate();
            } else {
              // if success
              userPrefs = gson.fromJson( response, UserPreferences.class );
              // start next intent
              Intent intent = new Intent(MainActivity.this, GameLobby.class);
              startActivity(intent);
            }
          }
        }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        // if error

        Log.d("INTERENT", error.toString());

        if(error.toString().equals("com.android.volley.AuthFailureError")){
          toastIt("Incorrect username or password.");

          Intent intent = new Intent(MainActivity.this, MainActivity.class);
          startActivity(intent);
        } else {

          toastIt("Internet failure: " + error.toString());
          recreate();
        }
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
