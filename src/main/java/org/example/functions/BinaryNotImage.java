package org.example.functions;

import org.example.MainWindow;
import org.opencv.core.*;
import java.util.logging.Logger;
import java.util.logging.Level;

public class BinaryNotImage {
    private static final Logger logger = Logger.getLogger(BinaryNotImage.class.getName());

    private Mat src;
    private Mat result;

    public BinaryNotImage(Mat src) {
        this.src = src;
        logger.info("Создан экземпляр BinaryNotImage");
    }

    public void execute() {
        try {
            result = new Mat();
            Core.bitwise_not(src, result);
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

    public void setResult(Mat result) {
        this.result = result;
    }
}
