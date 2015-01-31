package com.lksoft.nyaage.editor;

import com.lksoft.nyaage.editor.gametree.RoomTreeItem;
import com.lksoft.nyaage.editor.paint.ColorMap;
import com.lksoft.nyaage.editor.paint.FillPaintTool;
import com.lksoft.nyaage.editor.paint.FreeHandPaintTool;
import com.lksoft.nyaage.editor.paint.LinePaintTool;
import com.lksoft.nyaage.editor.paint.PaintTool;
import com.lksoft.nyaage.editor.paint.RectanglePaintTool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

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

    // Tools group
    ToggleGroup paintTools;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        paintTools = new ToggleGroup();
        freeHandButton.setToggleGroup(paintTools);
        lineButton.setToggleGroup(paintTools);
        fillButton.setToggleGroup(paintTools);
        rectangleButton.setToggleGroup(paintTools);

        paintTools.selectToggle(freeHandButton);

        //Combo
        areaCombo.setItems(FXCollections.observableArrayList(1, 2, 3));
        areaCombo.setValue(1);
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
            walkAreasLayer.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Color color = ColorMap.getColor((Integer)areaCombo.getValue());
                    PaintTool tool = ((PaintTool)paintTools.getSelectedToggle().getUserData());
                    tool.onMouseClicked(color, (int)event.getX(), (int)event.getY());
                }
            });
            walkAreasLayer.setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Color color = ColorMap.getColor((Integer)areaCombo.getValue());
                    PaintTool tool = ((PaintTool)paintTools.getSelectedToggle().getUserData());
                    tool.onMouseReleased(color, (int)event.getX(), (int)event.getY());
                }
            });
            walkAreasLayer.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Color color = ColorMap.getColor((Integer)areaCombo.getValue());
                    PaintTool tool = ((PaintTool)paintTools.getSelectedToggle().getUserData());
                    tool.onMouseDragged(color, (int)event.getX(), (int)event.getY());
                }
            });

            gc = bgLayer.getGraphicsContext2D();
            gc.setGlobalAlpha(0.5);
            gc.drawImage(areas, 0, 0);

            // Add to pane
            pane.getChildren().add(bgLayer);
            pane.getChildren().add(walkAreasLayer);
            scrollPane.setContent(pane);

            // Set tools
            freeHandButton.setUserData(new FreeHandPaintTool(walkAreasLayer.getGraphicsContext2D()));
            rectangleButton.setUserData(new RectanglePaintTool(walkAreasLayer.getGraphicsContext2D()));
            fillButton.setUserData(new FillPaintTool(walkAreasLayer.getGraphicsContext2D()));
            lineButton.setUserData(new LinePaintTool(walkAreasLayer.getGraphicsContext2D()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
