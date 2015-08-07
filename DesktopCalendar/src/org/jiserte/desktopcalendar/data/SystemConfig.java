package org.jiserte.desktopcalendar.data;

import java.io.File;

public class SystemConfig {

  //////////////////////////////////////////////////////////////////////////////
  // Internal state
  private final int width;
  private final int height;
  private final File baseImg;
  private final File wallpaper;
  private final int left;
  private final int right;
  private final int top;
  private final int bottom;
  private final float overlayPercentage;
  //////////////////////////////////////////////////////////////////////////////
  
  //////////////////////////////////////////////////////////////////////////////
  // Constructor
  public SystemConfig(int width, int height, File baseImg, File wallpaper,
      int left, int right, int top, int bottom, float overlayPercentage) {
    super();
    this.width = width;
    this.height = height;
    this.baseImg = baseImg;
    this.wallpaper = wallpaper;
    this.left = left;
    this.right= right;
    this.top = top;
    this.bottom = bottom;
    this.overlayPercentage = overlayPercentage;
  }
  //////////////////////////////////////////////////////////////////////////////

  //////////////////////////////////////////////////////////////////////////////
  // Public interface
  public int getWidth() {
    return width;
  }
  public int getHeight() {
    return height;
  }
  public File getBaseImg() {
    return baseImg;
  }
  public File getWallpaper() {
    return wallpaper;
  }
  public int getLeft() {
    return left;
  }
  public int getRight() {
    return right;
  }
  public int getTop() {
    return top;
  }
  public int getBottom() {
    return bottom;
  }
  public float getOverlayPercentage() {
    return this.overlayPercentage;
  }
  //////////////////////////////////////////////////////////////////////////////

  
  
}
