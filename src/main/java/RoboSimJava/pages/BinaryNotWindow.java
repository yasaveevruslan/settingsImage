package RoboSimJava.pages;

import RoboSimJava.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;


public class BinaryNotWindow {

    private static String imageFirst;
    private static String imageSecond;

    public BinaryNotWindow(String nameFirst, String nameSecond)
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
        PanelBinaryNot panel = new PanelBinaryNot(name, imageFirst, imageSecond);
        frame.setContentPane(panel);
        frame.setVisible(true);

        MainWindow.nameMethod = "none";
    }
}

class PanelBinaryNot extends JPanel implements ActionListener {

    private static final Logger logger = Logger.getLogger(PanelBinaryNot.class.getName());

    private String name, imageFirst, imageSecond;
    public PanelBinaryNot(String name, String imageFirst, String imageSecond)
    {
        super();
        this.name = name;
        super.setLayout(null);
        generationElements(name, imageFirst, imageSecond);
    }


    private JComboBox<String> firstImage, secondImage;

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
            logger.warning("Ошибка при установке начальных значений для окна для BinaryNot на изображении: " + e.getLocalizedMessage());
            MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.WARNING, "Ошибка при установке начальных значений для окна для BinaryNot на изображении: " + e.getLocalizedMessage()));
        }

        JLabel first = new JLabel("Источник");
        firstImage = new JComboBox<>(MainWindow.images.toArray(new String[0]));
        JLabel second = new JLabel("Результат");
        secondImage = new JComboBox<>(MainWindow.images.toArray(new String[0]));
        second.setFont(MainPanel.font);
        first.setFont(MainPanel.font);
        firstImage.setFont(MainPanel.font);
        secondImage.setFont(MainPanel.font);

        firstImage.setSelectedIndex(MainWindow.images.indexOf(this.imageFirst));
        secondImage.setSelectedIndex(MainWindow.images.indexOf(this.imageSecond));
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

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            imageFirst = (String) firstImage.getSelectedItem();
            imageSecond = (String) secondImage.getSelectedItem();
            MainWindow.updateProperty(name, imageFirst + ", " + imageSecond);
        } catch (IOException ex) {
            logger.warning("Ошибка в обработке значений с окна PanelBinaryNot " + ex.getLocalizedMessage());
            MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.WARNING, "Ошибка в обработке значений с окна PanelBinaryNot " + ex.getLocalizedMessage()));
        }

    }

}