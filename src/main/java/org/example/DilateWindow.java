package org.example;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;


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

        JLabel degrees = new JLabel("", JLabel.CENTER);
        JSlider slider = new JSlider(0, 100, cofPower);
        slider.setBounds(50, 200, 600, 50);
        degrees.setBounds(250, 250, 200, 50);

        slider.setPaintTrack(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        slider.setMajorTickSpacing(5);
        slider.setMinorTickSpacing(5);
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {

                try {
                    degrees.setText("Value of the slider is: " + ((JSlider)e.getSource()).getValue());
                    cofPower = ((JSlider)e.getSource()).getValue();
                    MainWindow.updateProperty(name, imageFirst + ", " + imageSecond + ", " + cofPower);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

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
            throw new RuntimeException(ex);
        }

    }

}
