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

    public static final String[] elements = {"original", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
            "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
            "30", "31", "32", "33", "34", "35", "36", "37", "38", "39"};

    public static final String[] methods = {"none",
            "rotateFirst", "rotateSecond", "rotateThird", "rotateFourth",
            "colorFirst", "colorSecond", "colorThird", "colorFourth",
            "cvtFirst", "cvtSecond", "cvtThird", "cvtFourth",
            "blurFirst", "blurSecond", "blurThird", "blurFourth",
            "dilateFirst", "dilateSecond", "dilateThird", "dilateFourth",
            "binaryNotFirst", "binaryNotSecond", "binaryNotThird", "binaryNotFourth",
            "binaryAndFirst", "binaryAndSecond", "binaryAndThird", "binaryAndFourth",
            "binaryOrFirst", "binaryOrSecond", "binaryOrThird", "binaryOrFourth",
            "rectFirst", "rectSecond", "rectThird", "rectFourth"
    };

    public static HashMap<String, Integer> cvt = new HashMap<>();

    public static HashMap<String, Object> objectsMethods = new HashMap<>();

    public static String nameMethod = "none";
    public static String lastNameMethod = "none";

    public static String firstImage = "original", secondImage = "original";

    public static Mat original, mat1, mat2, mat3, mat4, mat5, mat6, mat7, mat8, mat9,
            mat10, mat11, mat12, mat13, mat14, mat15, mat16, mat17, mat18, mat19,
            mat20, mat21, mat22, mat23, mat24, mat25, mat26, mat27, mat28, mat29,
            mat30, mat31, mat32, mat33, mat34, mat35, mat36, mat37, mat38, mat39;


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
                RotateWindow.generationWindow(nameMethod, "original", "31", 0);

                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "original" + ", " + "1" + ", " + "0");


                    RotateImage rotateImage = new RotateImage(picture.get("original"), 0);
                    rotateImage.execute();
                    objectsMethods.putIfAbsent(lastNameMethod, rotateImage);
                }
            }
            break;

            case "rotateSecond":
            {
                lastNameMethod = nameMethod;
                RotateWindow.generationWindow(nameMethod, "1", "2", 0);

                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "1" + ", " + "2" + ", " + "0");


                    RotateImage rotateImage = new RotateImage(picture.get("1"), 0);
                    rotateImage.execute();
                    objectsMethods.putIfAbsent(lastNameMethod, rotateImage);
                }
            }
            break;

            case "rotateThird":
            {
                lastNameMethod = nameMethod;
                RotateWindow.generationWindow(nameMethod, "2", "3", 0);

                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "2" + ", " + "3" + ", " + "0");

                    RotateImage rotateImage = new RotateImage(picture.get("2"), 0);
                    rotateImage.execute();
                    objectsMethods.putIfAbsent(lastNameMethod, rotateImage);
                }
            }
            break;

            case "rotateFourth":
            {
                lastNameMethod = nameMethod;
                RotateWindow.generationWindow(nameMethod, "3", "4", 0);

                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "3" + ", " + "4" + ", " + "0");

                    RotateImage rotateImage = new RotateImage(picture.get("3"), 0);
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
                CvtWindow.generationWindow(nameMethod, "original", "9", "COLOR_BGR2HSV");
                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "original" + ", " + "9" + ", " + cvt.get("COLOR_BGR2HSV"));


                    CvtImage cvtImage = new CvtImage(picture.get("original"), cvt.get("COLOR_BGR2HSV"));
                    cvtImage.execute();
                    objectsMethods.putIfAbsent(lastNameMethod, cvtImage);
                }
            }
            break;

            case "cvtSecond":
            {
                lastNameMethod = nameMethod;
                CvtWindow.generationWindow(nameMethod, "9", "10", "COLOR_BGR2HSV");
                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "9" + ", " + "10" + ", " + cvt.get("COLOR_BGR2HSV"));


                    CvtImage cvtImage = new CvtImage(picture.get("9"), cvt.get("COLOR_BGR2HSV"));
                    cvtImage.execute();
                    objectsMethods.putIfAbsent(lastNameMethod, cvtImage);
                }
            }
            break;

            case "cvtThird":
            {
                lastNameMethod = nameMethod;
                CvtWindow.generationWindow(nameMethod, "10", "11", "COLOR_BGR2HSV");
                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "10" + ", " + "11" + ", " + cvt.get("COLOR_BGR2HSV"));


                    CvtImage cvtImage = new CvtImage(picture.get("10"), cvt.get("COLOR_BGR2HSV"));
                    cvtImage.execute();
                    objectsMethods.putIfAbsent(lastNameMethod, cvtImage);
                }
            }
            break;

            case "cvtFourth":
            {
                lastNameMethod = nameMethod;
                CvtWindow.generationWindow(nameMethod, "11", "12", "COLOR_BGR2HSV");
                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "11" + ", " + "12" + ", " + cvt.get("COLOR_BGR2HSV"));

                    CvtImage cvtImage = new CvtImage(picture.get("11"), cvt.get("COLOR_BGR2HSV"));
                    cvtImage.execute();
                    objectsMethods.putIfAbsent(lastNameMethod, cvtImage);
                }
            }
            break;







            case "blurFirst":
            {
                lastNameMethod = nameMethod;
                BlurWindow.generationWindow(nameMethod, "original", "13", 1);
                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "original" + ", " + "13" + ", " + "1");


                    BlurImage blurImage = new BlurImage(picture.get("original"), 1);
                    blurImage.execute();
                    objectsMethods.putIfAbsent(lastNameMethod, blurImage);
                }
            }
            break;

            case "blurSecond":
            {
                lastNameMethod = nameMethod;
                BlurWindow.generationWindow(nameMethod, "13", "14", 1);
                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "13" + ", " + "14" + ", " + "1");


                    BlurImage blurImage = new BlurImage(picture.get("13"), 1);
                    blurImage.execute();
                    objectsMethods.putIfAbsent(lastNameMethod, blurImage);
                }
            }
            break;

            case "blurThird":
            {
                lastNameMethod = nameMethod;
                BlurWindow.generationWindow(nameMethod, "14", "15", 1);
                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "14" + ", " + "15" + ", " + "1");


                    BlurImage blurImage = new BlurImage(picture.get("14"), 1);
                    blurImage.execute();
                    objectsMethods.putIfAbsent(lastNameMethod, blurImage);
                }
            }
            break;

            case "blurFourth":
            {
                lastNameMethod = nameMethod;
                BlurWindow.generationWindow(nameMethod, "15", "16", 1);
                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "15" + ", " + "16" + ", " + "1");


                    BlurImage blurImage = new BlurImage(picture.get("15"), 1);
                    blurImage.execute();
                    objectsMethods.putIfAbsent(lastNameMethod, blurImage);
                }
            }
            break;






            case "dilateFirst":
            {
                lastNameMethod = nameMethod;
                DilateWindow.generationWindow(nameMethod, "original", "17", 1);
                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "original" + ", " + "17" + ", " + "1");


                    DilateImage dilateImage = new DilateImage(picture.get("original"), 1);
                    dilateImage.execute();
                    objectsMethods.putIfAbsent(lastNameMethod, dilateImage);
                }
            }
            break;

            case "dilateSecond":
            {
                lastNameMethod = nameMethod;
                DilateWindow.generationWindow(nameMethod, "17", "18", 1);
                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "17" + ", " + "18" + ", " + "1");


                    DilateImage dilateImage = new DilateImage(picture.get("17"), 1);
                    dilateImage.execute();
                    objectsMethods.putIfAbsent(lastNameMethod, dilateImage);
                }
            }
            break;

            case "dilateThird":
            {
                lastNameMethod = nameMethod;
                DilateWindow.generationWindow(nameMethod, "18", "19", 1);
                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "18" + ", " + "19" + ", " + "1");


                    DilateImage dilateImage = new DilateImage(picture.get("18"), 1);
                    dilateImage.execute();
                    objectsMethods.putIfAbsent(lastNameMethod, dilateImage);
                }
            }
            break;

            case "dilateFourth":
            {
                lastNameMethod = nameMethod;
                DilateWindow.generationWindow(nameMethod, "19", "20", 1);
                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "19" + ", " + "20" + ", " + "1");


                    DilateImage dilateImage = new DilateImage(picture.get("19"), 1);
                    dilateImage.execute();
                    objectsMethods.putIfAbsent(lastNameMethod, dilateImage);
                }
            }
            break;









            case "binaryNotFirst":
            {
                lastNameMethod = nameMethod;
                BinaryNotWindow.generationWindow(nameMethod, "9", "21");
                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "9" + ", " + "21");


                    BinaryNotImage binaryNotImage = new BinaryNotImage(picture.get("9"));
                    binaryNotImage.execute();
                    objectsMethods.putIfAbsent(lastNameMethod, binaryNotImage);
                }
            }
            break;

            case "binaryNotSecond":
            {
                lastNameMethod = nameMethod;
                BinaryNotWindow.generationWindow(nameMethod, "10", "22");
                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "10" + ", " + "22");


                    BinaryNotImage binaryNotImage = new BinaryNotImage(picture.get("10"));
                    binaryNotImage.execute();
                    objectsMethods.putIfAbsent(lastNameMethod, binaryNotImage);
                }
            }
            break;

            case "binaryNotThird":
            {
                lastNameMethod = nameMethod;
                BinaryNotWindow.generationWindow(nameMethod, "11", "23");
                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "11" + ", " + "23");


                    BinaryNotImage binaryNotImage = new BinaryNotImage(picture.get("11"));
                    binaryNotImage.execute();
                    objectsMethods.putIfAbsent(lastNameMethod, binaryNotImage);
                }
            }
            break;

            case "binaryNotFourth":
            {
                lastNameMethod = nameMethod;
                BinaryNotWindow.generationWindow(nameMethod, "12", "24");
                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "12" + ", " + "24");


                    BinaryNotImage binaryNotImage = new BinaryNotImage(picture.get("12"));
                    binaryNotImage.execute();
                    objectsMethods.putIfAbsent(lastNameMethod, binaryNotImage);
                }
            }
            break;









            case "binaryAndFirst":
            {
                lastNameMethod = nameMethod;
                BinaryAndWindow.generationWindow(nameMethod, "9", "9", "25");
                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "9" + ", " + "9" + ", " + "25");


                    BinaryAndImage binaryAndImage = new BinaryAndImage(picture.get("9"), picture.get("9"));
                    binaryAndImage.execute();
                    objectsMethods.putIfAbsent(lastNameMethod, binaryAndImage);
                }
            }
            break;

            case "binaryAndSecond":
            {
                lastNameMethod = nameMethod;
                BinaryAndWindow.generationWindow(nameMethod, "10", "10", "26");
                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "10" + ", " + "10" + ", " + "26");


                    BinaryAndImage binaryAndImage = new BinaryAndImage(picture.get("10"), picture.get("10"));
                    binaryAndImage.execute();
                    objectsMethods.putIfAbsent(lastNameMethod, binaryAndImage);
                }
            }
            break;

            case "binaryAndThird":
            {
                lastNameMethod = nameMethod;
                BinaryAndWindow.generationWindow(nameMethod, "11", "11", "27");
                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "11" + ", " + "11" + ", " + "27");


                    BinaryAndImage binaryAndImage = new BinaryAndImage(picture.get("11"), picture.get("11"));
                    binaryAndImage.execute();
                    objectsMethods.putIfAbsent(lastNameMethod, binaryAndImage);
                }
            }
            break;

            case "binaryAndFourth":
            {
                lastNameMethod = nameMethod;
                BinaryAndWindow.generationWindow(nameMethod, "12", "12", "28");
                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "12" + ", " + "12" + ", " + "28");


                    BinaryAndImage binaryAndImage = new BinaryAndImage(picture.get("12"), picture.get("12"));
                    binaryAndImage.execute();
                    objectsMethods.putIfAbsent(lastNameMethod, binaryAndImage);
                }
            }
            break;








            case "binaryOrFirst":
            {
                lastNameMethod = nameMethod;
                BinaryOrWindow.generationWindow(nameMethod, "9", "9", "29");
                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "9" + ", " + "9" + ", " + "29");


                    BinaryOrImage binaryOrImage = new BinaryOrImage(picture.get("9"), picture.get("9"));
                    binaryOrImage.execute();
                    objectsMethods.putIfAbsent(lastNameMethod, binaryOrImage);
                }
            }
            break;

            case "binaryOrSecond":
            {
                lastNameMethod = nameMethod;
                BinaryOrWindow.generationWindow(nameMethod, "10", "10", "30");
                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "10" + ", " + "10" + ", " + "30");


                    BinaryOrImage binaryOrImage = new BinaryOrImage(picture.get("10"), picture.get("10"));
                    binaryOrImage.execute();
                    objectsMethods.putIfAbsent(lastNameMethod, binaryOrImage);
                }
            }
            break;

            case "binaryOrThird":
            {
                lastNameMethod = nameMethod;
                BinaryOrWindow.generationWindow(nameMethod, "11", "11", "31");
                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "11" + ", " + "11" + ", " + "31");


                    BinaryOrImage binaryOrImage = new BinaryOrImage(picture.get("11"), picture.get("11"));
                    binaryOrImage.execute();
                    objectsMethods.putIfAbsent(lastNameMethod, binaryOrImage);
                }
            }
            break;

            case "binaryOrFourth":
            {
                lastNameMethod = nameMethod;
                BinaryOrWindow.generationWindow(nameMethod, "12", "12", "32");
                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "12" + ", " + "12" + ", " + "32");


                    BinaryOrImage binaryOrImage = new BinaryOrImage(picture.get("12"), picture.get("12"));
                    binaryOrImage.execute();
                    objectsMethods.putIfAbsent(lastNameMethod, binaryOrImage);
                }
            }
            break;








            case "rectFirst":
            {
                lastNameMethod = nameMethod;
                RectWindow.generationWindow(nameMethod, "original", "33", 0, 0, 640, 480, 640, 480);
                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "original" + ", " + "33" + ", " + "0" + ", " + "0" + ", " + "640" + ", " + "480" + ", " + "640" + ", " + "480");

                    RectImage rectImage = new RectImage(picture.get("original"), 0, 0, 640, 480);
                    rectImage.execute();
                    objectsMethods.putIfAbsent(lastNameMethod, rectImage);
                }
            }
            break;

            case "rectSecond":
            {
                lastNameMethod = nameMethod;
                RectWindow.generationWindow(nameMethod, "original", "34", 0, 0, 640, 480, 640, 480);
                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "original" + ", " + "34" + ", " + "0" + ", " + "0" + ", " + "640" + ", " + "480" + ", " + "640" + ", " + "480");

                    RectImage rectImage = new RectImage(picture.get("original"), 0, 0, 640, 480);
                    rectImage.execute();
                    objectsMethods.putIfAbsent(lastNameMethod, rectImage);
                }
            }
            break;

            case "rectThird":
            {
                lastNameMethod = nameMethod;
                RectWindow.generationWindow(nameMethod, "original", "35", 0, 0, 640, 480, 640, 480);
                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "original" + ", " + "35" + ", " + "0" + ", " + "0" + ", " + "640" + ", " + "480" + ", " + "640" + ", " + "480");

                    RectImage rectImage = new RectImage(picture.get("original"), 0, 0, 640, 480);
                    rectImage.execute();
                    objectsMethods.putIfAbsent(lastNameMethod, rectImage);
                }
            }
            break;

            case "rectFourth":
            {
                lastNameMethod = nameMethod;
                RectWindow.generationWindow(nameMethod, "original", "36", 0, 0, 640, 480, 640, 480);
                if (!objectsMethods.containsKey(lastNameMethod))
                {
                    MainWindow.updateProperty(lastNameMethod, "original" + ", " + "36" + ", " + "0" + ", " + "0" + ", " + "640" + ", " + "480" + ", " + "640" + ", " + "480");

                    RectImage rectImage = new RectImage(picture.get("original"), 0, 0, 640, 480);
                    rectImage.execute();
                    objectsMethods.putIfAbsent(lastNameMethod, rectImage);
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

                } else if (object.getValue() instanceof BlurImage) {

                    Mat src = picture.get(lastValues[0]);
                    BlurImage blurImage = new BlurImage(src, Integer.parseInt(lastValues[2]));
                    blurImage.execute();
                    resultImage = blurImage.getResult();

                } else if (object.getValue() instanceof DilateImage) {

                    Mat src = picture.get(lastValues[0]);
                    DilateImage dilateImage = new DilateImage(src, Integer.parseInt(lastValues[2]));
                    dilateImage.execute();
                    resultImage = dilateImage.getResult();

                } else if (object.getValue() instanceof BinaryNotImage) {

                    Mat src = picture.get(lastValues[0]);
                    BinaryNotImage binaryNotImage = new BinaryNotImage(src);
                    binaryNotImage.execute();
                    resultImage = binaryNotImage.getResult();

                } else if (object.getValue() instanceof BinaryAndImage) {

                    Mat src = picture.get(lastValues[0]);
                    Mat src2 = picture.get(lastValues[2]);
                    BinaryAndImage binaryAndImage = new BinaryAndImage(src, src2);
                    binaryAndImage.execute();
                    resultImage = binaryAndImage.getResult();

                } else if (object.getValue() instanceof BinaryOrImage) {

                    Mat src = picture.get(lastValues[0]);
                    Mat src2 = picture.get(lastValues[2]);
                    BinaryOrImage binaryOrImage = new BinaryOrImage(src, src2);
                    binaryOrImage.execute();
                    resultImage = binaryOrImage.getResult();

                } else if (object.getValue() instanceof RectImage) {

                    Mat src = picture.get(lastValues[0]);
                    RectImage rectImage = new RectImage(src, Integer.parseInt(lastValues[2]),
                            Integer.parseInt(lastValues[3]), Integer.parseInt(lastValues[4]), Integer.parseInt(lastValues[5]));
                    rectImage.execute();
                    resultImage = rectImage.getResult();

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
        mat32 = new Mat();
        mat33 = new Mat();
        mat34 = new Mat();
        mat35 = new Mat();
        mat36 = new Mat();
        mat37 = new Mat();
        mat38 = new Mat();
        mat39 = new Mat();

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
        picture.put("32", mat32);
        picture.put("33", mat33);
        picture.put("34", mat34);
        picture.put("35", mat35);
        picture.put("36", mat36);
        picture.put("37", mat37);
        picture.put("38", mat38);
        picture.put("39", mat39);

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
