package album.controleur;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import album.ObservableInterface;
import album.ObserverInterface;
import album.structure.*;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;

public class BrowseAlbumController implements ObservableInterface,ObserverInterface {
    @FXML private Label albumName;
    @FXML private Label nameL;
    @FXML private ImageView imageL;
    @FXML private Label nameR;
    @FXML private ImageView imageR;
    @FXML private Label progression;

    private List<ObserverInterface> obs = new ArrayList<>();
    private Album album;

    public BrowseAlbumController(){
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

    public void changeAlbum(Album album){
        this.album = album;
        update();
    }

    @FXML
    public void initialize() {
        albumName.setText(album.getName());
        progression.setText("Page "+(album.currentIndex()+1)+" sur "+album.size());
        update();
        notifyObserver();
    }

    public void update() {
        albumName.setText(album.getName());
        Page currentPage = album.getCurrentPage();

        if (currentPage.getLeft() != null) {
            nameL.setText(currentPage.getLeft().getName());
            imageL.setImage(new Image(currentPage.getLeft().getImagePath()));
            
            
            MenuItem del = new MenuItem("Delete");
            MenuItem changeName = new MenuItem("Change name");

            ContextMenu contextMenu = new ContextMenu(del, changeName);

            del.setOnAction(e -> handleDelLeft());
            changeName.setOnAction(e -> changeNameLeft());

            imageL.setOnContextMenuRequested(event -> {
                contextMenu.show(imageL, event.getScreenX(), event.getScreenY());
            });
        }
        else {
            nameL.setText(null);
            imageL.setImage(null);
        }

        if (currentPage.getRight() != null) {
            nameR.setText(currentPage.getRight().getName());
            imageR.setImage(new Image(currentPage.getRight().getImagePath()));

            MenuItem del = new MenuItem("Delete");
            MenuItem changeName = new MenuItem("Change name");

            ContextMenu contextMenu = new ContextMenu(del, changeName);

            del.setOnAction(e -> handleDelRight());
            changeName.setOnAction(e -> changeNameRight());

            imageR.setOnContextMenuRequested(event -> {
                contextMenu.show(imageR, event.getScreenX(), event.getScreenY());
            });
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
        update();
    }

    @FXML 
    public void handleBefore() {
        album.previousPage();
        progression.setText("Page "+(album.currentIndex()+1)+" sur "+album.size());
        update();
    }

    @FXML 
    public void handleAfter() {
        album.nextPage();
        progression.setText("Page "+(album.currentIndex()+1)+" sur "+album.size());
        update();
    }

    @FXML 
    public void handleEnd() {
        album.lastPage();
        progression.setText("Page "+(album.currentIndex()+1)+" sur "+album.size());
        update();
    }

    public void handleAddLeft(String imagePath) {

        askForPhotoName().ifPresent(imageName -> {
            Image image = new Image(imagePath);

            album.setLeft(new Photo(imageName, image.getUrl()));

            imageL.setImage(image);
            nameL.setText(imageName); 
        });
        
        update();
        notifyObserver();
    }

    public void handleDelLeft() {
        album.setLeft(null);
        update();
        notifyObserver();
    }

    public void handleAddRight(String imagePath) {

        askForPhotoName().ifPresent(imageName -> {
            Image image = new Image(imagePath);

            album.setRight(new Photo(imageName, image.getUrl()));

            imageR.setImage(image);
            nameR.setText(imageName); 
        });

        update();
        notifyObserver();
    }

    public void handleDelRight() {
        album.setRight(null);
        update();
        notifyObserver();
    }

    @FXML 
    public void handleNewPage() {
        album.addPage(new Page(null));
        album.lastPage();

        progression.setText("Page "+(album.currentIndex()+1)+" sur "+album.size());
        update();
        notifyObserver();
    }

    @FXML 
    public void handleDelPage(){
        album.removePage();
        progression.setText("Page "+(album.currentIndex()+1)+" sur "+album.size());
        update();
        notifyObserver();
    }

    public Optional<String> askForPhotoName(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Nom de l'image");
        dialog.setHeaderText("Veuillez entrer le nom de l'image de droite.");
        dialog.setContentText("Nom de l'image :");
        dialog.initModality(Modality.APPLICATION_MODAL);

        return dialog.showAndWait();
    }

    public void changeNameLeft(){
        askForPhotoName().ifPresent(imageName -> {
            nameL.setText(imageName);
        });
        update();
        notifyObserver();
    }

    public void changeNameRight(){
        askForPhotoName().ifPresent(imageName -> {
            nameR.setText(imageName);
        });
        update();
        notifyObserver();
    }
}
