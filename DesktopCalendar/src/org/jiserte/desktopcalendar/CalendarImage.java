package org.jiserte.desktopcalendar;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import javax.imageio.ImageIO;

public class CalendarImage {
  public BufferedImage createWallPaper(Dimension dimension, File baseImg,
      Calendar from, Calendar to, WorkingCalendar calendar) {

    Map<Priority, Color> colorMap = PriorityColorMapFactory.getDefaultMap();

    BufferedImage image = new BufferedImage(dimension.width, dimension.height,
        BufferedImage.TYPE_INT_RGB);
    Graphics2D gi = (Graphics2D) image.getGraphics();
    gi.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
        RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    gi.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
    gi.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    gi.setRenderingHint(RenderingHints.KEY_RENDERING,
        RenderingHints.VALUE_RENDER_QUALITY);
    gi.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
        RenderingHints.VALUE_COLOR_RENDER_QUALITY);
    gi.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
        RenderingHints.VALUE_STROKE_NORMALIZE);

    try {
      BufferedImage in = ImageIO.read(baseImg);
      gi.drawImage(in, 0, 0, null);
    } catch (IOException e) {
      e.printStackTrace();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Variables of the graphic layout
    int leftSpacer = 10;
    int rightSpacer = 10;
    int midHorizontalSpacer = 5;
    int topSpacer = 10;
    int bottomSpacer = 40;
    int midVerticalSpacer = 5;
    int midVerticalSmallSpacer = 2;
    int wdTabWidth = (int) (dimension.width - leftSpacer - rightSpacer
        - 2 * midHorizontalSpacer) / 3;
    int wdTabHeight = (int) (dimension.height - topSpacer - bottomSpacer
        - 5 * midVerticalSpacer - 4 * 6 * midVerticalSmallSpacer) / 30;
    ///////////////////////////////////////////////////////////////////////////

    int firstWeekDay = from.get(Calendar.DAY_OF_WEEK);
    int lastWeekDay = to.get(Calendar.DAY_OF_WEEK);
    if (firstWeekDay != Calendar.MONDAY || lastWeekDay != Calendar.FRIDAY) {
      return null;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Iterate over each column
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    int dayCounter = 0;
    for (int col = 0; col < 3; col++) {
      int xDisp = leftSpacer + (wdTabWidth + midHorizontalSpacer) * col;
      for (int week = 0; week < 6; week++) {
        for (int day = 0; day < 7; day++) {
          Calendar currentDay = Calendar.getInstance();
          currentDay.setTime(from.getTime());
          currentDay.add(Calendar.DAY_OF_MONTH, dayCounter);
          int yDisp = topSpacer
              + week * (midVerticalSpacer + 4 * midVerticalSmallSpacer
                  + 5 * wdTabHeight)
              + day * (midVerticalSmallSpacer + wdTabHeight);
          if (currentDay.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
              && currentDay.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            BufferedImage dayTabImage = new BufferedImage(wdTabWidth,
                wdTabHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = (Graphics2D) dayTabImage.getGraphics();

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

            WorkingDay currentWorkingDay = calendar.getWorkingDay(currentDay);
            Priority currentPriority = currentWorkingDay.getPriority();
            g.setColor(colorMap.get(currentPriority));
            g.fillRect(0, 0, wdTabWidth, wdTabHeight);
            g.setColor(new Color(30, 30, 60));
            g.setStroke(new BasicStroke(1));
            g.drawRect(0, 0, wdTabWidth, wdTabHeight);
            g.setColor(Color.black);
            g.setFont(new Font("Verdana", 0, (int) (wdTabHeight*0.7)));
            g.drawString(
                sdf.format(currentDay.getTime()) + " | "
                    + currentWorkingDay.getTaks(),
                midHorizontalSpacer, wdTabHeight - 2 * midVerticalSmallSpacer);
            if (currentWorkingDay.getPriority() == Priority.InThePast) {
              g.setColor(new Color(230, 40, 60));
              g.drawLine(wdTabHeight / 2, wdTabHeight * 3 / 4,
                  wdTabWidth - wdTabHeight / 2, wdTabHeight / 4);
            }
            AlphaComposite ac = java.awt.AlphaComposite
                .getInstance(AlphaComposite.SRC_OVER, 0.15F);
            gi.setComposite(ac);
            gi.drawImage(dayTabImage, xDisp, yDisp, null);
          }
          dayCounter++;
        }
      }
    }
    ///////////////////////////////////////////////////////////////////////////

    return image;

  }
}
