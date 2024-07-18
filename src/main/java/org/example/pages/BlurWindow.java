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


public class BlurWindow {

    private static String imageFirst;
    private static String imageSecond;
    private static int cofSize;

    public BlurWindow(String nameFirst, String nameSecond, int size)
    {
        imageFirst = nameFirst;
        imageSecond = nameSecond;
        cofSize = size;
    }



    public static void generationWindow(String name, String nameFirst, String nameSecond, int size)
    {
        imageFirst = nameFirst;
        imageSecond = nameSecond;
        cofSize = size;

        JFrame frame = new JFrame(name);
        frame.setBounds(1200, 0, 700, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        PanelBlur panel = new PanelBlur(name, imageFirst, imageSecond, cofSize);
        frame.setContentPane(panel);
        frame.setVisible(true);

        MainWindow.nameMethod = "none";
    }
}

class PanelBlur extends JPanel implements ActionListener {

    private static final Logger logger = Logger.getLogger(PanelBlur.class.getName());

    private String name, imageFirst, imageSecond;
    private int cofSize;
    public PanelBlur(String name, String imageFirst, String imageSecond, int size)
    {
        super();
        this.name = name;
        super.setLayout(null);
        generationElements(name, imageFirst, imageSecond, size);
    }


    private JComboBox<String> firstImage, secondImage;

    private void generationElements(String name, String imageFirst, String imageSecond, int size)
    {
        try
        {
            String[] lastValues = MainWindow.loadProperty(MainWindow.nameMethod).split(", ");
            if(lastValues.length>2)
            {
                int sizeValue = Integer.parseInt(lastValues[2]);
                String imageFirstValue = lastValues[0];
                String imageSecondValue = lastValues[1];
                this.imageFirst  = imageFirstValue;
                this.imageSecond = imageSecondValue;
                this.cofSize = sizeValue;
            }
            else
            {
                this.imageFirst = imageFirst;
                this.imageSecond = imageSecond;
                this.cofSize = size;
            }

        }
        catch (IOException e)
        {
            logger.warning("Ошибка при установке начальных значений для окна для размытия изображения: " + e.getLocalizedMessage());
            MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.WARNING, "Ошибка при установке начальных значений для окна для размытия изображения: " + e.getLocalizedMessage()));
        }

        JLabel first = new JLabel("Источник");
        firstImage = new JComboBox<>(MainWindow.elements);
        JLabel second = new JLabel("Результат");
        second.setFont(MainPanel.font);
        first.setFont(MainPanel.font);
        firstImage.setFont(MainPanel.font);

        secondImage = new JComboBox<>(MainWindow.elements);
        secondImage.setFont(MainPanel.font);

        firstImage.setSelectedIndex(Arrays.asList(MainWindow.elements).indexOf(this.imageFirst));
        secondImage.setSelectedIndex(Arrays.asList(MainWindow.elements).indexOf(this.imageSecond));
        firstImage.addActionListener(this);
        secondImage.addActionListener(this);
        first.setBounds(200, 5, 260, 50);
        second.setBounds(200, 120, 260, 50);
        firstImage.setBounds(290, 5, 200, 50);
        secondImage.setBounds(290, 120, 200, 50);
        super.add(first);
        super.add(firstImage);
        super.add(second);
        super.add(secondImage);

        JLabel degrees = new JLabel("", JLabel.CENTER);
        JSlider slider = new JSlider(0, 100, cofSize);
        slider.setBounds(50, 200, 600, 50);
        degrees.setBounds(250, 250, 300, 50);
        degrees.setFont(MainPanel.font);
        slider.setFont(MainPanel.font);

        slider.setPaintTrack(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        slider.setMajorTickSpacing(5);
        slider.setMinorTickSpacing(5);
        slider.addChangeListener(e -> {

            try {
                degrees.setText("Значение слайдера: " + ((JSlider)e.getSource()).getValue());
                cofSize = ((JSlider)e.getSource()).getValue();
                MainWindow.updateProperty(name, imageFirst + ", " + imageSecond + ", " + cofSize);
            } catch (IOException ex) {
                logger.severe("Ошибка в обработке значений со слайдера в BlurWindow" + ex.getLocalizedMessage());
                MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.SEVERE, "Ошибка в обработке значений со слайдера в BlurWindow" + ex.getLocalizedMessage()) );
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
            MainWindow.updateProperty(name, imageFirst + ", " + imageSecond + ", " + cofSize);
        } catch (IOException ex) {
            logger.warning("Ошибка в обработке значений с окна BlurWindow " + ex.getLocalizedMessage());
            MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.WARNING, "Ошибка в обработке значений с окна BlurWindow " + ex.getLocalizedMessage()));
        }

    }

}
