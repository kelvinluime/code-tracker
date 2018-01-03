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

        int[] arr = {1,2,42,63,25,83,3,9,7,5};
        quickSort(arr, 1, arr.length - 1);
        System.out.println(arr);

        launch(args);
        testBase.close();
        System.exit(0);
    }

    private static void quickSort(int[] arr, int start, int end) {
        if(start < end) {
            int q = partition(arr, start,  end);
        }
    }

    private static int partition(int[] arr, int start, int end) {
        int x = arr[end];
        int i = start - 1;
        for(int j = 0; j < end; j++) {
            if (arr[j] <= x) {
                i++;
                int tmp = arr[j];
                arr[j] = arr[i];
                arr[i] = tmp;
            }
        }
        arr[end] = arr[i];
        arr[i] = x;
        System.out.println(i + 1);
        return i + 1;
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
