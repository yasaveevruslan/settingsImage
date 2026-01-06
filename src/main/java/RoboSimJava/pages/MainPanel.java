package RoboSimJava.pages;

import RoboSimJava.MainWindow;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainPanel extends JPanel implements ActionListener {

    private static final Logger logger = Logger.getLogger(MainPanel.class.getName());

    private BufferedImage image, imageCopy;
    JComboBox<String> boxFirst, boxSecond, boxSettings, boxUse;

    String lastFirst, lastSecond = "original";
    public static Font font = new Font("Microsoft YaHei", Font.PLAIN, 16);
    public static Font fontLabel = new Font("Microsoft YaHei", Font.BOLD, 16);

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
        g.drawImage(this.image, 5, 5, (int)(this.image.getWidth() / 1.5), (int)(this.image.getHeight() / 1.5), null);
        g.drawImage(this.imageCopy, 5, 330, (int)(this.imageCopy.getWidth() / 1.5), (int)(this.imageCopy.getHeight() / 1.5), null);
        g.setFont(new Font("Microsoft YaHei", Font.BOLD, 30));
        g.setColor(Color.BLACK);

    }

    private void generationElements() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(239, 239, 239));

        GridBagConstraints gbc = new GridBagConstraints();

        Dimension labelSize = new Dimension(230, 50);
        Dimension comboBoxSize = new Dimension(230, 50);
        Dimension buttonSize = new Dimension(230, 50);

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // First column
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        JLabel nameFirst = new JLabel("Первое изображение");
        nameFirst.setFont(fontLabel);
        nameFirst.setPreferredSize(labelSize);
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(nameFirst, gbc);

        boxFirst = new JComboBox<>(MainWindow.images.toArray(new String[0]));
        boxFirst.addActionListener(this);
        boxFirst.setPreferredSize(comboBoxSize);
        boxFirst.setFont(font);
        boxFirst.setBackground(new Color(239, 239, 239));
        boxFirst.setOpaque(true);
        boxFirst.setBorder(BorderFactory.createLineBorder(new Color(21, 84, 130), 2));

        gbc.gridy = 2;
        mainPanel.add(boxFirst, gbc);

        gbc.gridy = 3;

        JLabel nameSettings = new JLabel("Настройка изображения");
        nameSettings.setFont(fontLabel);
        nameSettings.setPreferredSize(labelSize);
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(nameSettings, gbc);

        boxSettings = new JComboBox<>(MainWindow.functionsDefault);
        boxSettings.addActionListener(this);
        boxSettings.setPreferredSize(comboBoxSize);
        boxSettings.setFont(font);
        boxSettings.setBackground(new Color(239, 239, 239));
        boxSettings.setOpaque(true);
        boxSettings.setBorder(BorderFactory.createLineBorder(new Color(21, 84, 130), 2));

        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(boxSettings, gbc);

        boxUse = new JComboBox<>(MainWindow.functions.toArray(new String[0]));
        boxUse.addActionListener(this);
        boxUse.setPreferredSize(comboBoxSize);
        boxUse.setFont(font);
        boxUse.setBackground(new Color(239, 239, 239));
        boxUse.setOpaque(true);
        boxUse.setBorder(BorderFactory.createLineBorder(new Color(21, 84, 130), 2));

        gbc.gridx = 0;
        gbc.gridy = 6;
        mainPanel.add(boxUse, gbc);

        gbc.gridy = 7;

        JButton downloadButton = getDownloadButton();
        downloadButton.setPreferredSize(buttonSize);
        downloadButton.setFont(font);
        downloadButton.setBackground(new Color(239, 239, 239));
        downloadButton.setOpaque(true);
        Border dborder = BorderFactory.createLineBorder(new Color(21, 84, 130), 3);
        downloadButton.setBorder(dborder);
        downloadButton.setFocusPainted(false);
        mainPanel.add(downloadButton, gbc);

        // Second column
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel nameSecond = new JLabel("Второе изображение");
        nameSecond.setFont(fontLabel);
        nameSecond.setPreferredSize(labelSize);
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(nameSecond, gbc);

        boxSecond = new JComboBox<>(MainWindow.images.toArray(new String[0]));
        boxSecond.addActionListener(this);
        boxSecond.setPreferredSize(comboBoxSize);
        boxSecond.setFont(font);
        boxSecond.setBackground(new Color(239, 239, 239));
        boxSecond.setOpaque(true);
        boxSecond.setBorder(BorderFactory.createLineBorder(new Color(21, 84, 130), 2));

        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(boxSecond, gbc);

        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.NORTH;

        JLabel nameAdd = new JLabel("");
        nameAdd.setPreferredSize(labelSize);
        gbc.gridx = 1;
        gbc.gridy = 4;
        mainPanel.add(nameAdd, gbc);

        JButton addButton = new JButton("  Добавить метод");
        URL iconURL = MainWindow.class.getResource("/plus.png");
        if (iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            addButton.setIcon(icon);
            addButton.setHorizontalTextPosition(SwingConstants.RIGHT);
        } else {
            System.err.println("Icon not found!");
        }
        addButton.setBackground(new Color(239, 239, 239));
        addButton.setOpaque(true);
        Border border = BorderFactory.createLineBorder(new Color(21, 84, 130), 3);
        addButton.setBorder(border);
        addButton.setFocusPainted(false);

        addButton.addActionListener(e -> {
            int indexFirst = boxFirst.getSelectedIndex();
            int indexSecond = boxSecond.getSelectedIndex();
            MainWindow.nameImage++;
            MainWindow.images.add("" + MainWindow.nameImage);
            MainWindow.functions.add(MainWindow.nameImage + "." + boxSettings.getSelectedItem());
            MainWindow.nameMethod = (MainWindow.nameImage + "." + boxSettings.getSelectedItem());
            updateComboBox();
            boxFirst.setSelectedIndex(indexFirst);
            boxSecond.setSelectedIndex(indexSecond);
        });
        addButton.setPreferredSize(buttonSize);
        addButton.setFont(font);
        gbc.gridx = 1;
        gbc.gridy = 5;
        mainPanel.add(addButton, gbc);


        JButton openButton = new JButton("Открыть метод");
        openButton.setBackground(new Color(239, 239, 239));
        openButton.setOpaque(true);
        Border borderOpen = BorderFactory.createLineBorder(new Color(21, 84, 130), 3);
        openButton.setBorder(borderOpen);
        openButton.setFocusPainted(false);

        openButton.addActionListener(e -> MainWindow.nameMethod = (String) (boxUse.getSelectedItem()));
        openButton.setPreferredSize(buttonSize);
        openButton.setFont(font);
        gbc.gridx = 1;
        gbc.gridy = 6;
        mainPanel.add(openButton, gbc);

        gbc.gridy = 7;
        JButton changeFilePropertyButton = getChangeFileButton();
        changeFilePropertyButton.setFont(font);
        changeFilePropertyButton.setPreferredSize(buttonSize);
        changeFilePropertyButton.setBackground(new Color(239, 239, 239));
        changeFilePropertyButton.setOpaque(true);
        Border cborder = BorderFactory.createLineBorder(new Color(21, 84, 130), 3);
        changeFilePropertyButton.setBorder(cborder);
        changeFilePropertyButton.setFocusPainted(false);
        mainPanel.add(changeFilePropertyButton, gbc);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(-500, 10, 0, 10));
        add(mainPanel, BorderLayout.EAST);
    }



    @NotNull
    private static Path getAppConfigPath() {
        return Paths.get("src", "main", "java", "org", "example", "infa", "app.properties");
    }

    @NotNull
    private static JButton getDownloadButton() {
        JButton downloadButton = new JButton("Скачать настройки");
        downloadButton.addActionListener(e -> {
            Path appConfigPath = getAppConfigPath().toFile().toPath();
            try {
                downloadFile(appConfigPath.toFile());
            } catch (IOException ex) {
                logger.severe("Ошибка при скачивании файла с настройками: " + ex.getMessage());
                JOptionPane.showMessageDialog(null, "Ошибка при скачивании файла: " + ex.getMessage(), "Ошибка скачивания", JOptionPane.ERROR_MESSAGE);
            }
        });
        return downloadButton;
    }

    @NotNull
    private static JButton getChangeFileButton() {
        JButton changeFilePropertyButton = new JButton("Изменить настройки");
        changeFilePropertyButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Properties files", "properties");
            fileChooser.setFileFilter(filter);

            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    changeFileProperty(selectedFile);
                } catch (IOException ex) {
                    logger.severe("Ошибка при изменении файла: " + ex.getMessage());
                    JOptionPane.showMessageDialog(null, "Ошибка при изменении файла: " + ex.getMessage(), "Ошибка изменения", JOptionPane.ERROR_MESSAGE);
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

    private void updateComboBox() {
        boxFirst.removeAllItems();
        for (String item : MainWindow.images) {
            boxFirst.addItem(item);
        }

        boxSecond.removeAllItems();
        for (String item : MainWindow.images) {
            boxSecond.addItem(item);
        }

        boxUse.removeAllItems();
        for (String item : MainWindow.functions) {
            boxUse.addItem(item);
        }
    }

    private static void downloadFile(@NotNull File filePath) throws IOException {
        if (!filePath.exists()) {
            logger.info("Файл app.properties для скачивания не найден");
            JOptionPane.showMessageDialog(null, "Файл для скачивания не найден", "Ошибка скачивания", JOptionPane.ERROR_MESSAGE);
            MainWindow.createProperties();
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File(filePath.getName()));
        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File saveFile = fileChooser.getSelectedFile();
            try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(filePath));
                 FileOutputStream out = new FileOutputStream(saveFile)) {
                byte[] data = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(data, 0, 1024)) != -1) {
                    out.write(data, 0, bytesRead);
                }
                JOptionPane.showMessageDialog(null, "Файл успешно скачан", "Успех", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                logger.warning("Произошла ошибка скачивания: " + e.getMessage());
                JOptionPane.showMessageDialog(null, "Произошла ошибка скачивания: " + e.getMessage(), "Ошибка скачивания", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void changeFileProperty(File file) throws IOException {
        Path appConfigPath = getAppConfigPath();
        File fileConfig = appConfigPath.toFile();

        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(fileConfig)) {
            properties.load(fileInputStream);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            properties.clear();
            properties.load(reader);
        }

        Set<String> keys = properties.stringPropertyNames();
        Set<String> methodsSet = new HashSet<>(MainWindow.functions);

        if (keys.isEmpty()) {
            MainWindow.createProperties();
            JOptionPane.showMessageDialog(null, "Загруженный файл пустой", "Ошибка загрузки", JOptionPane.ERROR_MESSAGE);
        } else {
            Set<String> missingMethods = new HashSet<>(methodsSet);
            missingMethods.removeAll(keys);
            for (String method : missingMethods) {
                properties.setProperty(method, "0");
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
        }
    }
}