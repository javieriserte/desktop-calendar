package org.jiserte.desktopcalendar.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.jiserte.desktopcalendar.Priority;
import org.jiserte.desktopcalendar.WorkingCalendar;
import org.jiserte.desktopcalendar.WorkingDay;
import org.jiserte.desktopcalendar.WorkingDayComparator;
import org.jiserte.desktopcalendar.WorkingDayXmlParser;

public class CalendarDataPane extends JPanel {

  private static final String LOAD_DATA = "LOAD_DATA";

  //////////////////////////////////////////////////////////////////////////////
  // Components
  private JPanel buttonsPanel;
  private JButton loadDataButton;
  private JButton saveXMLButton;
  private JButton addDayButton;
  private JButton clearDataButton;
  private JButton newDataButton;
  //////////////////////////////////////////////////////////////////////////////
  
  //////////////////////////////////////////////////////////////////////////////
  // instance variables
  WorkingCalendar calendar;

  private JList<WorkingDay> workingDayList;
  //////////////////////////////////////////////////////////////////////////////
  
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
    
    ActionListener buttonsListener = new ButtonsActionListener();
    this.buttonsPanel = new JPanel();
    this.saveXMLButton = new JButton("Save XML");
    this.loadDataButton = new JButton("Load XML");
    this.loadDataButton.addActionListener(buttonsListener);
    this.loadDataButton.setActionCommand(LOAD_DATA);
    this.newDataButton = new JButton("New Data");
    this.clearDataButton = new JButton("Clear Data");
    this.addDayButton = new JButton("Add date");
    
    
    buttonsPanel.add(this.newDataButton);
    buttonsPanel.add(this.loadDataButton);
    buttonsPanel.add(this.saveXMLButton);
    buttonsPanel.add(this.clearDataButton);
    buttonsPanel.add(this.addDayButton);
    
    buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
    
    c.anchor = GridBagConstraints.NORTHWEST;
    c.fill = GridBagConstraints.BOTH; 
    c.gridx= 0;
    c.gridy=0;
    this.add(buttonsPanel,c);
    
    c.fill = GridBagConstraints.BOTH;
    c.gridx = 2;
    this.workingDayList = new JList<WorkingDay>();
    
    
    DefaultListModel<WorkingDay> model = getStartingListModel();

    workingDayList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    workingDayList.setModel(model);
    
    
    TitledBorder b1 = BorderFactory.createTitledBorder("Fechas");
    Border b2 = BorderFactory.createLineBorder(Color.black);
    Border b3 = BorderFactory.createEmptyBorder(5,5, 5, 5);
    
    workingDayList.setBorder(BorderFactory.createCompoundBorder(b1, BorderFactory.createCompoundBorder(b2, b3)));
    
    workingDayList.setCellRenderer(new WorkingDayCellRenderer());
    this.add(workingDayList,c);
    
    c.gridy=1;
    c.gridx=2;
    this.add(new WorkingDayEditorPanel(),c);
    
    this.setLayout(layout);
  }

  private DefaultListModel<WorkingDay> getStartingListModel() {
    
    this.calendar = new WorkingCalendar();
    WorkingDay wd1 = new WorkingDay(Calendar.getInstance(), "Nada que hacer hoy", Priority.Low);
    WorkingDay wd2 = new WorkingDay(Calendar.getInstance(), "Nada que hacer hoy", Priority.Mid); 
    WorkingDay wd3 = new WorkingDay(Calendar.getInstance(), "Nada que hacer hoy", Priority.High); 
    
    this.calendar.addWorkingDay(wd1);
    this.calendar.addWorkingDay(wd2);
    this.calendar.addWorkingDay(wd3);
    
    DefaultListModel<WorkingDay> model = new DefaultListModel<>();
    for (WorkingDay wd : this.calendar.getWorkingDays().values()) {
        System.out.println(wd);
       model.addElement(wd);
    }
    return model;
  }
  
  //////////////////////////////////////////////////////////////////////////////
  // Auxiliary classes
  public class ButtonsActionListener implements ActionListener {
    @Override public void actionPerformed(ActionEvent e) {
      String command = e.getActionCommand();
      switch (command) {
      case LOAD_DATA:
        WorkingDayXmlParser parser = new WorkingDayXmlParser();
        JFileChooser fc = new JFileChooser(".");
        fc.setVisible(true);
        fc.setMultiSelectionEnabled(false);
        fc.showOpenDialog(CalendarDataPane.this);
        File file = fc.getSelectedFile();
        if (file == null) {
          return;
        } else {
          WorkingCalendar cal = parser.read(file);
          CalendarDataPane.this.calendar = cal;
          DefaultListModel<WorkingDay> model = new DefaultListModel<>();
          List<WorkingDay> values = new ArrayList<>(cal.getWorkingDays().values());
          Collections.sort(values, new WorkingDayComparator());
          for (WorkingDay el : values) {
            model.addElement(el);
          }
          CalendarDataPane.this.workingDayList.setModel(model);
        }
        break;
      }
    }
  }
  //////////////////////////////////////////////////////////////////////////////

}
