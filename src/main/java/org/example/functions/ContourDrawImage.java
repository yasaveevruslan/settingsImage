package org.example.functions;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;

public class ContourDrawImage {
    private Mat src;
    private Mat result;

    public ContourDrawImage(Mat src) {
        this.src = src;
    }

    public void execute()
    {
        try {
            result = contours(src);
        }catch (Exception e)
        {
            result = null;
        }

    }

    public Mat contours(Mat mat)
    {
        ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Mat hierarchy = new Mat();

        Mat balele = new Mat();
        mat.convertTo(balele, CvType.CV_32SC1);
        // find contours:
        Imgproc.findContours(mat.clone(), contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);


        Mat source = new Mat(balele.size(), balele.type());
        for (int contourIdx = 0; contourIdx < contours.size(); contourIdx++)
        {
            Imgproc.drawContours(source, contours, contourIdx, new Scalar(0,0,255), -1);
        }

        return source;
    }

    public Mat getResult() {
        return result;
    }

}

