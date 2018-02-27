package com.microsoft.azure.storage;

import com.linkedin.flashback.scene.SceneMode;
import jdk.jfr.events.ExceptionThrownEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TestUtility {

    public static boolean enableDebugging = false;

    public static boolean enableRecordings = true;

    public static String containerPrefix = "javatestcontainer";

    public static SceneMode sceneMode = SceneMode.PLAYBACK;

    public static final String sceneDir =
            "C:\\Users\\frley\\Documents\\azure-storage-java-async\\azure-storage\\src\\test\\resources\\recordings\\";

    public static void scrubAuthHeader(String sceneName) {
        try {
            FileReader fr = new FileReader(sceneDir + sceneName);

        }
        catch (Exception e) {
            throw new Error(e);
        }
    }
}
