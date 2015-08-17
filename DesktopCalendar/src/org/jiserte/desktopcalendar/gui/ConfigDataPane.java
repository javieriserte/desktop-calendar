package org.jiserte.desktopcalendar.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jiserte.desktopcalendar.data.SystemConfig;
import org.jiserte.desktopcalendar.xmlio.ConfigXmlParser;
import org.jiserte.desktopcalendar.xmlio.ConfigXmlWriter;

public class ConfigDataPane extends JPanel {

  //////////////////////////////////////////////////////////////////////////////
  // Class Constants
	private static final long serialVersionUID = -4169467490623059644L;
	private static final String SAVE_CMD = "SAVE_CMD"; 
	private static final String LOAD_CMD = "LOAD_CMD";
  private static final String SELECT_BASE_IMG_CMD = "SELECT_BASE_IMG_CMD"; 
  private static final String SELECT_WALLPAPER_IMG_CMD = "SELECT_WALLPAPER_IMG_CMD"; 
  //////////////////////////////////////////////////////////////////////////////

  //////////////////////////////////////////////////////////////////////////////
  // Components
	private JTextField imageWidthTextBox;
  private JTextField imageHeightTextBox;
  private JTextField imageTopTextBox;
  private JTextField imageLeftTextBox;
  private JTextField imageRightTextBox;
  private JTextField imageBottomTextBox;
  private JTextField imageOverlayTextBox;
  private JTextField srcImageTxt;
  private JTextField dstImageTxt;
  private JFileChooser fileChooser;
  private LoadAndSaveButtonsActionListener actionListener = 
      new LoadAndSaveButtonsActionListener();
  //////////////////////////////////////////////////////////////////////////////

  
  //////////////////////////////////////////////////////////////////////////////
  // Constructor
	public ConfigDataPane() {
		super();
		this.createGUI();
	}
  //////////////////////////////////////////////////////////////////////////////


  //////////////////////////////////////////////////////////////////////////////
  // Private methods 
	/**
	 * Initializes the GUI
	 */
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

  /**
   * Creates panel with Load and save buttons
   */
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
    loadButton.setActionCommand(LOAD_CMD);
    loadButton.addActionListener(this.actionListener);
    JButton saveButton = new JButton("Guardar");
    saveButton.setActionCommand(SAVE_CMD);
    saveButton.addActionListener(this.actionListener);
    ////////////////////////////////////////////////////////////////////////////
    
    ////////////////////////////////////////////////////////////////////////////
    // Add components
    c.fill = GridBagConstraints.BOTH;
    c.gridx = 1; c.gridy = 0; jPanel.add(loadButton, c);
    c.gridx = 2; c.gridy = 0; jPanel.add(saveButton, c);
    ////////////////////////////////////////////////////////////////////////////
    
    return jPanel;
  }


  /**
   * Creates panel to introduce image filenames
   * @return
   */
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
    srcImageButton.setActionCommand(SELECT_BASE_IMG_CMD);
    srcImageButton.addActionListener(this.actionListener);
    JLabel dstImageLbl = new JLabel("Imagen de salida:");
    this.dstImageTxt = new JTextField();
    JButton dstImageButton = new JButton("Seleccionar");
    dstImageButton.setActionCommand(SELECT_WALLPAPER_IMG_CMD);
    dstImageButton.addActionListener(this.actionListener);
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


  /**
   * Creates panel to introduce graphical options
   * @return
   */
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


  public void setUIValues(SystemConfig inConfig) {
    this.imageWidthTextBox.setText(String.valueOf(inConfig.getWidth()));
    this.imageHeightTextBox.setText(String.valueOf(inConfig.getHeight()));
    this.imageTopTextBox.setText(String.valueOf(inConfig.getTop()));
    this.imageLeftTextBox.setText(String.valueOf(inConfig.getLeft()));
    this.imageRightTextBox.setText(String.valueOf(inConfig.getRight()));
    this.imageBottomTextBox.setText(String.valueOf(inConfig.getBottom()));
    this.imageOverlayTextBox.setText(String.valueOf(inConfig.getOverlayPercentage()));
    this.srcImageTxt.setText(inConfig.getBaseImg().getAbsolutePath());
    this.dstImageTxt.setText(inConfig.getWallpaper().getAbsolutePath());
  }
  

  public SystemConfig getSystemConfig() {
    int width = Integer.valueOf(this.imageWidthTextBox.getText());
    int height = Integer.valueOf(this.imageHeightTextBox.getText());
    int top = Integer.valueOf(this.imageTopTextBox.getText());
    int left = Integer.valueOf(this.imageLeftTextBox.getText());
    int right = Integer.valueOf(this.imageRightTextBox.getText());
    int bottom= Integer.valueOf(this.imageBottomTextBox.getText());
    float overlayPercentage = Float.valueOf(this.imageOverlayTextBox.getText());
    File wallpaper = new File(this.dstImageTxt.getText());
    File baseImg = new File(this.srcImageTxt.getText()); 
    SystemConfig config = new SystemConfig(width, height, baseImg, wallpaper,
        left, right, top, bottom, overlayPercentage);
    return config;
  }
  
  //////////////////////////////////////////////////////////////////////////////

  //////////////////////////////////////////////////////////////////////////////
  // Auxiliary classes
  private class LoadAndSaveButtonsActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      String cmd = e.getActionCommand();
      switch(cmd) {
      case SAVE_CMD:
        SystemConfig outConfig = ConfigDataPane.this.getSystemConfig();
        if (outConfig != null) {
          ConfigDataPane.this.fileChooser = new JFileChooser(
              System.getProperty("user.dir"));
          ConfigDataPane.this.fileChooser.setMultiSelectionEnabled(false);
          ConfigDataPane.this.fileChooser.setFileFilter(new XmlFileFilter());
          ConfigDataPane.this.fileChooser.showSaveDialog(ConfigDataPane.this);
          File outfile = ConfigDataPane.this.fileChooser.getSelectedFile();
          if (outfile != null) {
            (new ConfigXmlWriter()).write(outConfig, outfile);
          }
        }
        break;
      case LOAD_CMD:
        ConfigDataPane.this.fileChooser = new JFileChooser(
            System.getProperty("user.dir"));
        ConfigDataPane.this.fileChooser.setMultiSelectionEnabled(false);
        ConfigDataPane.this.fileChooser.setFileFilter(new XmlFileFilter());
        ConfigDataPane.this.fileChooser.showOpenDialog(ConfigDataPane.this);
        File infile = ConfigDataPane.this.fileChooser.getSelectedFile();
        SystemConfig inConfig;
        if (infile != null && infile.exists() && ( inConfig = 
            new ConfigXmlParser().read(infile)) != null) {
          ConfigDataPane.this.setUIValues(inConfig);
        }
        break;
      case SELECT_BASE_IMG_CMD:
        ConfigDataPane.this.fileChooser = new JFileChooser(
            System.getProperty("user.dir"));
        ConfigDataPane.this.fileChooser.setMultiSelectionEnabled(false);
        ConfigDataPane.this.fileChooser.setFileFilter(new StandardImgFileFilter());
        ConfigDataPane.this.fileChooser.showOpenDialog(ConfigDataPane.this);
        File baseImgFile = ConfigDataPane.this.fileChooser.getSelectedFile();
        if (baseImgFile != null && baseImgFile.exists()) {
          ConfigDataPane.this.srcImageTxt.setText(baseImgFile.getAbsolutePath());
        }
        break;
      case SELECT_WALLPAPER_IMG_CMD:
        ConfigDataPane.this.fileChooser = new JFileChooser(
            System.getProperty("user.dir"));
        ConfigDataPane.this.fileChooser.setMultiSelectionEnabled(false);
        ConfigDataPane.this.fileChooser.setFileFilter(new StandardImgFileFilter());
        ConfigDataPane.this.fileChooser.showSaveDialog(ConfigDataPane.this);
        File wallpaperImgFile = ConfigDataPane.this.fileChooser.getSelectedFile();
        if (wallpaperImgFile != null) {
          ConfigDataPane.this.dstImageTxt.setText(wallpaperImgFile.getAbsolutePath());
        }
        break;
        
      }
    }
  }
  //////////////////////////////////////////////////////////////////////////////


  
}
