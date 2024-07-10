package org.example;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.videoio.VideoCapture;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class MainWindow
{

    public static final String[] elements = {"original", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};

    public static final String[] methods = {"none", "rotateFirst", "rotateSecond", "rotateThird", "rotateFourth",
            "colorFirst", "colorSecond", "colorThird", "colorFourth",
            "cvtFirst", "cvtSecond", "cvtThird", "cvtFourth"};


    static ArrayList<Object> objectsMethods = new ArrayList<>();

    public static String nameMethod = "none";

    public static String firstImage = "original", secondImage = "original";

    public static Mat original, mat1, mat2, mat3, mat4, mat5, mat6, mat7, mat8, mat9, mat10,
            mat11, mat12, mat13, mat14, mat15, mat16, mat17, mat18, mat19, mat20,
            mat21, mat22, mat23, mat24, mat25, mat26, mat27, mat28, mat29, mat30, mat31;


    public static HashMap<String, Mat> picture = new HashMap<>();


    public static void main(String[] args) throws Exception
    {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        initializePicture();

        JFrame frame = new JFrame();
        frame.setBounds(0, 0, 1200, 1015);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainPanel facePanel = new MainPanel();
        frame.setContentPane(facePanel);

        frame.setVisible(true);

        MatToBufImg matToBufferedImageConverter = new MatToBufImg();
        MatToBufImg matToBufferedImageConverter2 = new MatToBufImg();


        VideoCapture camera = new VideoCapture(0);

        if (!camera.isOpened())
        {
            System.out.println("!!! Did not connect to camera !!!");
        }
        else
        {
            System.out.println("found webcam: " + camera.toString());
        }

        if (camera.isOpened())
        {
            Thread.sleep(30);
            while (true)
            {
                try
                {
                    initializeMethods();
                    System.out.println(objectsMethods.size());
                    putPicture();

                    camera.read(original);

                    if (picture.get(firstImage).empty())
                    {
                        camera.read(picture.get(firstImage));
                    }

                    if (picture.get(secondImage).empty())
                    {
                        camera.read(picture.get(secondImage));
                    }

                    if (!original.empty())
                    {

                        matToBufferedImageConverter.setMatrix(picture.get(firstImage), ".jpg");
                        BufferedImage bufImgFirst = matToBufferedImageConverter.getBufferedImage();

                        matToBufferedImageConverter2.setMatrix(picture.get(secondImage), ".jpg");
                        BufferedImage bufImgSecond = matToBufferedImageConverter2.getBufferedImage();

                        facePanel.setFace(bufImgFirst, bufImgSecond);
                        facePanel.repaint();
                    }
                    else
                    {
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        System.exit(1);
                        break;
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    continue;
                }


            }
            original.release();
            picture.get(firstImage).release();
            picture.get(secondImage).release();
        }



    }


    public static void initializeMethods()
    {
        switch (nameMethod){
            case "none":
                break;

            case "rotateFirst":
                RotateImage rotateImage = new RotateImage(original, Core.ROTATE_180);
                rotateImage.execute();
                objectsMethods.add(rotateImage);
                break;

            case "rotateSecond":
                break;

            case "rotateThird":
                break;

            case "rotateFourth":
                break;

            case "colorFirst":
                break;

            case "colorSecond":
                break;

            case "colorThird":
                break;

            case "colorFourth":
                break;

            case "cvtFirst":
                break;

            case "cvtSecond":
                break;

            case "cvtThird":
                break;

            case "cvtFourth":
                break;
        }
        nameMethod = "none";



    }


    private static void putPicture()
    {
        if(!objectsMethods.isEmpty())
        {

                Object object = objectsMethods.get(0);
                Mat resultImage = new Mat();
                Mat src = original;
                if (object instanceof RotateImage rotateImage) {
                    RotateImage rotateObject  = new RotateImage(src, rotateImage.getRotateCode());
                    rotateObject.execute();
                    resultImage = rotateObject.getResult();
                }
                else if (object instanceof ColorImage colorImage) {


                }
                else if (object instanceof CvtImage cvtImage) {


                }

                picture.put("1", resultImage.clone());
        }
    }




    public static void initializePicture()
    {
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

    }
}
