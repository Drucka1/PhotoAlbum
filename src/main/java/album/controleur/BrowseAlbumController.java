package album.controleur;

import java.util.ArrayList;
import java.util.List;

import album.Album;
import album.ObservableInterface;
import album.ObserverInterface;
import album.Photo;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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

    public BrowseAlbumController(){}

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
        album.addPhoto("Left Page 1", "/images/test.jpeg"); 
        album.addPhoto("Right Page 1", "/images/test2.jpg");
        album.addPhoto("Left Page 2", "/images/test2.jpg");
        album.addPhoto("Right Page 2", "/images/test.jpeg");
        album.addPhoto("Left Page 3", "/images/test.jpeg"); 
        album.addPhoto("Right Page 3", "/images/test2.jpg");

        notifyObserver();
        update();
    }

    private void update() {
        Photo currentPhoto = album.getCurrentPhoto();
        nameL.setText(currentPhoto.getName());
        imageL.setImage(currentPhoto.getImage());

        album.nextPage();
        Photo nextPhoto = album.getCurrentPhoto();
        if (nextPhoto != currentPhoto) {
            nameR.setText(nextPhoto.getName());
            imageR.setImage(nextPhoto.getImage());
        }
        else {
            nameR.setText("");
            imageR.setImage(null);
        }
        album.previousPage();
    }

    @FXML 
    public void handleStart() {
        album.firstPage();
        update();
    }

    @FXML 
    public void handleBefore() {
        album.previousPage();
        update();
    }

    @FXML 
    public void handleAfter() {
        album.nextPage();
        update();
    }

    @FXML 
    public void handleEnd() {
        album.lastPage();
        update();
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
