package album.controleur;

import java.util.ArrayList;
import java.util.List;

import album.Album;
import album.ObservableInterface;
import album.ObserverInterface;
import album.Page;
import album.Photo;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
        album.addPhoto(new Photo("Left Page 1", "/images/test0.jpeg")); 
        album.addPhoto(new Photo("Right Page 1", "/images/test8.png"));
        album.addPhoto(new Photo("Left Page 2", "/images/test2.jpg"));
        album.addPhoto(new Photo("Right Page 2", "/images/test5.png"));
        album.addPhoto(new Photo("Left Page 3", "/images/test7.png")); 
        album.addPhoto(new Photo("Right Page 3", "/images/test9.png"));
        albumName.setText(album.getName());

        notifyObserver();
        refresh();
    }

    private void refresh() {
        Page currentPage = album.getCurrentPage();
        nameL.setText(currentPage.getLeft().getName());
        imageL.setImage(new Image(currentPage.getLeft().getImagePath()));

        if (currentPage.getRight() != null) {
            nameR.setText(currentPage.getRight().getName());
            imageR.setImage(new Image(currentPage.getRight().getImagePath()));
        }
    }

    @FXML 
    public void handleStart() {
        album.firstPage();
        refresh();
    }

    @FXML 
    public void handleBefore() {
        album.previousPage();
        refresh();
    }

    @FXML 
    public void handleAfter() {
        album.nextPage();
        refresh();
    }

    @FXML 
    public void handleEnd() {
        album.lastPage();
        refresh();
    }

    @FXML 
    public void handleAdd() {
        notifyObserver();
    }

    @FXML 
    public void handleDel() {
        notifyObserver();
    }
}
