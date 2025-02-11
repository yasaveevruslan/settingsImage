package org.example.pages;

import org.example.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AreaWindow {

    private static String imageFirst;
    private static String imageSecond;

    public AreaWindow(String nameFirst, String nameSecond)
    {
        imageFirst = nameFirst;
        imageSecond = nameSecond;
    }



    public static void generationWindow(String name, String nameFirst, String nameSecond)
    {
        imageFirst = nameFirst;
        imageSecond = nameSecond;

        JFrame frame = new JFrame(name);
        frame.setBounds(1200, 0, 700, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        PanelArea panel = new PanelArea(name, imageFirst, imageSecond);
        frame.setContentPane(panel);
        frame.setVisible(true);

        MainWindow.nameMethod = "none";
    }
}

class PanelArea extends JPanel implements ActionListener {

    private static final Logger logger = Logger.getLogger(PanelArea.class.getName());

    private String name, imageFirst, imageSecond;
    public PanelArea(String name, String imageFirst, String imageSecond)
    {
        super();
        this.name = name;
        super.setLayout(null);
        generationElements(name, imageFirst, imageSecond);
    }


    private JComboBox<String> firstImage, secondImage;
    private JLabel area;
    private void generationElements(String name, String imageFirst, String imageSecond)
    {
        try
        {
            String[] lastValues = MainWindow.loadProperty(MainWindow.nameMethod).split(", ");
            if(lastValues.length>1)
            {
                String imageFirstValue = lastValues[0];
                String imageSecondValue = lastValues[1];
                this.imageFirst  = imageFirstValue;
                this.imageSecond = imageSecondValue;
            }
            else
            {
                this.imageFirst = imageFirst;
                this.imageSecond = imageSecond;
            }

        }
        catch (IOException e)
        {
            logger.warning("Ошибка при установке начальных значений для окна для PanelArea на изображении: " + e.getLocalizedMessage());
            MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.WARNING, "Ошибка при установке начальных значений для окна для PanelArea на изображении: " + e.getLocalizedMessage()));
        }

        JLabel first = new JLabel("Источник");
        firstImage = new JComboBox<>(MainWindow.elements);
        JLabel second = new JLabel("Результат");
        secondImage = new JComboBox<>(MainWindow.elements);
        firstImage.setSelectedIndex(Arrays.asList(MainWindow.elements).indexOf(this.imageFirst));
        secondImage.setSelectedIndex(Arrays.asList(MainWindow.elements).indexOf(this.imageSecond));
        firstImage.addActionListener(this);
        secondImage.addActionListener(this);
        first.setBounds(200, 5, 260, 50);
        second.setBounds(200, 120, 260, 50);
        firstImage.setBounds(290, 5, 200, 50);
        secondImage.setBounds(290, 120, 200, 50);
        second.setFont(MainPanel.font);
        first.setFont(MainPanel.font);
        firstImage.setFont(MainPanel.font);
        secondImage.setFont(MainPanel.font);

        super.add(first);
        super.add(firstImage);
        super.add(second);
        super.add(secondImage);
        area = new JLabel("area");
        area.setBounds(200, 200, 300, 50);
        area.setFont(MainPanel.font);
        super.add(area);
        JButton generation = getjButton(name, imageFirst, imageSecond);
        generation.setFont(MainPanel.font);
        super.add(generation);

    }

    private JButton getjButton(String name, String imageFirst, String imageSecond) {
        JButton generation = new JButton("generate");
        generation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    area.setText(MainWindow.loadProperty(name).split(", ")[2]);
                    MainWindow.updateProperty(name, imageFirst + ", " + imageSecond);

                } catch (IOException ex) {
                    logger.warning("Ошибка в получении значения с окна PanelArea " + ex.getLocalizedMessage());
                    MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.WARNING, "Ошибка в получении значения с окна PanelArea " + ex.getLocalizedMessage()));
                }

            }
        });
        generation.setBounds(200, 290, 220, 50);
        return generation;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            imageFirst = (String) firstImage.getSelectedItem();
            imageSecond = (String) secondImage.getSelectedItem();
            area.setText(MainWindow.loadProperty(name).split(", ")[2]);

            MainWindow.updateProperty(name, imageFirst + ", " + imageSecond);
        } catch (IOException ex) {
            logger.warning("Ошибка в обработке значений с окна PanelArea " + ex.getLocalizedMessage());
            MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.WARNING, "Ошибка в обработке значений с окна PanelArea " + ex.getLocalizedMessage()));
        }

    }
}
