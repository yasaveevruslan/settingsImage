package org.example.pages;

import org.example.MainWindow;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ColorWindow
{

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

        SwingUtilities.invokeLater(() ->
        {
            JFrame frame = new JFrame(name);
            frame.setBounds(1200, 0, 700, 900);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            ColorPanel panel = new ColorPanel(name, imageFirst, imageSecond, minRed, maxRed, minGreen, maxGreen, minBlue, maxBlue);
            frame.setContentPane(panel);
            frame.setVisible(true);

            MainWindow.nameMethod = "none";
        });
    }
}

class ColorPanel extends JPanel implements ActionListener, ChangeListener
{

    private static final Logger logger = Logger.getLogger(ColorPanel.class.getName());

    private final String name;
    private String imageFirst;
    private String imageSecond;
    private int minRed, maxRed, minGreen, maxGreen, minBlue, maxBlue;

    private JComboBox<String> firstImage, secondImage;
    private JLabel labelMinRed, labelMaxRed, labelMinGreen, labelMaxGreen, labelMinBlue, labelMaxBlue;
    private JSlider sliderMinRed, sliderMaxRed, sliderMinGreen, sliderMaxGreen, sliderMinBlue, sliderMaxBlue;

    public ColorPanel(String name, String nameFirst, String nameSecond, int minRed, int maxRed, int minGreen,
                      int maxGreen, int minBlue, int maxBlue)
    {
        this.name = name;
        setLayout((new GridBagLayout()));
        initialValue(nameFirst, nameSecond, minRed, maxRed, minGreen, maxGreen, minBlue, maxBlue);
        generationElements();
    }

    private void initialValue(String nameFirst, String nameSecond, int minRed, int maxRed, int minGreen,
                              int maxGreen, int minBlue, int maxBlue)
    {
        try {
            String[] lastValues = MainWindow.loadProperty(MainWindow.nameMethod).split(", ");
            if (lastValues.length > 2)
            {
                this.imageFirst = lastValues[0];
                this.imageSecond = lastValues[1];
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
            logger.warning("Ошибка при установке начальных значений для окна для создания бинарной маски(InRange): "
                    + e.getLocalizedMessage());
            MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.WARNING,
                    "Ошибка при установке начальных значений для окна для создания бинарной маски(InRange): "
                            + e.getLocalizedMessage()));
        }
    }


    private void generationElements() {

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets =  new Insets(8,8,8,8);

        JLabel first = new JLabel("Источник");
        first.setFont(MainPanel.font);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        add(first, gbc);

        firstImage = new JComboBox<>(MainWindow.elements);
        firstImage.setSelectedIndex(Arrays.asList(MainWindow.elements).indexOf(this.imageFirst));
        firstImage.setFont(MainPanel.font);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        add(firstImage, gbc);
        firstImage.addActionListener(this);

        JLabel second = new JLabel("Результат");
        second.setFont(MainPanel.font);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        add(second, gbc);

        secondImage = new JComboBox<>(MainWindow.elements);
        secondImage.setSelectedIndex(Arrays.asList(MainWindow.elements).indexOf(this.imageSecond));
        secondImage.addActionListener(this);
        secondImage.setFont(MainPanel.font);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        add(secondImage, gbc);


        labelMinRed = new JLabel("", JLabel.CENTER);
        labelMinRed.setFont(MainPanel.font);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        add(labelMinRed, gbc);


        sliderMinRed = new JSlider(0, 255, minRed);
        sliderMinRed.setFont(MainPanel.font);
        sliderMinRed.setPaintTrack(true);
        sliderMinRed.setPaintTicks(true);
        sliderMinRed.setPaintLabels(true);

        sliderMinRed.setMajorTickSpacing(45);
        sliderMinRed.setMinorTickSpacing(5);
        sliderMinRed.addChangeListener(this);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        add(sliderMinRed, gbc);

        labelMaxRed = new JLabel("", JLabel.CENTER);
        labelMaxRed.setFont(MainPanel.font);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        add(labelMaxRed, gbc);

        sliderMaxRed = new JSlider(0, 255, maxRed);
        sliderMaxRed.setBounds(50, 300, 600, 50);
        sliderMaxRed.setFont(MainPanel.font);
        sliderMaxRed.setPaintTrack(true);
        sliderMaxRed.setPaintTicks(true);
        sliderMaxRed.setPaintLabels(true);

        sliderMaxRed.setMinorTickSpacing(5);
        sliderMaxRed.setMajorTickSpacing(45);
        sliderMaxRed.addChangeListener(this);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        add(sliderMaxRed, gbc);


        labelMinGreen = new JLabel("", JLabel.CENTER);
        labelMinGreen.setFont(MainPanel.font);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        add(labelMinGreen, gbc);

        sliderMinGreen = new JSlider(0, 255, minGreen);
        sliderMinGreen.setFont(MainPanel.font);
        sliderMinGreen.setPaintTrack(true);
        sliderMinGreen.setPaintTicks(true);
        sliderMinGreen.setPaintLabels(true);
        sliderMinGreen.setMinorTickSpacing(5);
        sliderMinGreen.setMajorTickSpacing(45);
        sliderMinGreen.addChangeListener(this);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        add(sliderMinGreen, gbc);


        labelMaxGreen = new JLabel("", JLabel.CENTER);
        labelMaxGreen.setFont(MainPanel.font);
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        add(labelMaxGreen, gbc);

        sliderMaxGreen = new JSlider(0, 255, maxGreen);
        sliderMaxGreen.setFont(MainPanel.font);
        sliderMaxGreen.setPaintTrack(true);
        sliderMaxGreen.setPaintTicks(true);
        sliderMaxGreen.setPaintLabels(true);

        sliderMaxGreen.setMinorTickSpacing(5);
        sliderMaxGreen.setMajorTickSpacing(45);
        sliderMaxGreen.addChangeListener(this);
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        add(sliderMaxGreen, gbc);

        labelMinBlue = new JLabel("", JLabel.CENTER);
        labelMinBlue.setFont(MainPanel.font);
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        add(labelMinBlue, gbc);

        sliderMinBlue = new JSlider(0, 255, minBlue);
        sliderMinBlue.setFont(MainPanel.font);
        sliderMinBlue.setPaintTrack(true);
        sliderMinBlue.setPaintTicks(true);
        sliderMinBlue.setPaintLabels(true);

        sliderMinBlue.setMajorTickSpacing(45);
        sliderMinBlue.setMinorTickSpacing(5);
        sliderMinBlue.addChangeListener(this);
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        add(sliderMinBlue, gbc);

        labelMaxBlue = new JLabel("", JLabel.CENTER);
        labelMaxBlue.setFont(MainPanel.font);
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        add(labelMaxBlue, gbc);

        sliderMaxBlue = new JSlider(0, 255, maxBlue);
        sliderMaxBlue.setFont(MainPanel.font);
        sliderMaxBlue.setPaintTrack(true);
        sliderMaxBlue.setPaintTicks(true);
        sliderMaxBlue.setPaintLabels(true);

        sliderMaxBlue.setMajorTickSpacing(45);
        sliderMaxBlue.setMinorTickSpacing(5);
        sliderMaxBlue.addChangeListener(this);

        gbc.gridx = 0;
        gbc.gridy = 13;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        add(sliderMaxBlue, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        try
        {
            imageFirst = (String) firstImage.getSelectedItem();
            imageSecond = (String) secondImage.getSelectedItem();
        }
        catch (Exception ex)
        {
            logger.warning("Ошибка в обработке изоборжений с окна ColorWindow " + ex.getLocalizedMessage());
            MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.WARNING, "Ошибка в обработке значений с окна ColorWindow " + ex.getLocalizedMessage()));
        }
    }

    @Override
    public void stateChanged(ChangeEvent e)
    {
        try
        {
            minRed = sliderMinRed.getValue();
            labelMinRed.setText("мин Red: " + minRed);

            maxRed = sliderMaxRed.getValue();
            labelMaxRed.setText("макс Red: " + maxRed);

            minGreen = sliderMinGreen.getValue();
            labelMinGreen.setText("мин Green: " + minGreen);

            maxGreen = sliderMaxGreen.getValue();
            labelMaxGreen.setText("макс Green: " + maxGreen);

            minBlue = sliderMinBlue.getValue();
            labelMinBlue.setText("мин Blue: " + minBlue);

            maxBlue = sliderMaxBlue.getValue();
            labelMaxBlue.setText("макс Blue: " + maxBlue);

            MainWindow.updateProperty(name, imageFirst + ", " + imageSecond
                    + ", " + minRed + ", " + maxRed
                    + ", " + minGreen + ", " + maxGreen
                    + ", " + minBlue + ", " + maxBlue);
        }
        catch (IOException ex)
        {
            logger.warning("Ошибка в обновлении значений с окна ColorWindow "
                    + ex.getLocalizedMessage());
            MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.WARNING,
                    "Ошибка в обновлении значений с окна ColorWindow " + ex.getLocalizedMessage()));
        }
    }
}
