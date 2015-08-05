package org.jiserte.desktopcalendar.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.LayoutManager;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import org.jiserte.desktopcalendar.Priority;
import org.jiserte.desktopcalendar.PriorityColorMapFactory;

public class PriorityCellRenderer extends JPanel implements
    ListCellRenderer<Priority> {

  //////////////////////////////////////////////////////////////////////////////
  // Instance variables
  Map<Priority,Color> map;
  //////////////////////////////////////////////////////////////////////////////
  
  /**
   * 
   */
  private static final long serialVersionUID = -4018356018135973982L;

  
  
  public PriorityCellRenderer() {
    super();
    this.map = PriorityColorMapFactory.getDefaultMap();
  }

  @Override
  public Component getListCellRendererComponent(JList<? extends Priority> list,
      Priority value, int index, boolean isSelected, boolean cellHasFocus) {
    
    JPanel resultPanel = new JPanel();
   
    resultPanel.setOpaque(true);
    resultPanel.setBackground(this.map.get(value));
    JLabel textLbl = new JLabel(value.name());
    if (isSelected) {
    textLbl.setFont(textLbl.getFont().deriveFont(Font.BOLD));
    }
    resultPanel.add(textLbl);
    textLbl.setOpaque(false);
    resultPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
    return resultPanel;
  }

}
