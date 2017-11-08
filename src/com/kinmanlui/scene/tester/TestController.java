/**
 *
 */
package com.kinmanlui.scene.tester;

import com.kinmanlui.main.Test;
import com.kinmanlui.main.Tester;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class TestController implements Initializable{

    @FXML private ListView list;
    @FXML private TextArea textArea;
    private Tester tester;
    private HashMap<String, Test> testMap;
    private ListProperty<String> listProperty;

    // TODO: 10/16/17 Create a text field which allows users to search tester names

    @Override
    @SuppressWarnings("unchecked")
    public void initialize(URL location, ResourceBundle resources) {

        // testing
        System.out.println("Tester is loaded.");

        tester = new Tester();
        testMap = tester.getTestMap();
        listProperty = new SimpleListProperty<>();

        // TODO: 10/30/17 Bind map keys with list view so that list view can update automatically when insertion occurs
        listProperty.set(FXCollections.observableList(new ArrayList<>(testMap.keySet())));

        list.itemsProperty().bindBidirectional(listProperty);
        list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            textArea.setText(testMap.get(newValue).getSourceCode());
        });
        list.getSelectionModel().select(0);

        // For unknown reasons, this style does not work on css, so I put it in the controller
        textArea.setStyle("-fx-focus-color: transparent; -fx-text-box-border: transparent;");
        textArea.setFocusTraversable(false);
    }

    public void reload() {

    }

    @FXML
    private void addOnMousePressed() {
        try {
            Stage window = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("../editor/EditorScene.fxml"));
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
        throw new UnsupportedOperationException();
    }

    @FXML
    private void removeOnMousePressed() {
        throw new UnsupportedOperationException();
    }

    @FXML
    private void runOnMousePressed() {
        String test = (String) list.getSelectionModel().getSelectedItem();
        if (test != null) {
            tester.execute(testMap.get(test).getTestThread());
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
