package com.kinmanlui.scene.editor;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AlertBoxController {

    private boolean respond;

    @FXML private VBox wrapper;

    @FXML
    private void noOnKeyPressed() {
        respond = false;
        Stage.class.cast(wrapper.getScene().getWindow()).close();
    }

    @FXML
    private void yesOnKeyPressed() {
        respond = true;
        Stage.class.cast(wrapper.getScene().getWindow()).close();
    }

    boolean getRespond() { return respond; }
}
