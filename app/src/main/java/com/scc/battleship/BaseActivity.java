package com.scc.battleship;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BaseActivity extends AppCompatActivity {
  RequestQueue requestQueue;
  Gson gson;
  static UserPreferences[] allUsers;
  final String BATTLE_SERVER = "http://www.battlegameserver.com/";
  static UserPreferences userPrefs;

  static String username, password;
  static  int gameId;

  public static GameCell[][] playerBoard = new GameCell[11][11];
  public static GameCell[][] computerBoard = new GameCell[11][11];

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);


    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.setDateFormat( "yyyy-MM-dd'T'HH:mm:ssX");
    gson = gsonBuilder.create();

    // Volley library
    Cache cache = new DiskBasedCache( getCacheDir(), 1024 * 1024 );
    Network network = new BasicNetwork( new HurlStack() );

    requestQueue = new RequestQueue( cache, network );
    requestQueue.start();
  }

  public void toastIt( String msg ){
    Toast.makeText( getApplicationContext(),
        msg, Toast.LENGTH_LONG).show();
  }
}
