package org.jiserte.desktopcalendar.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.jiserte.desktopcalendar.Priority;
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
    layout.rowHeights   = new int[]{100,100};
    layout.rowWeights   = new double[]{1,0};
    
    this.setLayout(layout);
    
    JPanel buttonsPanel = new JPanel();

     
    buttonsPanel.add(new JButton("New Data"));
    buttonsPanel.add(new JButton("Load XML"));
    buttonsPanel.add(new JButton("Save XML"));
    buttonsPanel.add(new JButton("Clear Data"));
    buttonsPanel.add(new JButton("Add date"));
    
    buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
    
    c.anchor = GridBagConstraints.NORTHWEST;
    c.fill = GridBagConstraints.BOTH; 
    c.gridx= 0;
    c.gridy=0;
    this.add(buttonsPanel,c);
    
    c.fill = GridBagConstraints.BOTH;
    c.gridx = 2;
    JList<WorkingDay> comp = new JList<WorkingDay>();
    
    DefaultListModel<WorkingDay> model = new DefaultListModel<>();
    comp.setModel(model);
    WorkingDay wd1 = new WorkingDay(Calendar.getInstance(), "Nada que hacer hoy", Priority.Low);
    WorkingDay wd2 = new WorkingDay(Calendar.getInstance(), "Nada que hacer hoy", Priority.Mid); 
    WorkingDay wd3 = new WorkingDay(Calendar.getInstance(), "Nada que hacer hoy", Priority.High); 

//    model.addElement("HOLA");
//    model.addElement("Como");
//    model.addElement("Estas");
    model.addElement(wd1);
    model.addElement(wd2);
    model.addElement(wd3);
    
    
    TitledBorder b1 = BorderFactory.createTitledBorder("Fechas");
    Border b2 = BorderFactory.createLineBorder(Color.black);
    Border b3 = BorderFactory.createEmptyBorder(5,5, 5, 5);
    
    comp.setBorder(BorderFactory.createCompoundBorder(b1, BorderFactory.createCompoundBorder(b2, b3)));
    
    comp.setCellRenderer(new WorkingDayCellRenderer());
    this.add(comp,c);
    
    c.gridy=1;
    c.gridx=2;
    this.add(new WorkingDayEditorPanel(),c);
    
    this.setLayout(layout);
  }

}
