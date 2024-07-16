package org.example.pages;

import org.example.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;


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

    private String name, imageFirst, imageSecond, imageThird;
    public PanelBinaryAnd(String name, String imageFirst, String imageSecond, String imageThird)
    {
        super();
        this.name = name;
        super.setLayout(null);
        generationElements(name, imageFirst, imageSecond, imageThird);
    }


    private JComboBox firstImage, secondImage, thirdImage;

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
            e.getLocalizedMessage();
        }

        JLabel first = new JLabel("first");
        firstImage = new JComboBox(MainWindow.elements);
        JLabel second = new JLabel("second");
        secondImage = new JComboBox(MainWindow.elements);
        JLabel third = new JLabel("end");
        thirdImage = new JComboBox(MainWindow.elements);
        firstImage.setSelectedIndex(Arrays.asList(MainWindow.elements).indexOf(this.imageFirst));
        secondImage.setSelectedIndex(Arrays.asList(MainWindow.elements).indexOf(this.imageSecond));
        thirdImage.setSelectedIndex(Arrays.asList(MainWindow.elements).indexOf(this.imageThird));
        firstImage.addActionListener(this);
        secondImage.addActionListener(this);
        thirdImage.addActionListener(this);
        first.setBounds(200, 5, 70, 50);
        second.setBounds(200, 120, 70, 50);
        third.setBounds(200, 200, 70, 50);
        firstImage.setBounds(290, 5, 120, 50);
        secondImage.setBounds(290, 120, 120, 50);
        thirdImage.setBounds(290, 200, 120, 50);
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
            throw new RuntimeException(ex);
        }

    }

}
