package org.example.functions;

import org.example.MainWindow;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import java.util.logging.Logger;
import java.util.logging.Level;

public class CvtImage {
    private static final Logger logger = Logger.getLogger(CvtImage.class.getName());

    private Mat src;
    private int cvtCode;
    private Mat result;

    public CvtImage(Mat src, int cvtCode) {
        this.src = src;
        this.cvtCode = cvtCode;
        logger.info("Создан экземпляр CvtImage с параметрами: cvtCode = " + cvtCode);
    }

    public void execute() {
        try {
            result = new Mat();
            Imgproc.cvtColor(src, result, cvtCode);
            logger.info("Конвертация выполнена успешно.");
        } catch (Exception e) {
            result = null;
            logger.log(Level.SEVERE, "Ошибка во время выполнения конвертации: " + e.getLocalizedMessage(), e);
            MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.SEVERE, "Ошибка во время выполнения конвертации: " + e.getLocalizedMessage()));

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

    public int getCvtCode() {
        return cvtCode;
    }

    public void setCvtCode(int cvtCode) {
        this.cvtCode = cvtCode;
    }
}
