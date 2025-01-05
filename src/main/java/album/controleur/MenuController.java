package album.controleur;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import album.ObservableInterface;
import album.ObserverInterface;

import album.structure.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MenuController implements ObservableInterface{

    @FXML private MenuBar menuBar;
    @FXML private Menu otherAlbum;

    private List<ObserverInterface> obs = new ArrayList<>();
    private OverviewController oc;

    private Album album;

    public MenuController(){}

    public MenuController(Album album,OverviewController oc){
        this.album = album;
        this.oc = oc;
    }

    @FXML
    public void initialize() {
        ObservableList<String> albumNames = AlbumDB.loadAlbums();
        
        for (String albumName : albumNames) {
            MenuItem albumItem = new MenuItem(albumName);

            albumItem.setOnAction(event -> {

                asfForSave();
                album = AlbumDB.loadAlbum(albumName);
                notifyObserver();

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

    private void asfForSave(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous sauvegarder les modifications ?");
        alert.setContentText("Si vous quittez sans sauvegarder, les modifications seront perdues.");

        ButtonType yesButton = ButtonType.YES;
        ButtonType noButton = ButtonType.NO;
        alert.getButtonTypes().setAll(yesButton, noButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == yesButton) {
                AlbumDB.saveAlbum(album); 
                
            } 
        });
    }

    @FXML
    public void handleAcceuil() throws IOException{
        AlbumDB.saveAlbum(album);
        loadAcceuil();
    }
    
    @FXML 
    public void handleQuit(){
        asfForSave();
        Platform.exit(); 
    }

    @FXML 
    public void handleSetAlbumName(){
        askForAlbumName().ifPresent( newName -> album.setName(newName));
        //update
    }

    private Optional<String> askForAlbumName(){

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Nom de l'album");
        dialog.setHeaderText("Veuillez entrer le nom de l'album");
        dialog.setContentText("Nom de l'album :");
        dialog.initModality(Modality.APPLICATION_MODAL);
        
        return dialog.showAndWait();

    }

    @FXML 
    public void handleSave(){
        AlbumDB.saveAlbum(album);

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Sauvegarde effectuée");
        alert.setContentText("La sauvegarde de l'album a été effectuée.");

        alert.showAndWait();
    }

    @FXML 
    public void handleNewAlbum() throws IOException{
        asfForSave();
        String name = askForAlbumName().orElse(null);
        String path = askForPath();
        System.out.println("Path : "+path+ "name : "+name);
        album = new Album(name, path);
        notifyObserver();
    }
    
    private String askForPath(){
        DirectoryChooser directoryChooser = new DirectoryChooser();

        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        Stage stage = (Stage) menuBar.getScene().getWindow();  
        File selectedDirectory = directoryChooser.showDialog(stage);
        
        if (selectedDirectory != null) {
            return selectedDirectory.getAbsolutePath();
        } 
        return null;
    }

    @FXML
    public void handleGlobalOverview() throws IOException{
        oc.handleAll();
    }

    @FXML
    public void handleDel() throws IOException{
        AlbumDB.deleteAlbum(album.getName());
        loadAcceuil();
        
    }

    private void loadAcceuil() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Accueil.fxml"));
        BorderPane root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) menuBar.getScene().getWindow();
        
        stage.setScene(scene);  
        stage.setOnCloseRequest(null);
    }

}
