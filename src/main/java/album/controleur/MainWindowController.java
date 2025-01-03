package album.controleur;

import java.io.IOException;

import album.structure.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainWindowController {

    @FXML private Button acceuil;

    private Album album;

    public MainWindowController(){}

    public MainWindowController(Album album){
        this.album = album;
    }

    @FXML
    public void gotoAcceuil() throws IOException{
        AlbumDB.saveAlbum(album);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Accueil.fxml"));
        BorderPane root = loader.load();

        Scene scene = new Scene(root);

        Stage stage = (Stage) acceuil.getScene().getWindow();  
        
        stage.setScene(scene);  
    }

}
