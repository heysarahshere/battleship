package com.scc.battleship;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class Show extends BaseActivity {

  TextView usernameText, fullNameText, emailText, lostTextView, wonTextView, tiedTextView, levelTextView, expTextView, coinsTextView;
  ImageView avatarImageView;
  String fullName;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_show);

    usernameText = findViewById(R.id.usernameTextView);
    fullNameText = findViewById(R.id.fullNameTextView);
    emailText = findViewById(R.id.emailTextView);
    lostTextView = findViewById(R.id.lostTextView);
    tiedTextView = findViewById(R.id.tiedTextView);
    levelTextView = findViewById(R.id.levelTextView);
    coinsTextView = findViewById(R.id.coinsTextView);
    expTextView = findViewById(R.id.expTextView);
    wonTextView = findViewById(R.id.wonTextView);
    avatarImageView = findViewById(R.id.avatarImageView);

    fullName = getIntent().getStringExtra("firstName") + " " + getIntent().getStringExtra("lastName");
    //Set
    Picasso.with( getApplicationContext() ).load( "http://www.battlegameserver.com/" + getIntent().getStringExtra("avatar") ).into( avatarImageView );
    fullNameText.setText( fullName );
    usernameText.setText(getIntent().getStringExtra("username"));
    emailText.setText( getIntent().getStringExtra("email"));
    lostTextView.setText(getIntent().getStringExtra("lost"));
    wonTextView.setText( getIntent().getStringExtra("won"));
    tiedTextView.setText( getIntent().getStringExtra("tied"));
    expTextView.setText(getIntent().getStringExtra("exp"));
    coinsTextView.setText(getIntent().getStringExtra("coins"));
    levelTextView.setText(getIntent().getStringExtra("level"));

  }

}
