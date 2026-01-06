package RoboSimJava.functions;

import RoboSimJava.MainWindow;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MatToBufImg {
    private static final Logger logger = Logger.getLogger(MatToBufImg.class.getName());

    private Mat matrix;
    private MatOfByte mob;
    private String fileExten;

    public MatToBufImg() {

    }

    public MatToBufImg(Mat matrix, String fileExten) {
        this.matrix = matrix;
        this.fileExten = fileExten;
        this.mob = new MatOfByte();
    }

    public void setMatrix(Mat matrix, String fileExten) {
        this.matrix = matrix;
        this.fileExten = fileExten;
        this.mob = new MatOfByte();
    }

    public BufferedImage getBufferedImage() {
        Imgcodecs.imencode(fileExten, matrix, mob);
        byte[] byteArray = mob.toArray();
        BufferedImage bufImage = null;

        try {
            InputStream in = new ByteArrayInputStream(byteArray);
            bufImage = ImageIO.read(in);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Ошибка при чтении изображения из Mat в BufferedImage: " + e.getLocalizedMessage(), e);
            MainWindow.fileHandler.publish(new java.util.logging.LogRecord(Level.SEVERE, "Ошибка при чтении изображения из Mat в BufferedImage: " + e.getLocalizedMessage()));

        }
        return bufImage;
    }
}