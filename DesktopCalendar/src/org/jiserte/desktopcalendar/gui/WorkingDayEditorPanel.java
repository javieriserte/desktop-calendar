package org.jiserte.desktopcalendar.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdatepicker.JDateComponentFactory;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jiserte.desktopcalendar.Priority;
import org.jiserte.desktopcalendar.WorkingDay;

public class WorkingDayEditorPanel extends JPanel {

  public WDEObervable observable;
  public WorkingDay currentEditingWorkingDay;
  
  //////////////////////////////////////////////////////////////////////////////
  //
  /**
   * 
   */
  private static final long serialVersionUID = 3578154599013206591L;
  
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
    
    JDateComponentFactory f = new JDateComponentFactory();

    JDatePickerImpl datePickerFrom = (JDatePickerImpl)f.createJDatePicker(Calendar.getInstance().getTime());
    
    this.add(datePickerFrom,c);
    
    c.gridy=1;
    c.gridx=0;
    this.add(new JLabel("Tarea:"),c);
    c.gridx=1;
    c.gridwidth = 3;
    JTextField taskTextField = new JTextField("Escribir la tarea");
    
    this.add(taskTextField,c);
    
    c.gridy=2;
    c.gridx=0;
    c.gridwidth=1;
    this.add(new JLabel("Prioridad:"),c);
    c.gridx=1;
    JComboBox<Priority> priorityCmb = new JComboBox<Priority>();
    priorityCmb.setRenderer(new PriorityCellRenderer());
    DefaultComboBoxModel<Priority> aModel = new DefaultComboBoxModel<>(
        new Priority[]{Priority.High, Priority.Mid, Priority.Low, Priority.None,
            Priority.InThePast});
    
    priorityCmb.setModel(aModel);
    this.add(priorityCmb,c);
    c.insets = new Insets(2,2,2,2);
    
    c.gridx= 3;
    JButton setButton = new JButton("Setear!");
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
      WorkingDayEditorPanel.this.observable.setChanged();
      WorkingDayEditorPanel.this.observable.notifyObservers();
    }
  }
}
