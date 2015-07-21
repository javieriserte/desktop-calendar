package org.jiserte.desktopcalendar;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class ConfigXmlParser {

  public SystemConfig  read(File file) {
 
    int width = 0;
    int height = 0;
    File baseImg = null;
    File wallpaperImg = null;
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
      // Read the tags in the config file
      NodeList nList = doc.getElementsByTagName("size_x");
      width = Integer.valueOf(nList.item(0).getTextContent());
      
      nList = doc.getElementsByTagName("size_y");
      height = Integer.valueOf(nList.item(0).getTextContent());

      nList = doc.getElementsByTagName("base_img");
      baseImg = new File(nList.item(0).getTextContent());

      nList = doc.getElementsByTagName("wallpaper");
      wallpaperImg = new File(nList.item(0).getTextContent());
      //////////////////////////////////////////////////////////////////////////
   
      return new SystemConfig(width, height, baseImg, wallpaperImg);
   
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
  
}
