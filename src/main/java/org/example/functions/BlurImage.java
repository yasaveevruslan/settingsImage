package org.example.functions;

import org.example.MainWindow;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import java.util.logging.Logger;
import java.util.logging.Level;

public class BlurImage {
    private static final Logger logger = Logger.getLogger(BlurImage.class.getName());

    private Mat src;
    private int cof;
    private Mat result;

    public BlurImage(Mat src, int cof) {
        this.src = src;
        this.cof = cof;
        logger.info("Создан экземпляр BlurImage с параметрами: cof = " + cof);
    }

    public void execute() {
        try {
            result = new Mat();
            Imgproc.blur(src, result, new Size(cof, cof));
            logger.info("Размытие выполнено успешно.");
        } catch (Exception e) {
            result = null;
            logger.log(Level.SEVERE, "Ошибка во время выполнения размытия: " + e.getLocalizedMessage(), e);
            MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.SEVERE, "Ошибка во время выполнения размытия: " + e.getLocalizedMessage()));

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

    public int getCof() {
        return cof;
    }

    public void setCof(int cof) {
        this.cof = cof;
    }

    public void setResult(Mat result) {
        this.result = result;
    }
}
