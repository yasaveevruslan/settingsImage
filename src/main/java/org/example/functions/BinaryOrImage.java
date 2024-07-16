package org.example.functions;

import org.example.MainWindow;
import org.opencv.core.*;
import java.util.logging.Logger;
import java.util.logging.Level;

public class BinaryOrImage {
    private static final Logger logger = Logger.getLogger(BinaryOrImage.class.getName());

    private Mat src, src2;
    private Mat result;

    public BinaryOrImage(Mat src, Mat src2) {
        this.src = src;
        this.src2 = src2;
        logger.info("Создан экземпляр BinaryOrImage");
    }

    public void execute() {
        try {
            result = new Mat();
            Core.bitwise_or(src, src2, result);
            logger.info("Выполнение завершено успешно.");
        } catch (Exception e) {
            result = null;
            logger.log(Level.SEVERE, "Ошибка во время выполнения: " + e.getLocalizedMessage(), e);
            MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.SEVERE, "Ошибка во время выполнения: " + e.getLocalizedMessage()));
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

    public Mat getSrc2() {
        return src2;
    }

    public void setSrc2(Mat src2) {
        this.src2 = src2;
    }

    public void setResult(Mat result) {
        this.result = result;
    }
}
