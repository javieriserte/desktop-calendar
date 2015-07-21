package org.jiserte.desktopcalendar;

import java.util.Calendar;

public class WorkingDay {
	////////////////////////////////////////////////////////////////////////////
	// Internal state
	private Calendar date;
	private String taks;
	private Priority priority;
	////////////////////////////////////////////////////////////////////////////
	
	////////////////////////////////////////////////////////////////////////////
	// Constructor
	 public WorkingDay(Calendar date, String taks, Priority priority) {
	    super();
	    this.date = date;
	    this.taks = taks;
	    this.priority = priority;
	}
	////////////////////////////////////////////////////////////////////////////
	
	////////////////////////////////////////////////////////////////////////////
	// Public interface
  public Calendar getDate() {
	  return date;
	}
	public void setDate(Calendar date) {
		this.date = date;
	}
	public String getTaks() {
		return taks;
	}
	public void setTaks(String taks) {
		this.taks = taks;
	}
	public Priority getPriority() {
		return priority;
	}
	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	////////////////////////////////////////////////////////////////////////////

}
