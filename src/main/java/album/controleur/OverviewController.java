package album.controleur;

import album.ObserverInterface;
import album.structure.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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

        for (Page page : album.getPages()){

            if (page.getLeft() != null){
                ImageView img = new ImageView(new Image(page.getLeft().getImagePath()));
                img.setPreserveRatio(true);
                img.setFitWidth(280);

                flowPane.getChildren().add(new Label(page.getLeft().getName()));
                flowPane.getChildren().add(img);
            }
            

            if (page.getRight() != null){
                ImageView img = new ImageView(new Image(page.getRight().getImagePath()));
                img.setPreserveRatio(true);
                img.setFitWidth(280);

                flowPane.getChildren().add(new Label(page.getRight().getName()));
                flowPane.getChildren().add(img);
            }
            
        }
        
    }
}

