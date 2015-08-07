package org.jiserte.desktopcalendar.data;

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
	public String getTask() {
		return taks;
	}
	public void setTask(String task) {
		this.taks = task;
	}
	public Priority getPriority() {
		return priority;
	}
	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	////////////////////////////////////////////////////////////////////////////

}
