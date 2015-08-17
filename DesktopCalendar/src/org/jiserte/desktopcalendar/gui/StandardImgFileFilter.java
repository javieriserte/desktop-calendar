package org.jiserte.desktopcalendar.gui;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class StandardImgFileFilter extends FileFilter{

  @Override
  public boolean accept(File f) {
    return f.getName().toUpperCase().endsWith(".BMP") || 
        f.getName().toUpperCase().endsWith(".JPG") ||
        f.getName().toUpperCase().endsWith(".GIF") ||
        f.getName().toUpperCase().endsWith(".PNG") ||
        f.isDirectory();
  }

  @Override
  public String getDescription() {
    return "Standard image formats (PNG, JPG, GIF & BMP)";
  }

}
