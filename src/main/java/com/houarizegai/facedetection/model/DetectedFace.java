package com.houarizegai.facedetection.model;

import javafx.scene.image.Image;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DetectedFace {
    private Image outputImg; // Output image
    private int nbrOfFaces; // Number of faces
}
