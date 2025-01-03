package album.controleur;

import java.io.IOException;

import album.structure.*;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GlobalOverviewController {

    @FXML private FlowPane flowPane;
    
    private Album album;
    private Scene scene;

    public GlobalOverviewController() {}

    public GlobalOverviewController(Album album,Scene scene){
        this.album = album;
        this.scene = scene;
    }

    public void refresh() {
        flowPane.getChildren().clear();

        for (Page page : album.getPages()){

            HBox hbox = new HBox();
            hbox.setSpacing(2);

            if (page.getLeft() != null){
                ImageView img = new ImageView(new Image(page.getLeft().getImagePath()));
                img.setPreserveRatio(true);
                img.setFitWidth(280);

                hbox.getChildren().add(img);
            }
            

            if (page.getRight() != null){
                ImageView img = new ImageView(new Image(page.getRight().getImagePath()));
                img.setPreserveRatio(true);
                img.setFitWidth(280);

                hbox.getChildren().add(img);
            }

            VBox vbox = new VBox();
            vbox.setSpacing(5);
            vbox.setAlignment(Pos.CENTER);

            vbox.getChildren().addAll(hbox, new Label("Page "+album.getPages().indexOf(page)));

            flowPane.getChildren().add(vbox);
            
        }        
    }

    @FXML 
    public void handleBack() throws IOException{
        Stage stage = (Stage) flowPane.getScene().getWindow();  
        stage.setScene(scene); 
    }
}

