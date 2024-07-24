package org.example;

import org.example.functions.*;
import org.example.pages.*;
import org.jetbrains.annotations.NotNull;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MainWindow {

    private static final Logger logger = Logger.getLogger(MainWindow.class.getName());
    public static FileHandler fileHandler;

    public static final String[] elements = {"original", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
            "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
            "30", "31", "32", "33", "34", "35", "36", "37", "38", "39",
            "40", "41", "42", "43", "44", "45", "46", "47", "48", "49"};

    public static final String[] methods = {"none",
            "rotateFirst", "rotateSecond", "rotateThird", "rotateFourth",
            "colorFirst", "colorSecond", "colorThird", "colorFourth",
            "cvtFirst", "cvtSecond", "cvtThird", "cvtFourth",
            "blurFirst", "blurSecond", "blurThird", "blurFourth",
            "dilateFirst", "dilateSecond", "dilateThird", "dilateFourth",
            "binaryNotFirst", "binaryNotSecond", "binaryNotThird", "binaryNotFourth",
            "binaryAndFirst", "binaryAndSecond", "binaryAndThird", "binaryAndFourth",
            "binaryOrFirst", "binaryOrSecond", "binaryOrThird", "binaryOrFourth",
            "rectFirst", "rectSecond", "rectThird", "rectFourth",
            /*"contourDrawFirst", "contourDrawSecond", "contourDrawThird", "contourDrawFourth",
            "areaFirst", "areaSecond", "areaThird", "areaFourth"*/
    };

    public static HashMap<String, Integer> cvt = new HashMap<>();

    public static HashMap<String, Object> objectsMethods = new HashMap<>();

    public static String nameMethod = "none";
    public static String lastNameMethod = "none";

    public static String firstImage = "original", secondImage = "original";

    public static Mat original, mat1, mat2, mat3, mat4, mat5, mat6, mat7, mat8, mat9,
            mat10, mat11, mat12, mat13, mat14, mat15, mat16, mat17, mat18, mat19,
            mat20, mat21, mat22, mat23, mat24, mat25, mat26, mat27, mat28, mat29,
            mat30, mat31, mat32, mat33, mat34, mat35, mat36, mat37, mat38, mat39,
            mat40, mat41, mat42, mat43, mat44, mat45, mat46, mat47, mat48, mat49;


    public static HashMap<String, Mat> picture = new HashMap<>();

    static
    {
        nu.pattern.OpenCV.loadLocally();
    }

    public static void main(String[] args) throws Exception {
        initializePicture();

        try {
            fileHandler = new FileHandler("log.log");
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        createProperties();


        MatToBufImg matToBufferedImageConverter = new MatToBufImg();
        MatToBufImg matToBufferedImageConverter2 = new MatToBufImg();


        VideoCapture camera = new VideoCapture(0);

        if (!camera.isOpened()) {
            logger.severe("Камера недоступна");
            fileHandler.publish(new java.util.logging.LogRecord(Level.SEVERE, "Камера недоступна"));
            return;
        }

        logger.info("Камера найдена: " + camera);

        JFrame frame = new JFrame();
        frame.setBounds(0, 0, 1200, 1015);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("SettingsImage");

        URL iconURL = MainWindow.class.getResource("/favicon.png");
        if (iconURL != null)
        {
            ImageIcon icon = new ImageIcon(iconURL);
            frame.setIconImage(icon.getImage());
        }
        else
        {
            System.err.println("Icon not found!");
        }
        MainPanel facePanel = new MainPanel();
        facePanel.setBackground(new Color(239, 239, 239));

        frame.setContentPane(facePanel);


        frame.setVisible(true);
        while (true) {
            try {
                initializeMethods();
                putPicture();

                if (!camera.read(original)) {
                    logger.warning("Не удалось захватить кадр из камеры");
                    fileHandler.publish(new java.util.logging.LogRecord(Level.WARNING, "Не удалось захватить кадр из камеры"));
                    JOptionPane.showMessageDialog(null, "Не удалось захватить кадр из камеры", "Предупреждение", JOptionPane.WARNING_MESSAGE);
                    break;
                }

                if (picture.get(firstImage).empty()) {
                    camera.read(picture.get(firstImage));
                }

                if (picture.get(secondImage).empty()) {
                    camera.read(picture.get(secondImage));
                }

                if (!original.empty()) {

                    matToBufferedImageConverter.setMatrix(picture.get(firstImage), ".jpg");
                    BufferedImage bufImgFirst = matToBufferedImageConverter.getBufferedImage();

                    matToBufferedImageConverter2.setMatrix(picture.get(secondImage), ".jpg");
                    BufferedImage bufImgSecond = matToBufferedImageConverter2.getBufferedImage();

                    facePanel.setFace(bufImgFirst, bufImgSecond);
                    facePanel.repaint();
                } else {
                    logger.warning("Получен пустой кадр");
                    fileHandler.publish(new java.util.logging.LogRecord(Level.WARNING, "Получен пустой кадр"));
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    System.exit(1);
                    break;
                }
            } catch (Exception e) {
                logger.severe("Произошла ошибка: " + e.getMessage());
                fileHandler.publish(new java.util.logging.LogRecord(Level.SEVERE, "Произошла ошибка: " + e.getMessage()));
            }

        }

        original.release();
        picture.get(firstImage).release();
        picture.get(secondImage).release();

    }


    public static void initializeMethods() {

        try {
            switch (nameMethod) {
                case "none":
                    break;

                case "rotateFirst": {
                    lastNameMethod = nameMethod;
                    RotateWindow.generationWindow(nameMethod, "original", "31", 0);

                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "original" + ", " + "1" + ", " + "0");


                        RotateImage rotateImage = new RotateImage(picture.get("original"), 0);
                        rotateImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, rotateImage);
                    }
                }
                break;

                case "rotateSecond": {
                    lastNameMethod = nameMethod;
                    RotateWindow.generationWindow(nameMethod, "1", "2", 0);

                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "1" + ", " + "2" + ", " + "0");


                        RotateImage rotateImage = new RotateImage(picture.get("1"), 0);
                        rotateImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, rotateImage);
                    }
                }
                break;

                case "rotateThird": {
                    lastNameMethod = nameMethod;
                    RotateWindow.generationWindow(nameMethod, "2", "3", 0);

                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "2" + ", " + "3" + ", " + "0");

                        RotateImage rotateImage = new RotateImage(picture.get("2"), 0);
                        rotateImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, rotateImage);
                    }
                }
                break;

                case "rotateFourth": {
                    lastNameMethod = nameMethod;
                    RotateWindow.generationWindow(nameMethod, "3", "4", 0);

                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "3" + ", " + "4" + ", " + "0");

                        RotateImage rotateImage = new RotateImage(picture.get("3"), 0);
                        rotateImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, rotateImage);
                    }
                }
                break;


                case "colorFirst": {
                    lastNameMethod = nameMethod;
                    ColorWindow.generationWindow(nameMethod, "original", "5", 0, 255, 0, 255, 0, 255);
                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "original" + ", " + "5" + ", " + "0" + ", " + "255" + ", " + "0" + ", " + "255" + ", " + "0" + ", " + "255");

                        ColorImage colorImage = new ColorImage(picture.get("original"), 0, 255, 0, 255, 0, 255);
                        colorImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, colorImage);
                    }
                }
                break;

                case "colorSecond":
                    lastNameMethod = nameMethod;
                    ColorWindow.generationWindow(nameMethod, "5", "6", 0, 255, 0, 255, 0, 255);
                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "5" + ", " + "6" + ", " + "0" + ", " + "255" + ", " + "0" + ", " + "255" + ", " + "0" + ", " + "255");

                        ColorImage colorImage = new ColorImage(picture.get("5"), 0, 255, 0, 255, 0, 255);
                        colorImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, colorImage);
                    }
                    break;

                case "colorThird":
                    lastNameMethod = nameMethod;
                    ColorWindow.generationWindow(nameMethod, "6", "7", 0, 255, 0, 255, 0, 255);
                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "6" + ", " + "7" + ", " + "0" + ", " + "255" + ", " + "0" + ", " + "255" + ", " + "0" + ", " + "255");

                        ColorImage colorImage = new ColorImage(picture.get("6"), 0, 255, 0, 255, 0, 255);
                        colorImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, colorImage);
                    }
                    break;

                case "colorFourth":
                    lastNameMethod = nameMethod;
                    ColorWindow.generationWindow(nameMethod, "7", "8", 0, 255, 0, 255, 0, 255);
                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "7" + ", " + "8" + ", " + "0" + ", " + "255" + ", " + "0" + ", " + "255" + ", " + "0" + ", " + "255");

                        ColorImage colorImage = new ColorImage(picture.get("7"), 0, 255, 0, 255, 0, 255);
                        colorImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, colorImage);
                    }
                    break;


                case "cvtFirst": {
                    lastNameMethod = nameMethod;
                    CvtWindow.generationWindow(nameMethod, "original", "9", "COLOR_BGR2HSV");
                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "original" + ", " + "9" + ", " + cvt.get("COLOR_BGR2HSV"));


                        CvtImage cvtImage = new CvtImage(picture.get("original"), cvt.get("COLOR_BGR2HSV"));
                        cvtImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, cvtImage);
                    }
                }
                break;

                case "cvtSecond": {
                    lastNameMethod = nameMethod;
                    CvtWindow.generationWindow(nameMethod, "9", "10", "COLOR_BGR2HSV");
                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "9" + ", " + "10" + ", " + cvt.get("COLOR_BGR2HSV"));


                        CvtImage cvtImage = new CvtImage(picture.get("9"), cvt.get("COLOR_BGR2HSV"));
                        cvtImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, cvtImage);
                    }
                }
                break;

                case "cvtThird": {
                    lastNameMethod = nameMethod;
                    CvtWindow.generationWindow(nameMethod, "10", "11", "COLOR_BGR2HSV");
                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "10" + ", " + "11" + ", " + cvt.get("COLOR_BGR2HSV"));


                        CvtImage cvtImage = new CvtImage(picture.get("10"), cvt.get("COLOR_BGR2HSV"));
                        cvtImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, cvtImage);
                    }
                }
                break;

                case "cvtFourth": {
                    lastNameMethod = nameMethod;
                    CvtWindow.generationWindow(nameMethod, "11", "12", "COLOR_BGR2HSV");
                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "11" + ", " + "12" + ", " + cvt.get("COLOR_BGR2HSV"));

                        CvtImage cvtImage = new CvtImage(picture.get("11"), cvt.get("COLOR_BGR2HSV"));
                        cvtImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, cvtImage);
                    }
                }
                break;


                case "blurFirst": {
                    lastNameMethod = nameMethod;
                    BlurWindow.generationWindow(nameMethod, "original", "13", 1);
                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "original" + ", " + "13" + ", " + "1");


                        BlurImage blurImage = new BlurImage(picture.get("original"), 1);
                        blurImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, blurImage);
                    }
                }
                break;

                case "blurSecond": {
                    lastNameMethod = nameMethod;
                    BlurWindow.generationWindow(nameMethod, "13", "14", 1);
                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "13" + ", " + "14" + ", " + "1");


                        BlurImage blurImage = new BlurImage(picture.get("13"), 1);
                        blurImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, blurImage);
                    }
                }
                break;

                case "blurThird": {
                    lastNameMethod = nameMethod;
                    BlurWindow.generationWindow(nameMethod, "14", "15", 1);
                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "14" + ", " + "15" + ", " + "1");


                        BlurImage blurImage = new BlurImage(picture.get("14"), 1);
                        blurImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, blurImage);
                    }
                }
                break;

                case "blurFourth": {
                    lastNameMethod = nameMethod;
                    BlurWindow.generationWindow(nameMethod, "15", "16", 1);
                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "15" + ", " + "16" + ", " + "1");


                        BlurImage blurImage = new BlurImage(picture.get("15"), 1);
                        blurImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, blurImage);
                    }
                }
                break;


                case "dilateFirst": {
                    lastNameMethod = nameMethod;
                    DilateWindow.generationWindow(nameMethod, "original", "17", 1);
                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "original" + ", " + "17" + ", " + "1");


                        DilateImage dilateImage = new DilateImage(picture.get("original"), 1);
                        dilateImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, dilateImage);
                    }
                }
                break;

                case "dilateSecond": {
                    lastNameMethod = nameMethod;
                    DilateWindow.generationWindow(nameMethod, "17", "18", 1);
                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "17" + ", " + "18" + ", " + "1");


                        DilateImage dilateImage = new DilateImage(picture.get("17"), 1);
                        dilateImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, dilateImage);
                    }
                }
                break;

                case "dilateThird": {
                    lastNameMethod = nameMethod;
                    DilateWindow.generationWindow(nameMethod, "18", "19", 1);
                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "18" + ", " + "19" + ", " + "1");


                        DilateImage dilateImage = new DilateImage(picture.get("18"), 1);
                        dilateImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, dilateImage);
                    }
                }
                break;

                case "dilateFourth": {
                    lastNameMethod = nameMethod;
                    DilateWindow.generationWindow(nameMethod, "19", "20", 1);
                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "19" + ", " + "20" + ", " + "1");


                        DilateImage dilateImage = new DilateImage(picture.get("19"), 1);
                        dilateImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, dilateImage);
                    }
                }
                break;

                case "binaryNotFirst": {
                    lastNameMethod = nameMethod;
                    BinaryNotWindow.generationWindow(nameMethod, "9", "21");
                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "9" + ", " + "21");


                        BinaryNotImage binaryNotImage = new BinaryNotImage(picture.get("9"));
                        binaryNotImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, binaryNotImage);
                    }
                }
                break;

                case "binaryNotSecond": {
                    lastNameMethod = nameMethod;
                    BinaryNotWindow.generationWindow(nameMethod, "10", "22");
                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "10" + ", " + "22");


                        BinaryNotImage binaryNotImage = new BinaryNotImage(picture.get("10"));
                        binaryNotImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, binaryNotImage);
                    }
                }
                break;

                case "binaryNotThird": {
                    lastNameMethod = nameMethod;
                    BinaryNotWindow.generationWindow(nameMethod, "11", "23");
                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "11" + ", " + "23");


                        BinaryNotImage binaryNotImage = new BinaryNotImage(picture.get("11"));
                        binaryNotImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, binaryNotImage);
                    }
                }
                break;

                case "binaryNotFourth": {
                    lastNameMethod = nameMethod;
                    BinaryNotWindow.generationWindow(nameMethod, "12", "24");
                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "12" + ", " + "24");


                        BinaryNotImage binaryNotImage = new BinaryNotImage(picture.get("12"));
                        binaryNotImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, binaryNotImage);
                    }
                }
                break;


                case "binaryAndFirst": {
                    lastNameMethod = nameMethod;
                    BinaryAndWindow.generationWindow(nameMethod, "9", "9", "25");
                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "9" + ", " + "9" + ", " + "25");


                        BinaryAndImage binaryAndImage = new BinaryAndImage(picture.get("9"), picture.get("9"));
                        binaryAndImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, binaryAndImage);
                    }
                }
                break;

                case "binaryAndSecond": {
                    lastNameMethod = nameMethod;
                    BinaryAndWindow.generationWindow(nameMethod, "10", "10", "26");
                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "10" + ", " + "10" + ", " + "26");


                        BinaryAndImage binaryAndImage = new BinaryAndImage(picture.get("10"), picture.get("10"));
                        binaryAndImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, binaryAndImage);
                    }
                }
                break;

                case "binaryAndThird": {
                    lastNameMethod = nameMethod;
                    BinaryAndWindow.generationWindow(nameMethod, "11", "11", "27");
                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "11" + ", " + "11" + ", " + "27");


                        BinaryAndImage binaryAndImage = new BinaryAndImage(picture.get("11"), picture.get("11"));
                        binaryAndImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, binaryAndImage);
                    }
                }
                break;

                case "binaryAndFourth": {
                    lastNameMethod = nameMethod;
                    BinaryAndWindow.generationWindow(nameMethod, "12", "12", "28");
                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "12" + ", " + "12" + ", " + "28");


                        BinaryAndImage binaryAndImage = new BinaryAndImage(picture.get("12"), picture.get("12"));
                        binaryAndImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, binaryAndImage);
                    }
                }
                break;


                case "binaryOrFirst": {
                    lastNameMethod = nameMethod;
                    BinaryOrWindow.generationWindow(nameMethod, "9", "9", "29");
                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "9" + ", " + "9" + ", " + "29");


                        BinaryOrImage binaryOrImage = new BinaryOrImage(picture.get("9"), picture.get("9"));
                        binaryOrImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, binaryOrImage);
                    }
                }
                break;

                case "binaryOrSecond": {
                    lastNameMethod = nameMethod;
                    BinaryOrWindow.generationWindow(nameMethod, "10", "10", "30");
                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "10" + ", " + "10" + ", " + "30");


                        BinaryOrImage binaryOrImage = new BinaryOrImage(picture.get("10"), picture.get("10"));
                        binaryOrImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, binaryOrImage);
                    }
                }
                break;

                case "binaryOrThird": {
                    lastNameMethod = nameMethod;
                    BinaryOrWindow.generationWindow(nameMethod, "11", "11", "31");
                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "11" + ", " + "11" + ", " + "31");


                        BinaryOrImage binaryOrImage = new BinaryOrImage(picture.get("11"), picture.get("11"));
                        binaryOrImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, binaryOrImage);
                    }
                }
                break;

                case "binaryOrFourth": {
                    lastNameMethod = nameMethod;
                    BinaryOrWindow.generationWindow(nameMethod, "12", "12", "32");
                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "12" + ", " + "12" + ", " + "32");


                        BinaryOrImage binaryOrImage = new BinaryOrImage(picture.get("12"), picture.get("12"));
                        binaryOrImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, binaryOrImage);
                    }
                }
                break;


                case "rectFirst": {
                    lastNameMethod = nameMethod;
                    RectWindow.generationWindow(nameMethod, "original", "33", 0, 0, 640, 480, 640, 480);
                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "original" + ", " + "33" + ", " + "0" + ", " + "0" + ", " + "640" + ", " + "480" + ", " + "640" + ", " + "480");

                        RectImage rectImage = new RectImage(picture.get("original"), 0, 0, 640, 480);
                        rectImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, rectImage);
                    }
                }
                break;

                case "rectSecond": {
                    lastNameMethod = nameMethod;
                    RectWindow.generationWindow(nameMethod, "original", "34", 0, 0, 640, 480, 640, 480);
                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "original" + ", " + "34" + ", " + "0" + ", " + "0" + ", " + "640" + ", " + "480" + ", " + "640" + ", " + "480");

                        RectImage rectImage = new RectImage(picture.get("original"), 0, 0, 640, 480);
                        rectImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, rectImage);
                    }
                }
                break;

                case "rectThird": {
                    lastNameMethod = nameMethod;
                    RectWindow.generationWindow(nameMethod, "original", "35", 0, 0, 640, 480, 640, 480);
                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "original" + ", " + "35" + ", " + "0" + ", " + "0" + ", " + "640" + ", " + "480" + ", " + "640" + ", " + "480");

                        RectImage rectImage = new RectImage(picture.get("original"), 0, 0, 640, 480);
                        rectImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, rectImage);
                    }
                }
                break;

                case "rectFourth": {
                    lastNameMethod = nameMethod;
                    RectWindow.generationWindow(nameMethod, "original", "36", 0, 0, 640, 480, 640, 480);
                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "original" + ", " + "36" + ", " + "0" + ", " + "0" + ", " + "640" + ", " + "480" + ", " + "640" + ", " + "480");

                        RectImage rectImage = new RectImage(picture.get("original"), 0, 0, 640, 480);
                        rectImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, rectImage);
                    }
                }
                break;


                case "areaFirst": {
                    lastNameMethod = nameMethod;
                    AreaWindow.generationWindow(nameMethod, "9", "21");
                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "9" + ", " + "21" + ", " + "24");


                        AreaImage areaImage = new AreaImage(picture.get("9"));
                        areaImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, areaImage);
                    }
                }
                break;

                case "areaSecond": {
                    lastNameMethod = nameMethod;
                    AreaWindow.generationWindow(nameMethod, "10", "22");
                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "10" + ", " + "22" + ", " + "24");


                        AreaImage areaImage = new AreaImage(picture.get("10"));
                        areaImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, areaImage);
                    }
                }
                break;

                case "areaThird": {
                    lastNameMethod = nameMethod;
                    AreaWindow.generationWindow(nameMethod, "11", "23");
                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "11" + ", " + "23" + ", " + "24");


                        AreaImage areaImage = new AreaImage(picture.get("11"));
                        areaImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, areaImage);
                    }
                }
                break;

                case "areaFourth": {
                    lastNameMethod = nameMethod;
                    AreaWindow.generationWindow(nameMethod, "12", "24");
                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "12" + ", " + "24" + ", " + "24");


                        AreaImage areaImage = new AreaImage(picture.get("12"));
                        areaImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, areaImage);
                    }
                }
                break;


                case "contourDrawFirst": {
                    lastNameMethod = nameMethod;
                    ContourDrawWindow.generationWindow(nameMethod, "9", "37");
                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "9" + ", " + "37");


                        ContourDrawImage contourDrawImage = new ContourDrawImage(picture.get("9"));
                        contourDrawImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, contourDrawImage);
                    }
                }
                break;

                case "contourDrawSecond": {
                    lastNameMethod = nameMethod;
                    ContourDrawWindow.generationWindow(nameMethod, "9", "38");
                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "9" + ", " + "38");


                        ContourDrawImage contourDrawImage = new ContourDrawImage(picture.get("9"));
                        contourDrawImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, contourDrawImage);
                    }
                }
                break;

                case "contourDrawThird": {
                    lastNameMethod = nameMethod;
                    ContourDrawWindow.generationWindow(nameMethod, "9", "39");
                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "9" + ", " + "39");


                        ContourDrawImage contourDrawImage = new ContourDrawImage(picture.get("9"));
                        contourDrawImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, contourDrawImage);
                    }
                }
                break;

                case "contourDrawFourth": {
                    lastNameMethod = nameMethod;
                    ContourDrawWindow.generationWindow(nameMethod, "9", "40");
                    if (!objectsMethods.containsKey(lastNameMethod)) {
                        MainWindow.updateProperty(lastNameMethod, "9" + ", " + "40");


                        ContourDrawImage contourDrawImage = new ContourDrawImage(picture.get("9"));
                        contourDrawImage.execute();
                        objectsMethods.putIfAbsent(lastNameMethod, contourDrawImage);
                    }
                }
                break;
            }
            nameMethod = "none";
        }
        catch (Exception e)
        {
            logger.severe("Произошла ошибка: в методе инциализации методов" + e.getMessage());
            fileHandler.publish(new java.util.logging.LogRecord(Level.SEVERE, "Произошла ошибка: в методе инциализации методов: " + e.getMessage()));
        }
    }




    private static void putPicture() {
        try {
            if (!objectsMethods.isEmpty()) {
                for (Map.Entry<String, Object> object : objectsMethods.entrySet()) {
                    Mat resultImage = new Mat();
                    String[] lastValues = loadProperty(object.getKey()).split(", ");

                    if (lastValues.length > 1) {
                        Mat src = picture.get(lastValues[0]);
                        if (src == null || src.empty()) {
                            break;
                        }

                        if (object.getValue() instanceof RotateImage)
                        {
                            RotateImage rotateObject = new RotateImage(src, Integer.parseInt(lastValues[2]));
                            rotateObject.execute();
                            resultImage = rotateObject.getResult();
                        }
                        else if (object.getValue() instanceof ColorImage)
                        {
                            ColorImage colorObject = new ColorImage(src,
                                    Integer.parseInt(lastValues[2]), Integer.parseInt(lastValues[3]),
                                    Integer.parseInt(lastValues[4]), Integer.parseInt(lastValues[5]),
                                    Integer.parseInt(lastValues[6]), Integer.parseInt(lastValues[7]));
                            colorObject.execute();
                            resultImage = colorObject.getResult();
                        }
                        else if (object.getValue() instanceof CvtImage)
                        {

                            CvtImage cvtImage = new CvtImage(src, Integer.parseInt(lastValues[2]));
                            cvtImage.execute();
                            resultImage = cvtImage.getResult();
                        }
                        else if (object.getValue() instanceof BlurImage)
                        {


                            BlurImage blurImage = new BlurImage(src, Integer.parseInt(lastValues[2]));
                            blurImage.execute();
                            resultImage = blurImage.getResult();

                        }
                        else if (object.getValue() instanceof DilateImage)
                        {
                            DilateImage dilateImage = new DilateImage(src, Integer.parseInt(lastValues[2]));
                            dilateImage.execute();
                            resultImage = dilateImage.getResult();
                        }
                        else if (object.getValue() instanceof BinaryNotImage)
                        {
                            BinaryNotImage binaryNotImage = new BinaryNotImage(src);
                            binaryNotImage.execute();
                            resultImage = binaryNotImage.getResult();
                        }
                        else if (object.getValue() instanceof BinaryAndImage)
                        {

                            Mat src2 = picture.get(lastValues[2]);
                            if (src2 == null || src2.empty())
                            {
                                break;
                            }

                            BinaryAndImage binaryAndImage = new BinaryAndImage(src, src2);
                            binaryAndImage.execute();
                            resultImage = binaryAndImage.getResult();

                        }
                        else if (object.getValue() instanceof BinaryOrImage)
                        {

                            Mat src2 = picture.get(lastValues[2]);
                            if (src2 == null || src2.empty())
                            {
                                break;
                            }

                            BinaryOrImage binaryOrImage = new BinaryOrImage(src, src2);
                            binaryOrImage.execute();
                            resultImage = binaryOrImage.getResult();
                        }
                        else if (object.getValue() instanceof RectImage)
                        {
                            RectImage rectImage = new RectImage(src, Integer.parseInt(lastValues[2]),
                                    Integer.parseInt(lastValues[3]), Integer.parseInt(lastValues[4]), Integer.parseInt(lastValues[5]));
                            rectImage.execute();
                            resultImage = rectImage.getResult();
                        }
                        else if (object.getValue() instanceof AreaImage)
                        {
                            AreaImage areaImage = new AreaImage(src);
                            areaImage.execute();
                            resultImage = areaImage.getResult();
                            MainWindow.updateProperty(object.getKey(), lastValues[0] + ", " + lastValues[1] + ", " + areaImage.getArea());
                        }
                        else if (object.getValue() instanceof ContourDrawImage)
                        {
                            ContourDrawImage contourDrawImage = new ContourDrawImage(src);
                            contourDrawImage.execute();
                            resultImage = contourDrawImage.getResult();
                        }
                        picture.put(lastValues[1], resultImage.clone());
                    }

                }
            }
        } catch (Exception e) {
            logger.severe("Произошла ошибка: в методе putPicture" + e.getMessage());
            fileHandler.publish(new java.util.logging.LogRecord(Level.SEVERE, "Произошла ошибка: в методе putPicture: " + e.getMessage()));
        }
    }



    public static void initializePicture() {
        original = new Mat();

        for (int i = 1; i < elements.length; i++)
        {
            picture.put(elements[i], new Mat());
        }

        picture.put("original", original);

        cvt.put("COLOR_BGR2BGRA", Imgproc.COLOR_BGR2BGRA);
        cvt.put("COLOR_BGR2HLS", Imgproc.COLOR_BGR2HLS);
        cvt.put("COLOR_BGR2GRAY", Imgproc.COLOR_BGR2GRAY);
        cvt.put("COLOR_BGR2HSV", Imgproc.COLOR_BGR2HSV);
        cvt.put("COLOR_BGR2Lab", Imgproc.COLOR_BGR2Lab);
        cvt.put("COLOR_BGR2Luv", Imgproc.COLOR_BGR2Luv);
        cvt.put("COLOR_BGR2YUV", Imgproc.COLOR_BGR2YUV);
    }


    @NotNull
    private static Path getPropertiesPath() {
        return Paths.get("src", "main", "java", "org", "example", "infa", "app.properties");
    }

    public static void createProperties() throws IOException {
        Path appConfigPath = getPropertiesPath();
        File file = appConfigPath.toFile();

        if (!file.exists()) {

            File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }

            Properties properties = new Properties();

            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                for (String method : methods) {
                    properties.setProperty(method, "0");
                }
                properties.store(fileOutputStream, "File to store settings");
            }
        }
    }

    public static String loadProperty(String key) throws IOException {
        Path appConfigPath = getPropertiesPath();

        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(appConfigPath.toFile())) {
            properties.load(fileInputStream);
        }

        return properties.getProperty(key, "0");
    }

    public static void updateProperty(String key, String value) throws IOException {
        Path appConfigPath = getPropertiesPath();

        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(appConfigPath.toFile())) {
            properties.load(fileInputStream);
        }

        properties.setProperty(key, value);

        try (FileOutputStream fileOutputStream = new FileOutputStream(appConfigPath.toFile())) {
            properties.store(fileOutputStream, "File to store settings");
        }
    }

    @NotNull
    public static HashMap<String, String> loadProperties() throws IOException {
        Path appConfigPath = getPropertiesPath();

        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(appConfigPath.toFile())) {
            properties.load(fileInputStream);
        }

        HashMap<String, String> propertiesMap = new HashMap<>();
        for (String name : methods) {
            propertiesMap.put(name, properties.getProperty(name, "0"));
        }

        return propertiesMap;
    }
}
