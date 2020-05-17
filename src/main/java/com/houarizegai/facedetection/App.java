package com.houarizegai.facedetection;

import com.houarizegai.facedetection.controllers.FaceDetectionController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.opencv.core.Core;

import java.io.IOException;

public class App extends Application {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/FaceDetection.fxml"));
            stage.setScene(new Scene(root));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        stage.setTitle("Face Detection");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() {
        FaceDetectionController.capture.release();
    }
}
