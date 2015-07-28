package org.jiserte.desktopcalendar.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jiserte.desktopcalendar.Priority;

public class WorkingDayEditorPanel extends JPanel {

  /**
   * 
   */
  private static final long serialVersionUID = 3578154599013206591L;

  public WorkingDayEditorPanel() {
    super();
    this.createGUI();
  }

  private void createGUI() {
    GridBagLayout layout = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    this.setLayout(layout);
    
    layout.columnWeights = new double[]{0,0,0,0,0,0,1};
    layout.columnWidths = new int[]{50,50,50,50,50,50,50};
    layout.rowWeights  = new double[]{0,0,0,1};
    layout.rowHeights  = new int[]{20,20,20,20};
    
    c.insets = new Insets(2,2,2,2);
    
    c.fill = GridBagConstraints.BOTH;
    c.gridx=0;
    c.gridy=0;
    this.add(new JLabel("Día:"),c);
    c.gridx=1;
    this.add(new JTextField(),c);
    c.gridx=2;
    this.add(new JLabel("Mes:"),c);
    c.gridx=3;
    this.add(new JTextField(),c);
    c.gridx=4;
    this.add(new JLabel("Año:"),c);
    c.gridx=5;
    this.add(new JTextField(),c);
    
    c.gridy=1;
    c.gridx=0;
    this.add(new JLabel("Tarea:"),c);
    c.gridx=1;
    c.gridwidth = 6;
    JTextField taskTextField = new JTextField("Escribir la tarea");
    
    this.add(taskTextField,c);
    
    c.gridy=2;
    c.gridx=0;
    c.gridwidth=1;
    this.add(new JLabel("Prioridad"),c);
    c.gridx=1;
    this.add(new JComboBox<Priority>(),c);
    c.insets = new Insets(2,2,2,2);
    
    
  }

}
