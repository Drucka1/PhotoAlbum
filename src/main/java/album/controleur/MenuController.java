package album.controleur;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import album.ObservableInterface;
import album.ObserverInterface;

import album.structure.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MenuController implements ObservableInterface{

    @FXML private MenuBar menuBar;
    @FXML private Menu otherAlbum;

    private List<ObserverInterface> obs = new ArrayList<>();

    private Album album;

    public MenuController(){}

    public MenuController(Album album){
        this.album = album;
    }

    @FXML
    public void initialize() {
        ObservableList<String> albumNames = AlbumDB.loadAlbums();
        
        for (String albumName : albumNames) {
            MenuItem albumItem = new MenuItem(albumName);

            albumItem.setOnAction(event -> {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Voulez-vous sauvegarder les modifications ?");
                alert.setContentText("Si vous quittez sans sauvegarder, les modifications seront perdues.");

                // Affiche les boutons "Oui" et "Non"
                ButtonType yesButton = ButtonType.YES;
                ButtonType noButton = ButtonType.NO;
                alert.getButtonTypes().setAll(yesButton, noButton);

                alert.showAndWait().ifPresent(response -> {
                    if (response == yesButton) {
                        AlbumDB.saveAlbum(album); 
                        
                    } 
                    album = AlbumDB.loadAlbum(albumName);
                    notifyObserver();
                });


            });

            otherAlbum.getItems().add(albumItem);
        }

    }

    public void addObserver(ObserverInterface observer){
        obs.add(observer);
    }

    public void notifyObserver(){
        for (ObserverInterface observer : obs){
            observer.changeAlbum(album);
        }
    }

    @FXML
    public void gotoAcceuil() throws IOException{
        AlbumDB.saveAlbum(album);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Accueil.fxml"));
        BorderPane root = loader.load();

        Scene scene = new Scene(root);

        Stage stage = (Stage) menuBar.getScene().getWindow();
        
        stage.setScene(scene);  
    }
    
    @FXML 
    public void handleQuit(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous sauvegarder les modifications ?");
        alert.setContentText("Si vous quittez sans sauvegarder, les modifications seront perdues.");

        // Affiche les boutons "Oui" et "Non"
        ButtonType yesButton = ButtonType.YES;
        ButtonType noButton = ButtonType.NO;
        alert.getButtonTypes().setAll(yesButton, noButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == yesButton) {
                AlbumDB.saveAlbum(album); 
                Platform.exit(); 
            } else {
                Platform.exit(); 
            }
        });
    }

}
