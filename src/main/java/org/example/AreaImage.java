package org.example;

import org.opencv.core.*;

public class AreaImage {
    private Mat src;
    private Mat result;
    private int area;

    public AreaImage(Mat src) {
        this.src = src;
    }

    public void execute()
    {
        try
        {

            result = src;
            area = Core.countNonZero(src);


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
    public int getArea() {
        return area;
    }

}

