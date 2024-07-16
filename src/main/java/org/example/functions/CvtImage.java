package org.example.functions;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;


public class CvtImage {
    private Mat src;
    private int cvtCode;
    private Mat result;

    public CvtImage(Mat src, int cvtCode) {
        this.src = src;
        this.cvtCode = cvtCode;
    }

    public void execute()
    {
        try
        {
            result = new Mat();
            Imgproc.cvtColor(src, result, cvtCode);
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

    public int getcvtCode() {
        return cvtCode;
    }

    public void setcvtCode(int cvtCode) {
        this.cvtCode = cvtCode;
    }
}

