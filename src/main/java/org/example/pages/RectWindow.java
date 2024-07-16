package org.example.pages;

import org.example.MainWindow;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RectWindow {

    private static String imageFirst;
    private static String imageSecond;
    private static int x, y, width, height, mainWidth, mainHeight;

    public RectWindow(String nameFirst, String nameSecond, int xF, int yF, int widthF, int heightF, int mainWidthF, int mainHeightF)
    {
        imageFirst = nameFirst;
        imageSecond = nameSecond;
        x = xF;
        y = yF;
        width = widthF;
        height = heightF;
        mainWidth = mainWidthF;
        mainHeight = mainHeightF;
    }



    public static void generationWindow(String name, String nameFirst, String nameSecond, int xF, int yF, int widthF, int heightF, int mainWidthF, int mainHeightF)
    {
        imageFirst = nameFirst;
        imageSecond = nameSecond;
        x = xF;
        y = yF;
        width = widthF;
        height = heightF;
        mainWidth = mainWidthF;
        mainHeight = mainHeightF;

        JFrame frame = new JFrame(name);
        frame.setBounds(1200, 0, 700, 900);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        RectPanel panel = new RectPanel(name, imageFirst, imageSecond, x, y, width, height, mainWidth, mainHeight);
        frame.setContentPane(panel);
        frame.setVisible(true);

        MainWindow.nameMethod = "none";
    }
}

class RectPanel extends JPanel implements ActionListener, ChangeListener {

    private static final Logger logger = Logger.getLogger(RectPanel.class.getName());

    private String name, imageFirst, imageSecond;
    private int x, y, width, height, mainWidth, mainHeight;
    public RectPanel(String name, String nameFirst, String nameSecond, int xF, int yF, int widthF, int heightF, int mainWidthF, int mainHeightF)
    {
        super();
        this.name = name;
        super.setLayout(null);
        generationElements(nameFirst, nameSecond, xF, yF, widthF, heightF, mainWidthF, mainHeightF);
    }


    private JComboBox<String> firstImage, secondImage;
    private JLabel labelX, labelY, labelWidth, labelHeight;
    private JSlider sliderX, sliderY, sliderWidth, sliderHeight;

    private void generationElements(String nameFirst, String nameSecond, int xF, int yF, int widthF, int heightF, int mainWidthF, int mainHeightF)
    {
        try
        {
            String[] lastValues = MainWindow.loadProperty(MainWindow.nameMethod).split(", ");
            if(lastValues.length>2)
            {
                String imageFirstValue = lastValues[0];
                String imageSecondValue = lastValues[1];
                this.imageFirst  = imageFirstValue;
                this.imageSecond = imageSecondValue;
                this.x = Integer.parseInt(lastValues[2]);
                this.y = Integer.parseInt(lastValues[3]);
                this.width = Integer.parseInt(lastValues[4]);
                this.height = Integer.parseInt(lastValues[5]);
                this.mainWidth = Integer.parseInt(lastValues[6]);
                this.mainHeight = Integer.parseInt(lastValues[7]);
            }
            else
            {
                imageFirst = nameFirst;
                imageSecond = nameSecond;
                x = xF;
                y = yF;
                width = widthF;
                height = heightF;
                mainWidth = mainWidthF;
                mainHeight = mainHeightF;
            }

        }
        catch (IOException e)
        {
            logger.warning("Ошибка при установке начальных значений для окна для обрезки изображения: " + e.getLocalizedMessage());
            MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.WARNING, "Ошибка при установке начальных значений для окна для обрезки изображения: " + e.getLocalizedMessage()));
        }

        JLabel first = new JLabel("Источник");
        firstImage = new JComboBox<>(MainWindow.elements);
        JLabel second = new JLabel("Результат");
        secondImage = new JComboBox<>(MainWindow.elements);
        firstImage.setSelectedIndex(Arrays.asList(MainWindow.elements).indexOf(this.imageFirst));
        secondImage.setSelectedIndex(Arrays.asList(MainWindow.elements).indexOf(this.imageSecond));
        firstImage.addActionListener(this);
        secondImage.addActionListener(this);
        first.setBounds(200, 5, 70, 50);
        second.setBounds(200, 120, 70, 50);
        firstImage.setBounds(290, 5, 120, 50);
        secondImage.setBounds(290, 120, 120, 50);
        super.add(first);
        super.add(firstImage);
        super.add(second);
        super.add(secondImage);

        labelX = new JLabel("", JLabel.CENTER);
        sliderX = new JSlider(0, 640, x);
        sliderX.setBounds(50, 200, 600, 50);
        labelX.setBounds(250, 250, 200, 50);

        sliderX.setPaintTrack(true);
        sliderX.setPaintTicks(true);
        sliderX.setPaintLabels(true);
        sliderX.setMajorTickSpacing(64);
        sliderX.setMinorTickSpacing(5);
        sliderX.addChangeListener(this);
        sliderX.setMajorTickSpacing(5);
        sliderX.setPaintTicks(true);
        sliderX.setVisible(true);
        super.add(labelX);
        super.add(sliderX);

        labelY = new JLabel("", JLabel.CENTER);
        sliderY = new JSlider(0, 640, y);
        sliderY.setBounds(50, 300, 600, 50);
        labelY.setBounds(250, 350, 200, 50);

        sliderY.setPaintTrack(true);
        sliderY.setPaintTicks(true);
        sliderY.setPaintLabels(true);
        sliderY.setMajorTickSpacing(64);
        sliderY.setMinorTickSpacing(5);
        sliderY.addChangeListener(this);
        sliderY.setMajorTickSpacing(5);
        sliderY.setPaintTicks(true);
        sliderY.setVisible(true);
        super.add(labelY);
        super.add(sliderY);







        labelWidth = new JLabel("", JLabel.CENTER);
        sliderWidth = new JSlider(0, 640, width);
        sliderWidth.setBounds(50, 400, 600, 50);
        labelWidth.setBounds(250, 450, 200, 50);

        sliderWidth.setPaintTrack(true);
        sliderWidth.setPaintTicks(true);
        sliderWidth.setPaintLabels(true);
        sliderWidth.setMajorTickSpacing(64);
        sliderWidth.setMinorTickSpacing(5);
        sliderWidth.addChangeListener(this);
        sliderWidth.setMajorTickSpacing(5);
        sliderWidth.setPaintTicks(true);
        sliderWidth.setVisible(true);
        super.add(labelWidth);
        super.add(sliderWidth);

        labelHeight = new JLabel("", JLabel.CENTER);
        sliderHeight = new JSlider(0, 640, height);
        sliderHeight.setBounds(50, 500, 600, 50);
        labelHeight.setBounds(250, 550, 200, 50);

        sliderHeight.setPaintTrack(true);
        sliderHeight.setPaintTicks(true);
        sliderHeight.setPaintLabels(true);
        sliderHeight.setMajorTickSpacing(64);
        sliderHeight.setMinorTickSpacing(5);
        sliderHeight.addChangeListener(this);
        sliderHeight.setMajorTickSpacing(5);
        sliderHeight.setPaintTicks(true);
        sliderHeight.setVisible(true);
        super.add(labelHeight);
        super.add(sliderHeight);

    }

    @Override
    public void stateChanged(ChangeEvent e) {
        try {
            labelX.setText("X: " + sliderX.getValue());
            x = sliderX.getValue();

            labelY.setText("Y: " + sliderY.getValue());
            y = sliderY.getValue();

            int newWidth = mainWidth < x + sliderWidth.getValue() ? mainWidth - x : sliderWidth.getValue();
            labelWidth.setText("Ширина: " + newWidth);
            width = newWidth;

            int newHeight = mainHeight < y + sliderHeight.getValue() ? mainHeight - y : sliderHeight.getValue();
            labelHeight.setText("Высота: " + newHeight);
            height = newHeight;


            MainWindow.updateProperty(name, imageFirst + ", " + imageSecond
                    + ", " + x + ", " + y
                    + ", " + width + ", " + height
                    + ", " + mainWidth + ", " + mainHeight);
        } catch (IOException ex) {
            logger.warning("Ошибка при обновлении значений с окна для обрезки изображения: " + ex.getLocalizedMessage());
            MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.WARNING, "Ошибка при обновлении значений с окна для обрезки изображения: " + ex.getLocalizedMessage()));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try
        {
            imageFirst = (String) firstImage.getSelectedItem();
            imageSecond = (String) secondImage.getSelectedItem();
            MainWindow.updateProperty(name, imageFirst + ", " + imageSecond
                    + ", " + x + ", " + y
                    + ", " + width + ", " + height
                    + ", " + mainWidth + ", " + mainHeight);
        }
        catch (IOException ex)
        {
            logger.warning("Ошибка в обработке значений с окна RectWindow " + ex.getLocalizedMessage());
            MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.WARNING, "Ошибка в обработке значений с окна RectWindow " + ex.getLocalizedMessage()));
        }

    }

}
