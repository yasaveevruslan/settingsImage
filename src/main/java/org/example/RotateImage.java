package org.example;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;


public class RotateImage {
    private Mat src;
    private int rotateCode;
    private Mat result;

    public RotateImage(Mat src, int rotateCode) {
        this.src = src;
        this.rotateCode = rotateCode;
    }

    public void execute()
    {
        try
        {
//            result = new Mat(src.rows(), src.cols(), src.type());
            result = new Mat();
            Point src_center = new Point(src.cols() / 2.0F, src.rows() / 2.0F);
            Mat rot_mat = Imgproc.getRotationMatrix2D(src_center, 360 - rotateCode, 1.0);
            Imgproc.warpAffine(src, result, rot_mat, src.size());

//            result = new Mat();
//            Core.rotate(src, result, rotateCode);
//            src.release();
            rot_mat.release();

//            result = dst;
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

    public void setResult(Mat result) {
        this.result = result;
    }

    public Mat getSrc() {
        return src;
    }

    public void setSrc(Mat src) {
        this.src = src;
    }

    public int getRotateCode() {
        return rotateCode;
    }

    public void setRotateCode(int rotateCode) {
        this.rotateCode = rotateCode;
    }
}

