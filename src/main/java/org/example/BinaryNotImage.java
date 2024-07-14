package org.example;

import org.opencv.core.*;

public class BinaryNotImage {
    private Mat src;
    private Mat result;

    public BinaryNotImage(Mat src) {
        this.src = src;
    }

    public void execute()
    {
        try
        {

            result = new Mat();
            Core.bitwise_not(src, result);

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

