package org.jiserte.desktopcalendar;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * This class creates a map for a given priority to a color.
 * @author Javier
 *
 */
public class PriorityColorMapFactory {
  //////////////////////////////////////////////////////////////////////////////
  // Public interface
  public static Map<Priority, Color> getDefaultMap() {
	Map<Priority,Color> map = new HashMap<>();
	map.put(Priority.None, new Color(250,250,250));
	map.put(Priority.Low, new Color (220,255,220));
	map.put(Priority.Mid, new Color (255,255,160));
	map.put(Priority.High, new Color (255,220,220));
	map.put(Priority.InThePast, new Color (131,139,131));
	return map;
  }
  //////////////////////////////////////////////////////////////////////////////
}
