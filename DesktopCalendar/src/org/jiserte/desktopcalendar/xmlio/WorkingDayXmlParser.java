package org.jiserte.desktopcalendar.xmlio;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.jiserte.desktopcalendar.data.Priority;
import org.jiserte.desktopcalendar.data.WorkingCalendar;
import org.jiserte.desktopcalendar.data.WorkingDay;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;
import java.util.Calendar;
 
public class WorkingDayXmlParser {
 
  public WorkingCalendar  read(File file) {
 
    WorkingCalendar result = new WorkingCalendar();
    try {
      //////////////////////////////////////////////////////////////////////////
      // Read the xml file
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(file);
      // And normalize the document
      doc.getDocumentElement().normalize();
      //////////////////////////////////////////////////////////////////////////
 
      //////////////////////////////////////////////////////////////////////////
      // Iterate over 'day' elements
      NodeList nList = doc.getElementsByTagName("day");
      for (int temp = 0; temp < nList.getLength(); temp++) {
        Node nNode = nList.item(temp);
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
          Element eElement = (Element) nNode;
          
          Calendar cal  = this.parseDate(eElement.getElementsByTagName("date")
              .item(0).getTextContent());
          String task = eElement.getElementsByTagName("task").item(0)
              .getTextContent();
          Priority priority = this.parsePriority(eElement
              .getElementsByTagName("priority").item(0).getTextContent(),cal);
          result.addWorkingDay(new WorkingDay(cal, task, priority));
        }
      }
      //////////////////////////////////////////////////////////////////////////
      
      //////////////////////////////////////////////////////////////////////////
      // read from and to
      nList = doc.getElementsByTagName("from");
      Calendar from = this.parseDate(nList.item(0).getTextContent());
      nList = doc.getElementsByTagName("to");
      Calendar to = this.parseDate(nList.item(0).getTextContent());
      //////////////////////////////////////////////////////////////////////////
      
      //////////////////////////////////////////////////////////////////////////
      // Add range data to working calendar and keep dates in range
      result.setFrom(from);
      result.setTo(to);
      result.keepDatesInRange();
      //////////////////////////////////////////////////////////////////////////
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  private Priority parsePriority(String attribute, Calendar taskDay) {
    Priority currentPriority = null;
    switch (attribute.trim().toUpperCase()) {
    case "INTHEPAST":
      currentPriority =  Priority.InThePast;
      break;
    case "NONE":
      currentPriority = Priority.None;
      break;
    case "LOW":
      currentPriority = Priority.Low;
      break;
    case "MID":
      currentPriority = Priority.Mid;
      break;
    case "HIGH":
      currentPriority = Priority.High;
      break;
    }
    if (taskDay.before(Calendar.getInstance())) {
      currentPriority = Priority.InThePast;
    }
    return currentPriority;
  }

  private Calendar parseDate(String attribute) {
    String[] data = attribute.split("/");
    if (data.length==3) {
      Calendar cal = Calendar.getInstance();
      cal.setTimeInMillis(0);
      cal.set(Integer.valueOf(data[2]), Integer.valueOf(data[1])-1,Integer.valueOf(data[0]));
      return cal;
    }
    return null;
  }
 
}