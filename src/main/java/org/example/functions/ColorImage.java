package org.example.functions;

import org.example.MainWindow;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import java.util.logging.Logger;
import java.util.logging.Level;

public class ColorImage {
    private static final Logger logger = Logger.getLogger(ColorImage.class.getName());

    private Mat src;
    private int minR, maxR, minG, maxG, minB, maxB;
    private Mat result;

    public ColorImage(Mat src, int minR, int maxR, int minG, int maxG, int minB, int maxB) {
        this.src = src;
        this.minR = minR;
        this.maxR = maxR;
        this.minG = minG;
        this.maxG = maxG;
        this.minB = minB;
        this.maxB = maxB;
        logger.info("Создан экземпляр ColorImage с параметрами: minR = " + minR + ", maxR = " + maxR + ", minG = " + minG + ", maxG = " + maxG + ", minB = " + minB + ", maxB = " + maxB);
    }

    public void execute() {
        try {
            result = new Mat();
            Core.inRange(src, new Scalar(minR, minG, minB), new Scalar(maxR, maxG, maxB), result);
            logger.info("Фильтрация по цвету выполнена успешно.");
        } catch (Exception e) {
            result = null;
            logger.log(Level.SEVERE, "Ошибка во время выполнения фильтрации по цвету: " + e.getLocalizedMessage(), e);
            MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.SEVERE, "Ошибка во время выполнения фильтрации по цвету: " + e.getLocalizedMessage()));

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

    public int getMinR() {
        return minR;
    }

    public void setMinR(int minR) {
        this.minR = minR;
    }

    public int getMaxR() {
        return maxR;
    }

    public void setMaxR(int maxR) {
        this.maxR = maxR;
    }

    public int getMinG() {
        return minG;
    }

    public void setMinG(int minG) {
        this.minG = minG;
    }

    public int getMaxG() {
        return maxG;
    }

    public void setMaxG(int maxG) {
        this.maxG = maxG;
    }

    public int getMinB() {
        return minB;
    }

    public void setMinB(int minB) {
        this.minB = minB;
    }

    public int getMaxB() {
        return maxB;
    }

    public void setMaxB(int maxB) {
        this.maxB = maxB;
    }

    public void setResult(Mat result) {
        this.result = result;
    }
}
