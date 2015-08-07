package org.jiserte.desktopcalendar.xmlio;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jiserte.desktopcalendar.data.SystemConfig;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class ConfigXmlParser {

  public SystemConfig  read(File file) {
 
    int width = 0;
    int height = 0;
    File baseImg = null;
    File wallpaperImg = null;
    int left=10;
    int top = 20;
    int right = 10;
    int bottom = 40;
    float overlayPercentage = 0.5F;
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

      nList = doc.getElementsByTagName("left");
      left = Integer.valueOf(nList.item(0).getTextContent());
      
      nList = doc.getElementsByTagName("right");
      right = Integer.valueOf(nList.item(0).getTextContent());

      nList = doc.getElementsByTagName("top");
      top = Integer.valueOf(nList.item(0).getTextContent());

      nList = doc.getElementsByTagName("bottom");
      bottom = Integer.valueOf(nList.item(0).getTextContent());

      nList = doc.getElementsByTagName("overlay");
      overlayPercentage = Float.valueOf(nList.item(0).getTextContent());
      //////////////////////////////////////////////////////////////////////////

      return new SystemConfig(width, height, baseImg, wallpaperImg, left ,right,
          top,bottom,overlayPercentage);
   
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
  
}
