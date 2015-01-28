package com.lksoft.nyaage.editor;

import com.lksoft.nyaage.editor.gametree.RoomTreeItem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

/**
 * Created by lake on 15/01/13.
 */
public class RoomWalkAreasController implements Initializable {
    // The room
    private RoomTreeItem room;

    @FXML
    ScrollPane scrollPane;

    @FXML
    ToggleButton freeHandButton;
    @FXML
    ToggleButton lineButton;
    @FXML
    ToggleButton fillButton;
    @FXML
    ToggleButton rectangleButton;

    @FXML
    ChoiceBox areaCombo;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup group = new ToggleGroup();
        freeHandButton.setToggleGroup(group);
        lineButton.setToggleGroup(group);
        fillButton.setToggleGroup(group);
        rectangleButton.setToggleGroup(group);

        group.selectToggle(freeHandButton);
    }

    /**
     * Set the room
     * @param room
     */
    public void setRoom(RoomTreeItem room) {
        this.room = room;

        try {
            File bgBmp = Project.getCurrent().getResource("/image/"+room.getRoom().getBackgrounds().get(0)+".png");
            String walkMapName = room.getRoom().getWalkMap().substring(0, room.getRoom().getWalkMap().lastIndexOf('.'));
            File walkAreasBmp = Project.getCurrent().getResource("/"+walkMapName+".bmp");
            Image bg = new Image(new FileInputStream(bgBmp));
            Image areas = new Image(new FileInputStream(walkAreasBmp));

            Pane pane = new Pane();

            // Bg
            Canvas bgLayer = new Canvas(bg.getWidth(), bg.getHeight());
            GraphicsContext gc = bgLayer.getGraphicsContext2D();
            gc.drawImage(bg, 0, 0);

            // Walk area
            Canvas walkAreasLayer = new Canvas(bg.getWidth(), bg.getHeight());
            gc = bgLayer.getGraphicsContext2D();
            gc.setGlobalAlpha(0.5);
            gc.drawImage(areas, 0, 0);

            // Add to pane
            pane.getChildren().add(bgLayer);
            pane.getChildren().add(walkAreasLayer);
            scrollPane.setContent(pane);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
