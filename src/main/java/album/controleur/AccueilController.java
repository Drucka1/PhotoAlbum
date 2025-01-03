package album.controleur;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import album.structure.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class AccueilController {

    @FXML private Button button;
    @FXML private Label label;
    @FXML private ListView<String> albumListView;

    public AccueilController(){}

    @FXML
    public void initialize() {

        ObservableList<String> albums = loadAlbums();

        albumListView.getItems().clear();
        albumListView.setItems(albums);

        albumListView.setOnMouseClicked(event -> {
            String selectedAlbum = albumListView.getSelectionModel().getSelectedItem();
            if (selectedAlbum != null) {
                try {
                    gotoCreation(selectedAlbum);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    
    private Scene loadCreation(Album album, String directoryPath) throws IOException {
        //Album album = albumTest();
        //Album album = AlbumDB.loadAlbum(albumName);
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

    

    private ObservableList<String> loadAlbums() {
        // Simuler le chargement des albums depuis un répertoire
        File albumDir = new File("./src/main/resources/albums/");
        File[] files = albumDir.listFiles(); 
        System.out.println(files);

        ObservableList<String> albumNames = FXCollections.observableArrayList();
        if (files != null) {
            for (File file : files) {
                albumNames.add(file.getName()); 
            }
        }
        return albumNames;
    }
    
    private void gotoCreation(String albumName) throws IOException{
        String path = askForPath();
        Stage stage = (Stage) button.getScene().getWindow();  
        if (path != null){
            Album album = AlbumDB.loadAlbum(albumName);
            stage.setScene(loadCreation(album,path));
        }
        else {
            label.setText("Aucun répertoire sélectionné");
        }

    }


    @FXML 
    public void handleNewAlbum() throws IOException{
        Stage stage = (Stage) button.getScene().getWindow();  

        String name = askForAlbumName();
        String path = askForPath();

        if (name != null && path != null)  stage.setScene(loadCreation(new Album(name), path));
    }

    private String askForAlbumName(){

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Nom de l'album");
        dialog.setHeaderText("Veuillez entrer le nom de l'album");
        dialog.setContentText("Nom de l'album :");

        
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
