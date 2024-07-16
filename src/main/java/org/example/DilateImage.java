package org.example;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;


public class DilateImage {
    private Mat src;
    private int power;
    private Mat result;

    public DilateImage(Mat src, int power) {
        this.src = src;
        this.power = power;
    }

    public void execute()
    {
        try
        {

            result = new Mat();
            src.copyTo(result);

            Mat element1 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT,
                    new Size(2 * power + 1, 2 * power + 1));
            Imgproc.dilate(src, result, element1);

        }
        catch (Exception e)
        {
            result = null;
//            System.out.println(e.getLocalizedMessage());
        }
    }

    public int getPower() {
        return power;
    }

    public Mat getSrc() {
        return src;
    }

    public Mat getResult() {
        return result;
    }

}

