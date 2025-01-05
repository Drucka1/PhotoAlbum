package album.controleur;

import java.io.IOException;

import album.ObserverInterface;
import album.structure.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OverviewController implements ObserverInterface {

    @FXML private FlowPane flowPane;
    
    private Album album;


    private BrowseAlbumController ba;

    public OverviewController() {}

    public OverviewController(Album album, BrowseAlbumController ba){
        this.album = album;
        this.ba = ba;
    }

    public void changeAlbum(Album album){
        this.album = album;
        update();
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

    @FXML 
    public void handleAll() throws IOException{

        AlbumDB.saveAlbum(album);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/GlobalOverview.fxml"));

        GlobalOverviewController go = new GlobalOverviewController(album,flowPane.getScene(),ba);
        loader.setControllerFactory(controllerClass -> go);


        VBox root = loader.load();

        Scene scene = new Scene(root);

        Stage stage = (Stage) flowPane.getScene().getWindow();  
        go.refresh();
        stage.setScene(scene); 

    }
}

