package com.scc.battleship;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class UserProfile extends BaseActivity {

  TextView usernameText, fullNameText, emailText, lostTextView, wonTextView, tiedTextView, levelTextView, expTextView, coinsTextView;
  ImageView avatarImageView;
  String fullName;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_profile);

    fullName = userPrefs.getFirstName().toString() + " " + userPrefs.getLastName().toString();
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

    Picasso.with( getApplicationContext() ).load( "http://www.battlegameserver.com/" + userPrefs.getAvatarImage() ).into( avatarImageView );
    usernameText.setText( userPrefs.getAvatarName().toString() );
    fullNameText.setText( fullName );
    emailText.setText( userPrefs.getEmail().toString() );
    lostTextView.setText( userPrefs.getBattlesLost().toString() );
    wonTextView.setText( userPrefs.getBattlesWon().toString() );
    tiedTextView.setText( userPrefs.getBattlesTied().toString() );
    levelTextView.setText( userPrefs.getLevel().toString() );
    expTextView.setText( userPrefs.getExperiencePoints().toString() );
    coinsTextView.setText( userPrefs.getCoins().toString() );

  }


}
