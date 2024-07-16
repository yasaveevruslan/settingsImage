package org.example.pages;

import org.example.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;


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

    private String name, imageFirst, imageSecond;
    public PanelBinaryNot(String name, String imageFirst, String imageSecond)
    {
        super();
        this.name = name;
        super.setLayout(null);
        generationElements(name, imageFirst, imageSecond);
    }


    private JComboBox firstImage, secondImage;

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
            e.getLocalizedMessage();
        }

        JLabel first = new JLabel("first");
        firstImage = new JComboBox(MainWindow.elements);
        JLabel second = new JLabel("end");
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

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            imageFirst = (String) firstImage.getSelectedItem();
            imageSecond = (String) secondImage.getSelectedItem();
            MainWindow.updateProperty(name, imageFirst + ", " + imageSecond);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

}
