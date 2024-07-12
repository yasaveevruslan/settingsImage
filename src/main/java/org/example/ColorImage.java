package org.example;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;


public class ColorImage {
    private Mat src;
    private int minR, maxR, minG, maxG, minB, maxB;
    private Mat result;

    public ColorImage(Mat src, int minR, int maxR, int minG, int maxG, int minB, int maxB) {
        this.src = src;
        this.minR = minR;
        this.maxR = maxR;
        this.minG = minG;
        this.maxG = maxG;
        this.minB = minB;
        this.maxB = maxB;
    }

    public void execute()
    {
        try
        {

            result = new Mat();
            Core.inRange(src, new Scalar(minR, minG, minB), new Scalar(maxR, maxG, maxB), result);

        }
        catch (Exception e)
        {
            result = null;
//            System.out.println(e.getLocalizedMessage());
        }
    }

    public Mat getResult() {
        return result;
    }

}

