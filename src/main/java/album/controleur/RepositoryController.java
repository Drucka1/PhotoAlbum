package album.controleur;

import album.structure.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class RepositoryController {

    private static final String[] EXTENSIONS = {".jpg", ".jpeg", ".png", ".gif", ".bmp", ".tiff"};
    
    @FXML private FlowPane flowPane;
    private Album album;

    private BrowseAlbumController browseAlbumController;

    public RepositoryController(){}

    public RepositoryController(Album album,BrowseAlbumController browseAlbumController){
        this.album = album;
        this.browseAlbumController = browseAlbumController;
    }

    @FXML
    public void initialize() {
        if (album.getDirectoryPath() != null) refresh();
    }

    @FXML
    public void refresh(){
        flowPane.getChildren().clear();
        for (File image : getImagesFromDirectory()){
            String imagePath = image.toURI().toString(); 
            System.err.println(imagePath);

            ImageView imageView = new ImageView(new Image(imagePath));
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(140);

            MenuItem addLeft = new MenuItem("Add left");
            MenuItem addRight = new MenuItem("Add right");

            ContextMenu contextMenu = new ContextMenu(addLeft, addRight);

            addLeft.setOnAction(e -> browseAlbumController.handleAddLeft(imagePath));
            addRight.setOnAction(e -> browseAlbumController.handleAddRight(imagePath));

            imageView.setOnContextMenuRequested(event -> {
                contextMenu.show(imageView, event.getScreenX(), event.getScreenY());
            });


            VBox vbox = new VBox();
            vbox.getChildren().addAll(imageView,new Text(image.getName()));
            flowPane.getChildren().add(vbox);
        }
    }

    @FXML
    public void changeDirectory(){
        DirectoryChooser directoryChooser = new DirectoryChooser();

        directoryChooser.setInitialDirectory(new File(album.getDirectoryPath()));

        Stage stage = (Stage) flowPane.getScene().getWindow();  
        File selectedDirectory = directoryChooser.showDialog(stage);
        
        if (selectedDirectory != null) setDirectoryPath(selectedDirectory.getAbsolutePath());
    }

    public void setDirectoryPath(String newDirectoryPath){
        album.setDirectoryPath(newDirectoryPath);
        refresh();
    }

    private List<File> getImagesFromDirectory() {
        File directory = new File(album.getDirectoryPath());
        List<File> imageFiles = new ArrayList<>();

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && isImage(file)) {
                        imageFiles.add(file);
                    }
                }
            }
        } else {
            System.out.println("Le répertoire n'existe pas ou n'est pas valide.");
        }

        return imageFiles;
    }

    private boolean isImage(File file){
        for (String ext : EXTENSIONS){
            if (file.getName().toLowerCase().endsWith(ext)){
                return true;
            }
        }
        return false;
    }
}