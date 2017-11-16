/**
 *
 */
package com.kinmanlui.controller;

import com.kinmanlui.info.Resource;
import com.kinmanlui.structures.Test;
import com.kinmanlui.structures.TestBase;
import com.kinmanlui.tester.Tester;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MainController implements Initializable{

    @FXML private ListView list;
    @FXML private TextArea textArea;
    private Tester tester;
    private HashMap<String, Test> testMap;
    private ListProperty<String> listProperty;
    private TestBase testBase;

    // TODO: 10/16/17 Create a text field which allows users to search com.kinmanlui.tester names

    @Override
    @SuppressWarnings("unchecked")
    public void initialize(URL location, ResourceBundle resources) {
        testBase = TestBase.INSTANCE;

        // testing
        System.out.println("Tester is loaded.");

        tester = new Tester();
        testMap = tester.getTestMap();
        listProperty = new SimpleListProperty<>();

        // TODO: 10/30/17 Bind map keys with list view so that list view can update automatically when insertion occurs
        listProperty.set(FXCollections.observableList(new ArrayList<>(testMap.keySet())));

        // Update text area based on the selected item in the list view
        list.itemsProperty().bindBidirectional(listProperty);
        list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            textArea.setText(testMap.get(newValue).getContent());
        });
        list.getSelectionModel().select(0);

        // For unknown reasons, this style does not work on css, so I put it in the com.kinmanlui.controller
        textArea.setStyle("-fx-focus-color: transparent; -fx-text-box-border: transparent;");
        textArea.setFocusTraversable(false);
    }

    @FXML
    private void addOnMousePressed() {
        try {
            Stage window = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/kinmanlui/fxml/EditorScene.fxml"));
            window.initModality(Modality.APPLICATION_MODAL);
            window.setResizable(false);
            window.setScene(new Scene(root));
            window.showAndWait();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void editOnMousePressed() {
        try {
            // Avoid using static method FXMLLoader.load() because com.kinmanlui.controller will not be initialized. Otherwise,
            // it will throw null-pointer exception
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/kinmanlui/fxml/EditorScene.fxml"));
            Parent root = loader.load();    // load() method should only be specified once

            EditorController controller = loader.getController();
            String testName = (String) list.getSelectionModel().getSelectedItem();
            Test test = testMap.get(testName);
            try {
                controller.edit(test);  // TODO: 11/11/17 Throws null-pointer exception
            } catch (NullPointerException e) {
                System.err.println(e.getMessage());
            }
            Stage window = new Stage();
            window.setResizable(false);
            window.initModality(Modality.WINDOW_MODAL);
            window.setScene(new Scene(root));
            window.showAndWait();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    private void removeOnMousePressed() {
        // TODO: 11/8/17 Reload main scene after deleting

        // Delete the physical file
        String testName = (String) list.getSelectionModel().getSelectedItem();
        String className = testMap.get(testName).getClassName();
        try {
            Files.delete(Paths.get(Resource.ALGORITHM_PATH + className + ".java"));
        } catch (IOException e) {
            System.err.println("Unable to delete file: " + e.getMessage());
        }

        // Delete the line associating with the file in the com.kinmanlui.database
        testBase.remove(className);
    }

    @FXML
    private void runOnMousePressed() {
        String testName = (String) list.getSelectionModel().getSelectedItem();
        if (testName != null) {
            tester.execute(testMap.get(testName).getTestThread());
        }
    }

    @FXML
    private void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
                runOnMousePressed();
                e.consume();
                break;
        }
    }
}
