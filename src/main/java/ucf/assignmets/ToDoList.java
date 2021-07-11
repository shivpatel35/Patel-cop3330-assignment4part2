/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Shiv Patel
 */

package ucf.assignmets;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

//start class for fxmlFile
public class ToDoList extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            String url = "ucf/assignmets/ToDoList.fxml";
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(url));



            Scene scene = new Scene(root);


            primaryStage.setTitle("Todo List");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}

