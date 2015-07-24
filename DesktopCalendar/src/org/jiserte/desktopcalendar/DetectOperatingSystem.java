package org.jiserte.desktopcalendar;

public class DetectOperatingSystem {

  private static final String NameOS = System.getProperty("os.name")
      .toLowerCase();

  public static boolean isWindows() {
    return NameOS.contains("win");
  }
  public static boolean isLinux() {
    return NameOS.contains("linux");
  }

}
