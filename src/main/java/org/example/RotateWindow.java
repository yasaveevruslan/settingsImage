package org.example;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class RotateWindow {

    public static String imageFirst;
    public static String imageSecond;
    public static int degreesPosition;

    public RotateWindow(String nameFirst, String nameSecond, int degrees)
    {
        imageFirst = nameFirst;
        imageSecond = nameSecond;
        degreesPosition = degrees;
    }



    public void generationWindow(String name)
    {
        JFrame frame = new JFrame(name);
        frame.setBounds(1200, 0, 700, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Panel panel = new Panel();
        frame.setContentPane(panel);
        frame.setVisible(true);

        MainWindow.nameMethod = "none";
    }
}

class Panel extends JPanel implements ActionListener {
    public Panel()
    {
        super();
        super.setLayout(null);
        generationElements();
    }


    private JComboBox firstImage, secondImage;

    private void generationElements()
    {
        JLabel first = new JLabel("first");
        firstImage = new JComboBox(MainWindow.elements);
        JLabel second = new JLabel("end");
        secondImage = new JComboBox(MainWindow.elements);
        secondImage.setSelectedIndex(10);
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
        JSlider slider = new JSlider(-360, 360, RotateWindow.degreesPosition);
        slider.setBounds(50, 200, 600, 50);
        degrees.setBounds(250, 250, 200, 50);

        slider.setPaintTrack(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        slider.setMajorTickSpacing(45);
        slider.setMinorTickSpacing(5);
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                degrees.setText("Value of the slider is: " + ((JSlider)e.getSource()).getValue());
                RotateWindow.degreesPosition = ((JSlider)e.getSource()).getValue();
                System.out.println(RotateWindow.degreesPosition);
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
        RotateWindow.imageFirst = (String) firstImage.getSelectedItem();
        RotateWindow.imageSecond = (String) secondImage.getSelectedItem();
    }

}
