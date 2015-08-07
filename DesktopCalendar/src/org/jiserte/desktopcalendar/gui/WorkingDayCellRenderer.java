package org.jiserte.desktopcalendar.gui;

import java.awt.Color;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;

import org.jiserte.desktopcalendar.data.Priority;
import org.jiserte.desktopcalendar.data.PriorityColorMapFactory;
import org.jiserte.desktopcalendar.data.WorkingDay;

public class WorkingDayCellRenderer implements ListCellRenderer<WorkingDay> {

  @Override
  public Component getListCellRendererComponent(
      JList<? extends WorkingDay> list, WorkingDay value, int index,
      boolean isSelected, boolean cellHasFocus) {
    JPanel cellPanel = new JPanel();

    cellPanel.setOpaque(true);
    Map<Priority, Color> map = PriorityColorMapFactory.getDefaultMap();

    Border b1 = BorderFactory.createEmptyBorder(4, 4, 4, 4);
    Border b2 = isSelected ? 
        BorderFactory.createLineBorder(Color.lightGray, 1)
        : BorderFactory.createEmptyBorder(1, 1, 1, 1);
    Border b3 = cellHasFocus ? 
        BorderFactory.createDashedBorder(new Color(180, 190, 240), 1f, 6f, 6f, 
            false) : BorderFactory.createEmptyBorder(1, 1, 1, 1);

    cellPanel.setBorder(BorderFactory.createCompoundBorder(b2,
        BorderFactory.createCompoundBorder(b1, b3)));
    JLabel textLabel = new JLabel("  "
        + new SimpleDateFormat("dd/MM/YYYY").format(value.getDate().getTime())
        + " | " + value.getTask());

    BoxLayout mgr = new BoxLayout(cellPanel, BoxLayout.X_AXIS);
    cellPanel.setLayout(mgr);

    textLabel.setHorizontalAlignment(JLabel.LEFT);

    JLabel colorLabel = new JLabel("      ");
    colorLabel.setOpaque(true);
    colorLabel.setBackground(map.get(value.getPriority()));
    colorLabel.setBorder(BorderFactory.createLineBorder(Color.gray));

    cellPanel.add(colorLabel);
    cellPanel.add(textLabel);
    return cellPanel;
  }

}
