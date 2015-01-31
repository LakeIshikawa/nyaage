package com.lksoft.nyaage.editor;

import com.lksoft.nyaage.editor.gametree.RoomTreeItem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Created by lake on 15/01/12.
 */
public class RoomInspectorController implements Initializable {

    // Referenced room
    private RoomTreeItem room;

    @FXML
    TextField name;
    @FXML
    TextField bgAnimeTime;
    @FXML
    ImageView image;
    @FXML
    Label bgFile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void editWalkAreas(ActionEvent event) throws IOException {
        URL editor = getClass().getResource("/room_walkareas.fxml");
        FXMLLoader loader = new FXMLLoader(editor);
        Pane pane = loader.load();
        RoomWalkAreasController controller = loader.getController();

        // Set properties
        controller.setRoom(room);

        Stage stage = new Stage();
        stage.setTitle("Edit walk areas: "+room.getRoom().getName());
        stage.setScene(new Scene(pane));
        stage.show();
    }

    @FXML
    public void loadBg(ActionEvent event) throws IOException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open");
        chooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Image file", ".png", ".bmp"));
        File selected = chooser.showOpenDialog(null);
        if( selected == null ) return;

        // Copy image
        Project.getCurrent().copyTo("/image/bg/", selected);
        int dot = selected.getName().lastIndexOf(".");
        if( dot == -1 ) dot = selected.getName().length();
        String fname = selected.getName().substring(0, dot);
        String projPath = "/image/bg/"+selected.getName();

        image.setImage(new Image(new FileInputStream(Project.getCurrent().getResource(projPath))));
        room.getRoom().getBackgrounds().set(0, "bg/"+fname);
        bgFile.setText(room.getRoom().getBackgrounds().get(0));
    }

    /**
     * Set the room data to the view
     * @param room
     */
    public void setRoom(final RoomTreeItem room) throws FileNotFoundException {
        this.room = room;

        name.setText(room.getRoom().getName());
        room.valueProperty().bind(name.textProperty());
        name.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                room.getRoom().setName(newValue);
            }
        });

        bgAnimeTime.setText(String.valueOf(room.getRoom().getBgFrameTime()));
        bgAnimeTime.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    float parse = Float.parseFloat(newValue);
                    room.getRoom().setBgFrameTime(parse);
                } catch ( NumberFormatException e){

                }
            }
        });

        if( !room.getRoom().getBackgrounds().isEmpty() ) {
            File bg = Project.getCurrent().getResource("/image/"+room.getRoom().getBackgrounds().get(0)+".png");
            image.setImage(new Image(new FileInputStream(bg)));
            bgFile.setText(room.getRoom().getBackgrounds().get(0));
        }
    }
}
