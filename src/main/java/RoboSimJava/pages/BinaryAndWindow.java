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


public class BinaryAndWindow {

    private static String imageFirst;
    private static String imageSecond;
    private static String imageThird;

    public BinaryAndWindow(String nameFirst, String nameSecond, String nameThird)
    {
        imageFirst = nameFirst;
        imageSecond = nameSecond;
        imageThird = nameThird;
    }



    public static void generationWindow(String name, String nameFirst, String nameSecond, String nameThird)
    {
        imageFirst = nameFirst;
        imageSecond = nameSecond;
        imageThird = nameThird;

        JFrame frame = new JFrame(name);
        frame.setBounds(1200, 0, 700, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        PanelBinaryAnd panel = new PanelBinaryAnd(name, imageFirst, imageSecond, imageThird);
        frame.setContentPane(panel);
        frame.setVisible(true);

        MainWindow.nameMethod = "none";
    }
}

class PanelBinaryAnd extends JPanel implements ActionListener {

    private static final Logger logger = Logger.getLogger(PanelBinaryAnd.class.getName());

    private final String name;
    private String imageFirst;
    private String imageSecond;
    private String imageThird;
    public PanelBinaryAnd(String name, String imageFirst, String imageSecond, String imageThird)
    {
        super();
        this.name = name;
        super.setLayout(null);
        generationElements(name, imageFirst, imageSecond, imageThird);
    }


    private JComboBox<String> firstImage, secondImage, thirdImage;

    private void generationElements(String name, String imageFirst, String imageSecond, String imageThird)
    {
        try
        {
            String[] lastValues = MainWindow.loadProperty(MainWindow.nameMethod).split(", ");
            if(lastValues.length>2)
            {
                String imageFirstValue = lastValues[0];
                String imageSecondValue = lastValues[1];
                String imageThirdValue = lastValues[2];
                this.imageFirst  = imageFirstValue;
                this.imageSecond = imageSecondValue;
                this.imageThird = imageThirdValue;
            }
            else
            {
                this.imageFirst = imageFirst;
                this.imageSecond = imageSecond;
                this.imageThird = imageThird;
            }

        }
        catch (IOException e)
        {
            logger.warning("Ошибка при установке начальных значений для окна для BinaryAnd на изображении: " + e.getLocalizedMessage());
            MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.WARNING, "Ошибка при установке начальных значений для окна для BinaryAnd на изображении: " + e.getLocalizedMessage()));
        }

        JLabel first = new JLabel("Первый источник");
        firstImage = new JComboBox<>(MainWindow.images.toArray(new String[0]));
        JLabel second = new JLabel("Второй источник");
        secondImage = new JComboBox<>(MainWindow.images.toArray(new String[0]));
        JLabel third = new JLabel("Результат");
        thirdImage = new JComboBox<>(MainWindow.images.toArray(new String[0]));
        firstImage.setSelectedIndex(MainWindow.images.indexOf(this.imageFirst));
        secondImage.setSelectedIndex(MainWindow.images.indexOf(this.imageSecond));
        thirdImage.setSelectedIndex(MainWindow.images.indexOf(this.imageThird));
        firstImage.addActionListener(this);
        secondImage.addActionListener(this);
        thirdImage.addActionListener(this);
        first.setBounds(130, 5, 260, 50);
        second.setBounds(130, 120, 260, 50);
        third.setBounds(130, 200, 260, 50);
        firstImage.setBounds(290, 5, 200, 50);
        secondImage.setBounds(290, 120, 200, 50);
        thirdImage.setBounds(290, 200, 200, 50);
        second.setFont(MainPanel.font);
        first.setFont(MainPanel.font);
        firstImage.setFont(MainPanel.font);
        secondImage.setFont(MainPanel.font);
        third.setFont(MainPanel.font);
        thirdImage.setFont(MainPanel.font);
        super.add(first);
        super.add(firstImage);
        super.add(second);
        super.add(secondImage);
        super.add(third);
        super.add(thirdImage);

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            imageFirst = (String) firstImage.getSelectedItem();
            imageSecond = (String) secondImage.getSelectedItem();
            imageThird = (String) thirdImage.getSelectedItem();
            MainWindow.updateProperty(name, imageFirst + ", " + imageThird + ", " + imageSecond);
        } catch (IOException ex) {
            logger.warning("Ошибка в обработке значений с окна PanelBinaryAnd " + ex.getLocalizedMessage());
            MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.WARNING, "Ошибка в обработке значений с окна PanelBinaryAnd " + ex.getLocalizedMessage()));
        }

    }

}