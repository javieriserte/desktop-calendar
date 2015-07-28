package org.jiserte.desktopcalendar.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

import org.jiserte.desktopcalendar.WorkingDay;

public class CalendarDataPane extends JPanel {

  /**
   * 
   */
  private static final long serialVersionUID = 232850896722799109L;

  public CalendarDataPane() {
    super();
    
    GridBagLayout layout = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    layout.columnWeights = new double[]{0,0,1};
    layout.columnWidths = new int[]{50,5,100};
    layout.rowHeights   = new int[]{100};
    layout.rowWeights   = new double[]{1};
    
    this.setLayout(layout);
    
    JPanel buttonsPanel = new JPanel();

     
    
    buttonsPanel.add(new JButton("Load XML"));
    buttonsPanel.add(new JButton("Save XML"));
    
    c.anchor = GridBagConstraints.NORTHWEST;
    c.fill = GridBagConstraints.BOTH; 
    c.gridx= 0;
    c.gridy=0;
    this.add(buttonsPanel,c);
    
    c.fill = GridBagConstraints.BOTH;
    c.gridx = 2;
    JList<String> comp = new JList<String>();
    
    DefaultListModel<String> model = new DefaultListModel<>();
    comp.setModel(model);
    model.addElement("HOLA");
    model.addElement("Como");
    model.addElement("Estas");
    this.add(comp,c);
    
    this.setLayout(layout);
  }

}
