/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIs;

import Database.DBConnector;
import Utilities.FileHandler;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Computebot
 */
public class DatabaseLaunchWindow extends Application {
    
     /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Stage databaseLocatorWindow = new Stage();
        
        BorderPane root = new BorderPane();
        root.setId("rootLaunchWindow");
        GridPane innerPane = new GridPane();
        innerPane.setId("innerLaunchPane");

        Text titleText = new Text("Welcome to your\nWhole Slabs Tracker\nPress 'Connect' or choose another slab database");
        titleText.setId("titleText");
        Label filePathLabel = new Label("Database Path:");
        TextField databasePathField = new TextField(FileHandler.readDatabaseConfig());
        Button browseBtn = new Button("Browse");
        Button connectBtn = new Button("Connect");
        Button closeBtn = new Button("Close");
        
        innerPane.add(titleText, 0, 0, 6, 1);
        innerPane.add(filePathLabel, 0, 2);
        innerPane.add(databasePathField, 0, 3, 6, 1);
        innerPane.add(browseBtn, 3, 4);
        innerPane.add(connectBtn, 4, 4);
        innerPane.add(closeBtn, 5, 4);
        
        innerPane.setMaxSize(350, 175);
        innerPane.setMinSize(350, 175);
        root.setTop(innerPane);
        BorderPane.setAlignment(innerPane,Pos.TOP_CENTER);
        Scene scene = new Scene(root, 600, 490);        
    
        databaseLocatorWindow.setTitle("Please Locate Database");
        databaseLocatorWindow.setScene(scene);
        scene.getStylesheets().add("/GUIs/GUIsStyleSheet.css");
        databaseLocatorWindow.getIcons().add(new Image("/GUIs/ARLogo.png"));
        databaseLocatorWindow.show();
        browseBtn.setOnAction((ActionEvent event) -> {
            FileHandler.setFilePath(primaryStage, databasePathField);
        });
        connectBtn.setOnAction((ActionEvent event) -> {
            DBConnector.setDBPath(databasePathField.getText().trim());
            try{
                SlabTracker newSlabTrackerWindow = new SlabTracker(primaryStage,databasePathField.getText().trim());
                databaseLocatorWindow.close();
            }catch(Exception e){/*Do Nothing because database string is incorrect*/}
        });
        closeBtn.setOnAction((ActionEvent event) -> {
            System.exit(0);
        });
    }
}
