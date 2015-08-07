package org.jiserte.desktopcalendar;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import javax.imageio.ImageIO;

import org.jiserte.desktopcalendar.data.Priority;
import org.jiserte.desktopcalendar.data.PriorityColorMapFactory;
import org.jiserte.desktopcalendar.data.WorkingCalendar;
import org.jiserte.desktopcalendar.data.WorkingDay;

public class CalendarImage {
  public BufferedImage createWallPaper(Dimension dimension, File baseImg,
      Calendar from, Calendar to, WorkingCalendar calendar, Insets margins,
      float overlayPercentage) {

    Map<Priority, Color> colorMap = PriorityColorMapFactory.getDefaultMap();

    BufferedImage image = new BufferedImage(dimension.width, dimension.height,
        BufferedImage.TYPE_INT_RGB);
    Graphics2D gi = (Graphics2D) image.getGraphics();
    setRenderingHints(gi);

    try {
      BufferedImage in = ImageIO.read(baseImg);
      gi.drawImage(in, 0, 0, null);
    } catch (IOException e) {
      e.printStackTrace();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Variables of the graphic layout
    int midHorizontalSpacer = 5;
    int midVerticalSpacer = 5;
    int midVerticalSmallSpacer = 2;
    int wdTabWidth = (int) (dimension.width -  margins.left - margins.right
        - 2 * midHorizontalSpacer) / 3;
    int wdTabHeight = (int) (dimension.height - margins.top -  margins.bottom
        - 5 * midVerticalSpacer - 4 * 6 * midVerticalSmallSpacer) / 30;
    ////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////
    // Check if current day is a week day
    int firstWeekDay = from.get(Calendar.DAY_OF_WEEK);
    int lastWeekDay = to.get(Calendar.DAY_OF_WEEK);
    if (firstWeekDay != Calendar.MONDAY || lastWeekDay != Calendar.FRIDAY) {
      return null;
    }
    ////////////////////////////////////////////////////////////////////////////
    
    ////////////////////////////////////////////////////////////////////////////
    // Iterate over each column
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Calendar today = Calendar.getInstance();
    int d = today.get(Calendar.DAY_OF_MONTH);
    int m = today.get(Calendar.MONTH);
    int y = today.get(Calendar.YEAR);
    today.setTimeInMillis(0);
    today.set(Calendar.DAY_OF_MONTH, d);
    today.set(Calendar.MONTH, m);
    today.set(Calendar.YEAR, y);
    
    int dayCounter = 0;
    for (int col = 0; col < 3; col++) {
      int xDisp =  margins.left + (wdTabWidth + midHorizontalSpacer) * col;
      for (int week = 0; week < 6; week++) {
        for (int day = 0; day < 7; day++) {
          Calendar currentDay = Calendar.getInstance();
          currentDay.setTime(from.getTime());
          currentDay.add(Calendar.DAY_OF_MONTH, dayCounter);
          int yDisp = margins.top
              + week * (midVerticalSpacer + 4 * midVerticalSmallSpacer
                  + 5 * wdTabHeight)
              + day * (midVerticalSmallSpacer + wdTabHeight);
          if (currentDay.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
              && currentDay.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            BufferedImage dayTabImage = new BufferedImage(wdTabWidth,
                wdTabHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = (Graphics2D) dayTabImage.getGraphics();

            setRenderingHints(g);

            WorkingDay currentWorkingDay = calendar.getWorkingDay(currentDay);
            Priority currentPriority = currentWorkingDay.getPriority();
            g.setColor(colorMap.get(currentPriority));
            g.fillRect(0, 0, wdTabWidth, wdTabHeight);
            g.setColor(new Color(30, 30, 60));
            g.setStroke(new BasicStroke(2));
            g.drawRect(0, 0, wdTabWidth-1, wdTabHeight-1);
            g.setColor(Color.black);
            g.setFont(new Font("Verdana", 0, (int) (wdTabHeight*0.7)));
            g.drawString(
                sdf.format(currentDay.getTime()) + " | "
                    + currentWorkingDay.getTask(),
                midHorizontalSpacer, wdTabHeight - 2 * midVerticalSmallSpacer);
            if (currentWorkingDay.getPriority() == Priority.InThePast) {
              g.setColor(new Color(230, 40, 60));
              g.drawLine(wdTabHeight / 2, wdTabHeight * 3 / 4,
                  wdTabWidth - wdTabHeight / 2, wdTabHeight / 4);
            }
            if (currentDay.equals(today)) {
              Polygon starPolygon = this.getStarPolygon(wdTabHeight*0.4,8,wdTabWidth - wdTabHeight / 2,wdTabHeight / 2);
              g.setColor(new Color(100,210,100));
              g.fillPolygon(starPolygon);
              g.setColor(new Color(60, 180, 60));
              g.setStroke(new BasicStroke(0.5f));
              g.drawPolygon(starPolygon);
            }
            AlphaComposite ac = java.awt.AlphaComposite
                .getInstance(AlphaComposite.SRC_OVER, overlayPercentage);
            gi.setComposite(ac);
            gi.drawImage(dayTabImage, xDisp, yDisp, null);
          }
          dayCounter++;
        }
      }
    }
    ///////////////////////////////////////////////////////////////////////////
//    gi.setColor(Color.red);
//    gi.fillPolygon(this.getStarPolygon());
     return image;

  }

  private Polygon getStarPolygon(double size, int points, int xOffSet, int yOffset) {
    
    double angle = Math.PI / points;
    int[] xpoints = new int[2*points];
    int[] ypoints = new int[2*points];
    for (int p = 0; p<points*2;p++) {
    double alpha = p*angle-Math.PI/2;
      
      xpoints[p] = xOffSet +  (int) (Math.cos(alpha)*size*(1-(p%2)*0.5));
      ypoints[p] = yOffset + (int) (Math.sin(alpha)*size*(1-(p%2)*0.5));
    }
    return new Polygon(xpoints, ypoints, points*2);
  }

  private void setRenderingHints(Graphics2D g) {
    g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
        RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
    g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    g.setRenderingHint(RenderingHints.KEY_RENDERING,
        RenderingHints.VALUE_RENDER_QUALITY);
    g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
        RenderingHints.VALUE_COLOR_RENDER_QUALITY);
    g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
        RenderingHints.VALUE_STROKE_NORMALIZE);
  }
}
