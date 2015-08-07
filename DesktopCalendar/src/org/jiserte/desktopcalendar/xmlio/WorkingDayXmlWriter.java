package org.jiserte.desktopcalendar.xmlio;

import java.util.Collections;
import java.util.List;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jiserte.desktopcalendar.data.WorkingCalendar;
import org.jiserte.desktopcalendar.data.WorkingDay;
import org.jiserte.desktopcalendar.data.WorkingDayComparator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class WorkingDayXmlWriter {
  
  public void write(WorkingCalendar calendar, File outfile) {
    try {

      //////////////////////////////////////////////////////////////////////////
      // String date formatter
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
      //////////////////////////////////////////////////////////////////////////

      //////////////////////////////////////////////////////////////////////////
      // Create a new document
      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
      Document doc = docBuilder.newDocument();
      //////////////////////////////////////////////////////////////////////////

      //////////////////////////////////////////////////////////////////////////
      // Root element
      Element rootElement = doc.createElement("calendar");
      doc.appendChild(rootElement);
      //////////////////////////////////////////////////////////////////////////

      //////////////////////////////////////////////////////////////////////////
      // Date range element
      Element fromEl = doc.createElement("from");
      rootElement.appendChild(fromEl);
      fromEl.setTextContent(sdf.format(calendar.getFrom().getTime()));
      Element toEl = doc.createElement("to");
      rootElement.appendChild(toEl);
      toEl.setTextContent(sdf.format(calendar.getTo().getTime()));
      //////////////////////////////////////////////////////////////////////////
      
      //////////////////////////////////////////////////////////////////////////
      // Day elements
      List<WorkingDay> wds = new ArrayList<>();
      for (WorkingDay day : calendar.getWorkingDays().values()){
        wds.add(day);
      }
      Collections.sort(wds, new WorkingDayComparator());
      for (WorkingDay day  : wds) {
        Element dayEl = doc.createElement("day");
        ////////////////////////////////////////////////////////////////////////
        // Set date in day
        Element dateInDayEl = doc.createElement("date");
        dateInDayEl.setTextContent(sdf.format(day.getDate().getTime()));
        dayEl.appendChild(dateInDayEl);
        // Set priority in day
        Element priorityInDayEl = doc.createElement("priority");
        priorityInDayEl.setTextContent(day.getPriority().name());
        dayEl.appendChild(priorityInDayEl);
        // Set Task in day
        Element taskInDayElement = doc.createElement("task");
        taskInDayElement.setTextContent(day.getTask());
        dayEl.appendChild(taskInDayElement);
        ////////////////////////////////////////////////////////////////////////
        rootElement.appendChild(dayEl);
      }
      //////////////////////////////////////////////////////////////////////////

      //////////////////////////////////////////////////////////////////////////
      // write the content into xml file
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount","2");
      DOMSource source = new DOMSource(doc);
      doc.normalize();
      StreamResult result = new StreamResult(outfile);
      transformer.transform(source, result);
      //////////////////////////////////////////////////////////////////////////

    } catch (ParserConfigurationException pce) {
      pce.printStackTrace();
    } catch (TransformerException tfe) {
      tfe.printStackTrace();
    }
  }
}
