package org.jiserte.desktopcalendar;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.jiserte.desktopcalendar.data.SystemConfig;
import org.jiserte.desktopcalendar.data.WorkingCalendar;
import org.jiserte.desktopcalendar.syscalls.DetectOperatingSystem;
import org.jiserte.desktopcalendar.syscalls.SystemParametersInfoCall;
import org.jiserte.desktopcalendar.xmlio.ConfigXmlParser;
import org.jiserte.desktopcalendar.xmlio.WorkingDayXmlParser;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef.UINT_PTR;
import com.sun.jna.win32.W32APIFunctionMapper;
import com.sun.jna.win32.W32APITypeMapper;

import cmdGA2.CommandLine;
import cmdGA2.SingleArgumentOption;
import cmdGA2.returnvalues.InfileValue;

public class WallCalendarCli {

  public static void main(String[] args) {

    ////////////////////////////////////////////////////////////////////////////
    // Create command line
    CommandLine commandLine = new CommandLine();
    // Add options to the command line
    SingleArgumentOption<File> inOpt = new SingleArgumentOption<>(commandLine,
        "--in", new InfileValue(), null);
    SingleArgumentOption<File> cfgOpt = new SingleArgumentOption<File>(
        commandLine, "--cfg", new InfileValue(), null);
    // read command line
    commandLine.readAndExitOnError(args);
    ////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////
    // Validate command line
    if (!inOpt.isPresent() || !cfgOpt.isPresent()) {
      System.err.println("One or more mandatory options are not present");
      System.exit(1);
    }
    ////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////
    // Get Values from the command line
    File in = inOpt.getValue();
    File cfg = cfgOpt.getValue();
    ////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////
    // Read Xml Files with the calendar and config data
    WorkingCalendar wc = new WorkingDayXmlParser().read(in);
    SystemConfig config = new ConfigXmlParser().read(cfg);
    ////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////
    // Create CalendarImage
    CalendarImage calI = new CalendarImage();
    Calendar from = Calendar.getInstance();
    from.setTimeInMillis(0);
    from.set(wc.getFrom().get(Calendar.YEAR),
             wc.getFrom().get(Calendar.MONTH),
             wc.getFrom().get(Calendar.DAY_OF_MONTH));
    Calendar to = Calendar.getInstance();
    to.setTimeInMillis(0);
    to.set(wc.getTo().get(Calendar.YEAR),
           wc.getTo().get(Calendar.MONTH),
           wc.getTo().get(Calendar.DAY_OF_MONTH));
    ////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////
    // Create the image
    Insets insets = new Insets(config.getTop(), config.getLeft(),
        config.getBottom(), config.getRight());
    BufferedImage image = calI.createWallPaper(
        new Dimension(config.getWidth(), config.getHeight()),
        config.getBaseImg(), from, to, wc,insets, config.getOverlayPercentage());
    ////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////
    // Export image to file
    try {
      File outWPFile = config.getWallpaper();
      String outWPFileName = outWPFile.getAbsolutePath(); 
      String ext = outWPFileName.substring(outWPFileName.length() - 3, 
          outWPFileName.length()).toUpperCase();
      if (ext != "PNG" && ext != "JPG" ) {
        ext = "JPG";
      }
      ImageIO.write(image, ext, config.getWallpaper());
    } catch (IOException e) {
      e.printStackTrace();
    }
    ////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////
    // Change windows wallpaper
    if (DetectOperatingSystem.isWindows()) {
      Map<Object, Object> apiMapper = new HashMap<>();
      apiMapper.put(com.sun.jna.Library.OPTION_TYPE_MAPPER,
          W32APITypeMapper.UNICODE);
      apiMapper.put(com.sun.jna.Library.OPTION_FUNCTION_MAPPER,
          W32APIFunctionMapper.UNICODE);

      SystemParametersInfoCall libraryCaller = (SystemParametersInfoCall) Native
          .loadLibrary("user32", SystemParametersInfoCall.class, apiMapper);

      libraryCaller.SystemParametersInfo(
          new UINT_PTR(SystemParametersInfoCall.SPI_SETDESKWALLPAPER),
          new UINT_PTR(0), config.getWallpaper().getAbsolutePath(),
          new UINT_PTR(SystemParametersInfoCall.SPIF_UPDATEINIFILE
              | SystemParametersInfoCall.SPIF_SENDWININICHANGE));
    }
    if (DetectOperatingSystem.isLinux()) {
      try {
        Process p = Runtime.getRuntime().exec(
            "gsettings set org.gnome.desktop.background picture-uri file://"
                + config.getWallpaper().getAbsolutePath());
        p.waitFor();
      } catch (IOException e) {
        e.printStackTrace();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    ////////////////////////////////////////////////////////////////////////////
  }

}
