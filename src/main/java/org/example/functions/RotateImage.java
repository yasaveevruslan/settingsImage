package org.example.functions;

import org.example.MainWindow;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RotateImage {
    private static final Logger logger = Logger.getLogger(RotateImage.class.getName());

    private Mat src;
    private int rotateCode;
    private Mat result;

    public RotateImage(Mat src, int rotateCode) {
        this.src = src;
        this.rotateCode = rotateCode;
        logger.info("Создан экземпляр RotateImage с rotateCode=" + rotateCode);
    }

    public void execute() {
        try {
            result = new Mat();
            Point src_center = new Point(src.cols() / 2.0F, src.rows() / 2.0F);
            Mat rot_mat = Imgproc.getRotationMatrix2D(src_center, 360 - rotateCode, 1.0);
            Imgproc.warpAffine(src, result, rot_mat, src.size());
            rot_mat.release();

            logger.info("Изображение успешно повернуто на " + rotateCode + " градусов.");
        } catch (Exception e) {
            result = null;
            logger.log(Level.SEVERE, "Ошибка при повороте изображения: " + e.getLocalizedMessage(), e);
            MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.SEVERE, "Ошибка при повороте изображения: " + e.getLocalizedMessage()));
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
