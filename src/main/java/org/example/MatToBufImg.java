package org.example;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MatToBufImg {
    Mat matrix;
    MatOfByte mob;
    String fileExten;

    public MatToBufImg()
    {

    }

    public MatToBufImg(Mat amatrix, String fileExt)
    {
        matrix = amatrix;
        fileExten = fileExt;
        mob = new MatOfByte();
    }

    public void setMatrix(Mat amatrix, String fileExt)
    {
        matrix = amatrix;
        fileExten = fileExt;
        mob = new MatOfByte();
    }

    public BufferedImage getBufferedImage()
    {
        Imgcodecs.imencode(fileExten, matrix, mob);
        byte[] byteArray = mob.toArray();
        BufferedImage bufImage = null;

        try {
            InputStream in = new ByteArrayInputStream(byteArray);
            bufImage = ImageIO.read(in);
        } catch (IOException e) {

//            e.printStackTrace();
        }
        return bufImage;
    }
}
