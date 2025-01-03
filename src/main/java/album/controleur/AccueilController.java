package album.controleur;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import album.structure.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class AccueilController {

    @FXML private Button button;
    @FXML private Label label;


    public AccueilController(){}

    public void gotoCreation() throws IOException{
        DirectoryChooser directoryChooser = new DirectoryChooser();

        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        Stage stage = (Stage) button.getScene().getWindow();  
        File selectedDirectory = directoryChooser.showDialog(stage);
        
        if (selectedDirectory != null) {
            stage.setScene(loadCreation(selectedDirectory.getAbsolutePath()));
        } else {
            label.setText("Aucun répertoire sélectionné");
        }

    }

    private Scene loadCreation(String directoryPath) throws IOException {
        //Album album = albumTest();
        Album album = AlbumDB.loadAlbum("My Album");
        FXMLLoader loader = new FXMLLoader();

        URL fxmlURL = getClass().getResource("/view/MainWindow.fxml");
        if (fxmlURL == null) {
            System.err.println("Could not find .fxml");
            System.exit(1);
        }
        
        MainWindowController mw = new MainWindowController(album);
        OverviewController oc = new OverviewController(album);
        BrowseAlbumController ba = new BrowseAlbumController(album,directoryPath);
        RepositoryController rc = new RepositoryController(directoryPath);
        ba.addObserver(oc);

        loader.setControllerFactory(controllerClass -> {
            if (controllerClass.equals(MainWindowController.class)) return mw;
            else if (controllerClass.equals(OverviewController.class)) return oc;
            else if (controllerClass.equals(BrowseAlbumController.class)) return ba;
            else if (controllerClass.equals(RepositoryController.class)) return rc;
            return null; 
        });

        
        loader.setLocation(fxmlURL);
        Parent root = loader.load();

        return new Scene(root);
    }

    private Album albumTest(){
        Album album = new Album("My Album");
        album.addPhoto(new Photo("Left Page 1", "/images/test0.jpeg")); 
        album.addPhoto(new Photo("Right Page 1", "/images/test8.png"));
        album.addPhoto(new Photo("Left Page 2", "/images/test2.jpg"));
        album.addPhoto(new Photo("Right Page 2", "/images/test5.png"));
        album.addPhoto(new Photo("Left Page 3", "/images/test7.png")); 
        album.addPhoto(new Photo("Right Page 3", "/images/test9.png"));
        return album;

    }
}
