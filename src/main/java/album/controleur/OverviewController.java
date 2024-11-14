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

    public OverviewController() {}

    public OverviewController(Album album){
        this.album = album;
    }

    public void update() {
        flowPane.getChildren().clear();

        for (Photo photo : album.getPhotos()){
            ImageView img = new ImageView(photo.getImage());
            img.setPreserveRatio(true);
            img.setFitWidth(300);

            flowPane.getChildren().add(img);
        }
        
    }
}

