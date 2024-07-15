package org.example;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;


public class RectImage {
    private Mat src;
    private int x, y, width, height;
    private Mat result;

    public RectImage(Mat src, int x, int y, int width, int height) {
        this.src = src;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void execute()
    {
        try
        {
            result = new Mat();

            int newWidth = src.cols() < x + width ? src.cols() - x : width;
            int newHeight = src.rows() < y + height ? src.rows() - y : height;
            Rect point = new Rect(x, y, newWidth, newHeight);
            result = src.submat(point);

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

