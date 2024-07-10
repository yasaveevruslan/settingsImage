package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class MainPanel extends JPanel implements ActionListener {



    private BufferedImage image, imageCopy;
    JComboBox boxFirst, boxSecond, boxSittings;

    String lastFirst, lastSecond = "original";

    public MainPanel()
    {
        super();
        super.setLayout(null);
        generationElements();

    }

    public void setFace(BufferedImage img, BufferedImage imgCopy)
    {
        image = img;
        imageCopy = imgCopy;

    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        if (this.image == null)
        {
            System.out.println("!! The JPanel image is null !!");
            return;
        }
        g.drawImage(this.image, 5, 5, this.image.getWidth(), this.image.getHeight(), null);
        g.drawImage(this.imageCopy, 5, 490, this.imageCopy.getWidth(), this.imageCopy.getHeight(), null);
        g.setFont(new Font("arial", 2, 20));
        g.setColor(Color.WHITE);

    }

    public void generationElements()
    {
        JLabel nameFirst = new JLabel("First image");
        boxFirst = new JComboBox(MainWindow.elements);
        boxFirst.addActionListener(this);
        nameFirst.setBounds(700, 5, 100, 50);
        boxFirst.setBounds(820, 5, 100, 50);
        super.add(nameFirst);
        super.add(boxFirst);

        JLabel nameSecond = new JLabel("Second image");
        boxSecond = new JComboBox(MainWindow.elements);
        boxSecond.addActionListener(this);
        nameSecond.setBounds(700, 490, 100, 50);
        boxSecond.setBounds(820, 490, 100, 50);
        super.add(nameSecond);
        super.add(boxSecond);

        JLabel nameSittings = new JLabel("Sittings image");
        boxSittings = new JComboBox(MainWindow.methods);
        boxSittings.addActionListener(this);
        nameSittings.setBounds(700, 245, 100, 50);
        boxSittings.setBounds(820, 245, 100, 50);
        super.add(nameSittings);
        super.add(boxSittings);

        JButton open = new JButton("add method");
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainWindow.nameMethod = (String) (boxSittings.getSelectedItem());
                System.out.println((String) (boxSittings.getSelectedItem()));
            }
        });
        open.setBounds(950, 245, 120, 50);
        super.add(open);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        lastFirst = (String)(boxFirst.getSelectedItem());
        MainWindow.firstImage = lastFirst;

        lastSecond = (String)(boxSecond.getSelectedItem());
        MainWindow.secondImage = lastSecond;

        System.out.println(MainWindow.firstImage);
        System.out.println(MainWindow.secondImage);

    }
}
