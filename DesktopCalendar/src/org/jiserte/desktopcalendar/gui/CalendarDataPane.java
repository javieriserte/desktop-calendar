package org.jiserte.desktopcalendar.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.TimeZone;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdatepicker.DateModel;
import org.jdatepicker.JDateComponentFactory;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jiserte.desktopcalendar.data.Priority;
import org.jiserte.desktopcalendar.data.WorkingCalendar;
import org.jiserte.desktopcalendar.data.WorkingDay;
import org.jiserte.desktopcalendar.data.WorkingDayComparator;
import org.jiserte.desktopcalendar.xmlio.WorkingDayXmlParser;
import org.jiserte.desktopcalendar.xmlio.WorkingDayXmlWriter;

public class CalendarDataPane extends JPanel implements Observer{

  /////////////////////////////////////////////////////////////////////////////
  // Class variables
  private static final String LOAD_DATA = "LOAD_DATA";
  private static final String ADD_DATE = "ADD_DATE";
  private static final String SAVE_DATA = "SAVE_DATA";
  private static final String NEW_DATA = "NEW_DATA";
  /////////////////////////////////////////////////////////////////////////////

  //////////////////////////////////////////////////////////////////////////////
  // Components
  private JPanel buttonsPanel;
  private JButton loadDataButton;
  private JButton saveXMLButton;
  private JButton addDayButton;
  private JButton newDataButton;
  private JDatePickerImpl fromPicker;
  private JDatePickerImpl toPicker;
  //////////////////////////////////////////////////////////////////////////////
  
  //////////////////////////////////////////////////////////////////////////////
  // instance variables
  WorkingCalendar calendar;
  private JList<WorkingDay> workingDayList;
  private WorkingDayEditorPanel wdep;
  //////////////////////////////////////////////////////////////////////////////
  
  /**
   * 
   */
  private static final long serialVersionUID = 232850896722799109L;
  
  public CalendarDataPane()  {
    super();
    
    ////////////////////////////////////////////////////////////////////////////
    // Set layout manager
    GridBagLayout layout = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    layout.columnWeights = new double[]{0,0,1};
    layout.columnWidths = new int[]{50,5,100};
    layout.rowHeights   = new int[]{60,100,100};
    layout.rowWeights   = new double[]{0,1,0};
    this.setLayout(layout);
    ////////////////////////////////////////////////////////////////////////////
    
    ////////////////////////////////////////////////////////////////////////////
    // Add Command Buttons
    this.createButtonsGUI();
    
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

    JDatePickerImpl datePickerFrom = (JDatePickerImpl)f.createJDatePicker();
    JDatePickerImpl datePickerTo = (JDatePickerImpl)f.createJDatePicker();
    this.fromPicker = datePickerFrom;
    this.toPicker = datePickerTo;
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
    this.workingDayList.setOpaque(false);
    workingDayList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    workingDayList.addListSelectionListener(new WorkingDaySelectionListener());
    this.setStartingCalendar();
    
    TitledBorder b1 = BorderFactory.createTitledBorder("Fechas");
    Border b2 = BorderFactory.createEmptyBorder(5,5, 5, 5);
    Border b3 = BorderFactory.createLineBorder(Color.gray);
    
    workingDayList.setBorder(BorderFactory.createCompoundBorder(b1, 
        BorderFactory.createCompoundBorder(b2, b3)));
    
    workingDayList.setCellRenderer(new WorkingDayCellRenderer());
    this.add(workingDayList,c);
    
    c.gridy=2;
    c.gridx=2;
    wdep = new WorkingDayEditorPanel();
    wdep.setEditingCurrentDay(this.workingDayList.getModel().getElementAt(0));
    wdep.observable.addObserver(this);
    this.add(wdep,c);
    
    this.setLayout(layout);
  }

  private void createButtonsGUI() {
    ////////////////////////////////////////////////////////////////////////////
    // Create action listener for buttons
    ActionListener buttonsListener = new ButtonsActionListener();
    ////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////
    // Create panel to hold buttons and define layout
    this.buttonsPanel = new JPanel();
    GridBagLayout layout = new GridBagLayout();
    layout.columnWeights = new double[]{1};
    layout.columnWidths = new int[]{150};
    layout.rowHeights = new int[]{20,20,20,20,0};
    layout.rowWeights = new double[]{0,0,0,0,1};
    GridBagConstraints c = new GridBagConstraints();
    c.insets = new Insets(5, 5, 5, 5);
    buttonsPanel.setLayout(layout);
    ////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////
    // Save buttons
    this.saveXMLButton = new JButton("Guardar XML");
    this.saveXMLButton.addActionListener(buttonsListener);
    this.saveXMLButton.setActionCommand(SAVE_DATA);
    // Load Button
    this.loadDataButton = new JButton("Cargar XML");
    this.loadDataButton.addActionListener(buttonsListener);
    this.loadDataButton.setActionCommand(LOAD_DATA);
    // New data button
    this.newDataButton = new JButton("Nuevo calendario");
    this.newDataButton.addActionListener(buttonsListener);
    this.newDataButton.setActionCommand(NEW_DATA);
    // Add a day button
    this.addDayButton = new JButton("Agregar día");
    this.addDayButton.addActionListener(buttonsListener);
    this.addDayButton.setActionCommand(ADD_DATE);
    ////////////////////////////////////////////////////////////////////////////
    
    ////////////////////////////////////////////////////////////////////////////
    // Add buttons to panel
    c.fill = GridBagConstraints.BOTH;
    c.gridx = 0;
    c.gridy = 0;
    buttonsPanel.add(this.newDataButton,c);
    c.gridy = 1;
    buttonsPanel.add(this.loadDataButton,c);
    c.gridy = 2;
    buttonsPanel.add(this.saveXMLButton,c);
    c.gridy = 3;
    buttonsPanel.add(this.addDayButton,c);
    ////////////////////////////////////////////////////////////////////////////
    
  }

  private void setStartingCalendar() {
    
    WorkingCalendar cal = new WorkingCalendar();
    
    Calendar day1 = getProperCalendar(0);
    
    Calendar day2 = getProperCalendar(1);

    Calendar day3 = getProperCalendar(2);
    
    WorkingDay wd1 = new WorkingDay(day1, "Nada que hacer hoy", Priority.Low);
    WorkingDay wd2 = new WorkingDay(day2, "Nada que hacer hoy", Priority.Mid); 
    WorkingDay wd3 = new WorkingDay(day3, "Nada que hacer hoy", Priority.High); 

    cal.addWorkingDay(wd1);
    cal.addWorkingDay(wd2);
    cal.addWorkingDay(wd3);
    
    Calendar timeFrom = getProperCalendar(0);

    Calendar timeTo = getProperCalendar(0);
    
    cal.setFrom(timeFrom);
    cal.setTo(timeTo);
    
    CalendarDataPane.this.setWorkingCalendar(cal);
    return;
  }

  private Calendar getProperCalendar(int dayOffSet) {
    Calendar day = Calendar.getInstance(TimeZone.getTimeZone("GMT0"));
    day.set(Calendar.HOUR_OF_DAY, 24);
    day.set(Calendar.MINUTE, 0);
    day.set(Calendar.SECOND, 0);
    day.set(Calendar.MILLISECOND, 0);
    day.add(Calendar.DAY_OF_MONTH, dayOffSet);
    return day;
  }
  
  private void setWorkingCalendar(WorkingCalendar calendar) {
    
    CalendarDataPane.this.calendar = calendar;
    List<WorkingDay> values = new ArrayList<>(calendar.getWorkingDays().values());
    this.setListModel(values);
    this.setFromCalendar(calendar.getFrom());
    this.setToCalendar(calendar.getTo());
  }
  
  private void setFromCalendar(Calendar from) {
    this.fromPicker.getModel().setDay(from.get(Calendar.DAY_OF_MONTH));
    this.fromPicker.getModel().setMonth(from.get(Calendar.MONTH));
    this.fromPicker.getModel().setYear(from.get(Calendar.YEAR));
    this.fromPicker.updateUI();
  }

  private void setToCalendar(Calendar to) {
    this.toPicker.getModel().setDay(to.get(Calendar.DAY_OF_MONTH));
    this.toPicker.getModel().setMonth(to.get(Calendar.MONTH));
    this.toPicker.getModel().setYear(to.get(Calendar.YEAR));
    this.toPicker.updateUI();
  }

  private void setListModel(List<WorkingDay> days) {
    DefaultListModel<WorkingDay> model = new DefaultListModel<>();
    Collections.sort(days, new WorkingDayComparator());
    for (WorkingDay el : days) {
      model.addElement(el);
    }
    CalendarDataPane.this.workingDayList.setModel(model);
  }
  
  @Override
  public void update(Observable o, Object arg) {
    ////////////////////////////////////////////////////////////////////////////
    // Update the list content 
    List<WorkingDay> currentList = new ArrayList<>();
    for (int i = 0; i< this.workingDayList.getModel().getSize(); i++) {
      currentList.add(this.workingDayList.getModel().getElementAt(i));
    }
    Collections.sort(currentList, new WorkingDayComparator());
    DefaultListModel<WorkingDay> model = new DefaultListModel<>();
    for (WorkingDay wd : currentList) {
      model.addElement(wd);
    }
    this.workingDayList.setModel(model);
    ////////////////////////////////////////////////////////////////////////////
 
    ////////////////////////////////////////////////////////////////////////////
    // Update UI view
    this.updateUI();
    ////////////////////////////////////////////////////////////////////////////
  }
  
  //////////////////////////////////////////////////////////////////////////////
  // Auxiliary classes
  public class ButtonsActionListener implements ActionListener {
    @Override public void actionPerformed(ActionEvent e) {
      String command = e.getActionCommand();
      switch (command) {
      case LOAD_DATA:
        WorkingDayXmlParser parser = new WorkingDayXmlParser();
        JFileChooser fc = new JFileChooser(System.getProperty("user.home"));
        fc.setVisible(true);
        fc.setMultiSelectionEnabled(false);
        fc.setFileFilter(new XmlFileFilter());
        fc.showOpenDialog(CalendarDataPane.this);
        File file = fc.getSelectedFile();
        if (file == null) {
          return;
        } else {
          WorkingCalendar cal = parser.read(file);
          CalendarDataPane.this.setWorkingCalendar(cal);
        }
        break;
      case ADD_DATE:
        Calendar newCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT0.00"));
        newCalendar.set(Calendar.HOUR_OF_DAY, 24);
        newCalendar.set(Calendar.MINUTE, 0);
        newCalendar.set(Calendar.SECOND, 0);
        newCalendar.set(Calendar.MILLISECOND, 0);
        WorkingDay newDay = new WorkingDay(newCalendar, "Ninguna Tarea Asignada", Priority.None);
        ((DefaultListModel<WorkingDay>) CalendarDataPane.this.workingDayList.getModel()).addElement(newDay);
        CalendarDataPane.this.calendar.addWorkingDay(newDay);
        break;
        
      case SAVE_DATA:
        WorkingDayXmlWriter calendarWriter = new WorkingDayXmlWriter();
        JFileChooser fcs = new JFileChooser(System.getProperty("user.home"));
        fcs.setVisible(true);
        fcs.setMultiSelectionEnabled(false);
        fcs.setFileFilter(new XmlFileFilter());
        fcs.showSaveDialog(CalendarDataPane.this);
        File outfile = fcs.getSelectedFile();
        if (outfile != null) {
          calendarWriter.write(calendar, outfile);
        }
        break;
      case NEW_DATA:
        CalendarDataPane.this.setStartingCalendar();
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
  public class WorkingDaySelectionListener implements ListSelectionListener {

    @SuppressWarnings("unchecked")
    @Override
    public void valueChanged(ListSelectionEvent e) {
      JList<WorkingDay> source = (JList<WorkingDay>) e.getSource();
      WorkingDay selectedWorkingDay = source.getSelectedValue();
      if (selectedWorkingDay != null) {
        CalendarDataPane.this.wdep.setEditingCurrentDay(selectedWorkingDay);
      }
    }
    
  }
  //////////////////////////////////////////////////////////////////////////////

}
