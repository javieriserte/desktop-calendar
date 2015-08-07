package org.jiserte.desktopcalendar.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdatepicker.DateModel;
import org.jdatepicker.JDateComponentFactory;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jiserte.desktopcalendar.data.Priority;
import org.jiserte.desktopcalendar.data.WorkingDay;

public class WorkingDayEditorPanel extends JPanel {

  public WDEObervable observable;
  public WorkingDay currentEditingWorkingDay;
  
  //////////////////////////////////////////////////////////////////////////////
  // Components
  private JDatePickerImpl datePickerFrom;
  private JTextField taskTextField;
  private JComboBox<Priority> priorityCmb;
  private JButton setButton;
  //////////////////////////////////////////////////////////////////////////////
  /**
   * 
   */
  private static final long serialVersionUID = 3578154599013206591L;
  private JDateComponentFactory dateComponentFactory;

  
  public WorkingDayEditorPanel() {
    super();
    this.observable = new WDEObervable();
    this.createGUI();
  }
  
  public void addObserver(Observer observer) {
    this.observable.addObserver(observer);
  }
  
  public void setEditingCurrentDay(WorkingDay workingDay) {
    this.currentEditingWorkingDay = workingDay;
    ////////////////////////////////////////////////////////////////////////////
    // Create a new date picker with the date corresponding to the working day
    @SuppressWarnings("unchecked")
    DateModel<Date> model = (DateModel<Date>) this.datePickerFrom.getModel();
    model.setValue(workingDay.getDate().getTime());
    ////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////
    // Modifiy the text of the taks
    this.taskTextField.setText(workingDay.getTask());
    ////////////////////////////////////////////////////////////////////////////
    
    ////////////////////////////////////////////////////////////////////////////
    // Set the correct value in the priority combo box 
    this.priorityCmb.setSelectedItem(workingDay.getPriority());
    ////////////////////////////////////////////////////////////////////////////
    
    this.updateUI();
  }

  private void createGUI() {
    GridBagLayout layout = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    this.setLayout(layout);
    this.setBorder(BorderFactory.createTitledBorder("Editar fecha"));
    
    layout.columnWeights = new double[]{0,0,1,0};
    layout.columnWidths = new int[]{0,0,0,0};
    layout.rowWeights  = new double[]{0,0,0,1};
    layout.rowHeights  = new int[]{20,20,20,20};
    
    c.insets = new Insets(2,2,2,2);
    
    c.fill = GridBagConstraints.BOTH;
    c.gridx=0;
    c.gridy=0;
    this.add(new JLabel("Fecha:"),c);
    c.gridx=1;
    
    this.dateComponentFactory = new JDateComponentFactory();

    this.datePickerFrom = (JDatePickerImpl)dateComponentFactory.createJDatePicker(Calendar.getInstance().getTime());
    
    this.add(datePickerFrom,c);
    
    c.gridy=1;
    c.gridx=0;
    this.add(new JLabel("Tarea:"),c);
    c.gridx=1;
    c.gridwidth = 3;
    
    this.taskTextField = new JTextField("Escribir la tarea");
    
    this.add(taskTextField,c);
    
    c.gridy=2;
    c.gridx=0;
    c.gridwidth=1;
    this.add(new JLabel("Prioridad:"),c);
    c.gridx=1;
    priorityCmb = new JComboBox<Priority>();
    priorityCmb.setRenderer(new PriorityCellRenderer());

    DefaultComboBoxModel<Priority> aModel = new DefaultComboBoxModel<Priority>(
        new Priority[]{Priority.High, Priority.Mid, Priority.Low, Priority.None,
            Priority.InThePast});
    
    priorityCmb.setModel(aModel);
    this.add(priorityCmb,c);
    c.insets = new Insets(2,2,2,2);
    
    c.gridx= 3;
    setButton = new JButton("Setear!");
    setButton.addActionListener(new setButtonActionListener());
    this.add(setButton,c );
    
  }
  
  public class WDEObervable extends Observable {
    public void setChanged() {
      super.setChanged();
    }
  }
  
  public class setButtonActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

      //////////////////////////////////////////////////////////////////////////
      // Set the new day
      DateModel<?> wdModel = WorkingDayEditorPanel.this.datePickerFrom.getModel();
      
      Date d = (Date) WorkingDayEditorPanel.this.datePickerFrom.getModel().getValue();
      int day = wdModel.getDay();
      int month = wdModel.getMonth();
      int year = wdModel.getYear();
      Calendar newDay = Calendar.getInstance();
      newDay.setTimeInMillis(0);
      newDay.set(Calendar.DAY_OF_MONTH, day);
      newDay.set(Calendar.MONTH, month);
      newDay.set(Calendar.YEAR, year);
      WorkingDayEditorPanel.this.currentEditingWorkingDay.setDate(newDay);
      System.out.println("Day:" + d);
      //////////////////////////////////////////////////////////////////////////
      
      //////////////////////////////////////////////////////////////////////////
      // Set new Task
      String task = WorkingDayEditorPanel.this.taskTextField.getText();
      WorkingDayEditorPanel.this.currentEditingWorkingDay.setTask(task);
      //////////////////////////////////////////////////////////////////////////

      //////////////////////////////////////////////////////////////////////////
      // Set new Priority
      Priority priority = (Priority) WorkingDayEditorPanel.this.priorityCmb.getSelectedItem();
      WorkingDayEditorPanel.this.currentEditingWorkingDay.setPriority(priority);
      //////////////////////////////////////////////////////////////////////////

      WorkingDayEditorPanel.this.observable.setChanged();
      WorkingDayEditorPanel.this.observable.notifyObservers();
    }
  }
}
