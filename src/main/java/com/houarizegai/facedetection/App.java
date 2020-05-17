package com.houarizegai.facedetection;

import com.houarizegai.facedetection.engine.FaceDetectionEngine;
import com.houarizegai.facedetection.model.DetectedFace;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        DetectedFace detectedFace = FaceDetectionEngine.start(new Image("/images/input.jpg"));
        System.out.println(detectedFace.getNbrOfFaces());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
