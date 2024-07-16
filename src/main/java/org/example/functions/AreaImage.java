package org.example.functions;

import org.example.MainWindow;
import org.opencv.core.*;
import java.util.logging.Logger;
import java.util.logging.Level;

public class AreaImage {
    private static final Logger logger = Logger.getLogger(AreaImage.class.getName());

    private Mat src;
    private Mat result;
    private int area;

    public AreaImage(Mat src) {
        this.src = src;
        logger.info("Создан экземпляр AreaImage");
    }

    public void execute() {
        try {
            result = src;
            area = Core.countNonZero(src);
            logger.info("Выполнение завершено успешно. Площадь ненулевых пикселей: " + area);
        } catch (Exception e) {
            result = null;
            logger.log(Level.SEVERE, "Ошибка во время выполнения: " + e.getLocalizedMessage(), e);
            MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.SEVERE, "Ошибка во время выполнения: " + e.getLocalizedMessage()));
        }
    }

    public Mat getResult() {
        return result;
    }

    public int getArea() {
        return area;
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

    public void setArea(int area) {
        this.area = area;
    }
}
