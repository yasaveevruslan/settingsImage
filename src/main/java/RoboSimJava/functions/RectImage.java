package RoboSimJava.functions;

import RoboSimJava.MainWindow;
import org.opencv.core.*;
import java.util.logging.*;

public class RectImage {
    private static final Logger logger = Logger.getLogger(RectImage.class.getName());

    private Mat src;
    private int x, y, width, height;
    private Mat result;

    public RectImage(Mat src, int x, int y, int width, int height) {
        this.src = src;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        logger.info("Создан экземпляр RectImage с параметрами: src(" + src.cols() + "x" + src.rows() +
                "), x=" + x + ", y=" + y + ", width=" + width + ", height=" + height);
    }

    public void execute() {
        try {
            result = new Mat();

            int newWidth = src.cols() < x + width ? src.cols() - x : width;
            int newHeight = src.rows() < y + height ? src.rows() - y : height;
            Rect roi = new Rect(x, y, newWidth, newHeight);
            result = new Mat(src, roi);

            logger.log(Level.INFO, "Извлечение прямоугольника выполнено успешно.");

        } catch (Exception e) {
            result = null;
            logger.log(Level.SEVERE, "Ошибка при выполнении извлечения прямоугольника: " + e.getMessage(), e);
            MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.SEVERE, "Ошибка при выполнении извлечения прямоугольника: " + e.getMessage()));

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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setResult(Mat result) {
        this.result = result;
    }
}