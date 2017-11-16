/**
 * The starting point of the GUI application.
 * @author Kelvin Lui (www.kinmanlui.com)
 */
package com.kinmanlui.start;

import com.kinmanlui.structures.TestBase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage window;

    public static void main(String[] args) {
        TestBase testBase = TestBase.INSTANCE;
        launch(args);
        testBase.close();
        System.exit(0);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        Parent scene = FXMLLoader.load(getClass().getResource("/com/kinmanlui/fxml/Main.fxml"));
        window.setTitle("CodeTracker");
        window.setScene(new Scene(scene));
        window.setResizable(true);
        window.show();
    }
}
