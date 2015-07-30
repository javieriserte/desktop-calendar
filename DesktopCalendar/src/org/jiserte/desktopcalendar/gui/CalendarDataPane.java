package org.jiserte.desktopcalendar.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.jdatepicker.DateModel;
import org.jdatepicker.JDateComponentFactory;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jiserte.desktopcalendar.Priority;
import org.jiserte.desktopcalendar.WorkingCalendar;
import org.jiserte.desktopcalendar.WorkingDay;
import org.jiserte.desktopcalendar.WorkingDayComparator;
import org.jiserte.desktopcalendar.WorkingDayXmlParser;

public class CalendarDataPane extends JPanel implements Observer{

  /////////////////////////////////////////////////////////////////////////////
  // Class variables
  private static final String LOAD_DATA = "LOAD_DATA";
//  private static final String ADD_DATE = "ADD_DATE";
//  private static final String SAVE_DATA = "SAVE_DATA";
//  private static final String CLEAR_DATA = "CLEAR_DATA";
//  private static final String NEW_DATA = "NEW_DATA";
//  private static final String DATE_SELECTED = "Date selected";
  /////////////////////////////////////////////////////////////////////////////

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

  public CalendarDataPane()  {
    super();
    
    GridBagLayout layout = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    layout.columnWeights = new double[]{0,0,1};
    layout.columnWidths = new int[]{50,5,100};
    layout.rowHeights   = new int[]{60,100,100};
    layout.rowWeights   = new double[]{0,1,0};
    
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
    c.gridx=0;
    c.gridy=0;
    c.gridheight=3;
    this.add(buttonsPanel,c);
    
    c.gridheight=1;
    c.gridx=2;
    JPanel dateIntervalPane = new JPanel();
    dateIntervalPane.setBorder(BorderFactory.createTitledBorder("Intervalo De Tiempo"));
    JDateComponentFactory f = new JDateComponentFactory();

    JDatePickerImpl datePickerFrom = (JDatePickerImpl)f.createJDatePicker(Calendar.getInstance().getTime());
    JDatePickerImpl datePickerTo = (JDatePickerImpl)f.createJDatePicker(Calendar.getInstance().getTime());
    datePickerFrom.addActionListener(new DateToActionListener(CalendarDataPane.DateToActionListener.DATE_INTERVAL_FROM));
    datePickerTo.addActionListener(new DateToActionListener(CalendarDataPane.DateToActionListener.DATE_INTERVAL_TO));
    dateIntervalPane.add(new JLabel("Desde:"));
    dateIntervalPane.add(datePickerFrom);
    dateIntervalPane.add(new JLabel("Hasta:"));
    dateIntervalPane.add(datePickerTo);
    
    this.add(dateIntervalPane,c);
    
    
    
    c.fill = GridBagConstraints.BOTH;
    c.gridx = 2;
    c.gridy=1;
    this.workingDayList = new JList<WorkingDay>();
    workingDayList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    setStartingListModel();
    
    TitledBorder b1 = BorderFactory.createTitledBorder("Fechas");
    Border b3 = BorderFactory.createEmptyBorder(5,5, 5, 5);
    
    workingDayList.setBorder(BorderFactory.createCompoundBorder(b1,  b3));
    
    workingDayList.setCellRenderer(new WorkingDayCellRenderer());
    this.add(workingDayList,c);
    
    c.gridy=2;
    c.gridx=2;
    WorkingDayEditorPanel wdep = new WorkingDayEditorPanel();
    wdep.observable.addObserver(this);
    this.add(wdep,c);
    
    this.setLayout(layout);
  }

  private void setStartingListModel() {
    
    WorkingCalendar cal = new WorkingCalendar();
    WorkingDay wd1 = new WorkingDay(Calendar.getInstance(), "Nada que hacer hoy", Priority.Low);
    WorkingDay wd2 = new WorkingDay(Calendar.getInstance(), "Nada que hacer hoy", Priority.Mid); 
    WorkingDay wd3 = new WorkingDay(Calendar.getInstance(), "Nada que hacer hoy", Priority.High); 

    cal.addWorkingDay(wd1);
    cal.addWorkingDay(wd2);
    cal.addWorkingDay(wd3);
    
    CalendarDataPane.this.setListModel(cal);
    return;
  }
  
  private void setListModel(WorkingCalendar calendar) {
    CalendarDataPane.this.calendar = calendar;
    DefaultListModel<WorkingDay> model = new DefaultListModel<>();
    List<WorkingDay> values = new ArrayList<>(calendar.getWorkingDays().values());
    Collections.sort(values, new WorkingDayComparator());
    for (WorkingDay el : values) {
      model.addElement(el);
    }
    CalendarDataPane.this.workingDayList.setModel(model);
  }
  
  @Override
  public void update(Observable o, Object arg) {
    System.out.println("Algo cambió che!!");
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
          CalendarDataPane.this.setListModel(cal);
        }
        break;
      }
    }
  }
  public class DateToActionListener implements ActionListener {
    private static final int DATE_INTERVAL_FROM = 1;
    private static final int DATE_INTERVAL_TO = 2;
//    private static final int SELECTED_WORKING_DAY = 3;
    private int calendarMapper;
    public DateToActionListener(int calendarMapper) {
      this.calendarMapper = calendarMapper;
    }
    @Override public void actionPerformed(ActionEvent event) {
      JDatePanelImpl source = (JDatePanelImpl)event.getSource();
      DateModel<?> model = source.getModel();
      Calendar cal = Calendar.getInstance();
      cal.setTimeInMillis(0);
      cal.set(Calendar.YEAR, model.getYear());
      cal.set(Calendar.MONTH, model.getMonth());
      cal.set(Calendar.DAY_OF_MONTH, model.getDay());
      this.setCalendar(cal);
    }
    private void setCalendar(Calendar calendar){
      switch (this.calendarMapper) {
      case DATE_INTERVAL_FROM:
        CalendarDataPane.this.calendar.setFrom(calendar);
        break;
      case DATE_INTERVAL_TO:
        CalendarDataPane.this.calendar.setTo(calendar);
        break;
      default:
        break;
      }
    }
  }
  //////////////////////////////////////////////////////////////////////////////

}
