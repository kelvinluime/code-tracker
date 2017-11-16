package com.kinmanlui.controller;

import com.kinmanlui.structures.*;
import com.kinmanlui.info.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

public class EditorController implements Initializable{

    @FXML private TextArea textArea;

    private TestBase testBase;
    private TextFile currentTextFile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LocalDate today = LocalDate.now();
        List<String> lines = new LinkedList<>();
        lines.add("// Created on " + today);
        lines.add(Resource.PACKAGE_STATEMENT);
        lines.add("");
        lines.add("public class YOUR_CLASS_NAME_HERE implements Runnable {");
        lines.add("    @Override");
        lines.add("    public void run() {");
        lines.add("        // YOUR CODE HERE");
        lines.add("    }");
        lines.add("}");

        lines.forEach(line -> textArea.appendText(line + "\n"));

        testBase = TestBase.INSTANCE;
    }

    public void edit(Test test) {
        textArea.clear();
        textArea.setText(test.getContent());
    }

    @FXML
    private void onLoad() {
        throw new UnsupportedOperationException("Function has not been implemented.");
    }

    @FXML
    private void onSave() throws IOException{
        List<String> lines = new LinkedList<>();
        boolean classNameFound = false;
        boolean isClassName = false;
        String className = null;
        String testName;
        Optional<ButtonType> result = null;

        // Output a window to ask for the test name
        Stage testNameStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/kinmanlui/fxml/TestNameScene.fxml"));
        testNameStage.initModality(Modality.APPLICATION_MODAL);
        testNameStage.setResizable(false);
        testNameStage.setScene(new Scene(loader.load()));
        testNameStage.showAndWait();

        TestNameController testNameController = loader.getController();
        testName = testNameController.getTestName();

        // Store all the text from the text area into a list while finding the class name for creating a new file
        if(testName != null) {
            for (String line : textArea.getText().split("\\n")) {
                lines.add(line);
                Queue<String> q = new LinkedBlockingDeque<>();
                q.addAll(Arrays.asList(line.split(" ")));
                while (!classNameFound && !q.isEmpty()) {
                    String currentWord = q.remove();
                    if (isClassName) {
                        classNameFound = true;
                        className = currentWord;    // TODO: 11/16/17 Find a more secure way to get class name
                    } else if (currentWord.equals("class")) { // Find class name after first occurrence of "class"
                        isClassName = true;
                    }
                }
            }
            currentTextFile = new TextFile(Paths.get(Resource.ALGORITHM_PATH + className + ".java"), lines);

            // Handle
            if(new File(Resource.ALGORITHM_PATH + className + ".java").exists()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Warning");
                alert.setTitle("");
                alert.setContentText("A file with the same class name already exists. Do you want to replace it?");
                result = alert.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK) {
                    Files.delete(Paths.get(Resource.ALGORITHM_PATH + className + ".java"));
                    Files.write(Paths.get(Resource.ALGORITHM_PATH + className + ".java"), lines);
                } else {
<<<<<<< HEAD
                    return; // If user does not wish to replace the original file, go back to editor scene
=======
                    return; // If does not wish to replace the original file, go back to editor scene
>>>>>>> 58363744bc2b5e69252f8bdf5969ff30eb750579
                }
            }

            Files.write(Paths.get(Resource.ALGORITHM_PATH + className + ".java"), lines);
            onClose();
        }
    }

    /**
     * Close the window after save successfully or exit.
     */
    @FXML
    private void onClose() {
        Stage.class.cast(textArea.getScene().getWindow()).close();
    }
}
