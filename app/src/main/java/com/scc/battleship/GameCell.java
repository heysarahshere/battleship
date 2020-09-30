package com.scc.battleship;

import android.graphics.Point;

public class GameCell {

  private boolean hasShip;
  private boolean miss;
  private boolean hit;
  private boolean waiting;
  private Point topLeft;
  private Point bottomRight;
  private Point viewOrigin;

  private int cellWidth, cellHeight;

  public GameCell() {
    this.hasShip = false;
    this.miss = false;
    this.hit = false;
    this.waiting = false;
  }

  public GameCell(boolean hasShip, boolean miss, boolean hit, boolean waiting, Point topLeft, Point bottomRight) {
    this.hasShip = hasShip;
    this.miss = miss;
    this.hit = hit;
    this.waiting = waiting;
    this.topLeft = topLeft;
    this.bottomRight = bottomRight;
  }

  public boolean isHasShip() {
    return hasShip;
  }

  public void setHasShip(boolean hasShip) {
    this.hasShip = hasShip;
  }

  public boolean isMiss() {
    return miss;
  }

  public void setMiss(boolean miss) {
    this.miss = miss;
  }

  public boolean isHit() {
    return hit;
  }

  public void setHit(boolean hit) {
    this.hit = hit;
  }

  public boolean isWaiting() {
    return waiting;
  }

  public void setWaiting(boolean waiting) {
    this.waiting = waiting;
  }

  public Point getTopLeft() {
    return topLeft;
  }

  public void setTopLeft(Point topLeft) {
    this.topLeft = topLeft;
  }

  public Point getBottomRight() {
    return bottomRight;
  }

  public void setBottomRight(Point bottomRight) {
    this.bottomRight = bottomRight;
  }

  public Point getViewOrigin() {
    return viewOrigin;
  }

  public void setViewOrigin(Point viewOrigin) {
    this.viewOrigin = viewOrigin;
  }

  public int getCellWidth() {
    return cellWidth;
  }

  public void setCellWidth(int cellWidth) {
    this.cellWidth = cellWidth;
  }

  public int getCellHeight() {
    return cellHeight;
  }

  public void setCellHeight(int cellHeight) {
    this.cellHeight = cellHeight;
  }
}
