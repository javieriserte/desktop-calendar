package org.jiserte.desktopcalendar;

import java.io.File;

public class SystemConfig {

  //////////////////////////////////////////////////////////////////////////////
  // Internal state
  private final int width;
  private final int height;
  private final File baseIg;
  private final File wallpaper;
  //////////////////////////////////////////////////////////////////////////////
  
  //////////////////////////////////////////////////////////////////////////////
  // Constructor
  public SystemConfig(int width, int height, File baseIg, File wallpaper) {
    super();
    this.width = width;
    this.height = height;
    this.baseIg = baseIg;
    this.wallpaper = wallpaper;
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
    return baseIg;
  }

  public File getWallpaper() {
    return wallpaper;
  }
  //////////////////////////////////////////////////////////////////////////////
  
  
}
