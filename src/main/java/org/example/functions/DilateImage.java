package org.example.functions;

import org.example.MainWindow;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DilateImage {
    private static final Logger logger = Logger.getLogger(DilateImage.class.getName());

    private Mat src;
    private int power;
    private Mat result;

    public DilateImage(Mat src, int power) {
        this.src = src;
        this.power = power;
        logger.info("Создан экземпляр DilateImage с параметрами: power = " + power);
    }

    public void execute() {
        try {
            result = new Mat();
            src.copyTo(result);

            Mat element1 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT,
                    new Size(2 * power + 1, 2 * power + 1));
            Imgproc.dilate(src, result, element1);

            logger.info("Дилатация выполнена успешно с параметрами: power = " + power);
        } catch (Exception e) {
            result = null;
            logger.log(Level.SEVERE, "Ошибка во время выполнения дилатации: " + e.getLocalizedMessage(), e);
            MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.SEVERE, "Ошибка во время выполнения дилатации: " + e.getLocalizedMessage()));

        }
    }

    public Mat getResult() {
        return result;
    }

    public Mat getSrc() {
        return src;
    }

    public void setSrc(Mat src) {
        this.src = src;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setResult(Mat result) {
        this.result = result;
    }
}
