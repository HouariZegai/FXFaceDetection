package com.houarizegai.facedetection.engine;

import com.houarizegai.facedetection.model.DetectedFace;
import com.houarizegai.facedetection.utils.Constants;
import com.houarizegai.facedetection.utils.Utils;
import javafx.scene.image.Image;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class FaceDetectionEngine {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static DetectedFace start(Image image) {
        Mat srcImg = Utils.imageToMat(image);

        CascadeClassifier classifier = new CascadeClassifier(Constants.RESOURCES_FOLDER + "xml/lbpcascade_frontalface.xml");

        MatOfRect faceDetection = new MatOfRect();
        classifier.detectMultiScale(srcImg, faceDetection);

        Rect[] faceDetectionArray = faceDetection.toArray();

        for(Rect rect: faceDetectionArray) {
            Imgproc.rectangle(srcImg, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height) , new Scalar(0, 0, 255), 3);
        }

        return new DetectedFace(Utils.matToImage(srcImg), faceDetectionArray.length);
    }
}
