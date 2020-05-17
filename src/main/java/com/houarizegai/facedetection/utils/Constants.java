package com.houarizegai.facedetection.utils;

import java.nio.file.Paths;

public class Constants {
    public static final String RESOURCES_PATH;

    static {
        // Get relative path
        RESOURCES_PATH = new StringBuilder().append(Paths.get("").toAbsolutePath())
                .append("\\src\\main\\resources\\").toString();
    }
}
