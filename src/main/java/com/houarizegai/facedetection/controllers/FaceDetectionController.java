package com.houarizegai.facedetection.controllers;

import com.houarizegai.facedetection.engine.FaceDetectionEngine;
import com.houarizegai.facedetection.model.DetectedFace;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FaceDetectionController implements Initializable {

    @FXML
    private Label lblNbrOfFaces;

    @FXML
    private StackPane imgContainer;
    @FXML
    private ImageView imageView;

    private FileChooser fileChooser; // For select path of saving picture captured

    public static boolean isCapture = false; // For stop & resume thread of camera

    public static VideoCapture capture;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /* Bind views */
        imageView.fitWidthProperty().bind(imgContainer.widthProperty());
        imageView.fitHeightProperty().bind(imgContainer.heightProperty());

        /* Init save file chooser */
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images (*.png)", "*.png"));
        fileChooser.setTitle("Save Image");
        imageView.setPreserveRatio(true);

        // Init open cv Camera
        capture = new VideoCapture();
        this.capture.open(0);
        if (!this.capture.isOpened()) {
            System.out.println("Camera doesn't exists!");
            Platform.exit();
        }

        new VideoTacker().start(); // Start camera capture

//        imageView.setFitWidth(600);
//        imageView.setFitHeight(400);
    }

    @FXML
    private void onCapture() { // Stop camera & taking picture
        isCapture = true;
    }

    @FXML
    private void onReload() { // Resume camera capture
        isCapture = false;
        new VideoTacker().start();
    }

    @FXML
    private void onSave() {
        isCapture = true; // Stop taking pictures

        File file = fileChooser.showSaveDialog(imageView.getScene().getWindow());
        if (file != null)
            try { // Save picture with .png extension
                ImageIO.write(SwingFXUtils.fromFXImage(imageView.getImage(), null), "PNG", file);
            } catch (IOException ex) {
                ex.printStackTrace(); // Can't save picture
            }
    }

    class VideoTacker extends Thread {
        @Override
        public void run() {
            while (!isCapture) { // Take picture & set it in image view (for each 30ms)
                try {
                    Mat frame = new Mat();
                    capture.read(frame); // Take picture and store it in frame object
                    if(frame.width() < 1)
                        continue;
                    DetectedFace detectedFace = FaceDetectionEngine.detectFaces(frame);
                    if(detectedFace != null) {
                         Platform.runLater(() -> {
                            lblNbrOfFaces.setText(String.valueOf(detectedFace.getNbrOfFaces()));
                            imageView.setImage(detectedFace.getOutputImg());
                         });
                    }
                    sleep(30);
                } catch (InterruptedException ex) {
                    System.err.println("Video Tracker error!");
                }
            }
        }
    }
}


