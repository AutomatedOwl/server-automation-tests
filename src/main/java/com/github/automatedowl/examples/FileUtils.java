package com.github.automatedowl.examples;

import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {
    public void writeJSON(JSONObject jsonObject) {
        try {
            FileWriter file = new FileWriter("output" + File.separator + "output.json");
            file.write(jsonObject.toString());
            file.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
