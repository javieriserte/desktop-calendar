package org.jiserte.desktopcalendar.data;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class WorkingCalendar {
  //////////////////////////////////////////////////////////////////////////////
  // Instance variables
  private Map<Calendar,WorkingDay> workingDays;
  private Calendar from;
  private Calendar to;
  //////////////////////////////////////////////////////////////////////////////

  //////////////////////////////////////////////////////////////////////////////
  public Map<Calendar, WorkingDay> getWorkingDays() {
    return workingDays;
  }

  public void setWorkingDays(Map<Calendar, WorkingDay> workingDays) {
    this.workingDays = workingDays;
  }
  
  public Calendar getFrom() {
    return from;
  }

  public void setFrom(Calendar from) {
    this.from = from;
  }

  public Calendar getTo() {
    return to;
  }

  public void setTo(Calendar to) {
    this.to = to;
  }

  public void addWorkingDay(WorkingDay workingDay) {
    if (this.getWorkingDays()==null) {
      this.setWorkingDays(new HashMap<Calendar,WorkingDay>());
    }
    this.getWorkingDays().put(workingDay.getDate(), workingDay);
  }
  public WorkingDay getWorkingDay(Calendar date) {
    if (this.getWorkingDays()==null || !this.getWorkingDays()
      .containsKey(date)) {
        return new WorkingDay(date, "", Priority.None);
    } 
    return this.getWorkingDays().get(date);
  }
  public void keepDatesInRange() {
    Collection<WorkingDay> workingDays = this.getWorkingDays().values();
    Calendar to = this.getTo();
    Calendar from = this.getFrom();
    Predicate<WorkingDay> predicate = new Predicate<WorkingDay>() {
      @Override public boolean test(WorkingDay workingDay) {
        return (workingDay.getDate().after(to) || 
          workingDay.getDate().before(from)) ;
      }
    };
    workingDays.removeIf(predicate);
  }
  //////////////////////////////////////////////////////////////////////////////
}
