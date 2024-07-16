package org.example.pages;

import org.example.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;

public class CvtWindow {

    private static String imageFirst;
    private static String imageSecond;
    private static String setting;

    public CvtWindow(String nameFirst, String nameSecond, String setting)
    {
        imageFirst = nameFirst;
        imageSecond = nameSecond;
        setting = setting;
    }



    public static void generationWindow(String name, String nameFirst, String nameSecond, String setting)
    {
        imageFirst = nameFirst;
        imageSecond = nameSecond;
        setting = setting;

        JFrame frame = new JFrame(name);
        frame.setBounds(1200, 0, 700, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        PanelCvt panel = new PanelCvt(name, imageFirst, imageSecond, setting);
        frame.setContentPane(panel);
        frame.setVisible(true);

        MainWindow.nameMethod = "none";
    }
}

class PanelCvt extends JPanel implements ActionListener {

    private String name, imageFirst, imageSecond;
    private String setting;
    private int cvtCode;
    public PanelCvt(String name, String imageFirst, String imageSecond, String setting)
    {
        super();
        this.name = name;
        super.setLayout(null);
        generationElements(name, imageFirst, imageSecond, setting);
    }


    private JComboBox firstImage, secondImage, cvtColor;

    private void generationElements(String name, String imageFirst, String imageSecond, String setting)
    {
        try
        {
            String[] lastValues = MainWindow.loadProperty(MainWindow.nameMethod).split(", ");
            if(lastValues.length>2)
            {
                String settingValue = lastValues[3];
                String imageFirstValue = lastValues[0];
                String imageSecondValue = lastValues[1];
                this.imageFirst  = imageFirstValue;
                this.imageSecond = imageSecondValue;
                this.setting = settingValue;
                this.cvtCode = Integer.parseInt(lastValues[2]);
            }
            else
            {
                this.imageFirst = imageFirst;
                this.imageSecond = imageSecond;
                this.setting = setting;
                this.cvtCode = MainWindow.cvt.get(setting);
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
        JLabel cvt = new JLabel("svtColor");
        cvtColor = new JComboBox(MainWindow.cvt.keySet().toArray(new String[0]));
        firstImage.setSelectedIndex(Arrays.asList(MainWindow.elements).indexOf(this.imageFirst));
        secondImage.setSelectedIndex(Arrays.asList(MainWindow.elements).indexOf(this.imageSecond));
        cvtColor.setSelectedIndex(Arrays.asList(MainWindow.cvt.keySet().toArray(new String[0])).indexOf(this.setting));
        firstImage.addActionListener(this);
        secondImage.addActionListener(this);
        cvtColor.addActionListener(this);
        first.setBounds(100, 5, 70, 50);
        second.setBounds(100, 120, 70, 50);
        cvt.setBounds(100, 200, 70, 50);
        firstImage.setBounds(290, 5, 120, 50);
        secondImage.setBounds(290, 120, 120, 50);
        cvtColor.setBounds(290, 200, 200, 50);
        super.add(first);
        super.add(firstImage);
        super.add(second);
        super.add(secondImage);
        super.add(cvt);
        super.add(cvtColor);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            imageFirst = (String) firstImage.getSelectedItem();
            imageSecond = (String) secondImage.getSelectedItem();
            setting = (String) cvtColor.getSelectedItem();
            cvtCode = MainWindow.cvt.get(setting);
            MainWindow.updateProperty(name, imageFirst + ", " + imageSecond + ", " + cvtCode + ", " + setting);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

}
