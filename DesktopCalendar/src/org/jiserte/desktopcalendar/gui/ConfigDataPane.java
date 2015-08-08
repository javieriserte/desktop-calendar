package org.jiserte.desktopcalendar.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ConfigDataPane extends JPanel {

	private static final long serialVersionUID = -4169467490623059644L;
  private JTextField imageWidthTextBox;
  private JTextField imageHeightTextBox;
  private JTextField imageTopTextBox;
  private JTextField imageLeftTextBox;
  private JTextField imageRightTextBox;
  private JTextField imageBottomTextBox;
  private JTextField imageOverlayTextBox;
  private JTextField srcImageTxt;
  private JTextField dstImageTxt;


	public ConfigDataPane() {
		super();
		this.createGUI();
	}


  private void createGUI() {
    
    ////////////////////////////////////////////////////////////////////////////
    // Set layout
    GridBagLayout layout = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    this.setLayout(layout);
    layout.rowHeights = new int[] {60,60,30,0};
    layout.rowWeights = new double[]{0,0,1,0};
    layout.columnWeights = new double[]{1};
    layout.columnWidths = new int[]{300};
    c.insets = new Insets(5, 5, 5, 5);
    ////////////////////////////////////////////////////////////////////////////
    
    ////////////////////////////////////////////////////////////////////////////
    // Add a panel for graphic options
    JPanel graphicOptPanel = this.createGraphicOptPanel();
    JPanel imageFilesPanel = this.createImageFilesPanel();
    JPanel actionButtonsPanel = this.createActionButtonsPanel();
    c.fill=GridBagConstraints.BOTH;
    c.gridx = 0; c.gridy = 0; this.add(graphicOptPanel,c);
    c.gridx = 0; c.gridy = 1; this.add(imageFilesPanel,c);
    c.gridx = 0; c.gridy = 3; this.add(actionButtonsPanel,c);
    ////////////////////////////////////////////////////////////////////////////
    
  }


  private JPanel createActionButtonsPanel() {
    
    JPanel jPanel = new JPanel();
    ////////////////////////////////////////////////////////////////////////////
    // Set layout
    GridBagLayout layout = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    jPanel.setLayout(layout);
    layout.rowHeights = new int[] {30};
    layout.rowWeights = new double[]{0};
    layout.columnWeights = new double[]{1,0,0};
    layout.columnWidths = new int[]{0,60,60};
    c.insets = new Insets(2, 2, 2, 2);
    ////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////
    // Define components
    JButton loadButton = new JButton("Cargar");
    JButton saveButton = new JButton("Guardar");
    ////////////////////////////////////////////////////////////////////////////
    
    ////////////////////////////////////////////////////////////////////////////
    // Add components
    c.fill = GridBagConstraints.BOTH;
    c.gridx = 1; c.gridy = 0; jPanel.add(loadButton, c);
    c.gridx = 2; c.gridy = 0; jPanel.add(saveButton, c);
    ////////////////////////////////////////////////////////////////////////////
    
    return jPanel;
  }


  private JPanel createImageFilesPanel() {
    JPanel jPanel = new JPanel();
    ////////////////////////////////////////////////////////////////////////////
    // Set layout
    jPanel.setBorder(BorderFactory.createTitledBorder("Archivos de imágenes"));
    GridBagLayout layout = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    jPanel.setLayout(layout);
    layout.rowHeights = new int[] {30,30};
    layout.rowWeights = new double[]{0,0};
    layout.columnWeights = new double[]{0,1,0};
    layout.columnWidths = new int[]{30,100,30};
    c.insets = new Insets(2, 2, 2, 2);
    ////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////
    // Define components
    JLabel srcImageLbl = new JLabel("Imagen de origen:");
    this.srcImageTxt = new JTextField();
    JButton srcImageButton = new JButton("Seleccionar");
    JLabel dstImageLbl = new JLabel("Imagen de salida:");
    this.dstImageTxt = new JTextField();
    JButton dstImageButton = new JButton("Seleccionar");
    ////////////////////////////////////////////////////////////////////////////
    
    ////////////////////////////////////////////////////////////////////////////
    // Add components
    c.fill = GridBagConstraints.BOTH;
    c.gridx = 0; c.gridy = 0; jPanel.add(srcImageLbl, c);
    c.gridx = 1; c.gridy = 0; jPanel.add(this.srcImageTxt, c);
    c.gridx = 2; c.gridy = 0; jPanel.add(srcImageButton, c);
    c.gridx = 0; c.gridy = 1; jPanel.add(dstImageLbl, c);
    c.gridx = 1; c.gridy = 1; jPanel.add(this.dstImageTxt, c);
    c.gridx = 2; c.gridy = 1; jPanel.add(dstImageButton, c);
    ////////////////////////////////////////////////////////////////////////////
    
    return jPanel;
  }


  private JPanel createGraphicOptPanel() {
    JPanel jPanel = new JPanel();
    jPanel.setBorder(BorderFactory.createTitledBorder("Opciones Gráficas"));
    
    ////////////////////////////////////////////////////////////////////////////
    // Set layout
    GridBagLayout layout = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    jPanel.setLayout(layout);
    layout.rowHeights = new int[] {30,30};
    layout.rowWeights = new double[]{0,0};
    layout.columnWeights = new double[]{1,1,1,1,1,1,1,1};
    layout.columnWidths = new int[]{40,40,40,40,40,40,40,40};
    c.insets = new Insets(2, 2, 2, 2);
    ////////////////////////////////////////////////////////////////////////////
    
    ////////////////////////////////////////////////////////////////////////////
    // Define components
    JLabel widthLbl = new JLabel("Ancho:");
    this.imageWidthTextBox = new JTextField("1920",5);
    JLabel heightLbl = new JLabel("Alto:");
    this.imageHeightTextBox = new JTextField("1080",5);
    JLabel overlayLbl = new JLabel("Transparencia:");
    this.imageOverlayTextBox = new JTextField("0.5",5);
    JLabel topLbl = new JLabel("Desde arriba:");
    this.imageTopTextBox = new JTextField("10",10);
    JLabel leftLbl = new JLabel("Desde la izquierda:");
    this.imageLeftTextBox = new JTextField("10",10);
    JLabel rightLbl = new JLabel("Desde la derecha:");
    this.imageRightTextBox = new JTextField("10",10);
    JLabel bottomLbl = new JLabel("Desde abajo:");
    this.imageBottomTextBox = new JTextField("50",50);
    ////////////////////////////////////////////////////////////////////////////
    
    ////////////////////////////////////////////////////////////////////////////
    // Add components
    c.fill = GridBagConstraints.BOTH;
    c.gridx = 0; c.gridy = 0; jPanel.add(widthLbl, c);
    c.gridx = 1; c.gridy = 0; jPanel.add(this.imageWidthTextBox, c);
    c.gridx = 2; c.gridy = 0; jPanel.add(heightLbl, c);
    c.gridx = 3; c.gridy = 0; jPanel.add(this.imageHeightTextBox, c);
    c.gridx = 4; c.gridy = 0; jPanel.add(overlayLbl, c);
    c.gridx = 5; c.gridy = 0; jPanel.add(this.imageOverlayTextBox, c);
    c.gridx = 0; c.gridy = 1; jPanel.add(topLbl, c);
    c.gridx = 1; c.gridy = 1; jPanel.add(this.imageTopTextBox, c);
    c.gridx = 2; c.gridy = 1; jPanel.add(leftLbl, c);
    c.gridx = 3; c.gridy = 1; jPanel.add(this.imageLeftTextBox, c);
    c.gridx = 4; c.gridy = 1; jPanel.add(rightLbl, c);
    c.gridx = 5; c.gridy = 1; jPanel.add(this.imageRightTextBox, c);
    c.gridx = 6; c.gridy = 1; jPanel.add(bottomLbl, c);
    c.gridx = 7; c.gridy = 1; jPanel.add(this.imageBottomTextBox, c);
    ////////////////////////////////////////////////////////////////////////////
    
    return jPanel;
  }

}
