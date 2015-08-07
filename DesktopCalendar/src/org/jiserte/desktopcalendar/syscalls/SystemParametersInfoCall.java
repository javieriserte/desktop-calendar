package org.jiserte.desktopcalendar.syscalls;

import com.sun.jna.platform.win32.WinDef.UINT_PTR;
import com.sun.jna.win32.*;

public interface SystemParametersInfoCall extends StdCallLibrary {

  //////////////////////////////////////////////////////////////////////////////
  // Constants For wallpaper change from MSDN.
  long SPI_SETDESKWALLPAPER = 20;
  long SPIF_UPDATEINIFILE = 0x01;
  long SPIF_SENDWININICHANGE = 0x02;
  // Public interface
  boolean SystemParametersInfo(UINT_PTR uiAction, UINT_PTR uiParam,
      String pvParam, UINT_PTR fWinIni);
  //////////////////////////////////////////////////////////////////////////////
}
