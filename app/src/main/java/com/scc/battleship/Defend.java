package com.scc.battleship;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class Defend extends BaseActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_defend);


    initGamegrid();
  }
  public void onClickEnemyShips(View v ) {
    Intent intent = new Intent(Defend.this, Attack.class);
    startActivity(intent);
  }

  public void initGamegrid(){
    for(int y=0; y < 11; y++){
      for(int x=0; x < 11; x++){
        playerBoard[x][y] = new GameCell();
      }
    }
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
