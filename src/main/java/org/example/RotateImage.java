package org.example;

import org.opencv.core.Core;
import org.opencv.core.Mat;

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
            result = new Mat();
            Core.rotate(src, result, rotateCode);
            src.release();
        }
        catch (Exception e)
        {
            result = null;
            System.out.println(e.getLocalizedMessage());
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

