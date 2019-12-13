/**
 * Program: Text Editor
 * Author: Abdushakoor Abdelazeem Mosa Tom (Shoukrey Tom)
 * Date: 13 Dec 2019
 * Purpose: Educational Purpose
 * Licenses: no license
 */
package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    public static Stage stage;
    @Override
    public void start(Stage windows) throws Exception{
        stage = windows;
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        windows.setTitle("Text Editor[ New Document ]");
        File url = new File("/home/shoukreytom/IdeaProjects/Text Editor/src/sample/icon.png");
        Image image = new Image(url.toURI().toURL().toString());
        windows.getIcons().add(image);
        windows.setScene(new Scene(root, 800, 500));
        windows.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
