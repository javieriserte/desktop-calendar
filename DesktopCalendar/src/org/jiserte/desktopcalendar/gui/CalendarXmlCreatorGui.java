package org.jiserte.desktopcalendar.gui;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

public class CalendarXmlCreatorGui extends JFrame {
  //////////////////////////////////////////////////////////////////////////////
  // Components
  private JTabbedPane mainTabbedpane;
  private CalendarDataPane calendarDataPane;
  //////////////////////////////////////////////////////////////////////////////

  //////////////////////////////////////////////////////////////////////////////
  // Instance variables
//  private List<WorkingDay> workingCalendr;
  //////////////////////////////////////////////////////////////////////////////
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
    this.calendarDataPane = new CalendarDataPane();

    this.add(this.mainTabbedpane);
    this.mainTabbedpane.addTab("Calendar data", calendarDataPane);
    this.mainTabbedpane.addTab("Config data", new JButton("Config"));
    
  }

}
