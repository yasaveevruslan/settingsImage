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


public class ColorWindow {

    private static String imageFirst;
    private static String imageSecond;
    private static int minRed, maxRed, minGreen, maxGreen, minBlue, maxBlue;

    public ColorWindow(String nameFirst, String nameSecond, int minRedF, int maxRedF, int minGreenF, int maxGreenF, int minBlueF, int maxBlueF)
    {
        imageFirst = nameFirst;
        imageSecond = nameSecond;
        minRed = minRedF;
        maxRed = maxRedF;
        minGreen = minGreenF;
        maxGreen = maxGreenF;
        minBlue = minBlueF;
        maxBlue = maxBlueF;
    }



    public static void generationWindow(String name, String nameFirst, String nameSecond, int minRedF, int maxRedF, int minGreenF, int maxGreenF, int minBlueF, int maxBlueF)
    {
        imageFirst = nameFirst;
        imageSecond = nameSecond;
        minRed = minRedF;
        maxRed = maxRedF;
        minGreen = minGreenF;
        maxGreen = maxGreenF;
        minBlue = minBlueF;
        maxBlue = maxBlueF;

        JFrame frame = new JFrame(name);
        frame.setBounds(1200, 0, 700, 900);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ColorPanel panel = new ColorPanel(name, imageFirst, imageSecond, minRed, maxRed, minGreen, maxGreen, minBlue, maxBlue);
        frame.setContentPane(panel);
        frame.setVisible(true);

        MainWindow.nameMethod = "none";
    }
}

class ColorPanel extends JPanel implements ActionListener, ChangeListener {

    private static final Logger logger = Logger.getLogger(ColorPanel.class.getName());

    private String name, imageFirst, imageSecond;
    private int minRed, maxRed, minGreen, maxGreen, minBlue, maxBlue;
    public ColorPanel(String name, String nameFirst, String nameSecond, int minRed, int maxRed, int minGreen, int maxGreen, int minBlue, int maxBlue)
    {
        super();
        this.name = name;
        super.setLayout(null);
        generationElements(nameFirst, nameSecond, minRed, maxRed, minGreen, maxGreen, minBlue, maxBlue);
    }


    private JComboBox<String> firstImage, secondImage;
    private JLabel labelMinRed, labelMaxRed, labelMinGreen, labelMaxGreen, labelMinBlue, labelMaxBlue;
    private JSlider sliderMinRed, sliderMaxRed, sliderMinGreen, sliderMaxGreen, sliderMinBlue, sliderMaxBlue;

    private void generationElements(String nameFirst, String nameSecond, int minRed, int maxRed, int minGreen, int maxGreen, int minBlue, int maxBlue)
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
                this.minRed = Integer.parseInt(lastValues[2]);
                this.maxRed = Integer.parseInt(lastValues[3]);
                this.minGreen = Integer.parseInt(lastValues[4]);
                this.maxGreen = Integer.parseInt(lastValues[5]);
                this.minBlue = Integer.parseInt(lastValues[6]);
                this.maxBlue = Integer.parseInt(lastValues[7]);
            }
            else
            {
                this.imageFirst = nameFirst;
                this.imageSecond = nameSecond;
                this.minRed = minRed;
                this.maxRed = maxRed;
                this.minGreen = minGreen;
                this.maxGreen = maxGreen;
                this.minBlue = minBlue;
                this.maxBlue = maxBlue;
            }

        }
        catch (IOException e)
        {
            logger.warning("Ошибка при установке начальных значений для окна для создания бинарной маски(InRange): " + e.getLocalizedMessage());
            MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.WARNING, "Ошибка при установке начальных значений для окна для создания бинарной маски(InRange): " + e.getLocalizedMessage()));
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

        labelMinRed = new JLabel("", JLabel.CENTER);
        sliderMinRed = new JSlider(0, 255, minRed);
        sliderMinRed.setBounds(50, 200, 600, 50);
        labelMinRed.setBounds(250, 250, 200, 50);

        sliderMinRed.setPaintTrack(true);
        sliderMinRed.setPaintTicks(true);
        sliderMinRed.setPaintLabels(true);
        sliderMinRed.setMajorTickSpacing(45);
        sliderMinRed.setMinorTickSpacing(5);
        sliderMinRed.addChangeListener(this);
        sliderMinRed.setMajorTickSpacing(5);
        sliderMinRed.setPaintTicks(true);
        sliderMinRed.setVisible(true);
        super.add(labelMinRed);
        super.add(sliderMinRed);

        labelMaxRed = new JLabel("", JLabel.CENTER);
        sliderMaxRed = new JSlider(0, 255, maxRed);
        sliderMaxRed.setBounds(50, 300, 600, 50);
        labelMaxRed.setBounds(250, 350, 200, 50);

        sliderMaxRed.setPaintTrack(true);
        sliderMaxRed.setPaintTicks(true);
        sliderMaxRed.setPaintLabels(true);
        sliderMaxRed.setMajorTickSpacing(45);
        sliderMaxRed.setMinorTickSpacing(5);
        sliderMaxRed.addChangeListener(this);
        sliderMaxRed.setMajorTickSpacing(5);
        sliderMaxRed.setPaintTicks(true);
        sliderMaxRed.setVisible(true);
        super.add(labelMaxRed);
        super.add(sliderMaxRed);







        labelMinGreen = new JLabel("", JLabel.CENTER);
        sliderMinGreen = new JSlider(0, 255, minGreen);
        sliderMinGreen.setBounds(50, 400, 600, 50);
        labelMinGreen.setBounds(250, 450, 200, 50);

        sliderMinGreen.setPaintTrack(true);
        sliderMinGreen.setPaintTicks(true);
        sliderMinGreen.setPaintLabels(true);
        sliderMinGreen.setMajorTickSpacing(45);
        sliderMinGreen.setMinorTickSpacing(5);
        sliderMinGreen.addChangeListener(this);
        sliderMinGreen.setMajorTickSpacing(5);
        sliderMinGreen.setPaintTicks(true);
        sliderMinGreen.setVisible(true);
        super.add(labelMinGreen);
        super.add(sliderMinGreen);

        labelMaxGreen = new JLabel("", JLabel.CENTER);
        sliderMaxGreen = new JSlider(0, 255, maxGreen);
        sliderMaxGreen.setBounds(50, 500, 600, 50);
        labelMaxGreen.setBounds(250, 550, 200, 50);

        sliderMaxGreen.setPaintTrack(true);
        sliderMaxGreen.setPaintTicks(true);
        sliderMaxGreen.setPaintLabels(true);
        sliderMaxGreen.setMajorTickSpacing(45);
        sliderMaxGreen.setMinorTickSpacing(5);
        sliderMaxGreen.addChangeListener(this);
        sliderMaxGreen.setMajorTickSpacing(5);
        sliderMaxGreen.setPaintTicks(true);
        sliderMaxGreen.setVisible(true);
        super.add(labelMaxGreen);
        super.add(sliderMaxGreen);







        labelMinBlue = new JLabel("", JLabel.CENTER);
        sliderMinBlue = new JSlider(0, 255, minBlue);
        sliderMinBlue.setBounds(50, 600, 600, 50);
        labelMinBlue.setBounds(250, 650, 200, 50);

        sliderMinBlue.setPaintTrack(true);
        sliderMinBlue.setPaintTicks(true);
        sliderMinBlue.setPaintLabels(true);
        sliderMinBlue.setMajorTickSpacing(45);
        sliderMinBlue.setMinorTickSpacing(5);
        sliderMinBlue.addChangeListener(this);
        sliderMinBlue.setMajorTickSpacing(5);
        sliderMinBlue.setPaintTicks(true);
        sliderMinBlue.setVisible(true);
        super.add(labelMinBlue);
        super.add(sliderMinBlue);

        labelMaxBlue = new JLabel("", JLabel.CENTER);
        sliderMaxBlue = new JSlider(0, 255, maxBlue);
        sliderMaxBlue.setBounds(50, 700, 600, 50);
        labelMaxBlue.setBounds(250, 750, 200, 50);

        sliderMaxBlue.setPaintTrack(true);
        sliderMaxBlue.setPaintTicks(true);
        sliderMaxBlue.setPaintLabels(true);
        sliderMaxBlue.setMajorTickSpacing(45);
        sliderMaxBlue.setMinorTickSpacing(5);
        sliderMaxBlue.addChangeListener(this);
        sliderMaxBlue.setMajorTickSpacing(5);
        sliderMaxBlue.setPaintTicks(true);
        sliderMaxBlue.setVisible(true);
        super.add(sliderMaxBlue);
        super.add(labelMaxBlue);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        try {
            labelMinRed.setText("мин Red: " + sliderMinRed.getValue());
            minRed = sliderMinRed.getValue();

            labelMaxRed.setText("макс Red: " + sliderMaxRed.getValue());
            maxRed = sliderMaxRed.getValue();

            labelMinGreen.setText("мин Green: " + sliderMinGreen.getValue());
            minGreen = sliderMinGreen.getValue();

            labelMaxGreen.setText("макс Green: " + sliderMaxGreen.getValue());
            maxGreen = sliderMaxGreen.getValue();

            labelMinBlue.setText("мин Blue: " + sliderMinBlue.getValue());
            minBlue = sliderMinBlue.getValue();

            labelMaxBlue.setText("макс Blue: " + sliderMaxBlue.getValue());
            maxBlue = sliderMaxBlue.getValue();

            MainWindow.updateProperty(name, imageFirst + ", " + imageSecond
                    + ", " + minRed + ", " + maxRed
                    + ", " + minGreen + ", " + maxGreen
                    + ", " + minBlue + ", " + maxBlue);
        } catch (IOException ex) {
            logger.warning("Ошибка в обновлении значений с окна ColorWindow " + ex.getLocalizedMessage());
            MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.WARNING, "Ошибка в обновлении значений с окна ColorWindow " + ex.getLocalizedMessage()));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try
        {
            imageFirst = (String) firstImage.getSelectedItem();
            imageSecond = (String) secondImage.getSelectedItem();
            MainWindow.updateProperty(name, imageFirst + ", " + imageSecond
                    + ", " + minRed + ", " + maxRed
                    + ", " + minGreen + ", " + maxGreen
                    + ", " + minBlue + ", " + maxBlue);
        }
        catch (IOException ex)
        {
            logger.warning("Ошибка в обработке значений с окна ColorWindow " + ex.getLocalizedMessage());
            MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.WARNING, "Ошибка в обработке значений с окна ColorWindow " + ex.getLocalizedMessage()));
        }

    }

}
