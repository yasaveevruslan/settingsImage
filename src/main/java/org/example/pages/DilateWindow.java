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


public class DilateWindow {

    private static String imageFirst;
    private static String imageSecond;
    private static int cofPower;

    public DilateWindow(String nameFirst, String nameSecond, int power)
    {
        imageFirst = nameFirst;
        imageSecond = nameSecond;
        cofPower = power;
    }



    public static void generationWindow(String name, String nameFirst, String nameSecond, int power)
    {
        imageFirst = nameFirst;
        imageSecond = nameSecond;
        cofPower = power;

        JFrame frame = new JFrame(name);
        frame.setBounds(1200, 0, 700, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        PanelDilate panel = new PanelDilate(name, imageFirst, imageSecond, cofPower);
        frame.setContentPane(panel);
        frame.setVisible(true);

        MainWindow.nameMethod = "none";
    }
}

class PanelDilate extends JPanel implements ActionListener {

    private static final Logger logger = Logger.getLogger(PanelDilate.class.getName());

    private String name, imageFirst, imageSecond;
    private int cofPower;
    public PanelDilate(String name, String imageFirst, String imageSecond, int power)
    {
        super();
        this.name = name;
        super.setLayout(null);
        generationElements(name, imageFirst, imageSecond, power);
    }


    private JComboBox firstImage, secondImage;

    private void generationElements(String name, String imageFirst, String imageSecond, int power)
    {
        try
        {
            String[] lastValues = MainWindow.loadProperty(MainWindow.nameMethod).split(", ");
            if(lastValues.length>2)
            {
                int powerValue = Integer.parseInt(lastValues[2]);
                String imageFirstValue = lastValues[0];
                String imageSecondValue = lastValues[1];
                this.imageFirst  = imageFirstValue;
                this.imageSecond = imageSecondValue;
                this.cofPower = powerValue;
            }
            else
            {
                this.imageFirst = imageFirst;
                this.imageSecond = imageSecond;
                this.cofPower = power;
            }

        }
        catch (IOException e)
        {
            logger.warning("Ошибка при обработке значений с окна для метода dilate: " + e.getLocalizedMessage());
            MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.WARNING, "Ошибка при обработке значений с окна для метода dilate: " + e.getLocalizedMessage()));
        }

        JLabel first = new JLabel("Источник");
        firstImage = new JComboBox(MainWindow.elements);
        JLabel second = new JLabel("Результат");
        secondImage = new JComboBox(MainWindow.elements);
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

        JLabel degrees = new JLabel("", JLabel.CENTER);
        JSlider slider = new JSlider(0, 100, cofPower);
        slider.setBounds(50, 200, 600, 50);
        degrees.setBounds(250, 250, 200, 50);

        slider.setPaintTrack(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        slider.setMajorTickSpacing(5);
        slider.setMinorTickSpacing(5);
        slider.addChangeListener(e -> {
            try {
                degrees.setText("Выставленное значение на слайдере: " + ((JSlider)e.getSource()).getValue());
                cofPower = ((JSlider)e.getSource()).getValue();
                MainWindow.updateProperty(name, imageFirst + ", " + imageSecond + ", " + cofPower);
            } catch (IOException ex) {
                logger.warning("Ошибка при получении значение со слайдера в окне для метода dilate: " + ex.getLocalizedMessage());
                MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.WARNING, "Ошибка при получении значение со слайдера в окне для метода dilate: " + ex.getLocalizedMessage()));
            }

        });
        slider.setMajorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setVisible(true);
        super.add(degrees);
        super.add(slider);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            imageFirst = (String) firstImage.getSelectedItem();
            imageSecond = (String) secondImage.getSelectedItem();
            MainWindow.updateProperty(name, imageFirst + ", " + imageSecond + ", " + cofPower);
        } catch (IOException ex) {
            logger.warning("Ошибка в обработке значений с окна DilateWindow " + ex.getLocalizedMessage());
            MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.WARNING, "Ошибка в обработке значений с окна DilateWindow " + ex.getLocalizedMessage()));
        }

    }

}