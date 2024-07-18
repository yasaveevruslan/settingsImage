package org.example.pages;

import org.example.MainWindow;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainPanel extends JPanel implements ActionListener {

    private static final Logger logger = Logger.getLogger(MainPanel.class.getName());

    private BufferedImage image, imageCopy;
    JComboBox<String> boxFirst, boxSecond, boxSettings;

    String lastFirst, lastSecond = "original";
    public static Font font = new Font("Arial", Font.PLAIN, 16);

    public MainPanel() {
        super(new BorderLayout());
        generationElements();

    }

    public void setFace(BufferedImage img, BufferedImage imgCopy) {
        image = img;
        imageCopy = imgCopy;
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (this.image == null) {
            logger.warning("Изображение на панели пустое");
            MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.WARNING, "Изображение на панели пустое"));
            return;
        }
        g.drawImage(this.image, 5, 5, this.image.getWidth(), this.image.getHeight(), null);
        g.drawImage(this.imageCopy, 5, 490, this.imageCopy.getWidth(), this.imageCopy.getHeight(), null);
        g.setFont(new Font("Arial", Font.ITALIC, 30));
        g.setColor(Color.BLACK);

    }

    private void generationElements() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel nameFirst = new JLabel("Первое изображение");
        nameFirst.setFont(font);
        boxFirst = new JComboBox<>(MainWindow.elements);
        boxFirst.addActionListener(this);
        boxFirst.setPreferredSize(new Dimension(230, 50));
        boxFirst.setFont(font);
        topPanel.add(nameFirst);
        topPanel.add(boxFirst);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel nameSecond = new JLabel("Второе изображение");
        nameSecond.setFont(font);

        boxSecond = new JComboBox<>(MainWindow.elements);
        boxSecond.addActionListener(this);
        boxSecond.setPreferredSize(new Dimension(220, 60));
        boxSecond.setFont(font);
        bottomPanel.add(nameSecond);
        bottomPanel.add(boxSecond);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel nameSettings = new JLabel("Настройка изображения");
        nameSettings.setFont(font);
        boxSettings = new JComboBox<>(MainWindow.methods);
        boxSettings.addActionListener(this);
        boxSettings.setPreferredSize(new Dimension(220, 50));
        boxSettings.setFont(font);

        centerPanel.add(nameSettings);
        centerPanel.add(boxSettings);

        JButton addButton = new JButton("Добавить метод");
        addButton.addActionListener(e -> {
            MainWindow.nameMethod = (String) (boxSettings.getSelectedItem());
            System.out.println((String) (boxSettings.getSelectedItem()));
        });
        addButton.setPreferredSize(new Dimension(220, 50));
        addButton.setFont(font);

        centerPanel.add(addButton);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton downloadButton = getDownloadButton();
        downloadButton.setPreferredSize(new Dimension(220, 50));
        downloadButton.setFont(font);


        actionPanel.add(downloadButton);
        JButton changeFilePropertyButton = getChangeFileButton();
        changeFilePropertyButton.setFont(font);
        changeFilePropertyButton.setPreferredSize(new Dimension(220, 50));
        actionPanel.add(changeFilePropertyButton);


        JPanel mainPanel = new JPanel(new GridLayout(4, 1));
        mainPanel.add(topPanel);
        mainPanel.add(centerPanel);
        mainPanel.add(bottomPanel);
        mainPanel.add(actionPanel);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 40));

        add(mainPanel, BorderLayout.EAST);
    }

    private static JButton getDownloadButton() {
        JButton downloadButton = new JButton("Скачать настройки");
        downloadButton.addActionListener(e -> {
            String appConfigPath = "src/main/java/org/example/infa/app.properties";
            try {
                downloadFile(appConfigPath);
            } catch (IOException ex) {
                logger.severe("Ошибка при скачивании файла с настройками: " + ex.getLocalizedMessage());
                MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.SEVERE, "Ошибка при скачивании файла с настройками: " + ex.getLocalizedMessage()));
            }
        });

        return downloadButton;
    }

    private static JButton getChangeFileButton() {
        JButton changeFilePropertyButton = new JButton("Изменить настройки");
        changeFilePropertyButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Properties files", "properties");
            fileChooser.setFileFilter(filter);

            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    changeFileProperty(selectedFile);
                } catch (IOException ex) {
                    logger.severe("Ошибка при изменении файла: " + ex.getMessage());
                    MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.SEVERE, "Ошибка при изменении файла: " + ex.getMessage()));
                }
            }
        });
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
            logger.info("Файл app.properties для скачивания не найден");
            MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.INFO, "Файл app.properties для скачивания не найден"));
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
                logger.warning("Произошла ошибка скачивания: " + e.getLocalizedMessage());
                MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.WARNING, "Произошла ошибка скачивания: " + e.getLocalizedMessage()));
                JOptionPane.showMessageDialog(null, "Произошла ошибка скачивания: " + e.getLocalizedMessage(), "Ошибка скачивания", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void changeFileProperty(File file) throws IOException {
        String appConfigPath = "src/main/java/org/example/infa/app.properties";
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
                for (String method : missingMethods) {
                    properties.setProperty(method, "0");
                }
            }

            Set<String> keysToRemove = new HashSet<>(keys);
            keysToRemove.removeAll(methodsSet);

            for (String keyToRemove : keysToRemove) {
                properties.remove(keyToRemove);
            }

            try (FileOutputStream fos = new FileOutputStream(fileConfig)) {
                properties.store(fos, "Updated properties");
            }
            JOptionPane.showMessageDialog(null, "Данные успешно записаны в файл app.properties", "Успех", JOptionPane.INFORMATION_MESSAGE);
            logger.fine("Данные успешно записаны в файл app.properties");
            MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.FINE, "Данные успешно записаны в файл app.properties"));
        }
    }
}
