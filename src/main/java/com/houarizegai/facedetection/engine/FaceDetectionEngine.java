package com.houarizegai.facedetection.engine;

import com.houarizegai.facedetection.model.DetectedFace;
import com.houarizegai.facedetection.utils.Constants;
import com.houarizegai.facedetection.utils.Utils;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class FaceDetectionEngine {

    public static DetectedFace detectFaces(Mat imgMat) {
//        if(image == null)
//            return null;
//
//        Mat srcImg = Utils.imageToMat(image);

        CascadeClassifier classifier = new CascadeClassifier(Constants.RESOURCES_PATH + "xml/lbpcascade_frontalface.xml");

        MatOfRect faceDetection = new MatOfRect();
        classifier.detectMultiScale(imgMat, faceDetection);

        Rect[] faceDetectionArray = faceDetection.toArray();
        for(Rect rect: faceDetectionArray) {
            Imgproc.rectangle(imgMat, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height) , new Scalar(0, 0, 255), 3);
        }

        return new DetectedFace(Utils.matToImage(imgMat), faceDetectionArray.length);
    }
}
