package album.controleur;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import album.structure.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Pair;

public class AccueilController {

    @FXML private Button button;
    @FXML private Label label;
    @FXML private ListView<Pair<String, Integer>> albumListView;

    public AccueilController(){}

    @FXML
    public void initialize() {

        ObservableList<Pair<String, Integer>> albums = AlbumDB.loadAlbums();

        albumListView.getItems().clear();

        albumListView.setCellFactory(param -> new ListCell<Pair<String, Integer>>() {
            @Override
            protected void updateItem(Pair<String, Integer> item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getKey()); 
                } else {
                    setText(null);
                }
            }
        });


        albumListView.setItems(albums);

        albumListView.setOnMouseClicked(event -> {
            Pair<String, Integer> selectedAlbum = albumListView.getSelectionModel().getSelectedItem();
            if (selectedAlbum != null) {
                try {
                    gotoCreation(""+selectedAlbum.getValue());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    
    public Scene loadCreation(Album album) throws IOException {
        //Album album = albumTest();
        //Album album = AlbumDB.loadAlbum(albumName);
        FXMLLoader loader = new FXMLLoader();

        URL fxmlURL = getClass().getResource("/view/MainWindow.fxml");
        if (fxmlURL == null) {
            System.err.println("Could not find .fxml");
            System.exit(1);
        }
        
        MainWindowController mw = new MainWindowController();
        BrowseAlbumController ba = new BrowseAlbumController(album);
        OverviewController oc = new OverviewController(album,ba);
        RepositoryController rc = new RepositoryController(album,ba);
        MenuController mc = new MenuController(album,oc,rc);

        ba.addObserver(oc);
        
        mc.addObserver(ba);
        mc.addObserver(oc);
        mc.addObserver(rc);

        loader.setControllerFactory(controllerClass -> {
            if (controllerClass.equals(MainWindowController.class)) return mw;
            else if (controllerClass.equals(OverviewController.class)) return oc;
            else if (controllerClass.equals(BrowseAlbumController.class)) return ba;
            else if (controllerClass.equals(RepositoryController.class)) return rc;
            else if (controllerClass.equals(MenuController.class)) return mc;
            return null; 
        });

        
        loader.setLocation(fxmlURL);
        Parent root = loader.load();

        Stage stage = (Stage) label.getScene().getWindow();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if (album.isModified()){
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
                    });
                    Platform.exit(); 
                }
                
            }
        });

        return new Scene(root);
    }
    
    public void gotoCreation(String albumName) throws IOException{
        Stage stage = (Stage) button.getScene().getWindow();  
        Album album = AlbumDB.loadAlbum(albumName);

        String path = album.getDirectoryPath();
        File file = new File(path);
        
        if (!file.exists()) {

            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Information");
            alert.setHeaderText("Chemin invalide");
            alert.setContentText("Le chemin n'existe plus, renseigner en un nouveau");

            alert.showAndWait();

            String newPath = askForPath();
            album.setDirectoryPath(newPath);
        }
        stage.setScene(loadCreation(album));
        stage.setMaximized(true);
    }


    @FXML 
    public void handleNewAlbum() throws IOException{
        Stage stage = (Stage) button.getScene().getWindow();  

        String name = askForAlbumName();
        String path = askForPath();
        Album album = new Album(name, path);
        album.setModified(true);
        if (name != null && path != null) stage.setScene(loadCreation(album));
    }

    private String askForAlbumName(){

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Nom de l'album");
        dialog.setHeaderText("Veuillez entrer le nom de l'album");
        dialog.setContentText("Nom de l'album :");
        dialog.initModality(Modality.APPLICATION_MODAL);
        
        Optional<String> name = dialog.showAndWait();
    
        return name.orElse(null);
    }
    
    private String askForPath(){
        DirectoryChooser directoryChooser = new DirectoryChooser();

        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        Stage stage = (Stage) button.getScene().getWindow();  
        File selectedDirectory = directoryChooser.showDialog(stage);
        
        if (selectedDirectory != null) {
            return selectedDirectory.getAbsolutePath();
        } 
        return null;
    }
}
