package RoboSimJava.functions;

import RoboSimJava.MainWindow;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;

public class ContourDrawImage {
    private static final Logger logger = Logger.getLogger(ContourDrawImage.class.getName());

    private Mat src;
    private Mat result;

    public ContourDrawImage(Mat src) {
        this.src = src;
        logger.info("Создан экземпляр ContourDrawImage");
    }

    public void execute() {
        try {
            result = contours(src);
            logger.info("Поиск и отрисовка контуров выполнены успешно.");
        } catch (Exception e) {
            result = null;
            logger.log(Level.SEVERE, "Ошибка во время выполнения поиска и отрисовки контуров: " + e.getLocalizedMessage(), e);
        }
    }

    public Mat contours(Mat mat) {
        ArrayList<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();

        try {
            Mat balele = new Mat();
            mat.convertTo(balele, CvType.CV_32SC1);
            // find contours:
            Imgproc.findContours(mat.clone(), contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

            Mat source = new Mat(balele.size(), balele.type());
            for (int contourIdx = 0; contourIdx < contours.size(); contourIdx++) {
                Imgproc.drawContours(source, contours, contourIdx, new Scalar(0, 0, 255), -1);
            }

            return source;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Ошибка во время выполнения поиска контуров: " + e.getLocalizedMessage(), e);
            MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.SEVERE, "Ошибка во время выполнения поиска контуров: " + e.getLocalizedMessage()));

            return null;
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
}