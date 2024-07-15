package org.example;

import org.opencv.core.*;

public class BinaryAndImage {
    private Mat src, src2;
    private Mat result;

    public BinaryAndImage(Mat src, Mat src2) {
        this.src = src;
        this.src2 = src2;
    }

    public void execute()
    {
        try
        {

            result = new Mat();
            Core.bitwise_and(src, src2, result);

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
