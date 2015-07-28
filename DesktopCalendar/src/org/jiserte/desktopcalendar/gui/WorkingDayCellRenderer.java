package org.jiserte.desktopcalendar.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import org.jiserte.desktopcalendar.Priority;
import org.jiserte.desktopcalendar.PriorityColorMapFactory;
import org.jiserte.desktopcalendar.WorkingDay;

public class WorkingDayCellRenderer implements ListCellRenderer<WorkingDay> {

  @Override
  public Component getListCellRendererComponent(
      JList<? extends WorkingDay> list, WorkingDay value, int index,
      boolean isSelected, boolean cellHasFocus) {
    JPanel component = new JPanel();
    
    component.setOpaque(true);
    Map<Priority, Color> map = PriorityColorMapFactory.getDefaultMap();
    component.setBackground(map.get(value.getPriority()));
    
    if (isSelected) {
      component.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    }
    
    JLabel comp = new JLabel(new SimpleDateFormat("dd/MM/YYYY").format(value.getDate().getTime()) + " | " + value.getTaks());
    component.setLayout(new BorderLayout(10,10));
    comp.setHorizontalAlignment(JLabel.LEFT);
    comp.setMinimumSize(new Dimension(100,50));
    comp.setPreferredSize(new Dimension(100,50));
    comp.setSize(new Dimension(100,50));
    if (cellHasFocus) {
      comp.setFont(comp.getFont().deriveFont(1));
    }
    component.add(comp );
    return component;
  }

}
