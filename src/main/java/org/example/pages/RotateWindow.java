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

        SwingUtilities.invokeLater(() ->
        {
            JFrame frame = new JFrame(name);
            frame.setBounds(1200, 0, 700, 400);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            Panel panel = new Panel(name, imageFirst, imageSecond, degreesPosition);
            frame.setContentPane(panel);
            frame.setVisible(true);

            MainWindow.nameMethod = "none";
        });
    }
}

class Panel extends JPanel implements ActionListener, ChangeListener
{

    private static final Logger logger = Logger.getLogger(Panel.class.getName());

    private final String name;
    private String imageFirst;
    private String imageSecond;
    private int degreesPosition;
    private JLabel degrees;
    private JComboBox<String> firstImage, secondImage;

    public Panel(String name, String imageFirst, String imageSecond, int degreesPosition)
    {
        this.name = name;
        setLayout((new GridBagLayout()));
        initialValue(imageFirst, imageSecond, degreesPosition);
        generationElements();
    }

    private void initialValue(String imageFirst, String imageSecond, int degreesPosition)
    {
        try {
            String[] lastValues = MainWindow.loadProperty(MainWindow.nameMethod).split(", ");
            if (lastValues.length > 2) {
                int degreesValue = Integer.parseInt(lastValues[2]);
                String imageFirstValue = lastValues[0];
                String imageSecondValue = lastValues[1];
                this.imageFirst = imageFirstValue;
                this.imageSecond = imageSecondValue;
                this.degreesPosition = degreesValue;
            } else {
                this.imageFirst = imageFirst;
                this.imageSecond = imageSecond;
                this.degreesPosition = degreesPosition;
            }
        } catch (IOException e) {
            logger.warning("Ошибка при установке начальных значений для окна для поворота изображения: " + e.getLocalizedMessage());
            MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.WARNING, "Ошибка при установке начальных значений для окна для поворота изображения: " + e.getLocalizedMessage()));
        }
    }

    private void generationElements() {

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets =  new Insets(25,25,25,25);

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

        degrees = new JLabel("", JLabel.CENTER);
        degrees.setFont(MainPanel.font);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        add(degrees, gbc);

        JSlider slider = new JSlider(-360, 360, degreesPosition);
        slider.setPaintTrack(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        slider.setMinorTickSpacing(5);
        slider.setMajorTickSpacing(45);
        slider.setPaintTicks(true);
        slider.addChangeListener(this);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        add(slider, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            imageFirst = (String) firstImage.getSelectedItem();
            imageSecond = (String) secondImage.getSelectedItem();
        } catch (Exception ex) {
            logger.severe("Ошибка в обработке изоборжений с окна RotateWindow " + ex.getLocalizedMessage());
            MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.SEVERE, "Ошибка в обработке значений с окна RotateWindow " + ex.getLocalizedMessage()));
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        try {
            degreesPosition = ((JSlider) e.getSource()).getValue();
            degrees.setText("Значение слайдера: " + degreesPosition);
            MainWindow.updateProperty(name, imageFirst + ", " + imageSecond + ", " + degreesPosition);
        } catch (IOException ex) {
            logger.severe("Ошибка в обработке значений с окна RotateWindow " + ex.getLocalizedMessage());
            MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.SEVERE, "Ошибка в обработке значений с окна RotateWindow " + ex.getLocalizedMessage()));
        }
    }
}
