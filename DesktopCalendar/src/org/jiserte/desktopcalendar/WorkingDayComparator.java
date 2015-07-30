package org.jiserte.desktopcalendar;

import java.util.Comparator;

public class WorkingDayComparator implements Comparator<WorkingDay>{

  @Override
  public int compare(WorkingDay o1, WorkingDay o2) {
    return o1.getDate().compareTo(o2.getDate());
  }

}
