package album.controleur;

import album.Album;
import album.ObserverInterface;
import album.Photo;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

public class OverviewController implements ObserverInterface {

    @FXML private FlowPane flowPane;
    
    private Album album;

    public OverviewController(Album album){
        this.album = album;
    }

    public void initialize() {
        if (flowPane == null) {
            System.err.println("FlowPane is null!");
        }
    }

    public void update() {
        if (flowPane != null){
            flowPane.getChildren().clear();

            for (Photo photo : album.getPhotos()){
                flowPane.getChildren().add(new ImageView(photo.getImage()));
            }
        }
        
    }
}

