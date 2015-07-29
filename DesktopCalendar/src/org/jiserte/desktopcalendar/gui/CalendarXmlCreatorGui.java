package org.jiserte.desktopcalendar.gui;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

public class CalendarXmlCreatorGui extends JFrame {
private JTabbedPane mainTabbedpane;

  /**
   * 
   */
  private static final long serialVersionUID = 603509286920261111L;

  public static void main(String[] args) {
   
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        
        CalendarXmlCreatorGui inst = new CalendarXmlCreatorGui();
          // creates the main instance
        
        inst.setVisible(true);
        inst.setPreferredSize(new Dimension(1024,768));
        inst.setSize(new Dimension(1024,768));
        inst.setLocationRelativeTo(null);
        inst.setTitle("Xml Creator");
        
        inst.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        inst.pack();
        
        
      }
    });
   

  }
  
  public CalendarXmlCreatorGui() {
    
    super();
    this.createGUI();
    
  }

  private void createGUI() {
    try {

      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            // Set System L&F
      this.createPanes();
        // Brings the main pane to screen
    } catch (Exception e) {
      e.printStackTrace();
    }
    
  }

  private void createPanes() {
    
    this.mainTabbedpane = new MainPane();
    
    this.add(this.mainTabbedpane);
    this.mainTabbedpane.addTab("Calendar data", new CalendarDataPane());
    this.mainTabbedpane.addTab("Config data", new JButton("Config"));
    
  }

}
