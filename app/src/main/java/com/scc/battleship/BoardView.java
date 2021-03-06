package com.scc.battleship;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;


// defense boardview
public class BoardView extends AppCompatImageView {

  Paint paint, paint2, paint3;
  public static int cellWidth;


  public BoardView ( Context context, AttributeSet attrs ){
    super(context, attrs);

    paint = new Paint();
    paint.setColor(Color.WHITE);
    paint.setStrokeWidth(5);
    paint.setStyle(Paint.Style.FILL_AND_STROKE);

    Typeface typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);
    paint.setTypeface(typeface);
    paint.setTextAlign(Paint.Align.CENTER);
  }

  @Override
  protected void onDraw( Canvas canvas ){
    int width = canvas.getWidth()-1;
    int height = canvas.getHeight()-1;
//    int cellHeight = height / 11;
    cellWidth = width / 11;

    paint.setTextSize(cellWidth);

    for(int i=0; i < 12; i++){
      canvas.drawLine(0, (cellWidth*i), width, (cellWidth * i), paint);
      canvas.drawLine((cellWidth*i), 0, (cellWidth * i), width, paint);
    }

    // doesn't need to set any of these
    for(int y=0; y < 11; y++){
      for(int x=0; x < 11; x++){
        BaseActivity.playerBoard[x][y].setTopLeft( new Point( x * cellWidth, y * cellWidth ));
        BaseActivity.playerBoard[x][y].setBottomRight( new Point( (x+1) * cellWidth, (y+1) * cellWidth ));
      }
    }

    Rect textbounds = new Rect();
    paint.getTextBounds("A", 0, 1, textbounds );

    int textHeight = textbounds.height();
    int textWidth = textbounds.width();

    int textX = cellWidth / 2;
    int textY = cellWidth + ((cellWidth / 2) - (textHeight));
    textY += cellWidth;
    canvas.drawText("A", textX, textY, paint );
    textY += cellWidth;
    canvas.drawText("B", textX, textY, paint );
    textY += cellWidth;
    canvas.drawText("C", textX, textY, paint );
    textY += cellWidth;
    canvas.drawText("D", textX, textY, paint );
    textY += cellWidth;
    canvas.drawText("E", textX, textY, paint );
    textY += cellWidth;
    canvas.drawText("F", textX, textY, paint );
    textY += cellWidth;
    canvas.drawText("G", textX, textY, paint );
    textY += cellWidth;
    canvas.drawText("H", textX, textY, paint );
    textY += cellWidth;
    canvas.drawText("I", textX, textY, paint );
    textY += cellWidth;
    canvas.drawText("J", textX, textY, paint );


    textX = cellWidth / 2;
    textY = cellWidth + ((cellWidth / 2) - (textHeight));

    textX += cellWidth;
    canvas.drawText("1", textX, textY, paint );
    textX += cellWidth;
    canvas.drawText("2", textX, textY, paint );
    textX += cellWidth;
    canvas.drawText("3", textX, textY, paint );
    textX += cellWidth;
    canvas.drawText("4", textX, textY, paint );
    textX += cellWidth;
    canvas.drawText("5", textX, textY, paint );
    textX += cellWidth;
    canvas.drawText("6", textX, textY, paint );
    textX += cellWidth;
    canvas.drawText("7", textX, textY, paint );
    textX += cellWidth;
    canvas.drawText("8", textX, textY, paint );
    textX += cellWidth;
    canvas.drawText("9", textX, textY, paint );
    textX += cellWidth;
    canvas.drawText("10", textX, textY, paint );

    float w = paint.measureText("W", 0, 0);
    // setting points in both grids

    float center = ( cellWidth / 2) - ( w/2);
    // setting points in both grids CHANGED BOTH FROM 11 TO 12
    for(int y=0; y < 11; y++){
      for(int x=0; x < 11; x++){
        if( BaseActivity.playerBoard[x][y].isHasShip()){
          // show that it has a ship
          // after placing ships, this is the player's board.
          drawCell("S", x, y, center, canvas );
        }
      }
    }

  }

  void drawCell(String contents, int x, int y, float center, Canvas canvas){
    canvas.drawText(contents, BaseActivity.playerBoard[x][y].getTopLeft().x + center,
        BaseActivity.playerBoard[x][y].getBottomRight().y, paint );
  }

}
