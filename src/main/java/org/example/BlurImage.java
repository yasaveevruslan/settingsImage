package org.example;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;


public class BlurImage {
    private Mat src;
    private int cof;
    private Mat result;

    public BlurImage(Mat src, int cof) {
        this.src = src;
        this.cof = cof;
    }

    public void execute()
    {
        try
        {

            result = new Mat();
            Imgproc.blur(src, result, new Size(cof, cof));

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

