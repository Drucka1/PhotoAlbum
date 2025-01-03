package album.controleur;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import album.Album;
import album.ObservableInterface;
import album.ObserverInterface;
import album.Page;
import album.Photo;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class BrowseAlbumController implements ObservableInterface {
    @FXML private Label albumName;
    @FXML private Label nameL;
    @FXML private ImageView imageL;
    @FXML private Label nameR;
    @FXML private ImageView imageR;
    @FXML private Label progression;

    private List<ObserverInterface> obs = new ArrayList<>();
    private Album album;

    public BrowseAlbumController(){
        this.album = new Album(null);
    }

    public BrowseAlbumController(Album album){
        this.album = album;
    }

    public void addObserver(ObserverInterface observer){
        obs.add(observer);
    }

    public void notifyObserver(){
        for (ObserverInterface observer : obs){
            observer.update();
        }
    }

    @FXML
    public void initialize() {
        albumName.setText(album.getName());
        progression.setText("Page "+(album.currentIndex()+1)+" sur "+album.size());
        refresh();
        notifyObserver();
    }

    private void refresh() {
        Page currentPage = album.getCurrentPage();

        if (currentPage.getLeft() != null) {
            nameL.setText(currentPage.getLeft().getName());
            imageL.setImage(new Image(currentPage.getLeft().getImagePath()));
        }
        else {
            nameL.setText(null);
            imageL.setImage(null);
        }

        if (currentPage.getRight() != null) {
            nameR.setText(currentPage.getRight().getName());
            imageR.setImage(new Image(currentPage.getRight().getImagePath()));
        }
        else {
            nameR.setText(null);
            imageR.setImage(null);
        }
    }

    @FXML 
    public void handleStart() {
        album.firstPage();
        progression.setText("Page "+(album.currentIndex()+1)+" sur "+album.size());
        refresh();
    }

    @FXML 
    public void handleBefore() {
        album.previousPage();
        progression.setText("Page "+(album.currentIndex()+1)+" sur "+album.size());
        refresh();
    }

    @FXML 
    public void handleAfter() {
        album.nextPage();
        progression.setText("Page "+(album.currentIndex()+1)+" sur "+album.size());
        refresh();
    }

    @FXML 
    public void handleEnd() {
        album.lastPage();
        progression.setText("Page "+(album.currentIndex()+1)+" sur "+album.size());
        refresh();
    }

    @FXML 
    public void handleAddLeft() {

        FileChooser fileChooser = new FileChooser();
        
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif");
        fileChooser.getExtensionFilters().add(imageFilter);

        File file = fileChooser.showOpenDialog(null); 

        if (file != null) {

            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Nom de l'image");
            dialog.setHeaderText("Veuillez entrer le nom de l'image à gauche.");
            dialog.setContentText("Nom de l'image :");

            
            dialog.showAndWait().ifPresent(imageName -> {
                Image image = new Image(file.toURI().toString());

                Page page = album.getCurrentPage();
                page.setLeft(new Photo(imageName, image.getUrl()));

                imageL.setImage(image);
                nameL.setText(imageName); 
            });
        }
        refresh();
        notifyObserver();
    }

    @FXML 
    public void handleDelLeft() {
        Page page = album.getCurrentPage();
        page.setLeft(null);
        refresh();
        notifyObserver();
    }

    @FXML 
    public void handleAddRight() {
        
        FileChooser fileChooser = new FileChooser();
        
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif");
        fileChooser.getExtensionFilters().add(imageFilter);

        File file = fileChooser.showOpenDialog(null); 

        if (file != null) {

            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Nom de l'image");
            dialog.setHeaderText("Veuillez entrer le nom de l'image à gauche.");
            dialog.setContentText("Nom de l'image :");

            
            dialog.showAndWait().ifPresent(imageName -> {
                Image image = new Image(file.toURI().toString());

                Page page = album.getCurrentPage();
                page.setRight(new Photo(imageName, image.getUrl()));

                imageR.setImage(image);
                nameR.setText(imageName); 
            });
        }
        refresh();
        notifyObserver();
    }

    @FXML 
    public void handleDelRight() {
        Page page = album.getCurrentPage();
        page.setRight(null);
        refresh();
        notifyObserver();
    }

    @FXML 
    public void handleNewPage() {
        album.addPages(new Page(null));
        album.lastPage();

        progression.setText("Page "+(album.currentIndex()+1)+" sur "+album.size());
        refresh();
        notifyObserver();
    }
}
