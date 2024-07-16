package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

public class MainPanel extends JPanel implements ActionListener {


    private BufferedImage image, imageCopy;
    JComboBox<String> boxFirst, boxSecond, boxSettings;

    String lastFirst, lastSecond = "original";

    public MainPanel() {
        super();
        super.setLayout(null);
        generationElements();

    }

    public void setFace(BufferedImage img, BufferedImage imgCopy) {
        image = img;
        imageCopy = imgCopy;

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (this.image == null) {
            System.out.println("!! The JPanel image is null !!");
            return;
        }
        g.drawImage(this.image, 5, 5, this.image.getWidth(), this.image.getHeight(), null);
        g.drawImage(this.imageCopy, 5, 490, this.imageCopy.getWidth(), this.imageCopy.getHeight(), null);
        g.setFont(new Font("arial", Font.ITALIC, 20));
        g.setColor(Color.WHITE);

    }

    public void generationElements() {
        JLabel nameFirst = new JLabel("First image");
        boxFirst = new JComboBox<>(MainWindow.elements);
        boxFirst.addActionListener(this);
        nameFirst.setBounds(700, 5, 100, 50);
        boxFirst.setBounds(820, 5, 100, 50);
        super.add(nameFirst);
        super.add(boxFirst);

        JLabel nameSecond = new JLabel("Second image");
        boxSecond = new JComboBox<>(MainWindow.elements);
        boxSecond.addActionListener(this);
        nameSecond.setBounds(700, 490, 100, 50);
        boxSecond.setBounds(820, 490, 100, 50);
        super.add(nameSecond);
        super.add(boxSecond);

        JLabel nameSittings = new JLabel("Settings image");
        boxSettings = new JComboBox<>(MainWindow.methods);
        boxSettings.addActionListener(this);
        nameSittings.setBounds(700, 245, 100, 50);
        boxSettings.setBounds(820, 245, 150, 50);
        super.add(nameSittings);
        super.add(boxSettings);

        JButton open = new JButton("add method");
        open.addActionListener(e -> {
            MainWindow.nameMethod = (String) (boxSettings.getSelectedItem());
            System.out.println((String) (boxSettings.getSelectedItem()));
        });
        open.setBounds(1000, 245, 120, 50);
        super.add(open);


        JButton downloadButton = getDownloadButton();
        super.add(downloadButton);

        JButton changeFilePropertyButton = getChangeFileButton();
        super.add(changeFilePropertyButton);
    }

    private static JButton getDownloadButton() {
        JButton downloadButton = new JButton("Скачать настройки");
        downloadButton.addActionListener(e -> {
            String appConfigPath = "src/main/java/org/example/app.properties";
            try {
                downloadFile(appConfigPath);
            } catch (IOException ex) {
                ex.getLocalizedMessage();
            }
        });

        downloadButton.setBounds(700, 550, 160, 50);
        return downloadButton;
    }

    private static JButton getChangeFileButton() {
        JButton changeFilePropertyButton = new JButton("Изменить настройки");
        changeFilePropertyButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    changeFileProperty(selectedFile);
                } catch (IOException ex) {
                    ex.getLocalizedMessage();
                    JOptionPane.showMessageDialog(null, "Ошибка при изменении файла: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        changeFilePropertyButton.setBounds(880, 550, 160, 50);
        return changeFilePropertyButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        lastFirst = (String) (boxFirst.getSelectedItem());
        MainWindow.firstImage = lastFirst;

        lastSecond = (String) (boxSecond.getSelectedItem());
        MainWindow.secondImage = lastSecond;

        System.out.println(MainWindow.firstImage);
        System.out.println(MainWindow.secondImage);

    }

    private static void downloadFile(String filePath) throws IOException {
        File file = new File(filePath);

        if (!file.exists()) {
            JOptionPane.showMessageDialog(null, "Файл для скачивания не найден", "Ошибка скачивания", JOptionPane.ERROR_MESSAGE);
            MainWindow.createProperties();
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File(file.getName()));
        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File saveFile = fileChooser.getSelectedFile();
            try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
                 FileOutputStream out = new FileOutputStream(saveFile)) {
                byte[] data = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(data, 0, 1024)) != -1) {
                    out.write(data, 0, bytesRead);
                }
                JOptionPane.showMessageDialog(null, "Файл успешно скачан", "Успех", JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Произошла ошибка скачивания: " + e.getLocalizedMessage(), "Ошибка скачивания", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void changeFileProperty(File file) throws IOException {
        String appConfigPath = "src/main/java/org/example/app.properties";
        File fileConfig = new File(appConfigPath);

        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(fileConfig)) {
            properties.load(fileInputStream);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            properties.clear();
            properties.load(reader);
        }

        Set<String> keys = properties.stringPropertyNames();

        Set<String> methodsSet = new HashSet<>(Arrays.asList(MainWindow.methods));

        if (keys.isEmpty()) {
            MainWindow.createProperties();
            JOptionPane.showMessageDialog(null, "Загруженный файл пустой", "Ошибка загрузки", JOptionPane.ERROR_MESSAGE);
        } else {
            Set<String> missingMethods = new HashSet<>(methodsSet);
            missingMethods.removeAll(keys);

            if (!missingMethods.isEmpty()) {
                System.out.println("Отсутствующие методы: " + missingMethods);
                for (String method : missingMethods) {
                    properties.setProperty(method, "0");
                }
            }

            Set<String> keysToRemove = new HashSet<>(keys);
            keysToRemove.removeAll(methodsSet);

            for (String keyToRemove : keysToRemove) {
                System.out.println(keyToRemove);
                properties.remove(keyToRemove);
            }

            try (FileOutputStream fos = new FileOutputStream(fileConfig)) {
                properties.store(fos, "Updated properties");
            }
            JOptionPane.showMessageDialog(null, "Данные успешно записаны в файл app.properties", "Успех", JOptionPane.INFORMATION_MESSAGE);

        }
    }
}
