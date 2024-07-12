package org.example;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

public class MainWindow {

    public static final String[] elements = {"original", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};

    public static final String[] methods = {"none", "rotateFirst", "rotateSecond", "rotateThird", "rotateFourth", "colorFirst", "colorSecond", "colorThird", "colorFourth", "cvtFirst", "cvtSecond", "cvtThird", "cvtFourth"};

    public static HashMap<String, Integer> cvt = new HashMap<>();

    public static HashMap<String, Object> objectsMethods = new HashMap<>();

    public static String nameMethod = "none";
    public static String lastNameMethod = "none";

    public static String firstImage = "original", secondImage = "original";

    public static Mat original, mat1, mat2, mat3, mat4, mat5, mat6, mat7, mat8, mat9, mat10, mat11, mat12, mat13, mat14, mat15, mat16, mat17, mat18, mat19, mat20, mat21, mat22, mat23, mat24, mat25, mat26, mat27, mat28, mat29, mat30, mat31;


    public static HashMap<String, Mat> picture = new HashMap<>();


    public static void main(String[] args) throws Exception {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        initializePicture();

        createProperties();



        JFrame frame = new JFrame();
        frame.setBounds(0, 0, 1200, 1015);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainPanel facePanel = new MainPanel();
        frame.setContentPane(facePanel);

        frame.setVisible(true);

        MatToBufImg matToBufferedImageConverter = new MatToBufImg();
        MatToBufImg matToBufferedImageConverter2 = new MatToBufImg();


        VideoCapture camera = new VideoCapture(0);

        if (!camera.isOpened()) {
            System.out.println("!!! Did not connect to camera !!!");
        } else {
            System.out.println("found webcam: " + camera);
        }

        if (camera.isOpened()) {
            Thread.sleep(30);
            while (true) {
                try {
                    initializeMethods();
                    putPicture();
                    camera.read(original);
//                    System.out.println(objectsMethods.size());
                    picture.put("original", original);

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
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        System.exit(1);
                        break;
                    }
                } catch (Exception e) {
                    e.getLocalizedMessage();
                }


            }
            original.release();
            picture.get(firstImage).release();
            picture.get(secondImage).release();
        }


    }


    public static void initializeMethods() throws IOException {

        switch (nameMethod) {
            case "none":
                break;

            case "rotateFirst":
            {
                lastNameMethod = nameMethod;
                RotateWindow.generationWindow(nameMethod, "original", "10", 0);

                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "original" + ", " + "10" + ", " + "0");


                    RotateImage rotateImage = new RotateImage(picture.get("original"), 0);
                    rotateImage.execute();
                    objectsMethods.putIfAbsent(lastNameMethod, rotateImage);
                }
            }
            break;

            case "rotateSecond":
            {
                lastNameMethod = nameMethod;
                RotateWindow.generationWindow(nameMethod, "10", "15", 90);

                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "10" + ", " + "15" + ", " + "90");


                    RotateImage rotateImage = new RotateImage(picture.get("10"), 90);
                    rotateImage.execute();
                    objectsMethods.putIfAbsent(lastNameMethod, rotateImage);
                }
            }
            break;

            case "rotateThird":
            {
                lastNameMethod = nameMethod;
                RotateWindow.generationWindow(nameMethod, "15", "20", -90);

                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "15" + ", " + "20" + ", " + "-90");

                    RotateImage rotateImage = new RotateImage(picture.get("15"), -90);
                    rotateImage.execute();
                    objectsMethods.putIfAbsent(lastNameMethod, rotateImage);
                }
            }
            break;

            case "rotateFourth":
            {
                lastNameMethod = nameMethod;
                RotateWindow.generationWindow(nameMethod, "20", "25", 180);

                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "20" + ", " + "25" + ", " + "180");

                    RotateImage rotateImage = new RotateImage(picture.get("20"), 180);
                    rotateImage.execute();
                    objectsMethods.putIfAbsent(lastNameMethod, rotateImage);
                }
            }
            break;

            case "colorFirst":
            {
                lastNameMethod = nameMethod;
                ColorWindow.generationWindow(nameMethod, "original", "5", 0, 255, 0, 255, 0, 255);
                if (!objectsMethods.containsKey(lastNameMethod))
                {
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
                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "5" + ", " + "6" + ", " + "0" + ", " + "255" + ", " + "0" + ", " + "255" + ", " + "0" + ", " + "255");

                    ColorImage colorImage = new ColorImage(picture.get("5"), 0, 255, 0, 255, 0, 255);
                    colorImage.execute();
                    objectsMethods.putIfAbsent(lastNameMethod, colorImage);
                }
                break;

            case "colorThird":
                lastNameMethod = nameMethod;
                ColorWindow.generationWindow(nameMethod, "6", "7", 0, 255, 0, 255, 0, 255);
                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "6" + ", " + "7" + ", " + "0" + ", " + "255" + ", " + "0" + ", " + "255" + ", " + "0" + ", " + "255");

                    ColorImage colorImage = new ColorImage(picture.get("6"), 0, 255, 0, 255, 0, 255);
                    colorImage.execute();
                    objectsMethods.putIfAbsent(lastNameMethod, colorImage);
                }
                break;

            case "colorFourth":
                lastNameMethod = nameMethod;
                ColorWindow.generationWindow(nameMethod, "7", "8", 0, 255, 0, 255, 0, 255);
                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "7" + ", " + "8" + ", " + "0" + ", " + "255" + ", " + "0" + ", " + "255" + ", " + "0" + ", " + "255");

                    ColorImage colorImage = new ColorImage(picture.get("7"), 0, 255, 0, 255, 0, 255);
                    colorImage.execute();
                    objectsMethods.putIfAbsent(lastNameMethod, colorImage);
                }
                break;

            case "cvtFirst":
            {
                lastNameMethod = nameMethod;
                CvtWindow.generationWindow(nameMethod, "original", "1", "COLOR_BGR2HSV");
                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "original" + ", " + "1" + ", " + cvt.get("COLOR_BGR2HSV"));


                    CvtImage cvtImage = new CvtImage(picture.get("original"), cvt.get("COLOR_BGR2HSV"));
                    cvtImage.execute();
                    objectsMethods.putIfAbsent(lastNameMethod, cvtImage);
                }
            }
            break;

            case "cvtSecond":
            {
                lastNameMethod = nameMethod;
                CvtWindow.generationWindow(nameMethod, "1", "2", "COLOR_BGR2HSV");
                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "1" + ", " + "2" + ", " + cvt.get("COLOR_BGR2HSV"));


                    CvtImage cvtImage = new CvtImage(picture.get("1"), cvt.get("COLOR_BGR2HSV"));
                    cvtImage.execute();
                    objectsMethods.putIfAbsent(lastNameMethod, cvtImage);
                }
            }
            break;

            case "cvtThird":
            {
                lastNameMethod = nameMethod;
                CvtWindow.generationWindow(nameMethod, "2", "3", "COLOR_BGR2HSV");
                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "2" + ", " + "3" + ", " + cvt.get("COLOR_BGR2HSV"));


                    CvtImage cvtImage = new CvtImage(picture.get("2"), cvt.get("COLOR_BGR2HSV"));
                    cvtImage.execute();
                    objectsMethods.putIfAbsent(lastNameMethod, cvtImage);
                }
            }
            break;

            case "cvtFourth":
            {
                lastNameMethod = nameMethod;
                CvtWindow.generationWindow(nameMethod, "3", "4", "COLOR_BGR2HSV");
                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "3" + ", " + "4" + ", " + cvt.get("COLOR_BGR2HSV"));

                    CvtImage cvtImage = new CvtImage(picture.get("3"), cvt.get("COLOR_BGR2HSV"));
                    cvtImage.execute();
                    objectsMethods.putIfAbsent(lastNameMethod, cvtImage);
                }
            }
            break;
        }
        nameMethod = "none";


    }


    private static void putPicture() throws IOException {
        if (!objectsMethods.isEmpty()) {
            for (Map.Entry<String, Object> object : objectsMethods.entrySet())
            {
//                Object object = objectsMethods.get(0);
                Mat resultImage = new Mat();
                String[] lastValues = loadProperties(object.getKey()).split(", ");
                System.out.println(Arrays.toString(lastValues));

                if (object.getValue() instanceof RotateImage)
                {
                    Mat src = picture.get(lastValues[0]);
                    RotateImage rotateObject = new RotateImage(src, Integer.parseInt(lastValues[2]));
                    rotateObject.execute();
                    resultImage = rotateObject.getResult();

                } else if (object.getValue() instanceof ColorImage)
                {
                    Mat src = picture.get(lastValues[0]);
                    ColorImage colorObject = new ColorImage(src,
                            Integer.parseInt(lastValues[2]), Integer.parseInt(lastValues[3]),
                            Integer.parseInt(lastValues[4]), Integer.parseInt(lastValues[5]),
                            Integer.parseInt(lastValues[6]), Integer.parseInt(lastValues[7]));
                    colorObject.execute();
                    resultImage = colorObject.getResult();

                } else if (object.getValue() instanceof CvtImage) {

                    Mat src = picture.get(lastValues[0]);
                    CvtImage cvtImage = new CvtImage(src, Integer.parseInt(lastValues[2]));
                    cvtImage.execute();
                    resultImage = cvtImage.getResult();
                }


                picture.put(lastValues[1], resultImage.clone());
            }

        }
    }


    public static void initializePicture() {
        original = new Mat();
        mat1 = new Mat();
        mat2 = new Mat();
        mat3 = new Mat();
        mat4 = new Mat();
        mat5 = new Mat();
        mat6 = new Mat();
        mat7 = new Mat();
        mat8 = new Mat();
        mat9 = new Mat();
        mat10 = new Mat();
        mat11 = new Mat();
        mat12 = new Mat();
        mat13 = new Mat();
        mat14 = new Mat();
        mat15 = new Mat();
        mat16 = new Mat();
        mat17 = new Mat();
        mat18 = new Mat();
        mat19 = new Mat();
        mat20 = new Mat();
        mat21 = new Mat();
        mat22 = new Mat();
        mat23 = new Mat();
        mat24 = new Mat();
        mat25 = new Mat();
        mat26 = new Mat();
        mat27 = new Mat();
        mat28 = new Mat();
        mat29 = new Mat();
        mat30 = new Mat();
        mat31 = new Mat();


        picture.put("original", original);
        picture.put("1", mat1);
        picture.put("2", mat2);
        picture.put("3", mat3);
        picture.put("4", mat4);
        picture.put("5", mat5);
        picture.put("6", mat6);
        picture.put("7", mat7);
        picture.put("8", mat8);
        picture.put("9", mat9);
        picture.put("10", mat10);
        picture.put("11", mat11);
        picture.put("12", mat12);
        picture.put("13", mat13);
        picture.put("14", mat14);
        picture.put("15", mat15);
        picture.put("16", mat16);
        picture.put("17", mat17);
        picture.put("18", mat18);
        picture.put("19", mat19);
        picture.put("20", mat20);
        picture.put("21", mat21);
        picture.put("22", mat22);
        picture.put("23", mat23);
        picture.put("24", mat24);
        picture.put("25", mat25);
        picture.put("26", mat26);
        picture.put("27", mat27);
        picture.put("28", mat28);
        picture.put("29", mat29);
        picture.put("30", mat30);
        picture.put("31", mat31);

        cvt.put("COLOR_BGR2BGRA", Imgproc.COLOR_BGR2BGRA);
        cvt.put("COLOR_BGR2HLS", Imgproc.COLOR_BGR2HLS);
        cvt.put("COLOR_BGR2GRAY", Imgproc.COLOR_BGR2GRAY);
        cvt.put("COLOR_BGR2HSV", Imgproc.COLOR_BGR2HSV);
        cvt.put("COLOR_BGR2Lab", Imgproc.COLOR_BGR2Lab);
        cvt.put("COLOR_BGR2Luv", Imgproc.COLOR_BGR2Luv);
        cvt.put("COLOR_BGR2YUV", Imgproc.COLOR_BGR2YUV);
    }

    private static void createProperties() throws IOException {

        String appConfigPath = "src/main/java/org/example/app.properties";

        Properties properties = new Properties();
        FileOutputStream fileOutputStream = new FileOutputStream(appConfigPath);



        for (String method : methods) {
            if (!method.equalsIgnoreCase("none")) {
                properties.setProperty(method, "0");
            }
        }

        properties.store(fileOutputStream, "File to store settings");

        fileOutputStream.close();
    }


    public static String loadProperties(String key) throws IOException {
        String appConfigPath = "src/main/java/org/example/app.properties";

        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream(appConfigPath);

        properties.load(fileInputStream);

        fileInputStream.close();

        return properties.getProperty(key, "0");
    }

    public static void updateProperty(String key, String value) throws IOException {
        String appConfigPath = "src/main/java/org/example/app.properties";

        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream(appConfigPath);

        properties.load(fileInputStream);

        fileInputStream.close();

        properties.setProperty(key, value);

        FileOutputStream fileOutputStream = new FileOutputStream(appConfigPath);

        properties.store(fileOutputStream, "File to store settings");

        fileOutputStream.close();
    }
}
