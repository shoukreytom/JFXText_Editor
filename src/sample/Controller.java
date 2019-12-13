package sample;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Controller {

    private FileChooser fc = new FileChooser();
    private Main m = new Main();
    @FXML
    private TextArea screen;

    @FXML
    public void newFile() {
        Main.stage.setTitle("Text Editor[ New Document ]");
        screen.clear();
    }

    @FXML
    public void openFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getenv("HOME")));
        File file = fileChooser.showOpenDialog(Main.stage.getOwner());
        Main.stage.setTitle("Text Editor[ "+file.getPath()+" ]");
        if (file != null) {
            new Thread() {
                @Override
                public void run() {
                    screen.setText(readFile(file).toString());
                }
            }.run();
        }
    }

    @FXML
    public void saveFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getenv("HOME")));
        File file = null;
        if (Main.stage.getTitle().equals("Text Editor[ New Document ]")) {
            file = fileChooser.showSaveDialog(Main.stage.getOwner());
            if(file != null) {
                try {
                    Files.createFile(Paths.get(file.toURI()));
                } catch (Exception e) {

                }
                String file_name = file.getPath();
                new Thread() {
                    @Override
                    public void run() {
                        File newfile = new File(file_name);
                        writeFile(newfile, screen);
                    }
                }.run();
                Main.stage.setTitle("Text Editor[ "+file.getPath()+" ]");
            }
        }else {
            int index1 = Main.stage.getTitle().indexOf("[ ");
            int index2 = Main.stage.getTitle().indexOf(" ]");
            String file_name = Main.stage.getTitle().substring(index1+2, index2);
            File wfile = new File(file_name);
            System.out.println(file_name);
            new Thread() {
                @Override
                public void run() {
                    writeFile(wfile, screen);
                }
            }.run();
        }
    }

    @FXML
    public void saveAs() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getenv("HOME")));
        File file = fileChooser.showSaveDialog(Main.stage.getOwner());
        if (file != null) {
            try {
                Files.createFile(Paths.get(file.toURI()));
            } catch (Exception e) {

            }
            new Thread() {

                @Override
                public void run() {
                    writeFile(file, screen);
                }
            }.run();
            Main.stage.setTitle("Text Editor[ "+file.getPath()+" ]");
        }
    }

    @FXML
    public void exit() {
        Main.stage.close();
    }

    @FXML
    public void undo() {
        screen.undo();
    }

    @FXML
    public void redo() {
        screen.redo();
    }

    @FXML
    public void copy() {
        screen.copy();
    }

    @FXML
    public void cut() {
        screen.cut();
    }

    @FXML
    public void paste() {
        screen.paste();
    }

    @FXML
    public void selectAll() {
        screen.selectAll();
    }

    //you can edit it
    @FXML
    public void about() {
        Stage stage = new Stage();
        stage.setTitle("About");
        BorderPane pane = new BorderPane();
        String text = "\n\n"+
                " * Program: Text Editor \n" +
                " * Author: Abdushakoor Abdelazeem Mosa Tom (Shoukrey Tom)\n" +
                " * Country: Sudan\n" +
                " * Date: 13 Dec 2019\n" +
                " * Purpose: Educational Purpose\n" +
                " * Licenses: no license";
        TextArea area = new TextArea(text);
        area.setStyle("-fx-background-color: #F7F5DF;");
        area.setFont(Font.font("New Times Roman", FontWeight.BOLD , 18));
        area.setEditable(false);
        pane.setCenter(area);
        stage.setScene(new Scene(pane));
        stage.initOwner(Main.stage);
        stage.show();
    }

    private StringBuilder readFile(File file) {
        StringBuilder sb = new StringBuilder();
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            int c;
            while ((c = in.read()) != -1) {
                sb.append((char) c);
            }
        } catch (Exception e) {
            return null;
        }
        return sb;
    }

    private void writeFile(File file, TextArea screen) {
        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
            if (screen.getText() != null) {
                byte[] bytes = screen.getText().getBytes();
                out.write(bytes);
            } else {
                out.writeUTF("");
            }
            out.close();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
