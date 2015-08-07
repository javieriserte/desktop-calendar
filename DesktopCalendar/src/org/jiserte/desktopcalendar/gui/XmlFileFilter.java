package org.jiserte.desktopcalendar.gui;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class XmlFileFilter extends FileFilter {

  @Override
  public boolean accept(File f) {
    return f.getName().toUpperCase().endsWith(".XML") || f.isDirectory();
  }

  @Override
  public String getDescription() {
    return "XML files";
  }

}
