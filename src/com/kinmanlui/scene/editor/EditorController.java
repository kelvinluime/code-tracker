package com.kinmanlui.scene.editor;

import com.kinmanlui.database.TestBase;
import com.kinmanlui.main.Main;
import com.kinmanlui.res.Resource;
import com.kinmanlui.structures.algorithm.Test;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
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
    private Test test;

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
        test = null;
    }

    public void edit(Test test) {
        textArea.clear();
        textArea.setText(test.getContent());
        this.test = test;
    }

    @FXML
    private void onLoad() {
        throw new UnsupportedOperationException("Function has not been implemented.");
    }

    @FXML
    private void onSave() throws IOException{

        // TODO: 11/9/17 Save differently if the user is just editing a file

        List<String> lines = new LinkedList<>();
        boolean classNameFound = false;
        boolean isClassName = false;
        String className = null;
        String testName;

        // Output a window to ask for the test name
        Stage testNameStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TestNameScene.fxml"));
        testNameStage.initModality(Modality.APPLICATION_MODAL);
        testNameStage.setResizable(false);
        testNameStage.setScene(new Scene(loader.load()));
        testNameStage.showAndWait();

        TestNameController testNameController = loader.getController();
        testName = testNameController.getTestName();

        // TODO: 10/20/17 Think of a safer approach to get class name
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
                        className = currentWord;
                    } else if (currentWord.equals("class")) { // Find class name after first occurrence of "class"
                        isClassName = true;
                    }
                }
            }
            currentTextFile = new TextFile(Paths.get(Resource.CODE_PATH + className + ".java"), lines);

            // Check if the file already exists
            if(new File(Resource.CODE_PATH + className + ".java").exists()) {
                Stage stage = new Stage();
                loader = new FXMLLoader(getClass().getResource("AlertBox.fxml"));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setResizable(false);
                stage.setScene(new Scene(loader.load()));
                stage.showAndWait();

                // If a same class file already exists, asks the user whether to replace it
                // TODO: 10/30/17 Does not respond
                AlertBoxController alertBoxController = loader.getController();
                if(alertBoxController.getRespond()) {   // Replace file when the respond is true
                    Files.delete(Paths.get(Resource.CODE_PATH + className + ".java"));
                    Files.write(currentTextFile.getFile().toAbsolutePath(), currentTextFile.getContent());
                }
            } else {
                testBase.insert(className, testName);
                Files.write(currentTextFile.getFile().toAbsolutePath(), currentTextFile.getContent());
                // TODO: 10/30/17 Reload test page when a new test is added

                loader = new FXMLLoader(getClass().getResource("../tester/Scene.fxml"));
                Main.window.setScene(new Scene(loader.load()));

                onClose();
            }
        }
    }

    @FXML
    private void onClose() {
        Stage.class.cast(textArea.getScene().getWindow()).close();
    }
}
