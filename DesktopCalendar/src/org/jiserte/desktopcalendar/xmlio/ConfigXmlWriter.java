package org.jiserte.desktopcalendar.xmlio;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jiserte.desktopcalendar.data.SystemConfig;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ConfigXmlWriter {
  public void write(SystemConfig config, File outfile) {
    try {

      //////////////////////////////////////////////////////////////////////////
      // String date formatter
      //////////////////////////////////////////////////////////////////////////

      //////////////////////////////////////////////////////////////////////////
      // Create a new document
      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
      Document doc = docBuilder.newDocument();
      //////////////////////////////////////////////////////////////////////////

      //////////////////////////////////////////////////////////////////////////
      // Root element
      Element rootElement = doc.createElement("calendar_options");
      doc.appendChild(rootElement);
      //////////////////////////////////////////////////////////////////////////

      //////////////////////////////////////////////////////////////////////////
      // Sub elements
      Element sizeXel = doc.createElement("size_x");
      Element sizeYel = doc.createElement("size_y");
      Element topEl = doc.createElement("top");
      Element leftEl = doc.createElement("left");
      Element rightEl = doc.createElement("right");
      Element bottomEl = doc.createElement("bottom");
      Element overlayEl = doc.createElement("overlay");
      Element baseImgEl = doc.createElement("base_img");
      Element wallPaperImgEl = doc.createElement("wallpaper");
      rootElement.appendChild(sizeXel);
      rootElement.appendChild(sizeYel);
      rootElement.appendChild(topEl);
      rootElement.appendChild(leftEl);
      rootElement.appendChild(rightEl);
      rootElement.appendChild(bottomEl);
      rootElement.appendChild(overlayEl);
      rootElement.appendChild(baseImgEl);
      rootElement.appendChild(wallPaperImgEl);
      sizeXel.setTextContent(String.valueOf(config.getWidth()));
      sizeYel.setTextContent(String.valueOf(config.getHeight()));
      topEl.setTextContent(String.valueOf(config.getTop()));
      leftEl.setTextContent(String.valueOf(config.getLeft()));
      rightEl.setTextContent(String.valueOf(config.getRight()));
      bottomEl.setTextContent(String.valueOf(config.getBottom()));
      overlayEl.setTextContent(String.valueOf(config.getOverlayPercentage()));
      baseImgEl.setTextContent(String.valueOf(config.getBaseImg()));
      wallPaperImgEl.setTextContent(String.valueOf(config.getWallpaper()));
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
