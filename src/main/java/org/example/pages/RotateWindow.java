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


public class RotateWindow {

    private static String imageFirst;
    private static String imageSecond;
    private static int degreesPosition;

    public RotateWindow(String nameFirst, String nameSecond, int degrees)
    {
        imageFirst = nameFirst;
        imageSecond = nameSecond;
        degreesPosition = degrees;
    }



    public static void generationWindow(String name, String nameFirst, String nameSecond, int degrees)
    {
        imageFirst = nameFirst;
        imageSecond = nameSecond;
        degreesPosition = degrees;

        JFrame frame = new JFrame(name);
        frame.setBounds(1200, 0, 700, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Panel panel = new Panel(name, imageFirst, imageSecond, degreesPosition);
        frame.setContentPane(panel);
        frame.setVisible(true);

        MainWindow.nameMethod = "none";
    }
}

class Panel extends JPanel implements ActionListener {

    private static final Logger logger = Logger.getLogger(Panel.class.getName());

    private String name, imageFirst, imageSecond;
    private int degreesPosition;
    public Panel(String name, String imageFirst, String imageSecond, int degreesPosition)
    {
        super();
        this.name = name;
        super.setLayout(null);
        generationElements(name, imageFirst, imageSecond, degreesPosition);
    }


    private JComboBox firstImage, secondImage;

    private void generationElements(String name, String imageFirst, String imageSecond, int d)
    {
        try
        {
            String[] lastValues = MainWindow.loadProperty(MainWindow.nameMethod).split(", ");
            if(lastValues.length>2)
            {
                int degreesValue = Integer.parseInt(lastValues[2]);
                String imageFirstValue = lastValues[0];
                String imageSecondValue = lastValues[1];
                this.imageFirst  = imageFirstValue;
                this.imageSecond = imageSecondValue;
                this.degreesPosition = degreesValue;
            }
            else
            {
                this.imageFirst = imageFirst;
                this.imageSecond = imageSecond;
                this.degreesPosition = d;
            }

        }
        catch (IOException e)
        {
            logger.warning("Ошибка при установке начальных значений для окна для поворота изображения: " + e.getLocalizedMessage());
            MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.WARNING, "Ошибка при установке начальных значений для окна для поворота изображения: " + e.getLocalizedMessage()));
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
        JSlider slider = new JSlider(-360, 360, degreesPosition);
        slider.setBounds(50, 200, 600, 50);
        degrees.setBounds(250, 250, 200, 50);

        slider.setPaintTrack(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        slider.setMajorTickSpacing(45);
        slider.setMinorTickSpacing(5);
        slider.addChangeListener(e -> {
            try {
                degrees.setText("Значение слайдера: " + ((JSlider)e.getSource()).getValue());
                degreesPosition = ((JSlider)e.getSource()).getValue();
                MainWindow.updateProperty(name, imageFirst + ", " + imageSecond + ", " + degreesPosition);
            } catch (IOException ex) {
                logger.severe("Ошибка в обработке значений со слайдера в RotateWindow" + ex.getLocalizedMessage());
                MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.SEVERE, "Ошибка в обработке значений со слайдера в RotateWindow" + ex.getLocalizedMessage()) );
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
            MainWindow.updateProperty(name, imageFirst + ", " + imageSecond + ", " + degreesPosition);
        } catch (IOException ex) {
            logger.severe("Ошибка в обработке значений с окна RotateWindow " + ex.getLocalizedMessage());
            MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.SEVERE, "Ошибка в обработке значений с окна RotateWindow " + ex.getLocalizedMessage()));
        }

    }

}
