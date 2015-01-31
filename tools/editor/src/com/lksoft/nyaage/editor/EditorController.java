package com.lksoft.nyaage.editor;

import com.badlogic.gdx.utils.Json;
import com.lksoft.nyaage.editor.gametree.GameTreeItem;
import com.lksoft.nyaage.editor.gametree.RoomTreeItem;
import com.lksoft.nyaage.editor.gametree.RoomsTreeItem;
import com.lksoft.nyaage.player.data.NyaGame;
import com.lksoft.nyaage.player.data.NyaRoom;

import org.controlsfx.dialog.Dialogs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

/**
 * Created by lake on 15/01/11.
 */
public class EditorController implements Initializable {

    @FXML
    TreeView gameTree;

    @FXML
    Pane inspector;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameTree.setShowRoot(false);

        // Selection listener
        gameTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (newValue == null) return;
                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                try {
                    setInspectorFor(selectedItem);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    public void newProject(ActionEvent event){
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("New project");
        File selected = chooser.showDialog(null);
        if( selected == null ) return;

        try {
            Project.newProject(selected);
        } catch (IOException e) {
            e.printStackTrace();
        }

        gameTree.setRoot(new GameTreeItem(Project.getCurrent().getGame()));
    }

    @FXML
    public void open(ActionEvent event){
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open");
        chooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Nya game file", ".nya"));
        File selected = chooser.showOpenDialog(null);
        if( selected == null ) return;

        Project.open(selected);

        gameTree.setRoot(new GameTreeItem(Project.getCurrent().getGame()));
    }

    @FXML
    public void save(ActionEvent event){
        NyaGame game = ((GameTreeItem)gameTree.getRoot()).getGame();

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save");
        chooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Nya game file", ".nya"));
        File selected = chooser.showSaveDialog(null);
        if( selected == null ) return;

        Json json = new Json();
        json.setIgnoreUnknownFields(true);
        String saveString = json.toJson(game);

        // Write the file
        try {
            FileOutputStream fos = new FileOutputStream(selected);
            fos.write(saveString.getBytes("UTF-8"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void exit(ActionEvent event){
        System.exit(0);
    }

    @FXML
    public void addRoom() {
        GameTreeItem root = (GameTreeItem) gameTree.getRoot();
        RoomsTreeItem rooms = (RoomsTreeItem) root.getChildren().get(0);

        NyaRoom room = new NyaRoom();
        room.setName("New room");
        rooms.getChildren().add(new RoomTreeItem(room));
    }

    @FXML
    public void addCharacter() {

    }

    @FXML
    public void remove() {

    }

    /**
     * Set the inspector the the specified item
     * @param selectedItem
     */
    private void setInspectorFor(TreeItem<String> selectedItem) throws IOException {
        if( selectedItem instanceof RoomTreeItem ){
            setRoomInspector((RoomTreeItem) selectedItem);
        }
    }

    /**
     * Set the inspector for specified room
     * @param selectedItem
     */
    private void setRoomInspector(RoomTreeItem selectedItem) throws IOException {
        URL roomInspector = getClass().getResource("/room_inspector.fxml");
        FXMLLoader loader = new FXMLLoader(roomInspector);
        Pane pane = loader.load();
        RoomInspectorController controller = loader.getController();

        // Set properties
        controller.setRoom(selectedItem);

        inspector.getChildren().clear();
        inspector.getChildren().addAll(pane.getChildren());
    }


}
