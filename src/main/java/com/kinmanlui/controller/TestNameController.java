package com.kinmanlui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.input.KeyCode.ENTER;

public class TestNameController implements Initializable {

    @FXML private TextField textField;
    @FXML private Button submitButton;
    @FXML private Button cancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        submitButton.setFocusTraversable(false);
        cancelButton.setFocusTraversable(false);
    }

    @FXML
    private void submitOnMousePressed() {
        Stage window = Stage.class.cast(textField.getScene().getWindow());
        window.close();
    }

    @FXML
    private void cancelOnMousePressed() {
        textField.setText(null);    // Keep it as a null value so editor can determine whether cancel has been pressed
        Stage window = Stage.class.cast(textField.getScene().getWindow());
        window.close();
    }

    @FXML
    private void onKeyPressed(KeyEvent e) {
        if(e.getCode() == ENTER) submitOnMousePressed();
    }

    String getTestName() {
        return textField.getText();
    }

}
